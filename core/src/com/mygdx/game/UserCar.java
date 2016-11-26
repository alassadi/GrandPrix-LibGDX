package com.mygdx.game;

/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class UserCar extends Car{


    double rotationRate = Math.PI / 50;
    private double maxSpeed;

    public UserCar(String textureFileName, float x, float y, double maxSpeed){
        //First call the parent class constructor (Car)
        super(textureFileName, x, y);
        this.maxSpeed = maxSpeed;

        //We want to rotate the graphics around the center when the user car turns up and down.
        getSprite().setOriginCenter();
    }

    /*public void updatePositionFromSpeed(){
        //First call the method "updatePositionFromSpeed" in the Car super class
        super.updatePositionFromSpeed();
        // Making sure that the user car cannot go out the borders
        stopAtEdge();

    }
    */
    public void updatePosition()
    {
        super.updatePosition();
    }
    public void deceleration()
    {
        if (getVelocity() > 0)
            setVelocity(getVelocity()-(float)0.02);
    }

    public void accelerate()
    {
        if (this.getVelocity()<maxSpeed)
            setVelocity(this.getVelocity() + (float) 0.05);
    }
    public void breaks()
    {
        if (getVelocity() > 0)
            setVelocity(getVelocity() - (float) 0.05);
    }
    public void turnLeft()
    {
        setAngle(getAngle() + rotationRate);
        getSprite().setRotation((float)getAngle() * (float)57.3);
        deceleration();
    }
    public void turnRight()
    {
        setAngle(getAngle() - rotationRate);
        getSprite().setRotation((float)getAngle() * (float)57.3);
        deceleration();
    }

}
