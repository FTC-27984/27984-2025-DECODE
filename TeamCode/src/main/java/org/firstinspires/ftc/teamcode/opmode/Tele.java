package org.firstinspires.ftc.teamcode.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.config.Robot;

@TeleOp
public class Tele extends OpMode {

    Robot r;
    MultipleTelemetry telemetryM;

    private boolean shoot = false;
    private double intakeOn = 0;
    private final Timer autoFlipTimer = new Timer();

    @Override
    public void init() {
        r = new Robot(hardwareMap);

        telemetryM = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry());

        // Make sure shooter starts in hold mode
        r.shooter.hold();
    }

    @Override
    public void loop() {
        r.periodic();

        // Intake control
        if (gamepad1.right_bumper) {
            intakeOn = intakeOn == 1 ? 0 : 1; // toggle spinIn
        }
        if (gamepad1.left_bumper) {
            intakeOn = intakeOn == 2 ? 0 : 2; // toggle spinOut
        }

        if (intakeOn == 1) r.intake.in();
        else if (intakeOn == 2) r.intake.out();
        else r.intake.stop();

        // Shooter control
        if (shoot) {
            r.shooter.shoot();
        } else {
            r.shooter.hold();
        }

        // Toggle shooting with B button
        if (gamepad1.b) {
            shoot = !shoot;
            autoFlipTimer.resetTimer();
        }

        // Simple auto-flip logic (example timing)
        if (shoot && autoFlipTimer.getElapsedTimeSeconds() > 2) {
            r.shooter.hold(); // stop after 2 seconds
            shoot = false;
        }

        // Dashboard telemetry
        TelemetryPacket packet = new TelemetryPacket();
        packet.addLine("Shooter Power: " + r.shooter.getPower());
        packet.addLine("Intake State: " + intakeOn);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);

        telemetryM.addData("Shooter Power", r.shooter.getPower());
        telemetryM.addData("Intake State", intakeOn);
        telemetryM.update();
    }

    @Override
    public void stop() {
        r.stop();
    }
}