package com.mygdx.game;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by fatih on 2016-11-22.
 */
public class AiCar extends Car {
    int i = 0;
    Random r = new Random();
    float destination=0;

    public AiCar(String textureFileName, float x, float y) {
        super(textureFileName, x, y);
        getSprite().setOriginCenter();
    }

    public void updatePositionFromSpeed() {
        //First call the method "updatePositionFromSpeed" in the Car super class
        super.updatePositionFromSpeed();
        // Since we control the track of the ai car, no need to use stop at the edge method.
        stopAtEdge();
    }

    public void Route(ArrayList<Vector2> waypoints, double speed, double rotationRate) {

//  This is used just to check a bug where the aiCars are not turning in the right direction
//        if (i >=9&&i <=11) {
//            System.out.print("angle: " + getAngle() * MathUtils.radiansToDegrees + ", ");
//            System.out.print("Destination: " + ((float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f) + "; ");
//        }
        destination = (float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f;
        if ((float) getAngle() * MathUtils.radiansToDegrees < destination //180/ 270
            //        && Math.abs((float) getAngle() * MathUtils.radiansToDegrees / ((float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f))>0.5
                ) {
            setAngle(getAngle() + rotationRate);
            getSprite().setRotation((float) getAngle() * MathUtils.radiansToDegrees);

            if (getVelocity() > 2)
                setVelocity(getVelocity() - (float)0.05);
        }
        else if ((float) getAngle() * MathUtils.radiansToDegrees > destination
            //        && Math.abs((float) getAngle() * MathUtils.radiansToDegrees / ((float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f))>0.5
                ) {

            setAngle(getAngle() - rotationRate);
            getSprite().setRotation((float) getAngle() * MathUtils.radiansToDegrees);

            if (getVelocity() > 2)
                setVelocity(getVelocity() - (float)0.05);
        }
        else if ((float) getAngle() * MathUtils.radiansToDegrees == destination) {
            setAngle(getAngle());
            getSprite().setRotation((float) getAngle() * MathUtils.radiansToDegrees);
        }



//        setAngle((float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f);
//        getSprite().setRotation((float) ((Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f));
        accelerate(speed, (float) 0.055);
//        }


//        if (Math.abs(waypoints.get(i).x - getSprite().getX())>35 || Math.abs(waypoints.get(i).y - getSprite().getY())>35) {
//
//        }
        if (Math.abs(waypoints.get(i).x - getSprite().getX()) < 45 && Math.abs(waypoints.get(i).y - getSprite().getY()) < 45) {
            i++;
            System.out.print(i);
        }
        if (i == waypoints.size()) {
            i = 0;
        }

    }


//    public void Route(ArrayList<Vector2> waypoints, double speed) {
//        setAngle((float) (Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f);
//        getSprite().setRotation((float) ((Math.atan2(waypoints.get(i).x - getSprite().getX(), -(waypoints.get(i).y - getSprite().getY())) * 180.0d / Math.PI) + 270.0f));
//        accelerate(speed, (float) 0.05);
////        if (Math.abs(waypoints.get(i).x - getSprite().getX())>35 || Math.abs(waypoints.get(i).y - getSprite().getY())>35) {
////
////        }
//        if (Math.abs(waypoints.get(i).x - getSprite().getX()) < 50 && Math.abs(waypoints.get(i).y - getSprite().getY()) < 50) {
//            i++;
//            //System.out.print(i);
//        }
//        if (i == waypoints.size()) {
//            i = 0;
//        }
//
//    }
}