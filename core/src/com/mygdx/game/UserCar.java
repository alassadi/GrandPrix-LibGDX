package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.mygdx.game.MyGdxGame.powerUpFont;

/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class UserCar extends Car {

    double rotationRate = Math.PI / 70; // how fast it turns
    static double maxSpeed;
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
    public void boost() {
        maxSpeed = 6;
        boostCounter = 300;
        powerUpFont = "POWERED UP!";
    }

    public void powerDown() {
        maxSpeed = 4;
        powerUpFont = "";
    }
}
