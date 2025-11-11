package org.firstinspires.ftc.teamcode.config.commands;

import com.pedropathing.util.Timer;
import org.firstinspires.ftc.teamcode.config.subsystem.Shooter;

public class Shoot {
    private final Shooter shooter;
    private final Timer timer = new Timer();
    private boolean active = false;

    public Shoot(Shooter shooter) {
        this.shooter = shooter;
    }

    public void start() {
        shooter.shoot();
        timer.resetTimer();
        active = true;
    }

    public void update() {
        if (!active) return;

        // Stop after 0.5 seconds (adjust as needed)
        if (timer.getElapsedTimeSeconds() > 0.5) {
            shooter.hold();
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }
}