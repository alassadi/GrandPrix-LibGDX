package com.mygdx.game;

/**
 * Created by fatih on 2016-11-29.
 */
public class Timer {

    private final long nanosPerMilli = 1000000;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = true;

    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    //get elapsed milliseconds
    public long getElapsedMilliseconds() {
        long elapsed;
        if (running) {
            elapsed = System.nanoTime() - startTime;
        } else {
            elapsed = stopTime - startTime;
        }
        return elapsed / nanosPerMilli;
    }

    public String formatTime(final long millis) {
        int minutesComponent = (int) (millis / (1000 * 60));
        int secondsComponent = (int) ((millis / 1000) % 60);
        int hunderdthsComponent = (int) ((millis / 10) % 100);
        String paddedMinutes = String.format("%02d", minutesComponent);
        String paddedSeconds = String.format("%02d", secondsComponent);
        String paddedHunderths = String.format("%02d", hunderdthsComponent);
        String formattedTime;
        if (millis > 0 && millis < 3600000) {
            formattedTime = paddedMinutes + ":" + paddedSeconds + ":" + paddedHunderths;
        } else {
            formattedTime = 59 + ":" + 59 + ":" + 99;
        }
        return formattedTime;
    }

    //get formatted elapsed time
    public String getElapsed() {
        String timeFormatted = "";
        timeFormatted = this.formatTime(this.getElapsedMilliseconds());
        return timeFormatted;
    }
}
