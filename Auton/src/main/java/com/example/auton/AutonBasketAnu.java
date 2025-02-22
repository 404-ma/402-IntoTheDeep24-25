package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonBasketAnu implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        // Initialize robot starting position
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -64)));
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -43)));
        beak.DrivePosition();
        while (grabber.CheckForBrake());

        // Move to drop off first sample
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -33)));
        grabber.HangSample();
        while (grabber.CheckForBrake());

        // Reset grabber and move to next position
        runner.move(b -> b.lineToYConstantHeading(-43));
        grabber.SetHeight(0);
        grabber.Close();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-33, -48)));
        runner.move(b -> b.turn(Math.toRadians(180)));

        // Move to pick up second sample
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-44.5, -35)));
        beak.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();

        // Move to drop off second sample
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-33, -20)));
        beak.OpenBeak();
        new DeferTimer(0.5).Wait();

        // Move to pick up third sample
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-44.5, -15)));
        beak.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();

        // Move to drop off third sample and park
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-33, 0)));
        beak.OpenBeak();
        new DeferTimer(0.5).Wait();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, 10))); // Park Position
    }

    public Pose2d getStartingPose() {
        return new Pose2d(-12.5, -64, Math.toRadians(270));
    }
}
