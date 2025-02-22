package org.firstinspires.ftc.teamcode.Helper;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.example.auton.IBeak;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@Config
public class BeakAction implements IBeak {
    public static Params PARAMS = new Params();
    public static double targetArmPosition = -1;
    public static double targetElbowPosition = -1;
    public static double targetBeakPosition = -1;
    private final Servo beak;
    private final Servo arm;
    private final Servo elbow;

    public BeakAction(@NonNull HardwareMap hardwareMap) {
        //   super();
        beak = hardwareMap.servo.get("beakServo");
        beak.setDirection(Servo.Direction.FORWARD);

        arm = hardwareMap.servo.get("armServo");
        arm.setDirection(Servo.Direction.FORWARD);

        elbow = hardwareMap.servo.get("elbowServo");
        elbow.setDirection(Servo.Direction.FORWARD);
    }

    public void MoveArm(double position) {
        arm.setPosition(position);
        targetArmPosition = position;
    }

    private void MoveElbow(double position) {
        elbow.setPosition(position);
        targetElbowPosition = position;
    }

    private void MoveBeak(double position) {
        beak.setPosition(position);
        targetBeakPosition = position;
    }

    public void DrivePosition() {
        MoveArm(PARAMS.armDrivePos);
        MoveElbow(PARAMS.elbowDrivePos);
        MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void PickupReachClose() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveElbow(PARAMS.elbowPickReachPos);
        MoveArm(PARAMS.armPickReachPos);
    }

    public void PickupReachMiddle() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveElbow(PARAMS.elbowPickReachMiddlePos);
        MoveArm(PARAMS.armPickReachMiddlePos);
    }

    public void PickupReachMaximum() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveElbow(PARAMS.elbowPickReachMaxPos);
        MoveArm(PARAMS.armPickReachMaxPos);
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosedPos) {
            MoveBeak(PARAMS.beakClosedPos);
            DeferredActions.CreateDeferredAction((long) PARAMS.delayBeakClosed, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            MoveElbow(PARAMS.elbowSuplexPos);
            MoveArm(PARAMS.armSuplexPos);
            DeferredActions.CreateDeferredAction((long) PARAMS.delaySuplexBeakOpen, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction((long) PARAMS.delaySuplexToDrivePos, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
        }
    }


    public void CloseBeak() {
        MoveBeak(PARAMS.beakClosedPos);
    }

    public void OpenBeak() {
        MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void ToggleBeak() {
        if (targetBeakPosition == PARAMS.beakClosedPos) {
            OpenBeak();
        } else {
            CloseBeak();
        }
    }


    public void MoveArmJoystick(float power) {
        // Compute Min Max based on Elbow Position
        boolean rightPosition = ((targetArmPosition >= PARAMS.armPickupMinPos) && (targetArmPosition <= PARAMS.armPickupMaxPos));

        if (rightPosition) {
            double armPos = Range.clip((targetArmPosition + (power * 0.008)), PARAMS.armPickupMinPos, PARAMS.armPickupMaxPos);
            MoveArm(armPos);
        }
    }

    public void changingArmUp() {
        double currentArm = arm.getPosition();

        if (currentArm <= 0.505) {
            double setArm = currentArm + 0.005;
            MoveArm(setArm);
        }
    }

    public void changingArmDown() {
        double currentArm = arm.getPosition();

        if (currentArm >= 0.36) {
            double setArm = currentArm - 0.005;
            MoveArm(setArm);
        }
    }


    public static class Params {
        // Drive Position - Protect Arm
        public double armDrivePos = 0.5;
        public double elbowDrivePos = 0.585;

        // Pickup Reach Position - Minimum Reach
        public double armPickReachPos = 0.560;
        public double elbowPickReachPos = 0.650;

        // Pickup Reach Position - Middle Reach
        public double armPickReachMiddlePos = 0.66;
        public double elbowPickReachMiddlePos = 0.615;

        // Pickup Reach Position - Maximum Reach
        public double armPickReachMaxPos = 0.710;
        public double elbowPickReachMaxPos = 0.590;

        // Beak Positions
        public double beakOpenGatherPos = 0.275;
        public double beakClosedPos = 0.5;

        //Suplex Positions
        public double armSuplexPos = 0.515; // Change this
        public double elbowSuplexPos = 0.56; //Change this


        //Suplex Timing Delays
        public double delayBeakClosed = 75; // Change this
        public double delaySuplexBeakOpen = 700; //Change this
        public double delaySuplexToDrivePos = 900; //Change this

        //new values
        public double armPickupMinPos = 0.61;
        public double armPickupMaxPos = 0.755;
    }
}
