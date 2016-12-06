package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

    SpriteBatch batch;
    Texture backGround;
    Texture welcome;
    Texture gameOver;
    Texture levelComplete;
    UserCar userCar;
    Texture board;
    GameState gameState = GameState.WelcomePage;
    float userCarPositionX = 550f;
    float userCarPositionY = 620f;
    AiCar aiCar;
    Texture gpLogo;
    float aiCarPositionX = 450f;
    float aiCarPositionY = 660f;
    ArrayList<Obstacle> checkpoints;
    ArrayList<Obstacle> slowOnGrass = new ArrayList<Obstacle>();
    ArrayList<Obstacle> outSideItems = new ArrayList<Obstacle>();
    ArrayList<Obstacle> finishLine;
    int[] arr = new int[7];
    int numberOfLaps = 0;

    Obstacle checkpoint, checkpoint1, checkpoint2, checkpoint3, checkpoint4, checkpoint5, checkpoint6;

    Obstacle tire1, tire2, tire3, tire4, tree1, tree2, tree3, tree4;
    Obstacle grass1, grass2, grass3, grass4, grass5, grass6, grass7, grass8,
            grass9, grass10, grass11, grass12, grass13, grass14, grass15;

    Obstacle powerUp1, powerUp2, powerUp3, powerUp4;
    ArrayList<Obstacle> powerUps = new ArrayList<>(4);
    Obstacle finishLine1;
    private Music intro_music;
    private Music inGame_music;

    static CharSequence driver = " ";

    BitmapFont font;

    private enum GameState {
        WelcomePage,
        GamePage,
        GameOver
    }

    @Override
    public void create() {
        welcome = new Texture("WellComePage.jpg");
        batch = new SpriteBatch();
        backGround = new Texture("BackGround.jpg");
        levelComplete = new Texture("level.png");
        gameOver = new Texture("game-over.jpg");

        gpLogo = new Texture("Gplogo.png");
        board = new Texture("Board.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        intro_music = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
        inGame_music = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic1.mp3"));

        createUserCar();
        createAiCar();
        createCheckPoints();
        createObstacles();
        createGrass();
        createPowerUps();
    }

    public void createUserCar() {
        userCar = new UserCar("userCar1.png", userCarPositionX, userCarPositionY, 4);
    }

    public void createAiCar() {
        aiCar = new AiCar("AiCar1.png", aiCarPositionX, aiCarPositionY);
    }

    public void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            userCar.accelerate();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            userCar.breaks();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            userCar.turnLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            userCar.turnRight();
        }
        userCar.deceleration();
    }

    @Override
    public void render() {

        switch (gameState) {
            case WelcomePage:
                renderWelcomePage();
                break;

            case GamePage:
                renderGamePage();
                break;

            case GameOver:
                renderGameOver();
                break;
        }
    }

    public void renderWelcomePage() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(welcome, 0, 0);
        intro_music.play();
        //if enter key is pressed in the welcome page it will go to game page and stop the welcome page music
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            gameState = GameState.GamePage;
            intro_music.stop();
        }

        // if escape key was pressed in the welcome page it will got to the game over page
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            intro_music.stop();
            gameState = GameState.GameOver;
        }
        batch.end();
    }

    public void renderGamePage() {
        inGame_music.play();
        checkInput();
        createCheckPoints();
        createFinishLine();
        batch.begin();
        for (Obstacle grass : slowOnGrass) {

            grass.draw(batch);
        }
        for (Obstacle finishLinePoint : finishLine) {
            finishLinePoint.draw(batch);
        }
        batch.draw(backGround, 0, 0);
        batch.draw(gpLogo, 300, 450, 300, 100);
        batch.draw(board, 50, 20, 400, 200);
        font.draw(batch, driver, 70, 200);

        for (Obstacle checkpoint : checkpoints) {
            checkpoint.draw(batch);
        }
        userCar.getSprite().draw(batch);
        userCar.updatePosition();
        aiCar.getSprite().draw(batch);
        aiCar.updatePositionFromSpeed();
        aiCar.Route();

        for (Obstacle outSideItem : outSideItems) {
            outSideItem.draw(batch);
        }

        checkGrass(userCar);
        checkRoutePoints(userCar);
        checkObstacles(userCar);
        powerUp(userCar);

        for (Obstacle powerups : powerUps) {
            powerups.draw(batch);
        }
        // exit game
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            inGame_music.stop();
            gameState = GameState.GameOver;
        }
        if (userCar.collidesWith(finishLine.get(0).getCollisionRectangle()) && (numberOfLaps == 1)) {
            // gameState = GameState.GameOver;  // game state is level complete
            userCar.forceBreak();
            batch.draw(levelComplete, 250,355 );
        }
        batch.end();
    }

    public void renderGameOver() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(gameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        createUserCar();
        createAiCar();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameState = GameState.WelcomePage;
        }
        batch.end();
    }

    //Obstacle
    public void createObstacles() {
        tire1 = new Obstacle("Tire1.png", 410, 230, 90, 37);
        outSideItems.add(tire1);
        tire2 = new Obstacle("Tire2.png", 45, 380, 37, 200);
        outSideItems.add(tire2);
        tire3 = new Obstacle("Tire3.png", 400, 725, 400, 37);
        outSideItems.add(tire3);
        tire4 = new Obstacle("Tire1.png", 320, 230, 90, 37);
        outSideItems.add(tire4);
        tree1 = new Obstacle("tree.png", 1050, 350, 100, 100);
        outSideItems.add(tree1);
        tree2 = new Obstacle("Tree1.png", 650, 200, 200, 60);
        outSideItems.add(tree2);
        tree3 = new Obstacle("tree.png", 690, 450, 90, 80);
        outSideItems.add(tree3);
        tree4 = new Obstacle("tree.png", 690, 350, 90, 80);
        outSideItems.add(tree4);
    }

    public void createCheckPoints() {
        checkpoints = new ArrayList<Obstacle>();
        checkpoint = new Obstacle("wall_0.jpg", 450, 615, 5, 96);
        checkpoint1 = new Obstacle("wall_0.jpg", 800, 615, 5, 96);
        checkpoint2 = new Obstacle("wall_1.jpg", 550, 160, 96, 5);
        checkpoint3 = new Obstacle("wall_0.jpg", 800, 64, 5, 96);
        checkpoint4 = new Obstacle("wall_0.jpg", 300, 285, 5, 90);
        checkpoint6 = new Obstacle("wall_1.jpg", 900, 370, 96, 5);
        checkpoint5 = new Obstacle("wall_1.jpg", 105, 500, 96, 5);
        checkpoints.add(checkpoint);
        checkpoints.add(checkpoint1);
        checkpoints.add(checkpoint2);
        checkpoints.add(checkpoint3);
        checkpoints.add(checkpoint4);
        checkpoints.add(checkpoint5);
        checkpoints.add(checkpoint6);
    }

    public void createGrass() {
        grass1 = new Obstacle("grass.png", 0, 0, 510, 230);
        slowOnGrass.add(grass1);
        grass2 = new Obstacle("grass.png", 510, 0, 770, 55);
        slowOnGrass.add(grass2);
        grass3 = new Obstacle("grass.png", 1190, 55, 100, 700);
        slowOnGrass.add(grass3);
        grass4 = new Obstacle("grass.png", 640, 170, 380, 25);
        slowOnGrass.add(grass4);
        grass5 = new Obstacle("grass.png", 640, 260, 220, 100);
        slowOnGrass.add(grass5);
        grass6 = new Obstacle("grass.png", 780, 305, 80, 270);
        slowOnGrass.add(grass6);
        grass7 = new Obstacle("grass.png", 245, 390, 430, 210);
        slowOnGrass.add(grass7);
        grass8 = new Obstacle("grass.png", 680, 585, 340, 15);
        slowOnGrass.add(grass8);
        grass9 = new Obstacle("grass.png", 800, 725, 370, 20);
        slowOnGrass.add(grass9);
        grass10 = new Obstacle("grass.png", 0, 725, 380, 20);
        slowOnGrass.add(grass10);
        grass11 = new Obstacle("grass.png", 0, 590, 90, 130);
        slowOnGrass.add(grass11);
        grass12 = new Obstacle("grass.png", 0, 230, 90, 130);
        slowOnGrass.add(grass12);
        grass13 = new Obstacle("grass.png", 100, 240, 200, 40);
        slowOnGrass.add(grass13);
        grass14 = new Obstacle("grass.png", 1020, 300, 150, 40);
        slowOnGrass.add(grass14);
        grass15 = new Obstacle("grass.png", 1020, 430, 150, 40);
        slowOnGrass.add(grass15);
    }

    //If collides with Obstacle
    public void checkObstacles(UserCar userCar) {
        for (int i = 0; i < outSideItems.size(); i++) {
            if (userCar.collidesWith(outSideItems.get(i).getCollisionRectangle())) {
                userCar.fullStop();
            }
        }
    }

    public void checkGrass(UserCar userCar) {
        for (int i = 0; i < slowOnGrass.size(); i++) {
            if (userCar.collidesWith(slowOnGrass.get(i).getCollisionRectangle())) {
                userCar.slowOnGrass();
            }
        }
    }

    public void createFinishLine() {
        finishLine = new ArrayList<Obstacle>(1);
        finishLine1 = new Obstacle("wall_0.jpg", 550, 615, 5, 96);
        finishLine.add(finishLine1);

    }

    public void createPowerUps() {
        int upper = 3;
        int lower = 0;
        int r = (int) (Math.random() * (upper - lower)) + lower;

        switch (r) {
            case 0:
                powerUp1 = new Obstacle("coin.png", 400, 660, 20, 20);
                powerUps.add(powerUp1);
                break;
            case 1:
                powerUp2 = new Obstacle("coin.png", 400, 660, 20, 20);
                powerUps.add(powerUp2);
                break;
            case 2:
                powerUp3 = new Obstacle("coin.png", 200, 400, 20, 20);
                powerUps.add(powerUp3);
                break;
            case 3:
                powerUp4 = new Obstacle("coin.png", 100, 400, 20, 20);
                powerUps.add(powerUp4);
                break;
        }
    }

    public boolean powerUp(UserCar userCar) {
        for (int i = 0; i < powerUps.size(); i++) {
            if (userCar.collidesWith(powerUps.get(i).getCollisionRectangle())) {
                powerUps.remove(i);
                userCar.boost();
                return true;
            } else return false;
        }
        return false;
    }

    public boolean checkFinishLine(int[] arr) {
        if (checkArray(arr)) {
            if (userCar.collidesWith(finishLine.get(0).getCollisionRectangle())) {
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = 0;
                }
                System.out.println("done with lap");
                return true;
            }
        }
        return false;
    }

    public boolean checkArray(int[] arr) {
        if ((arr[0] == 1) && (arr[1] == 1) && (arr[2] == 1) && (arr[3] == 1) && (arr[4] == 1) && (arr[5] == 1) && (arr[6] == 1))
            return true;
        else return false;
    }

    public void checkRoutePoints(UserCar userCar) {

        for (int j = 0; j < checkpoints.size(); j++) {
            if (userCar.collidesWith(checkpoints.get(j).getCollisionRectangle())) {
                arr[j] = 1; // if the next is 0 set to zero else set one
                //		 to make sure the start last point is counted at the end not the first
            }
        }

        if (checkArray(arr)) {
            if (checkFinishLine(arr)) {
                numberOfLaps++;
                if (numberOfLaps == 1) {
                    System.out.println("done with 1 lap " + (numberOfLaps));
                    driver = "Finished 1 Lap";

                } else if (numberOfLaps == 2) {
                    System.out.println(" lap 2 " + (numberOfLaps));
                    driver = "Finished 2 Laps";

                } else if (numberOfLaps == 3) {
                    System.out.println(" lap 3 " + (numberOfLaps));
                    driver = "Finished 3 Laps";
                    // HERE IS WHERE THE CAR FINISH THE RACE
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        backGround.dispose();
        welcome.dispose();
        gameOver.dispose();
        intro_music.dispose();
        inGame_music.dispose();
    }
}
