package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Autonomous(name = "**BLUE** Autonomous")
public class AutonBlue extends LinearOpMode {
    // The initial position of the robot, relative to some origin
    private final Pose2d startingPosition = new Pose2d(0, 0, 0);
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive mecanumDrive = new MecanumDrive(hardwareMap, startingPosition);
        waitForStart();
        // just move the robot 50 units forward and spin it around
        Actions.runBlocking(
                mecanumDrive.actionBuilder(mecanumDrive.pose).lineToX(50)
                        .turnTo(Math.toRadians(180)).build()
        );
    }
}
