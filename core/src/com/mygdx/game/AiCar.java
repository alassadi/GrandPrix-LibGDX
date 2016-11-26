package com.mygdx.game;


    /**
     * Created by fatih on 2016-11-22.
     */
    public class AiCar extends Car {


        public AiCar(String textureFileName, float x, float y){
            super(textureFileName, x, y);

            getSprite().setOriginCenter();
        }
        public void updatePositionFromSpeed() {
            //First call the method "updatePositionFromSpeed" in the Car super class
            super.updatePositionFromSpeed();
            // Since we control the track of the ai car, no need to use stop at the edge method.
            stopAtEdge();
        }

        public void Route() {

            if (getSprite().getX() > 110 && getSprite().getY() == 660) {
                getSprite().setRotation(0);
                setSpeedX(4);
                setSpeedY(0);

            }
            if (getSprite().getX() > 950 && getSprite().getY() == 660) {

                getSprite().setRotation(-30);
                setSpeedX(2);
                setSpeedY(-1);
            }
            if (getSprite().getX() > 1000 && getSprite().getY() == 630) {

                getSprite().setRotation(-60);
                setSpeedX(1);
                setSpeedY(-2);
            }
            if (getSprite().getX() > 1020 && getSprite().getY() == 590) {

                getSprite().setRotation(-90);
                setSpeedY(-2);
                setSpeedX(0);
            }
            if (getSprite().getY() == 530 && getSprite().getX() > 1020) {
                getSprite().setRotation((-120));
                setSpeedX(-1);
                setSpeedY(-2);
            }
            if (getSprite().getY() == 510 && getSprite().getX() > 1000) {
                getSprite().setRotation(-150);
                setSpeedX(-2);
                setSpeedY(-1);
            }
            if (getSprite().getY() == 480 && getSprite().getX() > 950) {
                getSprite().setRotation(-180);
                setSpeedX(-3);
                setSpeedY(0);
            }
            if (getSprite().getY() == 480 && getSprite().getX() > 940) {
                getSprite().setRotation(-150);
                setSpeedX(-2);
                setSpeedY(-1);
            }
            if (getSprite().getY() == 450 && getSprite().getX() > 890) {
                getSprite().setRotation(-120);
                setSpeedX(-1);
                setSpeedY(-2);
            }
            if (getSprite().getY() == 420 && getSprite().getX() > 860) {
                getSprite().setRotation(-90);
                setSpeedX(0);
                setSpeedY(-3);
            }
            if (getSprite().getY() == 300 && getSprite().getX() > 870) {
                getSprite().setRotation(-60);
                setSpeedX(+1);
                setSpeedY(-2);
            }
            if (getSprite().getY() == 250 && getSprite().getX() > 900) {
                getSprite().setRotation(-30);
                setSpeedX(+2);
                setSpeedY(-1);
            }
            if (getSprite().getY() == 180 && getSprite().getX() > 850) {
                getSprite().setRotation(-60);
                setSpeedX(1);
                setSpeedY(-2);
            }
            if (getSprite().getY() == 150 && getSprite().getX() > 800) {
                getSprite().setRotation(-90);
                setSpeedX(0);
                setSpeedY(-3);
            }
            if (getSprite().getY() == 120 && getSprite().getX() > 780) {
                getSprite().setRotation(-120);
                setSpeedX(-1);
                setSpeedY(-2);
            }
            if (getSprite().getY() == 100 && getSprite().getX() > 700) {
                getSprite().setRotation(-150);
                setSpeedX(-2);
                setSpeedY(-1);
            }
            if (getSprite().getY() == 60 && getSprite().getX() > 680) {
                getSprite().setRotation(-180);
                setSpeedX(-4);
                setSpeedY(0);
            }
            if (getSprite().getY() == 60 && getSprite().getX() < 600) {
                getSprite().setRotation(150);
                setSpeedX(-2);
                setSpeedY(1);
            }
            if (getSprite().getY() == 80 && getSprite().getX() < 570) {
                getSprite().setRotation(120);
                setSpeedX(-1);
                setSpeedY(2);

            }
            if (getSprite().getY() == 100 && getSprite().getX() < 550) {
                getSprite().setRotation(90);
                setSpeedX(0);
                setSpeedY(3);

            }
            if (getSprite().getY() >280 && getSprite().getX() == 549) {
                getSprite().setRotation(120);
                setSpeedX(-1);
                setSpeedY(2);

            }
            if (getSprite().getY() < 350 && getSprite().getX() ==540) {
                getSprite().setRotation(150);
                setSpeedX(-2);
                setSpeedY(1);

            }
            if (getSprite().getY() <380 && getSprite().getX() == 510) {
                getSprite().setRotation(180);
                setSpeedX(-3);
                setSpeedY(0);

            }
            if (getSprite().getY() <390 && getSprite().getX() == 300) {
                getSprite().setRotation(210);
                setSpeedX(-2);
                setSpeedY(-1);

            }
            if (getSprite().getY() <380 && getSprite().getX() == 240) {
                getSprite().setRotation(180);
                setSpeedX(-3);
                setSpeedY(0);

            }
            if (getSprite().getY() <380 && getSprite().getX() == 150) {
                getSprite().setRotation(150);
                setSpeedX(-2);
                setSpeedY(1);

            }
            if (getSprite().getY() <450 && getSprite().getX() == 100) {
                getSprite().setRotation(120);
                setSpeedX(-1);
                setSpeedY(2);

            }
            if (getSprite().getY() <500 && getSprite().getX() == 80) {
                getSprite().setRotation(90);
                setSpeedX(0);
                setSpeedY(3);

            }
            if (getSprite().getY() >600 && getSprite().getX() ==80) {
                getSprite().setRotation(60);
                setSpeedX(1);
                setSpeedY(2);

            }
            if (getSprite().getY() >620 && getSprite().getX() == 100) {
                getSprite().setRotation(30);
                setSpeedX(2);
                setSpeedY(1);

            }
        /*if (getSprite().getY() ==660 && getSprite().getX() > 150) {
            getSprite().setRotation(0);
            setSpeedX(4);
            setSpeedY(0);

        }*/










        }
    }


