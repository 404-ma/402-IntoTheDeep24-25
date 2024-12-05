package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Grabber {
    public static class Params {
        public int viperPickupPos = 600;
        public int viperHighBarPos = 5500;
        public int viperLowBarPos = 2090;
        public int viperHangOffset = 1100;
        public int viperMaxHeight = 5600;
    }

    public static Params PARAMS = new Params();

    Servo servo;
    DcMotor motor;
    final String servoName = "clawServo";
    final String motorName = "viperBasket";

    final double servoOpenPos = 0.500;
    final double servoClosedPos = 0.347;


    public Grabber(HardwareMap hwMap) {
        servo = hwMap.servo.get(servoName);
        servo.setPosition(servoOpenPos);
        motor = hwMap.dcMotor.get(motorName);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ResetEncoder();
    }

    public void ResetEncoder() {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SystemClock.sleep(20);
        motor.setTargetPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void Open() {
        servo.setPosition(servoOpenPos);
    }

    public void Close() {
        servo.setPosition(servoClosedPos);
    }

    public void SetHeight(int height) {
        height = Math.min(height, PARAMS.viperMaxHeight);
        height = Math.max(height, 0);
        motor.setTargetPosition(height);
        motor.setPower((height - motor.getCurrentPosition()) > 0 ? 1 : -1);
    }

    // Use this when you want to raise and lower it w/ a joystick or something
    public void ManualControl(double throttle) {
        throttle = -throttle;
        motor.setTargetPosition(motor.getCurrentPosition() + (int) Math.ceil(throttle));
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
            SystemClock.sleep(500);
            this.Open();
        }
    }
}
