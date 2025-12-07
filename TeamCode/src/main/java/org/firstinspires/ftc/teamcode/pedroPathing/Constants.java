package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {

    // Pedro follower constants (mass, tuned values, etc.)
    // TODO: change 5 to your robot's mass in kilograms
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(2.3);

    // Mecanum drivetrain constants: motor names + directions + max power
    // TODO: change the motor names here to match your RC configuration
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1.0)
            .rightFrontMotorName("rightfrontdrive")
            .rightRearMotorName("rightbackdrive")
            .leftRearMotorName("leftbackdrive")
            .leftFrontMotorName("leftfrontdrive")
            // Directions: you will probably need to tweak these based on how your robot drives
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD);

    // Path constraints (same style as your original file)
    // PathConstraints(tValueConstraint, velocityConstraint, translationalConstraint, headingConstraint)
    public static PathConstraints pathConstraints = new PathConstraints(
            0.99,
            100,
            1,
            1
    );

    // Build the Pedro Follower used everywhere (Tuning, Autos, etc.)
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}