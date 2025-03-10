package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Helper.GamePad;
import org.firstinspires.ftc.teamcode.Helper.TankDriveTrain;

import java.util.Locale;

@TeleOp(name="LEDTest&DriveTank", group="Test")
public class LEDTest extends LinearOpMode {

    public static class Params {
        public double servoStartPos = 0.532;
        public String servoName = "LEDtest2";
    }
    public static Params PARAMS = new Params();

    double newPosition = 0;
    double tlmServoPosition = 0;

    @Override
    public void runOpMode() {
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        telemetry.addLine("Driver Control");
        telemetry.addData("Version Number", 1.0);
        telemetry.addLine();
        telemetry.addData(">", "Press Start to Launch");
        telemetry.update();

        GamePad gamePad1 = new GamePad(gamepad1);
        TankDriveTrain tankerdrive = new TankDriveTrain(hardwareMap);
        Servo srv;

        srv = hardwareMap.servo.get(PARAMS.servoName);

        newPosition = PARAMS.servoStartPos;
        srv.setPosition(newPosition);
        tlmServoPosition = newPosition;

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();

        while (opModeIsActive()) {
            GamePad.GameplayInputType inpType = gamePad1.WaitForGamepadInput(30);
            switch (inpType) {
                case JOYSTICK:
                    double pos = 0;
                    pos = (gamepad1.left_stick_x * 0.5) + 0.501;
                    srv.setPosition(pos);
                    tankerdrive.setDriveFromJoystick(
                            gamepad1.left_stick_y,
                            gamepad1.right_stick_x
                    );
                    update_telemetry(pos);
                    break;
                default:
                    break;
            }

        }
    }
    private void update_telemetry(double lightValue) {
        telemetry.addLine().addData("Current Servo (Light) Value", lightValue);
        telemetry.addLine().addData("Current Joystick Input", gamepad1.left_stick_x);
        telemetry.update();
    }
}
