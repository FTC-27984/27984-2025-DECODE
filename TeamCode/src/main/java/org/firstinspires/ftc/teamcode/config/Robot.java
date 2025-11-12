package org.firstinspires.ftc.teamcode.config;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.config.pedro.Constants;
import org.firstinspires.ftc.teamcode.config.subsystem.Drive;
import org.firstinspires.ftc.teamcode.config.subsystem.Intake;
import org.firstinspires.ftc.teamcode.config.subsystem.Shooter;
import org.firstinspires.ftc.teamcode.config.util.Alliance;

public class Robot {

    public final Intake intake;
    public final Shooter shooter;
    public final Follower follower;
    public final Drive drive;

    private final LynxModule hub;
    private final Timer loopTimer = new Timer();

    public static Pose endPose;
    public static Pose defaultPose = new Pose(32, 30.25, 0);
    public static Pose shootTarget = new Pose(6, 138, 0);
    public double alliance;

    public Robot(HardwareMap hardwareMap, Alliance blue) {
        intake = new Intake(hardwareMap);
        shooter = new Shooter(hardwareMap);
        drive = new Drive(hardwareMap);
        follower = Constants.createFollower(hardwareMap);

        hub = hardwareMap.getAll(LynxModule.class).get(0);
        hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);

        loopTimer.resetTimer();
    }

    public void periodic() {
        // Clear bulk cache every 5 seconds
        if ((int) loopTimer.getElapsedTime() % 5 == 0) {
            hub.clearBulkCache();
        }

        // Update subsystems
        follower.update();
        shooter.periodic();
    }

    public void stop() {
        endPose = follower.getPose();
    }
}
