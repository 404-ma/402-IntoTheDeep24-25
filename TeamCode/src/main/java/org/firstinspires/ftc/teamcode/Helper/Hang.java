package org.firstinspires.ftc.teamcode.Helper;

import com.example.auton.DeferTimer;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hang {
    Servo grappleServo;
    DcMotor hangMotor;
    DeferTimer timer;
    BucketAction bucketAction;
    BeakAction beakAction;
    double speed = 0.67 - 0.55;
    long lastTime;
<<<<<<< Updated upstream
    double holdPosition = -1;
=======
    boolean hasHung = false;
>>>>>>> Stashed changes

    // Grapple Retract Positions  0.352
    public Hang(HardwareMap hwMap, BucketAction bucket, BeakAction beak) {
        grappleServo = hwMap.servo.get("grappleServo");
        hangMotor = hwMap.dcMotor.get("hangMotor");
        grappleServo.setDirection(Servo.Direction.FORWARD);
        bucketAction = bucket;
        beakAction = beak;
    }

    public void Init() {
        grappleServo.setPosition(0.48);
    }

    public void StartHangingRobot() {
        bucketAction.MoveBucket(0.42);
        beakAction.PickupReachMiddle();
        timer = new DeferTimer(1.5);
        beakAction.MoveElbow(.52);
        beakAction.MoveArm(.50);
        lastTime = System.currentTimeMillis();
    }
// Push Update
    public void ContinueHang() {
        if (timer == null)
            return;
        if (grappleServo.getPosition() < 0.59 && !timer.IsFinished()) {
            long newTime = System.currentTimeMillis();
            grappleServo.setPosition(grappleServo.getPosition() + (speed * (double) (newTime - lastTime) / 1000.0));
            lastTime = newTime;
        } else if (timer.IsFinished()) {
            HoldPosition();
        }
    }

    public void HoldPosition() {
        int holdPosition = hangMotor.getCurrentPosition();
        hangMotor.setTargetPosition(holdPosition);
        hangMotor.setPower(0.5);
        hangMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void BringBackArm() {
        grappleServo.setDirection(Servo.Direction.REVERSE);
        grappleServo.setPosition(0.5);
    }

    public void HoldPosition() {
        holdPosition = grappleServo.getPosition();
        grappleServo.setPosition(holdPosition);
    }
}


