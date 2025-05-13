package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonHangAndParkObservation implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        grabber.GoToHighBar();
        runner.move(b -> b.lineToYConstantHeading(-43));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-34));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        grabber.SetHeight(0);
        runner.move(b -> b.lineToYConstantHeading(-43));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(58, -53)));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(8.5, -64, Math.toRadians(270));
    }
}

