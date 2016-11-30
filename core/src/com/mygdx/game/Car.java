package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
/**
 * Created by Abdulrahman on 11/22/2016.
 */
public class Car {

    private int speedX = 0;
    private int speedY = 0;
    private double angle = Math.PI*2 ;
    private float velocity = 0;
    private Sprite sprite;
    private final int SHRINK_COLLISION_RADIUS;

    public Car (String textureFileName, float x, float y)
    {
        sprite = new Sprite(new Texture(textureFileName));
        sprite.setSize(60,40);
        sprite.setX(x);
        sprite.setY(y);
        SHRINK_COLLISION_RADIUS =13;

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
    public void setVelocity(float velocity)
    {
        this.velocity = velocity;
    }
    public float getVelocity ()
    {
        return velocity;
    }
    public double getAngle ()
    {
        return angle;
    }
    public void setAngle (double angle)
    {
        this.angle = angle;
    }
    public void updatePosition()
    {
        if(getVelocity()==0)
            return;
        setX(getX() + velocity * (float)Math.cos(angle));
        setY(getY() + velocity * (float)Math.sin(angle));
        stopAtEdge();
    }

    public void setSpeedX(int xSpeed) {
        this.speedX = xSpeed;
    }

    public void updatePositionFromSpeed(){
        if (getSpeedX()==0 && getSpeedY()==0) {
            return;
        }
        setX(getX()+getSpeedX());
        setY(getY()+getSpeedY());

    }

    // Making sure that the user car cannot go outside the borders of the game window.

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

    public Rectangle getCollisionRectangle(){
        return new Rectangle(
                getSprite().getX()+SHRINK_COLLISION_RADIUS,
                getSprite().getY()+SHRINK_COLLISION_RADIUS,
                getSprite().getWidth()-(2*SHRINK_COLLISION_RADIUS),
                getSprite().getHeight()-(2*SHRINK_COLLISION_RADIUS));
    }

    public boolean collidesWith(Rectangle otherRect){
        return getCollisionRectangle().overlaps(otherRect);
    }
}
