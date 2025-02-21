package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.BeakAction;
import org.firstinspires.ftc.teamcode.Helper.BucketAction;
import org.firstinspires.ftc.teamcode.Helper.DeferredActions;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Grabber;
import org.firstinspires.ftc.teamcode.Helper.Hang;

import java.util.List;
import java.util.Locale;

@Config
@TeleOp(name = "Driver Control", group = "Competition!!")
public class DriveControl extends LinearOpMode {
    private static final String version = "1.1";
    private boolean setReversed = false;
    private GamePad gpIn1;
    private GamePad gpIn2;
    private DrivetrainV2 drvTrain;
    private BeakAction beakAction;
    private BucketAction bucketAction;
    private Grabber grabber;
    private Hang hang;
    private DcMotor hangMotor;

    @Override
    public void runOpMode() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        gpIn1 = new GamePad(gamepad1, false);
        gpIn2 = new GamePad(gamepad2, false);
        drvTrain = new DrivetrainV2(hardwareMap);
        beakAction = new BeakAction(hardwareMap);
        bucketAction = new BucketAction(hardwareMap);
        grabber = new Grabber(hardwareMap);
        hang = new Hang(hardwareMap, bucketAction, beakAction);
        hangMotor = hardwareMap.dcMotor.get("hangMotor");
        hangMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        hangMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if (isStopRequested()) {
            return;
        }

        beakAction.DrivePosition();
        bucketAction.StartPosition();
        hang.Init();
        telemetry.clear();

        double speedMultiplier = 0.3;

        while (opModeIsActive()) {
            update_telemetry();

            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case BUTTON_Y:
                    beakAction.SuplexSample();
                    break;
                case BUTTON_B:
                    beakAction.PickupReachClose();
                    break;
                case BUTTON_X:
                    beakAction.PickupReachMiddle();
                    break;
                case BUTTON_A:
                    beakAction.PickupReachMaximum();
                    break;
                case RIGHT_TRIGGER:
                    beakAction.pickUpJoystick(gamepad2.right_trigger);
                    break;
                case LEFT_TRIGGER:
                    beakAction.pickUpJoystick(-gamepad2.left_trigger);
                    break;
                case BUTTON_R_BUMPER:
                    beakAction.ToggleBeak();
                    break;
                case BUTTON_L_BUMPER:
                    beakAction.changingArmUp();
                    break;
                case LEFT_STICK_BUTTON_ON:
                    beakAction.DrivePosition();
                    break;
                case BUTTON_BACK:
                    setReversed = !setReversed;
                    break;
                case DPAD_DOWN:
                    speedMultiplier = 0.25;
                    break;
                case DPAD_LEFT:
                    speedMultiplier = 0.75;
                    break;
                case DPAD_RIGHT:
                    speedMultiplier = 0.5;
                    break;
                case DPAD_UP:
                    speedMultiplier = 1;
                    break;

                case JOYSTICK:
                    drvTrain.setDriveVectorFromJoystick(gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            gamepad1.left_stick_y * (float) speedMultiplier, setReversed);
                    break;
            }

            GamePad.GameplayInputType inpType2 = gpIn2.WaitForGamepadInput(30);
            switch (inpType2) {
                case BUTTON_R_BUMPER:
                    grabber.ToggleClaw();
                    break;
                case BUTTON_L_BUMPER:
                    bucketAction.ToggleBucket();
                    break;
                case BUTTON_A:
                    grabber.HangSample();
                    break;
                case BUTTON_Y:
                    grabber.GoToPickupHeight();
                    break;
                case BUTTON_B:
                    hang.StartHangingRobot();
                    break;
                case DPAD_UP:
                    grabber.GoToHighBar();
                    break;
                case DPAD_DOWN:
                    grabber.GoToLowBar();
                    break;
                case JOYSTICK:
                    grabber.ManualControl(gamepad2.right_stick_y);
                    if (gamepad2.left_stick_y < -0.2)
                        hangMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    if (gamepad2.left_stick_y > 0.2)
                        hangMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    hangMotor.setPower(Math.abs(gamepad2.left_stick_y));
                    if (Math.abs(gamepad2.left_stick_y) > 0.2) {
                        hang.BringBackArm();
                    }
                    break;
                case RIGHT_STICK_BUTTON_ON:
                    grabber.ResetEncoder();
                    break;
            }
            grabber.CheckForBrake();
            hang.ContinueHang();
            ProcessDeferredActions();
        }
    }

    private void ProcessDeferredActions() {
        List<DeferredActions.DeferredActionType> action = DeferredActions.GetReadyActions();
        for (DeferredActions.DeferredActionType actionType : action) {
            switch (actionType) {
                case BEAK_OPEN:
                    beakAction.OpenBeak();
                    break;
                case BEAK_CLOSE:
                    beakAction.CloseBeak();
                    break;
                case BEAK_DRIVE_SAFE:
                    beakAction.DrivePosition();
                    break;
                case SUPLEX_BEAK:
                    beakAction.SuplexSample();
                    break;
                default:
                    telemetry.addLine("ERROR - Unsupported Deferred Action");
                    break;
            }
        }
    }


    private void update_telemetry() {
        telemetry.addLine("Gamepad #1");

        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpIn1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpIn1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpIn1.getTelemetry_InputLastType().toString());
        telemetry.addLine();

        String inpTime2 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpIn2.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP2 Time", inpTime2);
        telemetry.addLine().addData("GP2 Cnt", gpIn2.getTelemetry_InputCount());
        telemetry.addLine().addData("GP2 Input", gpIn2.getTelemetry_InputLastType().toString());
        telemetry.addLine();

        telemetry.addLine("Deferred Actions");
        String actTime = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(DeferredActions.tlmLastActionTimestamp);
        telemetry.addLine().addData("Time", actTime);
        telemetry.addLine().addData("Action", DeferredActions.tlmLastAction.toString());

        DcMotor viperMotor = hardwareMap.dcMotor.get("viperBasket");
        telemetry.addLine().addData("Viper Position: ", viperMotor.getCurrentPosition());
        telemetry.update();
    }
}
