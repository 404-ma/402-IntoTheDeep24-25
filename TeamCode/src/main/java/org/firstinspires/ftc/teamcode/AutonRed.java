package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "**RED** Autonomous")
public class AutonRed extends LinearOpMode {
    // The initial position of the robot, relative to some origin
    private final Pose2d startingPosition = new Pose2d(0, 0, 0);
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, startingPosition);
        waitForStart();
        // just move the robot 50 units forward and spin it around
        updateTelemetry(mecanumDrive.pose.position);
      Action action =
                mecanumDrive.actionBuilder(mecanumDrive.pose).setReversed(true).lineToX(-45)
                        .turnTo(Math.toRadians(-180)).build();
                        ;
        ;
        while(action.run(new TelemetryPacket()))
            updateTelemetry(mecanumDrive.pose.position);

        action = mecanumDrive.actionBuilder(mecanumDrive.pose)
                .setReversed(true).lineToY(-45)
                .turnTo(Math.toRadians(180))
                .build();
        while(action.run(new TelemetryPacket()))
            updateTelemetry(mecanumDrive.pose.position);
    }
    private void updateTelemetry(Vector2d pos) {
        telemetry.addLine().addData("Current X Pos:", pos.x);
        telemetry.addLine().addData("Current Y Pos:", pos.y);
        telemetry.update();
    }
}
