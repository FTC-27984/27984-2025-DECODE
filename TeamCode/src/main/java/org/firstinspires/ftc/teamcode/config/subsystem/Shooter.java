package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    private final DcMotor catapult1;
    private final DcMotor catapult2;

    private static final double CATAPULT_UP_POWER = -1.0;
    private static final double CATAPULT_DOWN_POWER = 1.0;
    private static final double CATAPULT_HOLD_POWER = 0.0;

    public enum Mode {
        UP,
        DOWN,
        HOLD
    }

    private Mode mode = Mode.HOLD;

    public Shooter(HardwareMap hardwareMap) {
        catapult1 = hardwareMap.get(DcMotor.class, "catapult1");
        catapult2 = hardwareMap.get(DcMotor.class, "catapult2");

        catapult1.setDirection(DcMotor.Direction.REVERSE);
        catapult2.setDirection(DcMotor.Direction.FORWARD);

        catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hold();
    }

    public void shoot() {
        mode = Mode.UP;
        catapult1.setPower(CATAPULT_UP_POWER);
        catapult2.setPower(CATAPULT_UP_POWER);
    }

    public void reload() {
        mode = Mode.DOWN;
        catapult1.setPower(CATAPULT_DOWN_POWER);
        catapult2.setPower(CATAPULT_DOWN_POWER);
    }

    public void hold() {
        mode = Mode.HOLD;
        catapult1.setPower(CATAPULT_HOLD_POWER);
        catapult2.setPower(CATAPULT_HOLD_POWER);
    }

    public void stop() {
        hold();
    }

    public Mode getMode() {
        return mode;
    }

    public double getPower() {
        return catapult1.getPower();
    }

    public void periodic() {
        // Call from Robot.periodic()
    }
}