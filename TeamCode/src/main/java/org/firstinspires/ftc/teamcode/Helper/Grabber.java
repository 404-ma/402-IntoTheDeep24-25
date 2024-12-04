package org.firstinspires.ftc.teamcode.Helper;

import android.os.SystemClock;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Grabber {
    public static class  Params {
        public int viperPickupPos = 600;
        public int viperHighBarPos = 5500;
        public int viperLowBarPos = 2090;
        public int viperHangOffset = 1100;
        public int viperMaxHeight = 5600;
    }

    public static Params PARAMS = new Params();

    private boolean isOpen = true;

    Servo servo;
    DcMotor motor;
    final String servoName = "clawServo";
    final String motorName = "viperBasket";

    final double servoOpenPos = 0.500;
    final double servoClosedPos = 0.347;

    public double clawVelocity = 0;
    int lastPosition = 0;
    long lastTime = 0;

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
        height = Math.min(height, PARAMS.viperMaxHeight);
        height = Math.max(height, 0);
        motor.setTargetPosition(height);
        motor.setPower((height - motor.getCurrentPosition()) > 0 ? 1 : -1);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    // Use this when you want to raise and lower it w/ a joystick or something
    public void ManualControl(double throttle){
        throttle = -throttle;
        int pos = motor.getCurrentPosition();

        // don't let people drive the motor to unsafe positions
        //if((throttle > 0 && pos <= 0) || (throttle < 0 && pos >= maxHeight))
        //    throttle = 0;
        motor.setPower(throttle);
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

    public void StartDescent(){
        clawVelocity = 0;
        lastPosition = motor.getCurrentPosition();
        lastTime = SystemClock.currentThreadTimeMillis();
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(-0.3);
    }
    public boolean DescentTick(){
        int pos = motor.getCurrentPosition();
        long time = SystemClock.currentThreadTimeMillis();
        double velocity = (double) (pos - lastPosition) / (time - lastTime);
        if(velocity < clawVelocity * 0.5){
            motor.setPower(0);
            Open();
            return false;
        }
        lastPosition = pos;
        lastTime = time;
        return true;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
