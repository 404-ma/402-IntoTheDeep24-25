package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class BucketAction {
    public static Params PARAMS = new Params();
    public static double targetBucketPosition = -1;
    private final Servo bucketServo;
    public BucketAction(@NonNull HardwareMap hdwMap) {
        bucketServo = hdwMap.servo.get("bucketServo");
        bucketServo.setDirection(Servo.Direction.FORWARD);
    }

    public void MoveBucket(double position) {
        bucketServo.setPosition(position);
        targetBucketPosition = position;
    }

    public void StartPosition() {
        MoveBucket(PARAMS.bucketStartPos);
    }

    public void DumpSample() {
        MoveBucket(PARAMS.bucketDumpPos);
    }

    public void PrepForCatch() {
        MoveBucket(PARAMS.bucketCatchPos);
    }

    public void ToggleBucket() {
        if (targetBucketPosition != PARAMS.bucketDumpPos) {
            DumpSample();
        } else {
            PrepForCatch();
        }
    }

    public static class Params {
        public double bucketStartPos = 0.58;   // Tucked in For Driving
        public double bucketCatchPos = 0.58;  // Catch from Beak
        public double bucketDumpPos = 0.49;    // Dump to Basket
    }
}
