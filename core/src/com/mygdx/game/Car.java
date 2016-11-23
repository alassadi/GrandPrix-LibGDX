package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class Car {

    private int speedX = 0;
    private int speedY = 0;
    private Sprite sprite;


    public Car (String textureFileName, float x, float y)
    {
        sprite = new Sprite(new Texture(textureFileName));
        sprite.setSize(60,40);
        sprite.setX(x);
        sprite.setY(y);
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

    public void setSpeedX(int xSpeed) {
        this.speedX = xSpeed;
    }

    public void updatePositionFromSpeed(){
        if (getSpeedX()==0 && getSpeedY()==0)
            return;
        setX(getX()+getSpeedX());
        setY(getY()+getSpeedY());

        //What should we do when we get to the screen edge? Bounce or stop?
        //bounceAtEdge();
        //stopAtEdge();
    }
    public void stopAtEdge(){
        if (getX()>Gdx.graphics.getWidth()-sprite.getWidth())
            setX(Gdx.graphics.getWidth()-sprite.getWidth());
        if (getX()<0)
            setX(0);
        if (getY()>Gdx.graphics.getHeight()-sprite.getHeight())
            setY(Gdx.graphics.getHeight()-sprite.getHeight());
        if (getY()<0)
            setY(0);
    }
}
