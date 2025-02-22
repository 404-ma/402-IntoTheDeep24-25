package org.firstinspires.ftc.teamcode.Helper;

import com.example.auton.DeferTimer;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    Servo grappleServo;
    DeferTimer timer;
    BucketAction bucketAction;
    BeakAction beakAction;
    double speed = 0.67 - 0.55;
    long lastTime;

    // Grapple Retract Positions  0.352
    public Hang(HardwareMap hwMap, BucketAction bucket, BeakAction beak) {
        grappleServo = hwMap.servo.get("grappleServo");
        grappleServo.setDirection(Servo.Direction.FORWARD);
        bucketAction = bucket;
        beakAction = beak;
    }

    public void Init() {
        grappleServo.setPosition(0.352);
    }

    public void StartHangingRobot() {
        bucketAction.MoveBucket(0.42);
        beakAction.MoveArm(.48);
        beakAction.MoveElbow(.5);
        timer = new DeferTimer(1.5);
        lastTime = System.currentTimeMillis();
    }

    public void ContinueHang() {
        if (timer == null)
            return;
        if (grappleServo.getPosition() < 0.55 && !timer.IsFinished()) {
            long newTime = System.currentTimeMillis();
            grappleServo.setPosition(grappleServo.getPosition() + (speed * (double) (newTime - lastTime) / 1000.0));
            lastTime = newTime;
        } else if (timer.IsFinished()) {
            //beakAction.MoveArm(0.3);
            //beakAction.MoveElbow(0.75);
        }
    }

    public void BringBackArm() {
        grappleServo.setDirection(Servo.Direction.REVERSE);
        grappleServo.setPosition(0.555);
    }
}
