package org.firstinspires.ftc.teamcode.config.Paths;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "RedGoalSide", group = "Pedro Pathing")
public class RedAllianceGoalSide {

    public PathChain RGPath1;
    public PathChain RGPath2EndWithShoot;
    public PathChain RGPath3Endwithopenintake;
    public PathChain RGPath4EndwithclosedIntake;
    public PathChain RGPath5Endwithshoot;
    public PathChain RGPath6Openwithopenintake;
    public PathChain RGPath7Endwithclosedintake;
    public PathChain RGPath8Endwithshoot;
    public PathChain RGPath9;
    public PathChain RGPath10;
    public RedAllianceGoalSide(Follower follower) {
        RGPath1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(123.000, 123.700), new Pose(120.738, 122.068))
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath2EndWithShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(120.738, 122.068), new Pose(123.175, 123.840))
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath3Endwithopenintake = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(123.175, 123.840),
                                new Pose(64.246, 80.640),
                                new Pose(100.357, 83.298)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        RGPath4EndwithclosedIntake = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100.357, 83.298), new Pose(124.062, 83.520))
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath5Endwithshoot = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(124.062, 83.520),
                                new Pose(51.175, 71.335),
                                new Pose(122.511, 123.175)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath6Openwithopenintake = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(122.511, 123.175),
                                new Pose(57.378, 76.209),
                                new Pose(100.135, 59.594)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        RGPath7Endwithclosedintake = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(100.135, 59.594), new Pose(123.840, 59.815))
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath8Endwithshoot = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(123.840, 59.815),
                                new Pose(42.978, 63.582),
                                new Pose(122.511, 122.954)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();

        RGPath9 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(122.511, 122.954),
                                new Pose(45.637, 68.455),
                                new Pose(114.978, 70.892)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();

        RGPath10 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(114.978, 70.892), new Pose(128.271, 71.335))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }
}
