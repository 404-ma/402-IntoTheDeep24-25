package org.firstinspires.ftc.teamcode.Helper;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@FunctionalInterface
// Signature of a callback function that can be used for telemetry
public interface AutonCallback {
    void execute(MecanumDrive mecanumDrive);
}
