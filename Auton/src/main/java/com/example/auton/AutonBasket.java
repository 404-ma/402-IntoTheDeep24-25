package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonBasket implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-8, -43)));
        while (grabber.CheckForBrake()) ;
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToYConstantHeading(-43));
        runner.move(b -> b.waitSeconds(0.2).strafeTo(new Vector2d(-35, -48)));
        grabber.SetHeight(0);
        grabber.Close();
        runner.move(b -> b.waitSeconds(0.1).lineToY(-13));
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-46.25, -13)));
        beak.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();
        runner.move(b -> b.strafeTo(new Vector2d(-35,-13)));
        runner.move(b -> b.turnTo(Math.toRadians(226)));
        runner.move(b -> b.strafeTo(new Vector2d(-54,-55.5)));
        grabber.GoToHighBar(); //THIS NEEDS TO GO TO HIGH BASKET, NOT HIGH BAR
        while (grabber.CheckForBrake());
        bucket.DumpSample();
        new DeferTimer(0.5).Wait();
        runner.move(b -> b.strafeTo(new Vector2d(-45, -45)));
        grabber.SetHeight(0);
        runner.move(b -> b.turnTo(Math.toRadians(270)));
        runner.move(b -> b.strafeTo(new Vector2d(-46.25, -13)));
        runner.move(b -> b.strafeTo(new Vector2d(-58,-13)));
        beak.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();
        runner.move(b -> b.strafeTo(new Vector2d(-46.25,-13)));
        runner.move(b -> b.turnTo(Math.toRadians(226)));
        runner.move(b -> b.strafeTo(new Vector2d(-54,-55.5)));
        grabber.GoToHighBar(); //THIS NEEDS TO GO TO HIGH BASKET, NOT HIGH BAR
        while (grabber.CheckForBrake());
        bucket.DumpSample();
        new DeferTimer(0.5).Wait();
//        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
//        runner.move(b -> b.turnTo(Math.toRadians(45)));
//        runner.move(b -> b.lineToX(-54));
//        beak.OpenBeak();
//        runner.move(b -> b.lineToX(-48));
//        runner.move(b -> b.turnTo(Math.toRadians(90)));
//        runner.move(b -> b.lineToYConstantHeading(-16));
//        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-58.5, -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
//        runner.move(b -> b.lineToYConstantHeading(-16));
//        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-65, -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToYConstantHeading(-54));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(-12, -64, Math.toRadians(270));
    }
}
