package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonHuman implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        grabber.GoToHighBar();
        runner.move(b -> b.lineToYConstantHeading(-43));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-34));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        grabber.SetHeight(0);
        runner.move(b -> b.lineToYConstantHeading(-43));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(36, -43)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(36, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(48, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(48, -55)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(48, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(57, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(57, -55)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(57, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(63, -15)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(63, -55)));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(12, -64, Math.toRadians(270));
    }
}

