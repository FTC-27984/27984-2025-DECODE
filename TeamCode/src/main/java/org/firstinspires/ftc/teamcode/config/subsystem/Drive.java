package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drive {
    public final DcMotor lf, lr, rf, rr;

    public Drive(HardwareMap h) {
        lf = h.get(DcMotor.class, "leftFront");
        lr = h.get(DcMotor.class, "leftRear");
        rf = h.get(DcMotor.class, "rightFront");
        rr = h.get(DcMotor.class, "rightRear");

        lf.setDirection(DcMotor.Direction.FORWARD);
        lr.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rr.setDirection(DcMotor.Direction.REVERSE);

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveMecanum(double forward, double strafe, double rotate) {
        // Mecanum drive math
        double lfPower = forward + strafe + rotate;
        double lrPower = forward - strafe + rotate;
        double rfPower = forward - strafe - rotate;
        double rrPower = forward + strafe - rotate;

        // Normalize powers
        double max = Math.max(Math.abs(lfPower), Math.max(Math.abs(lrPower),
                Math.max(Math.abs(rfPower), Math.abs(rrPower))));
        if (max > 1.0) {
            lfPower /= max;
            lrPower /= max;
            rfPower /= max;
            rrPower /= max;
        }

        lf.setPower(lfPower);
        lr.setPower(lrPower);
        rf.setPower(rfPower);
        rr.setPower(rrPower);
    }

    public void stop() {
        lf.setPower(0);
        lr.setPower(0);
        rf.setPower(0);
        rr.setPower(0);
    }
}