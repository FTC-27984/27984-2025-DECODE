package org.firstinspires.ftc.teamcode.config.subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A dedicated class for controlling the robot's intake mechanism.
 * This encapsulates hardware configuration and control logic for better organization.
 */
public class Intake {

    // --- Hardware and Constants ---
    private static DcMotor intake = null;

    /**
     * Defines the desired state or mode of the intake.
     */
    public enum Mode {
        IN,
        UP,
        OFF
    }

    /**
     * Constructor for the Intake class.
     * @param hardwareMap The hardware map object provided by the OpMode.
     */
    public Intake(HardwareMap hardwareMap) {
        // Map the hardware based on the names in the robot configuration
        intake = hardwareMap.get(DcMotor.class, "intake");

        // Set motor direction and behavior
        intake.setDirection(DcMotor.Direction.FORWARD); // Forward should INTAKE.
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets the power of the intake motor based on the desired mode.
     * @param mode The desired state (IN, OUT, or OFF).
     */
    public static void setIntakePower(Mode mode) {
        double power;
        // Power constants derived from your original TeleOp
        double INTAKE_IN_POWER = 1.0;
        double INTAKE_UP_POWER = -0.9;
        double INTAKE_OFF_POWER = 0.0;
        switch (mode) {
            case IN:
                power = INTAKE_IN_POWER;
                break;
            case UP:
                power = INTAKE_UP_POWER;
                break;
            case OFF:
            default:
                power = INTAKE_OFF_POWER;
                break;
        }
        intake.setPower(power);
    }

    /**
     * Gets the current power of the intake motor for telemetry.
     * @return The current motor power.
     */
    public double getCurrentPower() {
        return intake.getPower();
    }
}
