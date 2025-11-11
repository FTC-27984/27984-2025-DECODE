package org.firstinspires.ftc.teamcode.config.util;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.ArrayList;
import java.util.List;

public abstract class OpModeCommand extends OpMode {

    // Simple list to hold commands
    private final List<Runnable> scheduledCommands = new ArrayList<>();

    /**
     * Cancel all previously scheduled commands
     */
    public void reset() {
        scheduledCommands.clear();
    }

    /**
     * Run all scheduled commands
     */
    public void run() {
        for (Runnable cmd : scheduledCommands) {
            cmd.run();
        }
    }

    /**
     * Schedule one or more commands
     */
    public void schedule(Runnable... commands) {
        for (Runnable cmd : commands) {
            scheduledCommands.add(cmd);
        }
    }

    /**
     * Register subsystems - for compatibility
     * Here it's just a placeholder since no SolversLib
     */
    public void register(Object... subsystems) {
        // In a full implementation you could keep track of subsystems
    }

    @Override
    public void init() {
        initialize();
    }

    @Override
    public void loop() {
        run();
    }

    @Override
    public void stop() {
        reset();
    }

    /**
     * Called once when OpMode is initialized
     */
    public abstract void initialize();
}