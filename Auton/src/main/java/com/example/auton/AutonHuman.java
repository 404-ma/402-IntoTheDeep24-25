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
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(33, -43)));
        runner.move(b -> b.turn(Math.toRadians(-198)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(33, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(45, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(45, -53)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(45, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(54, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(54, -53)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(54, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(60, -7)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(60, -53)));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(8.5, -64, Math.toRadians(270));
    }
}

