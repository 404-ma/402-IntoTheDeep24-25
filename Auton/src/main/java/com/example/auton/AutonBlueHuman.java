package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing blue, and you want to move the three blue samples into the observation zone
public class AutonBlueHuman implements Auton {
    // The initial position of the robot, relative to some origin
    public void Run(Runner runner){
        // Note that the robot won't actually be aligned with the center of the samples,
        // but will actually be a little offset. This ensures that we don't accidentally hit something else
        // go to the first two samples
        runner.move(b -> b.splineTo(new Vector2d(-53,16), Math.toRadians(90)));
        // take it to the observation zone
        runner.move(b -> b.lineToY(60));
        // Rinse and repeat
        // Move towards the last sample
        runner.move(b -> b.lineToY(8));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-63, 16)).waitSeconds(0.1));
        // take it to the observation zone
        runner.move(b -> b.lineToY(60));
        // auton done, i guess; go back to the starting position
        runner.move(b -> b.strafeTo(new Vector2d(-50, getStartingPose().position.y)));
        runner.move(b -> b.turnTo(0));
        runner.move(b -> b.lineToXConstantHeading(getStartingPose().position.x));
    }
    public Pose2d getStartingPose() {
        return new Pose2d(-64.5, 12, 0);
    }
}
