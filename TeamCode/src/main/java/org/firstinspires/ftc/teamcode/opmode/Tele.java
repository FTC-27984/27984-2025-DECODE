package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

@TeleOp(name = "TeleOp Field-Centric", group = "Main")
public class Tele extends OpMode {

    private Robot r;
    private MultipleTelemetry telemetryM;

    private boolean shootMode = false;

    @Override
    public void init() {
        r = new Robot(hardwareMap, Alliance.BLUE);
        telemetryM = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        // --- Drive control (always field-centric) ---
        double forward = -gamepad1.left_stick_y;
        double strafe  = -gamepad1.left_stick_x;
        double rotate  = -gamepad1.right_stick_x * 0.7;

        r.follower.setTeleOpDrive(
                forward,
                strafe,
                rotate,
                false// false = field-centric
        );

        // --- Intake control ---
        if (gamepad1.right_bumper) {
            r.intake.in();
        } else if (gamepad1.left_bumper) {
            r.intake.out();
        } else {
            r.intake.stop();
        }

        // --- Shooter control ---
        if (gamepad1.a) {
            shootMode = true;
        } else if (gamepad1.b) {
            shootMode = false;
        }

        if (shootMode) {
            r.shooter.shoot();
        } else {
            r.shooter.hold();
        }

        // --- Periodic updates ---
        r.periodic();

        // --- Telemetry ---
        telemetryM.addData("Forward", forward);
        telemetryM.addData("Strafe", strafe);
        telemetryM.addData("Rotate", rotate);
        telemetryM.addData("Shooter Mode", shootMode ? "Shooting" : "Idle");
        telemetryM.addData("Shooter Power", r.shooter.getPower());
        telemetryM.update();
    }

    @Override
    public void stop() {
        r.stop();
        r.shooter.hold();
    }
}