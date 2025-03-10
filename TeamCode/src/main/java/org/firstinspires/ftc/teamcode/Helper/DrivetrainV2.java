package org.firstinspires.ftc.teamcode.Helper;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class DrivetrainV2 {
    public static Params PARAMS = new Params();


    private final DcMotor drvMotorFrontLeft;
    private final DcMotor drvMotorBackLeft;
    private final DcMotor drvMotorFrontRight;
    private final DcMotor drvMotorBackRight;
    protected volatile boolean brakingOn = false;

    public DrivetrainV2(@NonNull HardwareMap hdwMap) {
        drvMotorFrontLeft = hdwMap.dcMotor.get("frontLeft");
        drvMotorBackLeft = hdwMap.dcMotor.get("backLeft");
        drvMotorFrontRight = hdwMap.dcMotor.get("frontRight");
        drvMotorBackRight = hdwMap.dcMotor.get("backRight");

        // Account for motor mounting direction in our robot design
        drvMotorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        drvMotorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        drvMotorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        drvMotorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        drvMotorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drvMotorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drvMotorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drvMotorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setDriveVector(double forward, double strafe, double rotate) {
        if (brakingOn) return;

        double denominator = Math.max(Math.abs(forward) + Math.abs(strafe) + Math.abs(rotate), 1);

        double pwrFrontLeft = (forward + strafe + rotate) / denominator;
        double pwrBackLeft = (forward - strafe + rotate) / denominator;
        double pwrFrontRight = (forward - strafe - rotate) / denominator;
        double pwrBackRight = (forward + strafe - rotate) / denominator;

        drvMotorFrontLeft.setPower(pwrFrontLeft);
        drvMotorBackLeft.setPower(pwrBackLeft);
        drvMotorFrontRight.setPower(pwrFrontRight);
        drvMotorBackRight.setPower(pwrBackRight);
    }

    /// ONLY USE FOR TESTING
    public void setMotorsManually(boolean frontLeft, boolean frontRight, boolean backLeft, boolean backRight) {
        drvMotorBackLeft.setPower(backLeft ? 1.0 : 0.0);
        drvMotorBackRight.setPower(backRight ? 1.0 : 0.0);
        drvMotorFrontLeft.setPower(frontLeft ? 1.0 : 0.0);
        drvMotorFrontRight.setPower(frontRight ? 1.0 : 0.0);
    }

    public void setDriveVectorFromJoystick(float stickLeftX, float stickRightX, float stickLeftY, boolean setReversed) {
        if (brakingOn) return;

        double rotate = stickRightX;
        double forward = stickLeftY * PARAMS.joystickYInputAdjustment;
        double strafe = stickLeftX * PARAMS.strafingAdjustment;

        if (setReversed) {
            forward = stickLeftY * PARAMS.joystickYInputAdjustment * -1;
            strafe = stickLeftX * PARAMS.strafingAdjustment * -1;
        }

        setDriveVector(forward, strafe, rotate);
    }

    public void setBrakeStatus(boolean braking) {
        brakingOn = braking;
        if (braking) {
            boolean allStop = false;
            boolean timerExpired = false;
            long brakeStart = System.currentTimeMillis();


            while (!allStop && !timerExpired) {
                boolean flStop = coasterBrakeMotor(drvMotorFrontLeft);
                boolean blStop = coasterBrakeMotor(drvMotorBackLeft);
                boolean frStop = coasterBrakeMotor(drvMotorFrontRight);
                boolean brStop = coasterBrakeMotor(drvMotorBackRight);


                allStop = flStop && blStop && frStop && brStop;
                timerExpired = (System.currentTimeMillis() >= (brakeStart + PARAMS.brakingMaximumTime));


                if (!allStop && !timerExpired) {
                    try {
                        sleep(PARAMS.brakingInterval);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean coasterBrakeMotor(DcMotor motor) {
        double curPower = motor.getPower();
        boolean stopped = (curPower == 0);

        if (!stopped) {
            double newPower = curPower - (Math.signum(curPower) * PARAMS.brakingInterval);
            if (Math.abs(newPower) < PARAMS.brakingStopThreshold) newPower = 0;
            motor.setPower(newPower);
        }

        return stopped;
    }

    public static class Params {
        public double strafingAdjustment = 1.08;
        public double joystickYInputAdjustment = -1;
        public double brakingStopThreshold = 0.25;
        public double brakingGain = 0.15;
        public long brakingInterval = 100;
        public double brakingMaximumTime = (long) Math.ceil(1 / brakingGain) * brakingInterval;
    }
}

