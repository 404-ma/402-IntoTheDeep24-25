/*package com.example.auton;

import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;

import org.jetbrains.annotations.NotNull;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonHuman implements Auton {
    public void Run(Runner runner, IGrabber grabber) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeTo(new Vector2d(8, -43)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToY(-34));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.2).lineToY(-40));
        grabber.GoToPickupHeight();
        runner.move(b -> b.splineTo(new Vector2d(36, -32), Math.toRadians(90)));
        runner.move(b -> b.lineToY(-8));
        runner.move(b -> b.strafeTo(new Vector2d(48, -16)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.1).lineToY(-60));
        grabber.Close();
        runner.move(b -> b.waitSeconds(0.2));
        grabber.GoToLowBar();
        while (grabber.CheckForBrake()) ;
        grabber.GoToHighBar();
        runner.move(b -> b.splineTo(new Vector2d(0, getStartingPose().position.y), getStartingPose().heading));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToY(-34, (pose2dDual, posePath, v) -> 20));
        grabber.HangSample();
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.2).lineToY(-40));
        grabber.GoToPickupHeight();
        grabber.Close();
        runner.move(b -> b.splineTo(new Vector2d(36, -32), Math.toRadians(90)));
        runner.move(b -> b.lineToY(-8));
        runner.move(b -> b.strafeTo(new Vector2d(58, -16)));
//        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.waitSeconds(0.1).lineToY(-60));
//        grabber.Close();
//        runner.move(b -> b.waitSeconds(0.2));
//        grabber.GoToLowBar();
//        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.lineToY(-40));
//        grabber.GoToHighBar();
//        runner.move(b -> b.splineTo(getStartingPose().position, getStartingPose().heading));
//        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.strafeTo(new Vector2d(0, -34)));
//        grabber.HangSample();
//        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.turnTo(Math.toRadians(270)));
//        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.lineToY(-32));
//        grabber.HangSample();
//        while (grabber.CheckForBrake()) ;
//        runner.move(b -> b.lineToY(-16));
//        runner.move(b -> b.strafeTo(new Vector2d8, -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToY(4));
//        runner.move(b -> b.lineToY(-16));
//        runner.move(b -> b.strafeTo(new Vector2d(64., -16)));
//        runner.move(b -> b.waitSeconds(0.1).lineToY(4));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(12, -64, Math.toRadians(270));
    }
}*/

package com.example.auton;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

// The Auton used when playing red, and you want to move the three yellow samples into the basket zone
public class AutonHuman implements Auton {
    public void Run(Runner runner, IGrabber grabber, IBeak beak) {
        grabber.GoToHighBar();
        runner.move(b -> b.strafeTo(new Vector2d(8, -43)));
        while (grabber.CheckForBrake()) ;
        runner.move(b -> b.lineToY(-34));
        grabber.HangSample();
        while (grabber.CheckForBrake());
        runner.move(b -> b.strafeTo(new Vector2d(38, -34)));
    }

    public Pose2d getStartingPose() {
        return new Pose2d(12, -64, Math.toRadians(270));
    }
}

