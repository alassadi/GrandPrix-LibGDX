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
	ArrayList<Obstacle> checkpoints = new ArrayList<Obstacle>();
	ArrayList<Obstacle> outSideItems= new ArrayList<Obstacle>();
	int [] arr = new int[7];
	int count = 0;
	Obstacle checkpoint, checkpoint1, checkpoint2, checkpoint3, checkpoint4, checkpoint5, checkpoint6;
	Obstacle tire1,tire2,
			tire3,tree1;



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
		createCheckPoints();
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
		for (Obstacle checkpoint : checkpoints) {
			checkpoint.draw(batch);
		}
		userCar.getSprite().draw(batch);
		userCar.updatePosition();
		aiCar.getSprite().draw(batch);
		aiCar.updatePositionFromSpeed();
		aiCar.Route();

		for (Obstacle  outSideItem: outSideItems) {
			outSideItem.draw(batch);
		}

		checkRoutePoints(userCar);
		checkObstacles(userCar);

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
	public void createObstacles(){

		tire1=new Obstacle("Tire1.png",300,100,90,40);
		outSideItems.add(tire1);
		tire2=new Obstacle("Tire2.png",50,450,40,80);
		outSideItems.add(tire2);
		tire3=new Obstacle("Tire1.png",400,720,90,40);
		outSideItems.add(tire3);
		tree1=new Obstacle ("tree.png",390,200,80,80);
		outSideItems.add(tree1);
	}

	public void createCheckPoints()
	{
		checkpoint = new Obstacle("wall_0.jpg", 450 , 615, 5,96);
		checkpoint1 = new Obstacle("wall_0.jpg", 800 , 615, 5,96);
		checkpoint2 = new Obstacle("wall_1.jpg", 550 , 160, 96,5);
		checkpoint3 = new Obstacle("wall_0.jpg", 800 , 64, 5,96);
		checkpoint4 = new Obstacle("wall_0.jpg", 300 , 285, 5,90);
		checkpoint5 = new Obstacle("wall_1.jpg", 900 , 370, 96,5);
		checkpoint6 = new Obstacle("wall_1.jpg", 105 , 500, 96,5);
		checkpoints.add(checkpoint);
		checkpoints.add(checkpoint1);
		checkpoints.add(checkpoint2);
		checkpoints.add(checkpoint3);
		checkpoints.add(checkpoint4);
		checkpoints.add(checkpoint5);
		checkpoints.add(checkpoint6);
	}
	//If collides with Obstacle
	public void checkObstacles(UserCar userCar){
		for (int i=0; i<outSideItems.size(); i++){
			if (userCar.collidesWith(outSideItems.get(i).getCollisionRectangle())){
				userCar.fullStop();
			}
		}
	}

	public void checkRoutePoints(UserCar userCar){
			for (int i = 0 ; i < checkpoints.size() ; i ++)
			{
				if (userCar.collidesWith(checkpoints.get(i).getCollisionRectangle())){

					arr[i] = 1; // if the next is 0 set to zero else set one
					//		 to make sure the start last point is counted at the end not the first
				}
			}


			if((arr[0] == 1) && (arr[1] == 1)&& (arr[2] == 1)&& (arr[3] == 1)&& (arr[4] == 1)&& (arr[5] == 1)&& (arr[6] == 1)) {

				count = count+1;
				for(int i = 0; i < arr.length ; i++)
				{
					arr[i] = 0;
				}


			}

			if(count == 1)
				System.out.println("done with 1 lap " + (count));
			if (count == 2)
				System.out.println(" lab 2 " + (count));
			if (count == 3)
				System.out.println("lab 3    " + count);

	}

	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
		gameover.dispose();
	}

}
