package com.mygdx.game;

/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class UserCar extends Car{



    public UserCar(String textureFileName, float x, float y){
        //First call the parent class constructor (Figure)
        super(textureFileName, x, y);

        //We want to rotate the graphics around the center when pacman turns up and down.
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed(){
        //First call the method "updatePositionFromSpeed" in the Figure super class
        super.updatePositionFromSpeed();
        stopAtEdge();


    }
    public void fullStop()
    {
        setSpeedX(0);
        setSpeedY(0);
    }

    public void goUp(){

        setSpeedX(0);
        setSpeedY(2);
        getSprite().setFlip(false, false);
        getSprite().setRotation(90);
    }

    public void goDown(){
        setSpeedX(0);
        setSpeedY(-2);
        getSprite().setFlip(false, false);
        getSprite().setRotation(-90);
    }
    public void goZawieh()
    {
        getSprite().setRotation(-30);
        setSpeedX(2);
        setSpeedY(-1);

    }

    public void goLeft(){
        setSpeedX(-2);
        setSpeedY(0);
        getSprite().setFlip(getSpeedX()<0, false);
        getSprite().setRotation(0);
    }

    public void goRight(){
        setSpeedX(2);
        setSpeedY(0);
        getSprite().setFlip(getSpeedX()<0, false);
        getSprite().setRotation(0);
    }


}
