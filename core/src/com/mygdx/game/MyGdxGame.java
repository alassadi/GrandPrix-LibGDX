package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	ArrayList<Obstacle> checkpoints;
	ArrayList<Obstacle> finishline;
	int [] arr = new int[7];
	int numberOfLaps = 0;
	boolean finishlineStatus = false ;
	Obstacle checkpoint, checkpoint1, checkpoint2, checkpoint3, checkpoint4, checkpoint5, checkpoint6;
	Obstacle finishline1;
	private Music intro;
	private Music ingame ;

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
		intro = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
		ingame = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic1.mp3"));
		createUserCar();
		createAiCar();


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

		//
		//long id = ingame.play();
		//ingame.setLooping(id, true);
		intro.play();
		//if enter key is pressed in the welcome page it will go to game page
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {

			gamestate = Gamestate.GamePage;
			intro.stop();
		}
		// if escape key was pressed in the welcome page it will got to the game over page
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			intro.stop();
			gamestate = Gamestate.GameOver;
		}
		batch.end();
	}


	public void renderGamePage() {
		ingame.play();
		checkInput();
		createCheckPoints();
		createFinishLine();
		batch.begin();
		batch.draw(backGround, 0, 0);
		for (Obstacle checkpoint : checkpoints) {
			checkpoint.draw(batch);
		}
		for (Obstacle finishline11 : finishline) {
			finishline11.draw(batch);
		}


		userCar.getSprite().draw(batch);
		userCar.updatePosition();
		aiCar.getSprite().draw(batch);
		aiCar.updatePositionFromSpeed();
		aiCar.Route();
		checkRoutePoints(userCar);

		// exit game
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			ingame.stop();
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

	public void createCheckPoints()
	{
		checkpoints = new ArrayList<>();
		checkpoint = new Obstacle("wall_0.jpg", 450 , 615, 5,96);
		checkpoint1 = new Obstacle("wall_0.jpg", 800 , 615, 5,96);
		checkpoint2 = new Obstacle("wall_1.jpg", 550 , 160, 96,5);
		checkpoint3 = new Obstacle("wall_0.jpg", 800 , 64, 5,96);
		checkpoint4 = new Obstacle("wall_0.jpg", 300 , 285, 5,90);
		checkpoint6 = new Obstacle("wall_1.jpg", 900 , 370, 96,5);
		checkpoint5 = new Obstacle("wall_1.jpg", 105 , 500, 96,5);
		checkpoints.add(checkpoint);
		checkpoints.add(checkpoint1);
		checkpoints.add(checkpoint2);
		checkpoints.add(checkpoint3);
		checkpoints.add(checkpoint4);
		checkpoints.add(checkpoint5);
		checkpoints.add(checkpoint6);
	}
	public void createFinishLine()
	{
		finishline = new ArrayList<Obstacle>(1);
		finishline1 = new Obstacle("wall_0.jpg", 660 , 615, 5,96);
		finishline.add(finishline1);
	}

	public boolean checkFinishLine(int [] arr)
	{
		if(checkArray(arr))
		{
			if (userCar.collidesWith(finishline.get(0).getCollisionRectangle()))
			{
				for (int i = 0; i < arr.length; i++) {
					arr[i] = 0;
				}
				System.out.println("done with lap");
				return true;
			}
		}
		return false;
	}

	public boolean checkArray(int [] arr)
	{
		if((arr[0] == 1) && (arr[1] == 1)&& (arr[2] == 1)&& (arr[3] == 1)&& (arr[4] == 1)&& (arr[5] == 1)&& (arr[6] == 1))
			return true;
		else return false;
	}

	public void checkRoutePoints(UserCar userCar){

			for (int j = 0 ; j < checkpoints.size() ;j++) {
				if (userCar.collidesWith(checkpoints.get(j).getCollisionRectangle())) {

					arr[j] = 1; // if the next is 0 set to zero else set one
					//		 to make sure the start last point is counted at the end not the first

					}
			}

		if(checkArray(arr))
		{
			if (checkFinishLine(arr)) {

				numberOfLaps++;

				if (numberOfLaps == 1)
					System.out.println("done with 1 lap " + (numberOfLaps));
				else if (numberOfLaps == 2)
					System.out.println(" lab 2 " + (numberOfLaps));
				else if (numberOfLaps == 3)
					System.out.println("lab 3    " + numberOfLaps);


			}

		}


	}

	/*public void checkRoutePoints(UserCar userCar)
	{	int i = 0;
		for(Obstacle checkpoin : checkpoints){
			if(userCar.collidesWith(checkpoin.getCollisionRectangle())){
				arr[i] = 1;
			}
			i++;
			if(checkArray(arr))
			{
				System.out.println("lab done");
				arr= new int []{0,0,0,0,0,0,0};
			}
		}

	}
*/
	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
		gameover.dispose();
		intro.dispose();
		ingame.dispose();


	}

}
