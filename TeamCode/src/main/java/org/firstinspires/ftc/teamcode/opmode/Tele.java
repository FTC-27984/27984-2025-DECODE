package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.Robot;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

@TeleOp(name = "TeleOp", group = "Main")
public class Tele extends OpMode {

    private Robot r;
    private MultipleTelemetry telemetryM;

    private boolean shootMode = false;
    private boolean intakeIn = false;
    private boolean intakeOut = false;

    @Override
    public void init() {
        r = new Robot(hardwareMap, Alliance.BLUE);
        telemetryM = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {
        // --- Drive control (mecanum) ---
        double forward = -gamepad1.left_stick_y;
        double strafe  = -gamepad1.left_stick_x;
        double rotate  = -gamepad1.right_stick_x * 0.7; // scale rotation

        r.drive.driveMecanum(forward, strafe, rotate);

        // --- Intake control ---
        if (gamepad1.right_bumper) {
            intakeIn = !intakeIn;
            intakeOut = false;
        } else if (gamepad1.left_bumper) {
            intakeOut = !intakeOut;
            intakeIn = false;
        }

        if (intakeIn) {
            r.intake.in();
        } else if (intakeOut) {
            r.intake.out();
        } else {
            r.intake.stop();
        }

        // --- Shooter control ---
        if (gamepad1.a) {
            shootMode = !shootMode;
        }

        if (shootMode) {
            r.shooter.shoot();
        } else {
            r.shooter.hold();
        }

        // --- Telemetry ---
        telemetryM.addData("Drive Forward", forward);
        telemetryM.addData("Drive Strafe", strafe);
        telemetryM.addData("Drive Rotate", rotate);
        telemetryM.addData("Shooter Mode", shootMode ? "Shooting" : "Idle");
        telemetryM.addData("Intake In", intakeIn);
        telemetryM.addData("Intake Out", intakeOut);
        telemetryM.addData("Shooter Power", r.shooter.getPower());
        telemetryM.update();
    }

    @Override
    public void stop() {
        r.stop();
        r.drive.stop();
        r.shooter.hold();
    }
}