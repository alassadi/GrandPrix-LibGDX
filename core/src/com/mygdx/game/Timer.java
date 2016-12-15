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
    //private float deltaTime = 0;
    private final long startTime;
    private long elapsedTime;


    String time;

    public Timer() {
        timerFont = new BitmapFont();
        timerFont.setColor(Color.WHITE);
        batch = new SpriteBatch();
        startTime = TimeUtils.millis();

    }

    public void drawTime(SpriteBatch batch) {
        elapsedTime = TimeUtils
                .timeSinceMillis(startTime);
        time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)), elapsedTime % 100
        );

        timerFont.draw(batch, time, 30, 40);
    }

//    public void drawTime(SpriteBatch batch) {
//        elapsedTime = TimeUtils.timeSinceMillis(startTime);
//        str = Long.toString(elapsedTime);
//        timerFont.draw(batch, str, 100, 100);
//    }
}
