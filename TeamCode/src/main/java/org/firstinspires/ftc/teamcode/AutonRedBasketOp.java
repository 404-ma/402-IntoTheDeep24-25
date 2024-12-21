package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.example.auton.AutonRedBasket;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Helper.AutonRunner;
import org.firstinspires.ftc.teamcode.Helper.Grabber;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "Auton RED BASKET ZONE")
public class AutonRedBasketOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Grabber grabber = new Grabber(hardwareMap);
        grabber.Close();
        AutonRedBasket auton = new AutonRedBasket();
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, auton.getStartingPose());
        AutonRunner runner = new AutonRunner(mecanumDrive, this::updateTelemetry);
        waitForStart();
        auton.Run(runner, grabber);
    }

    private void updateTelemetry(MecanumDrive drive) {
        Vector2d pos = drive.pose.position;
        telemetry.addLine().addData("Current X Pos:", pos.x);
        telemetry.addLine().addData("Current Y Pos:", pos.y);
        telemetry.update();
    }
}
