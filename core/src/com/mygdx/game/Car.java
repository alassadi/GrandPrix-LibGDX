package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class Car {

    private int speedX = 0;
    private int speedY = 0;
    private double angle = Math.PI * 2;
    private float velocity = 0;
    private Sprite sprite;
    private final int SHRINK_COLLISION_RADIUS;

    double rotationRate = Math.PI / 70; // how fast it turns



    public Car(String textureFileName, float x, float y) {
        sprite = new Sprite(new Texture(textureFileName));
        sprite.setSize(60, 40);
        sprite.setX(x);
        sprite.setY(y);
        SHRINK_COLLISION_RADIUS = 13;

    }

    public Sprite getSprite() {
        return sprite;
    }


    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int ySpeed) {
        this.speedY = ySpeed;
    }

    public float getY() {
        return sprite.getY();
    }

    public float getX() {
        return sprite.getX();
    }

    public void setY(float y) {
        sprite.setY(y);
    }

    public void setX(float x) {
        sprite.setX(x);
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void updatePosition() {
        if (getVelocity() == 0)
            return;
        setX(getX() + velocity * (float) Math.cos(angle));
        setY(getY() + velocity * (float) Math.sin(angle));
        stopAtEdge();
    }

    public void setSpeedX(int xSpeed) {
        this.speedX = xSpeed;
    }

    public void updatePositionFromSpeed() {
        if (getSpeedX() == 0 && getSpeedY() == 0) {
            return;
        }
        setX(getX() + getSpeedX());
        setY(getY() + getSpeedY());
    }

    // Making sure that the user car cannot go outside the borders of the game window.
    public void stopAtEdge() {
        if (getX() > Gdx.graphics.getWidth() - sprite.getWidth())
            setX(Gdx.graphics.getWidth() - sprite.getWidth());
        if (getX() < 0)
            setX(0);
        if (getY() > Gdx.graphics.getHeight() - sprite.getHeight())
            setY(Gdx.graphics.getHeight() - sprite.getHeight());
        if (getY() < 0)
            setY(0);
    }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(
                getSprite().getX() + SHRINK_COLLISION_RADIUS,
                getSprite().getY() + SHRINK_COLLISION_RADIUS,
                getSprite().getWidth() - (2 * SHRINK_COLLISION_RADIUS),
                getSprite().getHeight() - (2 * SHRINK_COLLISION_RADIUS));
    }

    public boolean collidesWith(Rectangle otherRect) {
        return getCollisionRectangle().overlaps(otherRect);
    }



    public void deceleration(float decelerationRate) {
        if (getVelocity() > 0)
        {
            setVelocity(getVelocity() - decelerationRate);
            if (getVelocity() < 0) {
                setVelocity(0);
            }
        }
    }

    public void accelerate(double maxSpeed, float accelerationRate) {
        if (this.getVelocity() < maxSpeed)
            setVelocity(this.getVelocity() + accelerationRate);
    }

    public void breaks(float breaksRate) {
        if (getVelocity() > 0)
            setVelocity(getVelocity() - breaksRate);
        if (getVelocity() < 0) {
            setVelocity(0);
        }
    }

    public void turnLeft() {
        setAngle(getAngle() + rotationRate);
        getSprite().setRotation((float) getAngle() * MathUtils.radiansToDegrees);
        if (getVelocity() > 2)
            setVelocity(getVelocity() - (float)0.05);
    }

    public void turnRight() {
        setAngle(getAngle() - rotationRate);
        getSprite().setRotation((float) getAngle() * MathUtils.radiansToDegrees);
        if (getVelocity() > 2)
            setVelocity(getVelocity() - (float)0.05);
    }

    public void fullStop() {
        setVelocity(0);
    }

    public void slowOnGrass() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (getVelocity()>1) {
                setVelocity(getVelocity()-(float)0.07);
            }
        }
    }

    public void spinOnStain(int leftOrRight)
    {
        if (leftOrRight<1) {
            setAngle(getAngle() - rotationRate*0.75);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                getSprite().setRotation((float) getAngle() * (float) 59.3);
            }
            else {
                //tempAngle = getAngle() + rotationRate;
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    setAngle(getAngle() + rotationRate*0.75);
                    getSprite().setRotation((float) getAngle() * (float) 59.3);
                }
            }
        }
    }

    public void forceBreak() {
        while (getVelocity() >= 0) {
            setVelocity(getVelocity() - (float) 0.05);
            if (getVelocity()<0)
            {
                setVelocity(0);
            }
        }
    }
}
