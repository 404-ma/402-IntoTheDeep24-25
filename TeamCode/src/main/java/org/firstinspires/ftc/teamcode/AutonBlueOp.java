package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.example.auton.AutonBlue;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Helper.AutonRunner;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "**BLUE** Autonomous")
public class AutonBlueOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AutonBlue auton = new AutonBlue();
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, auton.getStartingPose());
        AutonRunner runner = new AutonRunner(mecanumDrive, this::updateTelemetry);
        waitForStart();
        auton.Run(runner);
    }

    private void updateTelemetry(MecanumDrive drive) {
        Vector2d pos = drive.pose.position;
        telemetry.addLine().addData("Current X Pos:", pos.x);
        telemetry.addLine().addData("Current Y Pos:", pos.y);
        telemetry.update();
    }
}