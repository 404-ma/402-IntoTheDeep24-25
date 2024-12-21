package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing blue, and you want to move the three blue samples into the observation zone
public class AutonBlueBasket implements Auton {
    public void Run(Runner runner, IGrabber grabber) {
        grabber.GoToHighBar();
        runner.move(b -> b.lineToYConstantHeading(43));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(39));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        grabber.Open();
        runner.move(b -> b.waitSeconds(0.5).strafeTo(new Vector2d(36, 48)));
        grabber.SetHeight(0);
        grabber.Close();
        runner.move(b -> b.waitSeconds(0.1).lineToY(8));
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(50, 16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(54));
        runner.move(b -> b.turnTo(Math.toRadians(225)));
        runner.move(b -> b.lineToX(56));
        runner.move(b -> b.strafeTo(new Vector2d(36, 8)));
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(58, 16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(54));
        runner.move(b -> b.turnTo(Math.toRadians(225)));
        runner.move(b -> b.strafeTo(new Vector2d(36, 8)));
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(64.5, 16)));
        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(54));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(12, 64, Math.toRadians(90));
    }
}
