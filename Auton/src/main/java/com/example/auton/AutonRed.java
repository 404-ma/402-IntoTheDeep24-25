package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class AutonRed implements Auton {
    public void Run(Runner runner) {
        runner.move(b -> b
                .setReversed(true).lineToX(26)
                .turnTo(Math.toRadians(-180)));
        runner.move(b -> b
                .splineTo(new Vector2d(25, -26), Math.toRadians(-180)));
        runner.move(b -> b
                .lineToX(-3).turnTo(Math.toRadians(-180)));
        runner.move(b -> b
                .splineTo(new Vector2d(25, -24), Math.toRadians(-180)));
        runner.move(b -> b
                .lineToX(-3).turnTo(Math.toRadians(-180))
                .splineTo(new Vector2d(25, -22), Math.toRadians(-180))
                );
    }

    public Pose2d getStartingPose() {
        return new Pose2d(70, 0, 0);
    }
}
