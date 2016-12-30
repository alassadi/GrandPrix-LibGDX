package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rossy on 30.11.2016 г..
 */
public class Timer {
    SpriteBatch batch;
    private BitmapFont timerFont;
<<<<<<< HEAD
    private final long startTime;
    private long elapsedTime;
=======
    //private float deltaTime = 0;
    private final long startTime;
    private long elapsedTime;


>>>>>>> 2d65a0e440925f8ee125da67e18df5ce80f39f9d
    String time;

    public Timer() {
        timerFont = new BitmapFont();
        timerFont.setColor(Color.WHITE);
        batch = new SpriteBatch();
        startTime = TimeUtils.millis();
<<<<<<< HEAD
    }

    public void drawTime(SpriteBatch batch) {
        elapsedTime = TimeUtils.timeSinceMillis(startTime);
        time = String.format("Elapsed Time: %02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)), elapsedTime % 100
=======

    }

    public void drawTime(SpriteBatch batch) {
        elapsedTime = TimeUtils
                .timeSinceMillis(startTime);
        time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)), elapsedTime % 100
>>>>>>> 2d65a0e440925f8ee125da67e18df5ce80f39f9d
        );

        timerFont.draw(batch, time, 30, 40);
    }
<<<<<<< HEAD
=======

//    public void drawTime(SpriteBatch batch) {
//        elapsedTime = TimeUtils.timeSinceMillis(startTime);
//        str = Long.toString(elapsedTime);
//        timerFont.draw(batch, str, 100, 100);
//    }
>>>>>>> 2d65a0e440925f8ee125da67e18df5ce80f39f9d
}
