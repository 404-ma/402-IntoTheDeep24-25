package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

import java.util.Locale;

@Config
@TeleOp(name="Single Motor Test", group ="Diagnostic")
public class SingleMotorTest extends LinearOpMode {
    public static class Params {
        public String motorName = "viperBasket";
        public boolean motorForward = false;
        public boolean motorZeroPowerStop = true;
        public int motorMaxPosition = 2400;
        public double motorNearLimitPercent = 0.5;
        public int motorButtonYPosition = 2000;
    }
    public static Params PARAMS = new Params();

    private GamePad gpInput;
    private DcMotor motor;
    private FtcDashboard dashboard;

    @Override
    public void runOpMode() {
        boolean good_init = initialize();
        waitForStart();
        if (isStopRequested() || !good_init)
            return;
        telemetry.clear();

        boolean ManualOverride = false;

        while (opModeIsActive()) {
            update_telemetry();

            GamePad.GameplayInputType inpType = gpInput.WaitForGamepadInput(100);
            switch (inpType) {
                case BUTTON_L_BUMPER:
                    motor.setDirection(DcMotorSimple.Direction.FORWARD);
                    break;

                case BUTTON_R_BUMPER:
                    motor.setDirection(DcMotorSimple.Direction.REVERSE);
                    break;

                case BUTTON_Y:
                    motor.setTargetPosition(PARAMS.motorButtonYPosition);
                    motor.setPower(0.5);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;

                case BUTTON_A:
                    motor.setTargetPosition(0);
                    motor.setPower(-0.5);
                    motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    break;

                case BUTTON_B:
                    ManualOverride = !ManualOverride;
                    // On Exit of Manual Reset Encoder to Zero
                    if (!ManualOverride)
                        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    break;

                case JOYSTICK:
                    if (motor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER)
                        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                    // Negative(-) Power --> Lowers Position, Positive(+) Power --> Increase Position
                    double power = -gamepad1.right_stick_y;  // Make Up = Positive

                    if (!ManualOverride) {
                        int pos = motor.getCurrentPosition();

                        if (power < 0) {
                            // Negative Power - 0 Position Limit for Motor
                            if (pos <= 0)
                                power = 0;
                            else if (pos <= (PARAMS.motorMaxPosition * PARAMS.motorNearLimitPercent))
                                power = Math.max(power, -0.3);
                        } else {
                            // Positive Power - Limit Parameter
                            if (pos >= PARAMS.motorMaxPosition)
                                power = 0;
                            else if (pos >= PARAMS.motorMaxPosition - (PARAMS.motorMaxPosition) * PARAMS.motorNearLimitPercent)
                                power = Math.min(power, 0.3);
                        }
                    }

                    motor.setPower( power );
                    break;
            }
        }
    }


    public boolean initialize() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine().addData("Motor Test:  ", PARAMS.motorName );
        telemetry.addLine();

        // Initialize Helpers
        try {
            gpInput = new GamePad(gamepad1);
            motor = hardwareMap.dcMotor.get(PARAMS.motorName);
            motor.setDirection(PARAMS.motorForward ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
            if (PARAMS.motorZeroPowerStop)
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            else
                motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);   // Zero Encoder Position
            sleep(20);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        telemetry.addLine("Motor Test");
        telemetry.addLine("Use Right Joystick Y for Power");
        telemetry.addLine("Left Bumper  --> Forward");
        telemetry.addLine("Right Bumper --> Reverse/n");

        String currTimestamp = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(new java.util.Date());
        telemetry.addLine().addData("Time      ", currTimestamp );
        telemetry.addLine().addData("Name       ", PARAMS.motorName );
        telemetry.addLine().addData("Zero Power ", ((PARAMS.motorZeroPowerStop) ? "Stop" : "Coast"));
        telemetry.addLine().addData("Direction  ", ((motor.getDirection() == DcMotorSimple.Direction.FORWARD) ? "Forward" : "Reverse") );
        telemetry.addLine().addData("Position   ", motor.getCurrentPosition() );
        telemetry.addLine().addData("Power      ", motor.getPower() );
        telemetry.update();

        // FTC Dashboard Telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Name", PARAMS.motorName );

        packet.put("Direction", (PARAMS.motorForward ? 1 : 0));
        packet.put("Position", motor.getCurrentPosition());
        packet.put("Power", motor.getPower());
        dashboard.sendTelemetryPacket(packet);
    }
}
