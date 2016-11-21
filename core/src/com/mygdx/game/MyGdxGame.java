package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backGround;
	Texture wellcome;
	Texture userCar;
	float userCarPositionX=200;
	int choice = 0 ;
	
	@Override
	public void create () {


		wellcome = new Texture("WellComePage.jpg") ;
		batch = new SpriteBatch();
		backGround=new Texture("BackGround.jpg");
		userCar=new Texture("userCar1.png");


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(wellcome, 0, 0);
		//batch.draw(backGround, 0, 0);

		//batch.draw(userCar, userCarPositionX, 600, 100, 70 );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		backGround.dispose();
		wellcome.dispose();
	}
}
