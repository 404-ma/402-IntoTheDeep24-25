package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    private boolean isOpen = true;

    Servo servo;
    DcMotor motor;
    final String servoName = "clawServo";
    final String motorName = "viperBasket";

    final double servoOpenPos = 0.412;
    final double servoClosedPos = 0.347;
    final int maxHeight = 2400;

    public Grabber(HardwareMap hwMap){
        servo = hwMap.servo.get(servoName);
        servo.setPosition(servoOpenPos);
        motor = hwMap.dcMotor.get(motorName);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        SystemClock.sleep(20);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void Open(){
        servo.setPosition(servoOpenPos);
        isOpen = true;
    }
    public void Close(){
        servo.setPosition(servoClosedPos);
        isOpen = false;
    }
    public void SetHeight(int height){
        height = Math.min(height, maxHeight);
        height = Math.max(height, 0);
        motor.setTargetPosition(height);
        motor.setPower((height - motor.getCurrentPosition()) > 0 ? 1 : -1);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    // Use this when you want to raise and lower it w/ a joystick or something
    public void ManualControl(double throttle){
        throttle = -throttle;
        if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int pos = motor.getCurrentPosition();
        // don't let people drive the motor to unsafe positions
        if((throttle < 0 && pos <= 0) || (throttle > 0 && pos >= maxHeight))
            throttle = 0;
        motor.setPower(throttle);
    }

    public boolean isOpen() {
        return isOpen;
    }
}
