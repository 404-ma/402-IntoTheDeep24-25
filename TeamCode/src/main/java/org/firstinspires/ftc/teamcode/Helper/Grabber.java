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
    Servo servo;
    DcMotor motor;
    boolean queueOpen;
    int targetPosition = -1;

    public Grabber(HardwareMap hwMap) {
        servo = hwMap.servo.get(PARAMS.servoName);
        motor = hwMap.dcMotor.get(PARAMS.motorName);
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
        servo.setPosition(PARAMS.servoOpenPos);
    }

    public void Close() {
        servo.setPosition(PARAMS.servoClosedPos);
    }

    // Use this when you want to raise and lower it w/ a joystick or something
    public void ManualControl(double throttle) {
        // Cap Raw Throttle Control When Close to Limit
        double power = -throttle;  // Swap Joystick Direction
        if (power > 0) {
            if (motor.getCurrentPosition() >= PARAMS.viperMaxHeight)
                power = 0;
            else if (motor.getCurrentPosition() >= PARAMS.viperManualSpeedReductionHeight)
                power = Math.min(power, 0.3);
        } else if ((power < 0) && (motor.getCurrentPosition() <= 500))
            power = Math.max(power, -0.3);

        if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(power);
    }

    public void SetHeight(int position) {
        motor.setTargetPosition(position);
        targetPosition = position;
        motor.setPower(0.9);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public boolean CheckForBrake() {
        if (targetPosition != -1 && Math.abs(motor.getCurrentPosition() - targetPosition) <= 75) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setPower(0.0);
            //motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            targetPosition = -1;
            if (queueOpen) {
                queueOpen = false;
                Open();
            }
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
            queueOpen = true;
        }
    }

    public static class Params {
        public String motorName = "viperBasket";
        public int viperPickupPos = 0;
        public int viperHighBarPos = 4800;
        public int viperLowBarPos = 1300;
        public int viperHangOffset = 1000;
        public int viperManualSpeedReductionHeight = 5000;
        public int viperMaxHeight = 5700;
        public String servoName = "clawServo";
        public double servoOpenPos = 0.515;
        public double servoClosedPos = 0.47;
    }


}
