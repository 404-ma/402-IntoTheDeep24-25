package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.DrivetrainV2;
import org.firstinspires.ftc.teamcode.Helper.GamePad;

import java.util.List;
import java.util.Locale;

@Config
@TeleOp(name = "Motor Test", group = "Competition!!")
public class MotorTest extends LinearOpMode {


    private static final String version = "1.0";
    private boolean setReversed = false;
    // private ClawMoves yclaw;
    private boolean frontLeft, backLeft, frontRight, backRight = false;

    @Override
    public void runOpMode() {
        // Load Introduction and Wait for Start
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Motor Test");
        telemetry.addData("Version Number", version);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.addLine();
        telemetry.addData(">", "B = Right, X = Left, Y = Viper");
        telemetry.update();

        GamePad gpIn1 = new GamePad(gamepad1);
        GamePad gpIn2 = new GamePad(gamepad2);

        DcMotor leftMotor = hardwareMap.dcMotor.get("tankLeft");
        DcMotor rightMotor = hardwareMap.dcMotor.get("tankRight");
        DcMotor viperMotor = hardwareMap.dcMotor.get("Viper");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        viperMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        if (isStopRequested()) return;

        boolean left = false, right = false, viper = false;

        telemetry.clear();

        while (opModeIsActive()) {
            update_telemetry(gpIn1, gpIn2);


            GamePad.GameplayInputType inpType1 = gpIn1.WaitForGamepadInput(30);
            switch (inpType1) {
                case BUTTON_X:
                    left = !left;
                    leftMotor.setPower(left ? 1 : 0);
                    break;
                case BUTTON_B:
                    right = !right;
                    rightMotor.setPower(right ? 1 : 0);
                    break;
                case BUTTON_Y:
                    viper = !viper;
                    viperMotor.setPower(viper ? 1 : 0);
                    break;
            }
        }

    }

    private void update_telemetry(GamePad gpi1, GamePad gpi2) {
        telemetry.addLine("Gamepad #1");
        String inpTime1 = new java.text.SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.US).format(gpi1.getTelemetry_InputLastTimestamp());
        telemetry.addLine().addData("GP1 Time", inpTime1);
        telemetry.addLine().addData("GP1 Cnt", gpi1.getTelemetry_InputCount());
        telemetry.addLine().addData("GP1 Input", gpi1.getTelemetry_InputLastType().toString());
        telemetry.addLine().addData("Front Left", frontLeft).addData("Front Right", frontRight).addData("Back Left", backLeft).addData("Back Right", backRight);
        telemetry.update();
    }
}