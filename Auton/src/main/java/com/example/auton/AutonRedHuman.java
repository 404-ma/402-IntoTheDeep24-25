package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonRedHuman implements Auton {
    public void Run(Runner runner) {
        runner.move(b -> b.lineToY(-48));
        runner.move(b -> b.waitSeconds(0.1).strafeTo(new Vector2d(36, -48)));
        runner.move(b -> b.waitSeconds(0.1).lineToY(-8));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(50, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
        runner.move(b -> b.turnTo(Math.toRadians(135)));
        runner.move(b -> b.lineToX(56));
        runner.move(b -> b.strafeTo(new Vector2d(36, -8)));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(58, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
        runner.move(b -> b.turnTo(Math.toRadians(135)));
        runner.move(b -> b.strafeTo(new Vector2d(36, -8)));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(66.5, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
    }
    public Pose2d getStartingPose() {
        return new Pose2d(12, -64, Math.toRadians(270));
    }
}
