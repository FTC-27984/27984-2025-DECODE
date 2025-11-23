package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass();

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("right_front_drive")
            .rightRearMotorName("right_back_drive")
            .leftRearMotorName("left_back_drive")
            .leftFrontMotorName("left_front_drive")
            .leftFrontMotorDirection(DcMotor.Direction.FORWARD)
            .leftRearMotorDirection(DcMotor.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotor.Direction.REVERSE)
            .rightRearMotorDirection(DcMotor.Direction.FORWARD);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        // Do not pass a Localizer; the builder handles it internally
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
