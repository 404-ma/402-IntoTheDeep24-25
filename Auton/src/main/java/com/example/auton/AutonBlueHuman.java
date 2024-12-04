package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing blue, and you want to move the three blue samples into the observation zone
public class AutonBlueHuman implements Auton {
    // The initial position of the robot, relative to some origin
    public void Run(Runner runner){
        // Note that the robot won't actually be aligned with the center of the samples,
        // but will actually be a little offset. This ensures that we don't accidentally hit something else
        // go to the first sample
        runner.move(b -> b.splineTo(new Vector2d(-44,16), Math.toRadians(90)));
        // take it to the observation zone
        runner.move(b -> b.lineToY(60));
        // Rinse and repeat
        // Move towards the second sample
        runner.move(b -> b.lineToY(8));
        runner.move(b -> b.splineTo(new Vector2d(-54,16), Math.toRadians(90)));
        // take it to the observation zone
        runner.move(b -> b.lineToY(60));
        // Move towards the third sample
        runner.move(b -> b.lineToY(8));
        runner.move(b -> b.splineTo(new Vector2d(-60,16), Math.toRadians(90)));
        // take it to the observation zone
        runner.move(b -> b.lineToY(60));
    }
    public Pose2d getStartingPose() {
        return new Pose2d(-72, 0, 0);
    }
}
