package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

import java.util.ArrayList;


public class MyGdxGame extends ApplicationAdapter {

	SpriteBatch batch;
	Texture backGround;
	Texture wellcome;
	Texture gameover;
	UserCar userCar;
	Texture board;
	Gamestate gamestate = Gamestate.WelcomePage;
	float userCarPositionX = 550f;
	float userCarPositionY = 620f;
	AiCar aiCar;
	Texture gplogo;
	float aiCarPositionX = 450f;
	float aiCarPositionY = 660f;
	ArrayList<Obstacle> checkpoints ;
	ArrayList<Obstacle> outSideItems= new ArrayList<Obstacle>();
	ArrayList<Obstacle> finishline;
	int [] arr = new int[7];
	int numberOfLaps = 0;
	boolean finishlineStatus = false ;
	Obstacle checkpoint, checkpoint1, checkpoint2, checkpoint3, checkpoint4, checkpoint5, checkpoint6;

	Obstacle tire1,tire2, tire3,tire4,
			tree1,tree2,tree3,tree4;



	Obstacle finishline1;
	private Music intro;
	private Music ingame ;

	static CharSequence driver = " ";

	 BitmapFont font;





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

		gplogo= new Texture("Gplogo.png");


		board = new Texture("Board.png");
		font=new BitmapFont();
		font.setColor(Color.WHITE);


		intro = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic.mp3"));
		ingame = Gdx.audio.newMusic(Gdx.files.internal("data/mymusic1.mp3"));
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
		batch.draw(gplogo,300,450,300,100);
		batch.draw(board, 50,20,400,200);
		font.draw(batch,driver,70,200);
		//timer
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

		for (Obstacle  outSideItem: outSideItems) {
			outSideItem.draw(batch);
		}

		checkRoutePoints(userCar);
		checkObstacles(userCar);

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





	//Timer
	private final long nanosPerMilli = 1000000;
	private long startTime = 0;
	private long stopTime = 0;
	private boolean running=true;

	public void start(){
		this.startTime=System.nanoTime();
		this.running=true;
	}
	public void stop(){
		this.stopTime=System.nanoTime();
		this.running=false;
	}
	public void reset(){
		this.startTime=0;
		this.stopTime=0;
		this.running=false;
	}
	//get elapsed milliseconds
	public long getElapsedMilliseconds() {
		long elapsed;
		if (running) {
			elapsed = System.nanoTime() - startTime;
		} else {
			elapsed = stopTime - startTime;
		}
		return elapsed / nanosPerMilli;
	}
	public String formatTime(final long millis){
		int minutesComponent = (int) (millis/(1000*60));
		int secondsComponent =(int) ((millis/1000) % 60);
		int hunderdthsComponent=(int) ((millis/10) %100);
		String paddedMinutes = String.format("%02d",minutesComponent);
		String paddedSeconds = String.format("%02d",secondsComponent);
		String paddedHunderths = String.format("%02d",hunderdthsComponent);
		String formattedTime;
		if (millis>0 && millis<3600000){
			formattedTime = paddedMinutes+":"+paddedSeconds+":"+paddedHunderths;
		}
		else {
			formattedTime=59+":"+59+":"+99;
		}
		return formattedTime;
	}
	//get formatted elapsed time
	public String getElapsed(){
		String timeFormatted="";
		timeFormatted = this.formatTime(this.getElapsedMilliseconds());
		return timeFormatted;
	}

	//Obstacle
	public void createObstacles(){

		tire1=new Obstacle("Tire1.png",410,230,90,37);
		outSideItems.add(tire1);
		tire2=new Obstacle("Tire2.png",45,380,37,200);
		outSideItems.add(tire2);
		tire3=new Obstacle("Tire3.png",400,725,400,37);
		outSideItems.add(tire3);
		tire4=new Obstacle("Tire1.png",320,230,90,37);
		outSideItems.add(tire4);
		tree1=new Obstacle ("tree.png",1050,350,100,100);
		outSideItems.add(tree1);
		tree2=new Obstacle("Tree1.png",650,200,200,60);
		outSideItems.add(tree2);
		tree3=new Obstacle("tree.png",690,450,90,80);
		outSideItems.add(tree3);
		tree4=new Obstacle("tree.png",690,350,90,80);
		outSideItems.add(tree4);

	}

	public void createCheckPoints()
	{
		checkpoints = new ArrayList<Obstacle>();
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
	//If collides with Obstacle
	public void checkObstacles(UserCar userCar) {
		for (int i = 0; i < outSideItems.size(); i++) {
			if (userCar.collidesWith(outSideItems.get(i).getCollisionRectangle())) {
				userCar.fullStop();
			}
		}
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
				{
					System.out.println("done with 1 lap " + (numberOfLaps));
					driver = "done with lap 1";

				}

				else if (numberOfLaps == 2)
				{
					System.out.println(" lab 2 " + (numberOfLaps));
					driver = "done with lap 2";

				}
				else if (numberOfLaps == 3)
				{
					System.out.println(" lab 3 " + (numberOfLaps));
					driver = "done with lap 3";
					// HERE IS WHERE THE CAR FINISH THE RACE

				}


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
