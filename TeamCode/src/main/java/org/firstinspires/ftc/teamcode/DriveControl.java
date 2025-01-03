package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.Grabber;

import java.util.Locale;

@Config
@TeleOp(name = "Driver Control", group = "Competition!!")
public class DriveControl extends LinearOpMode {


    private static final String version = "1.0";
    private boolean setReversed = false;
    // private ClawMoves yclaw;

    @Override
    public void runOpMode() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad gpIn1 = new GamePad(gamepad1, false);
        GamePad gpIn2 = new GamePad(gamepad2);
        DrivetrainV2 drvTrain = new DrivetrainV2(hardwareMap);
        Grabber grabber = new Grabber(hardwareMap);
        // TestServo serv1 = hardwareMap.servo.get(PARAMS.);

//        new HapticEventBusTester();
//        HapticEventBusTester hapticEvent = HapticEventBusTester.getInstance();

        waitForStart();
        if (isStopRequested()) return;


        telemetry.clear();

        double speedMultiplier = 0.3;
        double lastSpeed = speedMultiplier;
        boolean highBarOn = false;

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);


            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case LEFT_STICK_BUTTON_ON:
                    if (speedMultiplier != 1) {
                        lastSpeed = speedMultiplier;
                        speedMultiplier = 1;
                    }
                    break;

                case LEFT_STICK_BUTTON_OFF:
                    if (lastSpeed != 1) {
                        speedMultiplier = lastSpeed;
                        lastSpeed = 1;
                    }
                    break;

                case BUTTON_A:
//                    Set<String> targets = new HashSet<>();
//                    targets.add("haptic");
//                    EventBus.getInstance().emit(targets, gpIn1);
                    speedMultiplier = 0.25;
                    break;

                case BUTTON_X:
                    speedMultiplier = 0.75;
                    break;

                case BUTTON_B:
                    speedMultiplier = 0.5;
                    break;

                case BUTTON_Y:
                    speedMultiplier = 1;
                    break;

                case BUTTON_BACK:
                    setReversed = !setReversed;
                    break;

                case JOYSTICK:
//                    gpIn1.HapticsController.runShortHaptic();
                    drvTrain.setDriveVectorFromJoystick(-gamepad1.left_stick_x * (float) speedMultiplier,
                            gamepad1.right_stick_x * (float) speedMultiplier,
                            -gamepad1.left_stick_y * (float) speedMultiplier, setReversed);
                    break;


            }
            GamePad.GameplayInputType inpType2 = gpIn2.WaitForGamepadInput(30);
            switch (inpType2) {
                case BUTTON_B:
                    grabber.Close();
                    break;
                case BUTTON_X:
                    grabber.Open();
                    break;
                case BUTTON_A:
                    grabber.HangSample();
                    break;
                case BUTTON_Y:
                    grabber.GoToPickupHeight(false);
                    break;
                case DPAD_UP:
                    grabber.GoToHighBar();
                    break;
                case DPAD_DOWN:
                    grabber.GoToLowBar();
                    break;
                case JOYSTICK:
                    grabber.ManualControl(gamepad2.right_stick_y);
                    break;
                case RIGHT_STICK_BUTTON_ON:
                    grabber.ResetEncoder();
                    break;
            }
            grabber.CheckForBrake();
//            if(highBarOn)
//                highBarOn = grabber.GoToHighBarUpdate();
        }
    }


    private void update_telemetry(GamePad gpi1, GamePad gpi2) {
        telemetry.addLine("Gamepad #1");
        DcMotor viperMotor = hardwareMap.dcMotor.get("viperBasket");
        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("L Joy  X", "%6.3f", gamepad1.left_stick_x).addData("Y", "%6.3f", gamepad1.left_stick_y);
        telemetry.addLine().addData("R Joy  X", "%6.3f", gamepad1.right_stick_x).addData("Y", "%6.3f", gamepad1.right_stick_y);
        telemetry.addLine().addData("Motor Power: ", hardwareMap.dcMotor.get("viperBasket").getPower());
        telemetry.addLine().addData("Motor Current Position: ", viperMotor.getCurrentPosition());
        telemetry.addLine().addData("Motor Target Position: ",viperMotor.getTargetPosition());
        telemetry.addLine().addData("Position Difference: ", (viperMotor.getTargetPosition() - viperMotor.getCurrentPosition()));
        telemetry.update();
    }
}
