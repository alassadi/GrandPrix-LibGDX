package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {


    SpriteBatch batch;
    Texture backGround;

    Texture backGround2;
    Texture backGroundLevel3;

    Texture welcome;
    Texture gameOver;
    Texture levelCompleted;
    UserCar userCar;

    UserCar userCar2;
    Texture board;
    GameState gameState = GameState.WelcomePage;

    UserCar userCar3;


    AiCar aiCar;
    Texture gpLogo;
    float userCarPositionX=550;
    float userCarPositionY=620;
    float aiCarPositionX = 450f;
    float aiCarPositionY = 660f;
    ArrayList<Obstacle> checkpoints;
    ArrayList<Obstacle> checkpoints2;
    ArrayList<Obstacle> checkpoints3;
    ArrayList<Obstacle> slowOnGrass = new ArrayList<Obstacle>();
    ArrayList<Obstacle> slowOnGrassLevel3 = new ArrayList<Obstacle>();
    ArrayList<Obstacle> outSideItems = new ArrayList<Obstacle>();
    ArrayList<Obstacle> outSideItemsLevel3=new ArrayList<Obstacle>();
    ArrayList<Obstacle> finishLine;
    ArrayList<Obstacle> finishLineLevel2;
    ArrayList<Obstacle> finishLineLevel3;

    int[] arr = new int[7];
    int[] arr2 = new int[8];
    int[] arr3 = new int[8];
    int numberOfLaps = 0;
    int numberOfLaps2 = 0;
    int numberOfLaps3 = 0;
    Timer timer;

    Timer timer2;

    Timer timer3;

    Obstacle checkpoint, checkpoint1, checkpoint2, checkpoint3, checkpoint4, checkpoint5, checkpoint6, checkpoint7;

    Obstacle tire1, tire2, tire3, tire4, tree1, tree2, tree3, tree4,tree5;
    Obstacle grass1, grass2, grass3, grass4, grass5, grass6, grass7, grass8,
            grass9, grass10, grass11, grass12, grass13, grass14, grass15,grass16;

    Obstacle powerUp1, powerUp2, powerUp3, powerUp4;
    ArrayList<Obstacle> powerUps = new ArrayList<Obstacle>(1);
    ArrayList<Obstacle> powerUpsLevel2 = new ArrayList<Obstacle>(1);
    ArrayList<Obstacle> powerUpsLevel3 = new ArrayList<Obstacle>(1);

    Obstacle finishLine1,finishLine3, finishLine2;
    private Music intro_music;
    private Music powerUpEffect;
    private Music inGame_music;

    static CharSequence driver = " ";
    static CharSequence powerUpFont = " ";

    BitmapFont font;
    BitmapFont redFont;
    BitmapFont bitmapFontFinishTime;


    private enum GameState {
        WelcomePage,
        GamePage,
        LevelCompleted,
        Level2,
        Level2Completed,
        Level3,
        Level3Completed,

        GameOver
    }

    @Override
    public void create() {
        welcome = new Texture("WellComePage.jpg");
        batch = new SpriteBatch();
        backGround = new Texture("BackGround.jpg");

        backGround2 = new Texture("BackGround2.jpg");

        backGroundLevel3=new Texture("BackGroundLevel3.jpg");

        levelCompleted = new Texture("level.png");
        gameOver = new Texture("game-over.jpg");

        gpLogo = new Texture("Gplogo.png");
        board = new Texture("Board.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        redFont = new BitmapFont();
        redFont.setColor(Color.RED);
        bitmapFontFinishTime = new BitmapFont();
        bitmapFontFinishTime.setColor(Color.WHITE);

        intro_music = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
        inGame_music = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic1.mp3"));
        powerUpEffect = Gdx.audio.newMusic(Gdx.files.internal("data/powerup_effect.mp3"));

        createUserCar();
        createAiCar();
        createCheckPoints();
        createObstacles();
        createGrass();
        createPowerUps();
        createTimer();
        //Level3
        createObstacleLevel3();
        createSlowOnGrassLevel3();
        createPowerUpsLevel3();
        createPowerUpsLevel2();



    }

    public void createUserCar() {
        userCar = new UserCar("userCar1.png", 550, 620, 4);
    }


    public void createAiCar() {
        aiCar = new AiCar("AiCar1.png", aiCarPositionX, aiCarPositionY,4);
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

            case LevelCompleted:
                renderLevelCompleted();
                break;

            case Level2:
                renderLevel2();
                break;

            case Level2Completed:
                renderLevel2Completed();
                break;

            case Level3:
                renderLevel3();
                break;

            case Level3Completed:
                //renderLevel3Completed();
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
        batch.draw(board, 20, 20, 400, 200);
        font.draw(batch, driver, 70, 200);
        redFont.draw(batch, powerUpFont, 200, 130);
        timer.drawTime(batch);
/*
        for (Obstacle checkpoint : checkpoints) {
            checkpoint.draw(batch);
        }
        */
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

        for (Obstacle powerup : powerUps) {
            powerup.draw(batch);
        }
        // exit game
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            inGame_music.stop();
            gameState = GameState.GameOver;
        }
        if (numberOfLaps == 0) {                         /////////////////////////
            // game state is level complete
            gameState = GameState.LevelCompleted;
        }

        batch.end();
    }

    public void renderLevel2() {
        inGame_music.play();
        createCheckPoints2();
        checkRoutePoints2(userCar);
        createFinishLine2();
        checkInput();
        batch.begin();
        batch.draw(backGround2, 0, 0);
        batch.draw(board, 50, 20, 400, 200);
        font.draw(batch, driver, 70, 200);
        redFont.draw(batch, powerUpFont, 200, 130);
        for (Obstacle checkpoint : checkpoints2) {
            checkpoint.draw(batch);
        }

        for (Obstacle finishLinePoint : finishLineLevel2) {
            finishLinePoint.draw(batch);
        }
        timer2.drawTime(batch);
        if (numberOfLaps2 == 1) {                         /////////////////////////
            // game state is level complete
            gameState = GameState.Level2Completed;
        }

        powerUpLevel2(userCar);

        for (Obstacle powerup : powerUpsLevel2) {
            powerup.draw(batch);
        }
        userCar.getSprite().draw(batch);
        userCar.updatePosition();
        // here to transit to level 2 complete
        batch.end();

    }

    public void renderLevelCompleted() {
        batch.begin();
        batch.draw(levelCompleted, 250, 355);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            intro_music.stop();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameState = GameState.GameOver;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            inGame_music.stop();
            userCar.fullStop();
            userCar.setX(450);
            userCar.setY(580);
            createTimer2();
            gameState = GameState.Level2;
        }

        batch.end();
        // here we transit to another level
    }

    public void renderLevel2Completed() {
        batch.begin();
        batch.draw(levelCompleted, 250, 355);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            intro_music.stop();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            gameState = GameState.GameOver;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

            inGame_music.stop();
            userCar.fullStop();
            userCar.setX(450);
            userCar.setY(580);
            createTimer3();
            gameState = GameState.Level3;
        }

        batch.end();
        // here we transit to another level
    }

    public void renderLevel3(){

        inGame_music.play();
        checkInput();
        createCheckPoints3();
        checkRoutePoints3(userCar);
        createFinishLineLevel3();
        batch.begin();


        batch.draw(backGroundLevel3, 0, 0);


        for (Obstacle grass:slowOnGrassLevel3) {
            grass.draw(batch);
        }
        for (Obstacle checkpoint : checkpoints3) {
            checkpoint.draw(batch);
        }

        batch.draw(gpLogo, 680, 167, 300, 100);
        batch.draw(board, 25, 20, 300, 150);
        font.draw(batch, driver, 70, 200);
        redFont.draw(batch, powerUpFont, 120, 100);
        timer3.drawTime(batch);



        checkObstaclesLevel3(userCar);
        checkGrassLevel3(userCar);


        for (Obstacle powerUp3 : powerUpsLevel3) {
            powerUp3.draw(batch);
        }
        powerUpLevel3(userCar);



        userCar.getSprite().draw(batch);
        userCar.updatePosition();
        for (Obstacle outSideItemLevel3 : outSideItemsLevel3 ) {
            outSideItemLevel3.draw(batch);
        }
        batch.end();

    }

    public void renderGameOver() {
        batch.begin();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(gameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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

    public void  createObstacleLevel3(){
        tire1= new Obstacle("Tire2.png",490, 320, 37, 200 );
        outSideItemsLevel3.add(tire1);
        tire2= new Obstacle("Tire3.png",350, 720, 320, 40 );
        outSideItemsLevel3.add(tire2);
        tire3= new Obstacle("Tire3.png",620, 5, 320, 40 );
        outSideItemsLevel3.add(tire3);
        tree1= new Obstacle("tree.png", 850, 400, 80,80);
        outSideItemsLevel3.add(tree1);
        tree2= new Obstacle("Tree1.png", 100, 360, 200,60);
        outSideItemsLevel3.add(tree2);
        tree3= new Obstacle("Tree1.png", 1000, 710, 150,50);
        outSideItemsLevel3.add(tree3);
        tree4= new Obstacle("tree.png", 990, 200, 80,80);
        outSideItemsLevel3.add(tree4);
        tree5= new Obstacle("tree.png", 510, 200, 80,80);
        outSideItemsLevel3.add(tree5);

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
    public void createCheckPoints2() {
        checkpoints2 = new ArrayList<Obstacle>();
        checkpoint = new Obstacle("wall_0.jpg", 250, 580, 5, 96);
        checkpoint1 = new Obstacle("wall_0.jpg", 700, 580, 5, 96);
        checkpoint2 = new Obstacle("wall_1.jpg", 460, 160, 96, 5);
        checkpoint3 = new Obstacle("wall_0.jpg", 750, 54, 5, 96);
        checkpoint4 = new Obstacle("wall_0.jpg", 250, 455, 5, 90);
        checkpoint6 = new Obstacle("wall_1.jpg", 970, 370, 96, 5);
        checkpoint5 = new Obstacle("wall_1.jpg", 1120, 500, 96, 5);
        checkpoint7 = new Obstacle("wall_1.jpg", 600, 420, 96, 5);
        checkpoints2.add(checkpoint);
        checkpoints2.add(checkpoint1);
        checkpoints2.add(checkpoint2);
        checkpoints2.add(checkpoint3);
        checkpoints2.add(checkpoint4);
        checkpoints2.add(checkpoint5);
        checkpoints2.add(checkpoint6);
        checkpoints2.add(checkpoint7);
    }

    public void createCheckPoints3() {
        checkpoints3 = new ArrayList<Obstacle>();
        checkpoint = new Obstacle("wall_0.jpg", 250, 580, 5, 96);
        checkpoint1 = new Obstacle("wall_0.jpg", 700, 580, 5, 96);
        checkpoint2 = new Obstacle("wall_1.jpg", 460, 160, 96, 5);
        checkpoint3 = new Obstacle("wall_0.jpg", 750, 54, 5, 96);
        checkpoint4 = new Obstacle("wall_0.jpg", 250, 455, 5, 90);
        checkpoint6 = new Obstacle("wall_1.jpg", 970, 370, 96, 5);
        checkpoint5 = new Obstacle("wall_1.jpg", 1120, 500, 96, 5);
        checkpoint7 = new Obstacle("wall_1.jpg", 600, 420, 96, 5);
        checkpoints3.add(checkpoint);
        checkpoints3.add(checkpoint1);
        checkpoints3.add(checkpoint2);
        checkpoints3.add(checkpoint3);
        checkpoints3.add(checkpoint4);
        checkpoints3.add(checkpoint5);
        checkpoints3.add(checkpoint6);
        checkpoints3.add(checkpoint7);
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
    public void createSlowOnGrassLevel3(){
        grass1=new Obstacle("grass.png", 0,0,600,40);
        slowOnGrassLevel3.add(grass1);
        grass2=new Obstacle("grass.png", 950,0,300,40);
        slowOnGrassLevel3.add(grass2);
        grass3=new Obstacle("grass.png", 0,0,330,340);
        slowOnGrassLevel3.add(grass3);
        grass4=new Obstacle("grass.png", 0,500,100,300);
        slowOnGrassLevel3.add(grass4);
        grass5=new Obstacle("grass.png", 0,420,150,40);
        slowOnGrassLevel3.add(grass5);
        grass6=new Obstacle("grass.png", 0,720,340,40);
        slowOnGrassLevel3.add(grass6);
        grass7=new Obstacle("grass.png", 700,720,300,40);
        slowOnGrassLevel3.add(grass7);
        grass8=new Obstacle("grass.png", 880,480,40,180);
        slowOnGrassLevel3.add(grass8);
        grass9=new Obstacle("grass.png", 820,680,150,40);
        slowOnGrassLevel3.add(grass9);
        grass10=new Obstacle("grass.png", 1250,0,30,760);
        slowOnGrassLevel3.add(grass10);
        grass11=new Obstacle("grass.png", 600,185,380,80);
        slowOnGrassLevel3.add(grass11);
        grass12=new Obstacle("grass.png", 1070,250,20,310);
        slowOnGrassLevel3.add(grass12);
        grass13=new Obstacle("grass.png", 280,555,410,25);
        slowOnGrassLevel3.add(grass13);
        grass14=new Obstacle("grass.png", 480,275,80,40);
        slowOnGrassLevel3.add(grass14);
        grass15=new Obstacle("grass.png",700,400,140,20);
        slowOnGrassLevel3.add(grass15);

    }

    //If collides with Obstacle
    public void checkObstacles(UserCar userCar) {
        for (int i = 0; i < outSideItems.size(); i++) {
            if (userCar.collidesWith(outSideItems.get(i).getCollisionRectangle())) {
                userCar.fullStop();
            }
        }
    }
    public void checkObstaclesLevel3(UserCar userCar){
        for (int i= 0; i<outSideItemsLevel3.size(); i++){
            if (userCar.collidesWith(outSideItemsLevel3.get(i).getCollisionRectangle())){
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
    public void checkGrassLevel3(UserCar userCar){
        for (int i=0; i<slowOnGrassLevel3.size(); i++){
            if (userCar.collidesWith(slowOnGrassLevel3.get(i).getCollisionRectangle())){
                userCar.slowOnGrass();
            }
        }
    }

    public void createFinishLine() {
        finishLine = new ArrayList<Obstacle>(1);
        finishLine1 = new Obstacle("wall_0.jpg", 550, 615, 5, 96);
        finishLine.add(finishLine1);

    }
    public void createFinishLine2() {
        finishLineLevel2 = new ArrayList<Obstacle>(1);
        finishLine2 = new Obstacle("wall_0.jpg", 450, 580, 5, 96);
        finishLineLevel2.add(finishLine2);

    }
    public void createFinishLineLevel3(){
        finishLineLevel3=new ArrayList<Obstacle>(1);
        finishLine3=new Obstacle("wall_0.jpg",550,615,5,96 );
        finishLineLevel3.add(finishLine3);
    }

    public void createPowerUps() {
        int upper = 3;
        int lower = 0;
        int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

        switch (randomNumber) {
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
                powerUpEffect.play();
                return true;
            } else return false;
        }
        return false;
    }
    public void createPowerUpsLevel2(){
        int upper = 3;
        int lower = 0;
        int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

        switch (randomNumber) {
            case 0:
                powerUp1 = new Obstacle("coin.png", 400, 660, 20, 20);
                powerUpsLevel2.add(powerUp1);
                break;
            case 1:
                powerUp2 = new Obstacle("coin.png", 930, 100, 20, 20);
                powerUpsLevel2.add(powerUp2);
                break;
            case 2:
                powerUp3 = new Obstacle("coin.png", 1150, 500, 20, 20);
                powerUpsLevel2.add(powerUp3);
                break;
            case 3:
                powerUp4 = new Obstacle("coin.png",430, 300, 20, 20);
                powerUpsLevel2.add(powerUp4);
                break;
        }
    }
    public boolean powerUpLevel2(UserCar userCar) {
        for (int i = 0; i < powerUpsLevel2.size(); i++) {
            if (userCar.collidesWith(powerUpsLevel2.get(i).getCollisionRectangle())) {
                powerUpsLevel2.remove(i);
                userCar.boost();
                powerUpEffect.play();
                return true;
            } else return false;
        }
        return false;
    }

    public void createPowerUpsLevel3(){
        int upper = 3;
        int lower = 0;
        int randomNumber = (int) (Math.random() * (upper - lower)) + lower;

        switch (randomNumber) {
            case 0:
                powerUp1 = new Obstacle("coin.png", 400, 660, 20, 20);
                powerUpsLevel3.add(powerUp1);
                break;
            case 1:
                powerUp2 = new Obstacle("coin.png", 930, 100, 20, 20);
                powerUpsLevel3.add(powerUp2);
                break;
            case 2:
                powerUp3 = new Obstacle("coin.png", 1150, 500, 20, 20);
                powerUpsLevel3.add(powerUp3);
                break;
            case 3:
                powerUp4 = new Obstacle("coin.png",430, 300, 20, 20);
                powerUpsLevel3.add(powerUp4);
                break;
        }
    }
    public boolean powerUpLevel3(UserCar userCar) {
        for (int i = 0; i < powerUpsLevel3.size(); i++) {
            if (userCar.collidesWith(powerUpsLevel3.get(i).getCollisionRectangle())) {
                powerUpsLevel3.remove(i);
                userCar.boost();
                powerUpEffect.play();
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
                return true;
            }
        }
        return false;
    }

    public boolean checkFinishLine2(int[] arr) {
        if (checkArray2(arr)) {
            if (userCar.collidesWith(finishLineLevel2.get(0).getCollisionRectangle())) {
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = 0;
                }
                return true;
            }
        }
        return false;
    }
    public boolean checkFinishLine3(int[] arr) {
        if (checkArray2(arr)) {
            if (userCar.collidesWith(finishLineLevel3.get(0).getCollisionRectangle())) {
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = 0;
                }
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
    public boolean checkArray2(int[] arr) {
        if ((arr[0] == 1) && (arr[1] == 1) && (arr[2] == 1) && (arr[3] == 1) && (arr[4] == 1) && (arr[5] == 1) && (arr[6] == 1)&& (arr[7] == 1))
            return true;
        else return false;
    }

    public void checkRoutePoints(UserCar userCar) {
        driver = "";

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
    public void checkRoutePoints2(UserCar userCar) {
        driver = "";

        for (int j = 0; j < checkpoints2.size(); j++) {
            if (userCar.collidesWith(checkpoints2.get(j).getCollisionRectangle())) {
                arr2[j] = 1; // if the next is 0 set to zero else set one
                //		 to make sure the start last point is counted at the end not the first
            }
        }

        if (checkArray2(arr2)) {
            if (checkFinishLine2(arr2)) {
                numberOfLaps2++;
                if (numberOfLaps2 == 1) {
                    System.out.println("done with 1 lap " + (numberOfLaps2));
                    driver = "Finished 1 Lap";

                } else if (numberOfLaps2 == 2) {
                    System.out.println(" lap 2 " + (numberOfLaps2));
                    driver = "Finished 2 Laps";

                } else if (numberOfLaps2 == 3) {
                    System.out.println(" lap 3 " + (numberOfLaps2));
                    driver = "Finished 3 Laps";
                    // HERE IS WHERE THE CAR FINISH THE RACE
                }
            }
        }
    }

    public void checkRoutePoints3(UserCar userCar) {
        driver = "";

        for (int j = 0; j < checkpoints3.size(); j++) {
            if (userCar.collidesWith(checkpoints3.get(j).getCollisionRectangle())) {
                arr3[j] = 1; // if the next is 0 set to zero else set one
                //		 to make sure the start last point is counted at the end not the first
            }
        }

        if (checkArray2(arr3)) {
            if (checkFinishLine3(arr3)) {
                numberOfLaps3++;
                if (numberOfLaps3 == 1) {
                    System.out.println("done with 1 lap " + (numberOfLaps3));
                    driver = "Finished 1 Lap";

                } else if (numberOfLaps3 == 2) {
                    System.out.println(" lap 2 " + (numberOfLaps3));
                    driver = "Finished 2 Laps";

                } else if (numberOfLaps3 == 3) {
                    System.out.println(" lap 3 " + (numberOfLaps3));
                    driver = "Finished 3 Laps";
                    // HERE IS WHERE THE CAR FINISH THE RACE
                }
            }
        }
    }


    public void createTimer() {
        timer = new Timer();
    }

    public void createTimer2() {
        timer2 = new Timer();
    }

    public void createTimer3() {
        timer3 = new Timer();
    }


    @Override
    public void dispose() {
        batch.dispose();
        backGround.dispose();
        backGroundLevel3.dispose();
        board.dispose();
        font.dispose();
        gpLogo.dispose();
        welcome.dispose();
        gameOver.dispose();
        levelCompleted.dispose();
        intro_music.dispose();
        inGame_music.dispose();
        powerUpEffect.dispose();

        redFont.dispose();
        bitmapFontFinishTime.dispose();
        backGround2.dispose();
        backGroundLevel3.dispose();


    }
}
