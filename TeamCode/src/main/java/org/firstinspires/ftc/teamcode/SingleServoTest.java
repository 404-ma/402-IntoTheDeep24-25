package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;


@Config
@TeleOp(name="Single Servo Test", group ="Diagnostic")
public class SingleServoTest extends LinearOpMode {
    public static class Params {
        public String servoName = "armServo";
        public String servoToTeset = "elbowServo";
        public boolean servoForward = true;
        public double servoStartPos = 0.300;
        public double servoPresetPosX = 0.500;
        public double ServoPresetPosB = 0.800;
    }

    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private FtcDashboard dashboard;
    private Servo servo;
    private Servo servoToTest;

    private boolean tlmServoForward = false;
    private double tlmServoPosition = 0;
    private double newPosition = 0;

    @Override
    public void runOpMode() {
        boolean good_init = initialize();
        waitForStart();
        if (isStopRequested() || !good_init)
            return;
        telemetry.clear();

        // Set Servo initial Direction
        tlmServoForward = PARAMS.servoForward;
        servo.setDirection(tlmServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);

        // Move Servo to initial Position
        newPosition = PARAMS.servoStartPos;
        servo.setPosition(newPosition);
        tlmServoPosition = newPosition;

        while (opModeIsActive()) {
            update_telemetry();

            GamePad.GameplayInputType inpType = gpInput.WaitForGamepadInput(100);
            switch (inpType) {
                case BUTTON_A:
                    servoToTest.setPosition(newPosition);
                    tlmServoPosition = newPosition;
                    break;

                case BUTTON_L_BUMPER:
                    tlmServoForward = !tlmServoForward;
                    servoToTest.setDirection(tlmServoForward ? Servo.Direction.FORWARD : Servo.Direction.REVERSE);
                    break;

                case BUTTON_X:
                    newPosition = PARAMS.servoPresetPosX;
                    servoToTest.setPosition(newPosition);
                    break;

                case BUTTON_B:
                    newPosition = PARAMS.ServoPresetPosB;
                    servoToTest.setPosition(newPosition);
                    break;

                case DPAD_UP:
                    newPosition += 0.1;
                    break;

                case DPAD_DOWN:
                    newPosition -= 0.1;
                    break;

                case DPAD_LEFT:
                    newPosition += 0.005;
                    break;

                case DPAD_RIGHT:
                    newPosition -= 0.005;
                    break;
            }
        }
    }

    public boolean initialize() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine().addData("Servo Test: ", PARAMS.servoName );
        telemetry.addLine();

        // Initialize Helpers
        try {
            servo = hardwareMap.servo.get(PARAMS.servoName);
            servoToTest = hardwareMap.servo.get(PARAMS.servoToTeset);
            gpInput = new GamePad(gamepad1);
            dashboard = FtcDashboard.getInstance();
            dashboard.clearTelemetry();

            telemetry.addLine("All Sensors Initialized");
            telemetry.addLine("");
            telemetry.addData(">", "Press Play to Start");
            telemetry.update();
            return (true);
        } catch (Exception e) {
            telemetry.addLine("");
            telemetry.addLine("*** INITIALIZATION FAILED ***");
            telemetry.update();
            return (false);
        }
    }

    private void update_telemetry() {
        telemetry.addLine("Servo Test");
        telemetry.addLine("Use Dpad to Set Position");
        telemetry.addLine("  Up/Down    +/- 0.1");
        telemetry.addLine("  Left/Right +/- 0.005");
        telemetry.addLine("Button A --> GoTo New Position");
        telemetry.addLine("Left Bumper --> Change Direction/n");
        telemetry.addLine().addData("Name     ", PARAMS.servoName );
        telemetry.addLine().addData("Direction", (tlmServoForward ? "Forward" : "Reverse") );
        telemetry.addLine().addData("Curr Pos ", tlmServoPosition );
        telemetry.addLine().addData("New Pos ", newPosition );
        telemetry.addLine().addData("Elbow Servo Pos ", servoToTest.getPosition());
        telemetry.update();


        // FTC Dashboard Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.servoName );
        packet.put("Direction", (tlmServoForward ? "Forward" : "Reverse"));
        packet.put("Curr Position", tlmServoPosition);
        packet.put("New Position", newPosition);
        dashboard.sendTelemetryPacket(packet);
    }

}
