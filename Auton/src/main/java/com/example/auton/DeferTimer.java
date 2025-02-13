package com.example.auton;

public class DeferTimer {
    long endTime;

    public DeferTimer(double duration) {
        endTime = System.currentTimeMillis() + (long) (duration * 1000.0);
    }

    public boolean IsFinished() {
        boolean done = System.currentTimeMillis() >= endTime;
        if (done)
            endTime = Long.MAX_VALUE;
        return done;
    }

    public void Wait() {
        while (!IsFinished()) ;
    }
}
