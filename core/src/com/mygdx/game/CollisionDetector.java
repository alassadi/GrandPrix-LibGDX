package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Rossy on 1/2/2017.
 */
public class CollisionDetector {
    int leftOrRight;
    Random r = new Random();
    public void checkObstacles(UserCar userCar, ArrayList<Obstacle> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            if (userCar.collidesWith(obstacles.get(i).getCollisionRectangle())) {
                userCar.fullStop();
            }
        }
    }

    public void checkGrass(UserCar userCar, ArrayList<Obstacle> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            if (userCar.collidesWith(obstacles.get(i).getCollisionRectangle())) {
                userCar.slowOnGrass();
            }
        }
    }
    public void checkOilStains(UserCar userCar, ArrayList<Obstacle> obstacles) {
        for (int i = 0; i < obstacles.size(); i++) {
            leftOrRight = r.nextInt(2);
            if (userCar.collidesWith(obstacles.get(i).getCollisionRectangle())) {

                userCar.spinOnStain(leftOrRight, 0.75);
                //userCar.setAngle(userCar.tempAngle);
            }
        }
    }
}
