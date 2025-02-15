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
        MoveBeak(PARAMS.beakClosedPos);
    }

    public void PrepForPickup() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveElbow(PARAMS.elbowPickStartPos);
        MoveArm(PARAMS.armPickStartPos);
    }

    public void PickupReach() {
        MoveElbow(PARAMS.elbowPickReachPos);
        MoveArm(PARAMS.armPickReachPos);
        if (targetBeakPosition != PARAMS.beakOpenGatherPos)
            MoveBeak(PARAMS.beakOpenGatherPos);
    }

    public void SuplexElbPos() {
        MoveElbow(PARAMS.elbowSuplexPos);
    }

    public void SuplexArmPos() {
        MoveArm(PARAMS.armSuplexPos);
    }

    public void SuplexSample() {
        if (targetBeakPosition != PARAMS.beakClosedPos)  {
            MoveBeak(PARAMS.beakClosedPos);
            DeferredActions.CreateDeferredAction((long) PARAMS.beakClosedDelay, DeferredActions.DeferredActionType.SUPLEX_BEAK);
        } else {
            SuplexArmPos();
            SuplexElbPos();
            DeferredActions.CreateDeferredAction( (long) PARAMS.beakSuplexDelay, DeferredActions.DeferredActionType.BEAK_OPEN);
            DeferredActions.CreateDeferredAction((long) PARAMS.elbowSuplexSafeDelay, DeferredActions.DeferredActionType.BEAK_DRIVE_SAFE);
        }
    }

    public void PickupReachMiddle() {
        MoveBeak(PARAMS.beakOpenGatherPos);
        MoveArm(PARAMS.armPickReachMiddlePos);
        MoveElbow(PARAMS.elbowPickReachMiddlePos);
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

    public double conversion(double input) {
        double elbPos = 0.774589 * Math.pow(input, 0.311196);
        return elbPos;
    }

    public void pickUpJoystick(float power) {
        boolean rightPosition = ((targetArmPosition >= PARAMS.armPickupMinPos) && (targetArmPosition <= PARAMS.armPickupMaxPos));

        if (rightPosition) {
            double armPos = Range.clip((targetArmPosition + (power * 0.004)), PARAMS.armPickupMinPos, PARAMS.armPickupMaxPos);
            double elbPos = conversion(armPos);
            MoveArm(armPos);
            MoveElbow(elbPos);
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

    public void beakStart() {
        MoveBeak(0.40);
    }

    public static class Params {
        // Drive Position - Protect Arm
        public double armDrivePos = 0.300;
        public double elbowDrivePos = 0.800;

        // Sample Pickup Start - Clear Submersible Bar
        public double armPickStartPos = 0.62; //?
        public double elbowPickStartPos = 0.8;

        // Pickup Reach Position - Minimum Reach
        public double armPickReachPos = 0.3250;
        public double elbowPickReachPos = 0.8;

        // Pickup Reach Position - Middle Reach
        public double armPickReachMiddlePos = 0.66;
        public double elbowPickReachMiddlePos = 0.825;

        // Beak Positions
        public double beakOpenGatherPos = 0.58;
        public double beakClosedPos = 0.915;

        //Suplex Positions
        public double elbowSuplexPos = 0.00; //Change this
        public double armSuplexPos = 0.00; // Change this

        //Suplex Timing Delays
        public double beakClosedDelay = 0.00; // Change this
        public double beakSuplexDelay = 0.00; //Change this
        public double elbowSuplexSafeDelay = 0.00; //Change this

        //new values
        public double armPickupMinPos = 0.62;
        public double armPickupMaxPos = 0.83;
    }
}
