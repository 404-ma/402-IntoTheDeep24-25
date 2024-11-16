package org.firstinspires.ftc.teamcode.Helper;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.example.auton.ActionFunction;
import com.example.auton.Runner;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

public class AutonRunner implements Runner {
    private MecanumDrive drive;
    private AutonCallback callback;
    public AutonRunner(MecanumDrive mecanumDrive, AutonCallback telemetryCallback){
        drive = mecanumDrive;
        callback = telemetryCallback;
    }
    public void runAction(ActionFunction actionFunction) {
        Action action = actionFunction.evaluate(drive.actionBuilder(drive.pose)).build();
        while(action.run(new TelemetryPacket()))
            callback.execute(drive);
    }
}
