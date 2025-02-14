package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.example.auton.AutonHuman;
import com.example.auton.DeferTimer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Helper.AutonRunner;
import org.firstinspires.ftc.teamcode.Helper.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.Grabber;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "Auton HUMAN (OBSERVATION ZONE)")
public class AutonHumanOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Grabber grabber = new Grabber(hardwareMap);
        grabber.Close();
        BeakAction beakAction = new BeakAction(hardwareMap);
        beakAction.DrivePosition();
        new DeferTimer(1).Wait();
        AutonHuman auton = new AutonHuman();
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, auton.getStartingPose());
        AutonRunner runner = new AutonRunner(mecanumDrive, this::updateTelemetry);
        waitForStart();
        auton.Run(runner, grabber, beakAction);
    }

    private void updateTelemetry(MecanumDrive drive) {
        Vector2d pos = drive.pose.position;
        telemetry.addLine().addData("Current X Pos:", pos.x);
        telemetry.addLine().addData("Current Y Pos:", pos.y);
        telemetry.update();
    }
}
