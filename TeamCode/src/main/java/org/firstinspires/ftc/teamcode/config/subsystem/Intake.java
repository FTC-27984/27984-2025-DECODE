package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotor intake;

    private static final double INTAKE_IN_POWER = 1.0;
    private static final double INTAKE_OUT_POWER = -0.9;
    private static final double INTAKE_OFF_POWER = 0.0;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotor.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        stop();
    }

    public void in() {
        intake.setPower(INTAKE_IN_POWER);
    }

    public void out() {
        intake.setPower(INTAKE_OUT_POWER);
    }

    public void stop() {
        intake.setPower(INTAKE_OFF_POWER);
    }

    public double getPower() {
        return intake.getPower();
    }
}