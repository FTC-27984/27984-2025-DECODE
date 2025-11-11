package org.firstinspires.ftc.teamcode.config.commands;

import com.pedropathing.util.Timer;
import org.firstinspires.ftc.teamcode.config.subsystem.Intake;
import org.firstinspires.ftc.teamcode.config.subsystem.Shooter;

public class IntakeIn {
    private final Intake intake;
    private final Shooter shooter;
    private final Timer timer = new Timer();
    private boolean active = false;

    public IntakeIn(Intake intake, Shooter shooter) {
        this.intake = intake;
        this.shooter = shooter;
    }

    public void start() {
        shooter.hold();
        intake.in();
        timer.resetTimer();
        active = true;
    }

    public void update() {
        if (!active) return;

        if (timer.getElapsedTime() > 500) {
            intake.stop();
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }
}