package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture backGround;
	Texture wellcome;
	Texture gameover;
	UserCar userCar;
	Gamestate gamestate = Gamestate.WelcomePage;
	float userCarPositionX = 550f;
	float userCarPositionY = 620f;
	AiCar aiCar;
	float aiCarPositionX = 450f;
	float aiCarPositionY = 660f;
	ArrayList<Obstacle> obstacles;
	int [] arr = new int[10];

	Obstacle obstacle, obstacle1, obstacle2, obstacle3;

	private enum Gamestate {
		WelcomePage,
		GamePage,
		GameOver
	}


	@Override
	public void create() {
		wellcome = new Texture("WellComePage.jpg");
		batch = new SpriteBatch();
		backGround = new Texture("BackGround.jpg");
		gameover = new Texture("game-over.jpg");
		createUserCar();
		createAiCar();
		createObstacles();
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

		switch (gamestate) {
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
		batch.draw(wellcome, 0, 0);
		// if enter key is pressed in the welcome page it will go to game page
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			gamestate = Gamestate.GamePage;
		}
		// if escape key was pressed in the welcome page it will got to the game over page
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			gamestate = Gamestate.GameOver;
		}
		batch.end();
	}


	public void renderGamePage() {
		checkInput();
		batch.begin();
		batch.draw(backGround, 0, 0);
		for (Obstacle obstacle : obstacles) {
			obstacle.draw(batch);
		}
		userCar.getSprite().draw(batch);
		userCar.updatePosition();
		aiCar.getSprite().draw(batch);
		aiCar.updatePositionFromSpeed();
		aiCar.Route();
		checkObstacleCollision(userCar);

		// exit game
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			gamestate = Gamestate.GameOver;
		}

		batch.end();
	}

	public void renderGameOver() {
		batch.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.draw(gameover, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		createUserCar();
		createAiCar();
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			gamestate = Gamestate.WelcomePage;
		}
		batch.end();
	}

	public void createObstacles()
	{
		obstacles = new ArrayList<>();
		obstacle = new Obstacle("wall_1.jpg", 550 , 160, 96,5);
		obstacle1 = new Obstacle("wall_0.jpg", 800 , 64, 5,96);
		obstacle2 = new Obstacle("wall_0.jpg", 450 , 615, 5,96);
		obstacle3 = new Obstacle("wall_0.jpg", 300 , 285, 5,90);
		obstacles.add(obstacle);
		obstacles.add(obstacle1);
		obstacles.add(obstacle2);
		obstacles.add(obstacle3);
	}

	public void checkObstacleCollision(UserCar userCar){
		for (int x = 0 ; x <2 ; x++) {
			if (userCar.collidesWith(obstacle.getCollisionRectangle())){

				arr[0] = 1; // if the next is 0 set to zero else set one
							// to make sure the start last point is counted at the end not the first

			}
			if (userCar.collidesWith(obstacle1.getCollisionRectangle())){

				arr[1] = 1;
			}
			if (userCar.collidesWith(obstacle2.getCollisionRectangle())){

				arr[2] = 1;
			}
			if (userCar.collidesWith(obstacle3.getCollisionRectangle())){

				arr[3] = 1;
			}
			if((arr[0] == 1) && (arr[1] == 1)&& (arr[2] == 1)&& (arr[3] == 1))
				System.out.println("done");
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
		gameover.dispose();
	}

}
