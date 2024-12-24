package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import com.acmerobotics.dashboard.config.Config;
import com.example.auton.IGrabber;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Grabber implements IGrabber {
    public static Params PARAMS = new Params();
    final String servoName = "clawServo";
    final String motorName = "viperBasket";
    final double servoOpenPos = 0.800;
    final double servoClosedPos = 0.650;
    Servo servo;
    DcMotor motor;
    int targetPosition = -1;

    public Grabber(HardwareMap hwMap) {
        servo = hwMap.servo.get(servoName);
        servo.setPosition(servoOpenPos);
        motor = hwMap.dcMotor.get(motorName);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SystemClock.sleep(20);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void ResetEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void Open() {
        servo.setPosition(servoOpenPos);
    }

    public void Close() {
        servo.setPosition(servoClosedPos);
    }

    // Use this when you want to raise and lower it w/ a joystick or something
    public void ManualControl(double throttle) {
        double power = -throttle;
        if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(power);
    }

    //public void SetHeight(int height) {
    //    height = Math.min(height, PARAMS.viperMaxHeight);
    //    height = Math.max(height, 0);
    //    motor.setTargetPosition(height);
    //    motor.setPower((height - motor.getCurrentPosition()) > 0 ? 0.9 : -0.9);
    //}

    public void SetHeight(int position) {
        motor.setTargetPosition(position);
        targetPosition = position;
        motor.setPower(0.9);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean CheckForBrake() {
        if (targetPosition != -1 && Math.abs(motor.getCurrentPosition() - targetPosition) <= 5) {
            motor.setPower(0.0);
            targetPosition = -1;
            return false;
        }
        return true;
    }

    public void GoToHighBar() {
        SetHeight(PARAMS.viperHighBarPos);
    }

    public void GoToLowBar() {
        SetHeight(PARAMS.viperLowBarPos);
    }

    public void GoToPickupHeight() {
        SetHeight(PARAMS.viperPickupPos);
    }

    public void HangSample() {
        int pos = motor.getCurrentPosition();
        if (pos > PARAMS.viperHangOffset) {
            SetHeight(pos - PARAMS.viperHangOffset);
        }
    }

    public static class Params {
        public int viperPickupPos = 600;
        public int viperHighBarPos = 5700;
        public int viperLowBarPos = 2090;
        public int viperHangOffset = 1600;
        public int viperMaxHeight = 5600;
    }
}
