package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture backGround;
	Texture wellcome;
	Texture gameover;
	UserCar userCar;
	Gamestate gamestate = Gamestate.WelcomePage;
	float userCarPositionX = 450f;
	float userCarPositionY = 620f;
	AiCar aiCar;
	float aiCarPositionX = 450f;
	float aiCarPositionY = 660f;



	private enum Gamestate {
		WelcomePage,
		GamePage,
		GameOver
	}


	
	@Override
	public void create () {
		wellcome = new Texture("WellComePage.jpg") ;
		batch = new SpriteBatch();
		backGround= new Texture("BackGround.jpg");
		gameover = new Texture("game-over.jpg");
		createUserCar();
		createAiCar();

	}

	public void createUserCar()
	{
		userCar = new UserCar("userCar1.png",userCarPositionX,userCarPositionY );
	}

	public void createAiCar()
	{
		aiCar=new AiCar("AiCar1.png",aiCarPositionX,aiCarPositionY);
	}

	/*
	public void movements ()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT))
			userCarPositionX -= Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT))
			userCarPositionX += Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_UP))
			userCarPositionY += Gdx.graphics.getDeltaTime() * carSpeed;
		if(Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN))
			userCarPositionY -= Gdx.graphics.getDeltaTime() * carSpeed;

	}
	*/
	public void checkInput(){
		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			userCar.goUp();

		}
		else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			userCar.goDown();
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			userCar.goLeft();
		}

		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			userCar.goRight();
		}
		else userCar.fullStop();
	}
	@Override
	public void render () {
		batch.begin();

		switch (gamestate)
		{
			case WelcomePage:

				batch.draw(wellcome, 0, 0);
				if(Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
					gamestate = Gamestate.GamePage;
				}
				// if escape key was pressed in the welcome page
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					gamestate = Gamestate.GameOver;
				}
				break;

			case GamePage:

				batch.draw(backGround, 0, 0);
				checkInput();
				userCar.getSprite().draw(batch);
				userCar.updatePositionFromSpeed();
				aiCar.getSprite().draw(batch);
				aiCar.updatePositionFromSpeed();
				aiCar.Route();

				// exit game
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
				{
					gamestate = Gamestate.GameOver;
				}
				break;

			case GameOver:
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				batch.draw(gameover, 450, 290);
				break;

		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
		gameover.dispose();
	}

}
