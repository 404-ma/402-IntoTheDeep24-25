package org.firstinspires.ftc.teamcode.Helper;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.auton.ActionFunction;
import com.example.auton.Runner;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class AutonRunner implements Runner {
    private MecanumDrive drive;
    public AutonRunner(MecanumDrive mecanumDrive){
        drive = mecanumDrive;
    }
    public void runAction(ActionFunction actionFunction) {
        Actions.runBlocking(actionFunction.evaluate(drive.actionBuilder(drive.pose)).build());
    }
}
