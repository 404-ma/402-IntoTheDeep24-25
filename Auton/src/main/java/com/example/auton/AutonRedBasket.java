package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonRedBasket implements Auton {
    public void Run(Runner runner) {
        // Note that the robot won't actually be aligned with the center of the samples,
        // but will actually be a little offset. This ensures that we don't accidentally hit something else
        // go to the first two samples
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(53, 8)));
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        // take it to the observation zone
        runner.move(b -> b.lineToYConstantHeading(60));
        // Rinse and repeat
        // Move towards the last sample
        runner.move(b -> b.lineToYConstantHeading(8));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(63, 16)).waitSeconds(0.1));
        // take it to the observation zone
        runner.move(b -> b.lineToYConstantHeading(60));
        // auton done, i guess; go back to the starting position
        runner.move(b -> b.strafeTo(new Vector2d(50, getStartingPose().position.y)));
        runner.move(b -> b.turnTo(0));
        runner.move(b -> b.lineToXConstantHeading(getStartingPose().position.x));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(64.5, 12, Math.toRadians(0));
    }
}
