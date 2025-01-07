package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonHuman implements Auton {
    public void Run(Runner runner, IGrabber grabber) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(6, -43)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-34));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.2).strafeTo(new Vector2d(36, -48)));
        grabber.GoToPickupHeight(true);
        runner.move(b -> b.waitSeconds(0.1).lineToY(-8));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(48, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-61));
        while (grabber.CheckForBrake()) ;
        grabber.Close();
        runner.move(b -> b.waitSeconds(0.2));
        grabber.GoToLowBar();
        while (grabber.CheckForBrake()) ;
        grabber.GoToHighBar();
        runner.move(b -> b.strafeTo(new Vector2d(4, -50)));
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-32));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.lineToYConstantHeading(-16));
//        runner.move(b -> b.strafeToConstantHeading(new Vector2d8, -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(4));
//        runner.move(b -> b.lineToYConstantHeading(-16));
//        runner.move(b -> b.strafeToConstantHeading(new Vector2d(64., -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(4));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(12, -64, Math.toRadians(270));
    }
}
