package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.example.auton.AutonHuman;
import com.example.auton.DeferTimer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Helper.AutonRunner;
import org.firstinspires.ftc.teamcode.Helper.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.Grabber;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "Auton HUMAN (OBSERVATION ZONE)")
public class AutoHumanAnu extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Grabber grabber = new Grabber(hardwareMap);
        grabber.Close();

        BeakAction beakAction = new BeakAction(hardwareMap);
        beakAction.DrivePosition();
        new DeferTimer(1).Wait();

        BucketAction bucketAction = new BucketAction(hardwareMap);
        bucketAction.StartPosition();
        AutonHuman auton = new AutonHuman();

        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, auton.getStartingPose());
        AutonRunner runner = new AutonRunner(mecanumDrive, this::updateTelemetry);

        waitForStart();

        auton.Run(runner, grabber, beakAction, bucketAction);


        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-30, -40)));
        beakAction.PickupReachMiddle();
        new DeferTimer(1).Wait();
        beakAction.CloseBeak();
        new DeferTimer(0.5).Wait();
        bucketAction.PrepForCatch();
        new DeferTimer(0.5).Wait();
        beakAction.SuplexSample();
        new DeferTimer(1).Wait();

        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-20, -20)));
        beakAction.OpenBeak();
        new DeferTimer(0.5).Wait();

        runner.move(b -> b.strafeToConstantHeading(new Vector2d(-10, 0))); // Parking position
    }

    private void updateTelemetry(MecanumDrive drive) {
        Vector2d pos = drive.pose.position;
        telemetry.addLine().addData("Current X Pos:", pos.x);
        telemetry.addLine().addData("Current Y Pos:", pos.y);
        telemetry.update();
    }
}

