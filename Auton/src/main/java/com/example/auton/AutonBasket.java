package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonBasket implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak, IBucket bucket) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -43)));
        beak.DrivePosition();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -32.5)));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        grabber.Open();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-12.5, -45)));
        //grabber.ToggleClaw();
        grabber.SetHeight(0);
        //grabber.Close();
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-54.5, -39.4)));
        runner.move(b -> b.turn(Math.toRadians(-198)));
        //runner.move(b -> b.strafeToConstantHeading(new Vector2d(-55, -35)));
        //runner.move(b -> b.strafeToConstantHeading(new Vector2d(-44.5, -35.5)));
        beak.PickupMiddleAuton();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();
        beak.OpenBeak();
        new DeferTimer(0.2).Wait();
        beak.DrivePosition();
        runner.move(b -> b.strafeTo(new Vector2d(-63, -54.7))); //Basket
        runner.move(b -> b.turn(Math.toRadians(-47)));
        grabber.SetHeight(7300);
        while (grabber.CheckForBrake()) ;
        bucket.DumpSample();
        new DeferTimer(2).Wait();
        grabber.SetHeight(0);
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-60, -41.25))); // Block 2 position?
        bucket.PrepForCatch();
        //New Code Past Here
        runner.move(b -> b.turn(Math.toRadians(47)));
        beak.PickupMiddleAuton();
        new DeferTimer(1).Wait();
        beak.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucket.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beak.SuplexSample();
        new DeferTimer(1).Wait();
        beak.OpenBeak();
        new DeferTimer(0.2).Wait();
        beak.DrivePosition();
        runner.move(b -> b.strafeTo(new Vector2d(-63.2, -54.9))); //Basket
        runner.move(b -> b.turn(Math.toRadians(-47)));
        grabber.SetHeight(7300);
        while (grabber.CheckForBrake()) ;
        bucket.DumpSample();
        new DeferTimer(2).Wait();
        grabber.SetHeight(0);
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.strafeToConstantHeading(new Vector2d(43, -55))); // Block 3 position
    }

    public Pose2d getStartingPose() {
        return new Pose2d(-12.5, -64, Math.toRadians(270));
    }
}
