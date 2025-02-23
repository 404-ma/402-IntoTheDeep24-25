package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonBasket implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -43)));
        beak.DrivePosition();
        while (grabber.CheckForBrake());
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -33)));
        grabber.HangSample();
        while (grabber.CheckForBrake());
        runner.move(b -> b.lineToYConstantHeading(-43));
        grabber.SetHeight(0);
        grabber.Close();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-33, -48)));
        runner.move(b -> b.turn(Math.toRadians(180)));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-44.5, -35)));
        beak.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();
    }

    public Pose2d getStartingPose() {
        return new Pose2d(-12.5, -64, Math.toRadians(270));
    }
}
