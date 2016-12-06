package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class UserCar extends Car {

    double rotationRate = Math.PI / 70; // how fast it turns
    private double maxSpeed;
    int boostCounter = 0;

    public UserCar(String textureFileName, float x, float y, double maxSpeed) {
        //First call the parent class constructor (Car)
        super(textureFileName, x, y);
        this.maxSpeed = maxSpeed;
        //We want to rotate the graphics around the center when the user car turns up and down.
        getSprite().setOriginCenter();
    }

    public void updatePosition() {
        super.updatePosition();
        if (boostCounter > 0) {
            boostCounter--;
        } else {
            powerDown();
        }
    }

    public void deceleration() {
        if (getVelocity() > 0)
            setVelocity(getVelocity() - (float) 0.02);
    }

    public void accelerate() {
        if (this.getVelocity() < maxSpeed)
            setVelocity(this.getVelocity() + (float) 0.05);
    }

    public void breaks() {
        if (getVelocity() > 0)
            setVelocity(getVelocity() - (float) 0.05);

    }

    public void turnLeft() {
        setAngle(getAngle() + rotationRate);
        getSprite().setRotation((float) getAngle() * (float) 57.3);
        breaks();
    }

    public void turnRight() {
        setAngle(getAngle() - rotationRate);
        getSprite().setRotation((float) getAngle() * (float) 57.3);
        breaks();
    }

    public void fullStop() {
        setVelocity(0);
    }

    public void slowOnGrass() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setVelocity(1);
        }
    }

    public void boost() {
        maxSpeed = 6;
        boostCounter = 300;
    }

    public void powerDown() {
        maxSpeed = 4;
    }

    public void forceBreak() {
        while (getVelocity() > 0) {
            setVelocity(getVelocity() - (float) 0.05);
        }
    }

}
