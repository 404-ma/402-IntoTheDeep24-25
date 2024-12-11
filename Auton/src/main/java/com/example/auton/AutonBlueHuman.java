package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing blue, and you want to move the three blue samples into the observation zone
public class AutonBlueHuman implements Auton {
    public void Run(Runner runner){
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-36, 8)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-54, 16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(60));
        runner.move(b -> b.lineToYConstantHeading(8));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-62.5, 16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(60));
    }
    public Pose2d getStartingPose() {
        return new Pose2d(-12, 64, Math.toRadians(-90));
    }
}
