package org.firstinspires.ftc.teamcode.config.Paths;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BlueGoalSide", group = "Pedro Pathing")
public class BlueAllianceGoalSide {
    public PathChain BGPath1;
    public PathChain BGPath2EndWithShoot;
    public PathChain BGPath3Endwithopenintake;
    public PathChain BGPath4EndwithclosedIntake;
    public PathChain BGPath5Endwithshoot;
    public PathChain BGPath6Openwithopenintake;
    public PathChain BGPath7Endwithclosedintake;
    public PathChain BGPath8Endwithshoot;
    public PathChain BGPath9;
    public PathChain BGPath10;
    public BlueAllianceGoalSide(Follower follower) {
        BGPath1 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(21.000, 123.700),
                                new Pose(23.262, 122.068)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath2EndWithShoot = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(23.262, 122.068),
                                new Pose(20.825, 123.840)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath3Endwithopenintake = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(20.825, 123.840),
                                new Pose(79.754, 80.640),
                                new Pose(43.643, 83.298)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        BGPath4EndwithclosedIntake = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(43.643, 83.298),
                                new Pose(19.938, 83.520)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath5Endwithshoot = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(19.938, 83.520),
                                new Pose(92.825, 71.335),
                                new Pose(21.489, 123.175)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath6Openwithopenintake = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(21.489, 123.175),
                                new Pose(86.622, 76.209),
                                new Pose(43.865, 59.594)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        BGPath7Endwithclosedintake = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(43.865, 59.594),
                                new Pose(20.160, 59.815)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath8Endwithshoot = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(20.160, 59.815),
                                new Pose(101.022, 63.582),
                                new Pose(21.489, 122.954)
                        )
                )
                .setTangentHeadingInterpolation()
                .build();
        BGPath9 = follower
                .pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Pose(21.489, 122.954),
                                new Pose(98.363, 68.455),
                                new Pose(29.022, 70.892)
                        )
                )
                .setTangentHeadingInterpolation()
                .setReversed()
                .build();
        BGPath10 = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(
                                new Pose(29.022, 70.892),
                                new Pose(15.729, 71.335)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(90))
                .build();
    }
}