package org.firstinspires.ftc.teamcode.config.commands;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;

public class FollowPath {
    private final Follower follower;
    private final PathChain path;
    private boolean holdEnd = true;
    private double maxPower = 1;
    private double completionThreshold = 0.99;
    private boolean active = false;

    public FollowPath(Follower follower, PathChain path) {
        this.follower = follower;
        this.path = path;
    }

    public FollowPath(Follower follower, PathChain path, double maxPower) {
        this(follower, path);
        this.maxPower = maxPower;
    }

    public FollowPath(Follower follower, PathChain path, boolean holdEnd) {
        this(follower, path);
        this.holdEnd = holdEnd;
    }

    public FollowPath(Follower follower, PathChain path, boolean holdEnd, double maxPower) {
        this(follower, path, maxPower);
        this.holdEnd = holdEnd;
    }

    public FollowPath setHoldEnd(boolean holdEnd) {
        this.holdEnd = holdEnd;
        return this;
    }

    public FollowPath setMaxPower(double power) {
        this.maxPower = power;
        return this;
    }

    public FollowPath setCompletionThreshold(double t) {
        this.completionThreshold = t;
        return this;
    }

    public void start() {
        follower.setMaxPower(maxPower);
        follower.followPath(path, holdEnd);
        active = true;
    }

    public boolean isFinished() {
        return follower.atParametricEnd();
    }

    public void end() {
        follower.setMaxPower(1);
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}