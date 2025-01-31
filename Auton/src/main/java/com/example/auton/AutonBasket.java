package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonBasket implements Auton {
    public void Run(Runner runner, IGrabber grabber) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-6, -43)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-37));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.2).strafeTo(new Vector2d(-36, -48)));
        grabber.SetHeight(0);
        grabber.Close();
        runner.move(b -> b.waitSeconds(0.1).lineToY(-8));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-48, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
        runner.move(b -> b.turnTo(Math.toRadians(45)));
        runner.move(b -> b.lineToX(-54));
        runner.move(b -> b.lineToX(-48));
        runner.move(b -> b.turnTo(Math.toRadians(90)));
        runner.move(b -> b.lineToYConstantHeading(-16));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-58.5, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
        runner.move(b -> b.lineToYConstantHeading(-16));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-65, -16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(-12, -70, Math.toRadians(270));
    }
}
