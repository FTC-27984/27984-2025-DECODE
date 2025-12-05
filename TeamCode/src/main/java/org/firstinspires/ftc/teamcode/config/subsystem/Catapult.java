package org.firstinspires.ftc.teamcode.config.subsystem;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * A dedicated class for controlling the robot's catapult mechanism (using two motors).
 * This class provides methods to set the catapult to specific pivot modes.
 */
public class Catapult {

    // --- Hardware and Constants ---
    private DcMotor catapult1 = null;
    private DcMotor catapult2 = null;

    /**
     * Defines the desired state or mode of the catapult.
     */
    public enum Mode {
        UP,
        DOWN,
        BRAKE // Used to hold the position
    }

    /**
     * Constructor for the Catapult class.
     * @param hardwareMap The hardware map object provided by the OpMode.
     */
    public Catapult(HardwareMap hardwareMap) {
        // Map the hardware based on the names in the robot configuration
        catapult1 = hardwareMap.get(DcMotor.class, "catapult1");
        catapult2 = hardwareMap.get(DcMotor.class, "catapult2");

        // Set motor directions (must match your hardware configuration)
        // Backwards should pivot DOWN, or in the stowed position.
        catapult1.setDirection(DcMotor.Direction.REVERSE);
        catapult2.setDirection(DcMotor.Direction.FORWARD);

        // Set initial zero power behavior
        catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Note: For advanced control (like holding a specific position), you might want to
        // use RunMode.RUN_TO_POSITION or a custom PID controller here.
    }

    /**
     * Sets the power of the catapult motors based on the desired mode.
     * This method combines the logic previously handled in the TeleOp loop.
     * @param mode The desired state (UP, DOWN, or BRAKE).
     */
    public void setCatapultPower(Mode mode) {
        double power;
        DcMotor.ZeroPowerBehavior behavior = DcMotor.ZeroPowerBehavior.BRAKE;

        // Power constants derived from your original TeleOp
        double CATAPULT_UP_POWER = -1.0;
        double CATAPULT_DOWN_POWER = 1.0;
        double CATAPULT_OFF_POWER = 0.0;
        switch (mode) {
            case UP:
                power = CATAPULT_UP_POWER;
                // If moving, RUN mode is fine.
                break;
            case DOWN:
                power = CATAPULT_DOWN_POWER;
                // If moving, RUN mode is fine.
                break;
            case BRAKE:
            default:
                power = CATAPULT_OFF_POWER;
                // Keeps the motor from coasting
                break;
        }

        // Apply power and behavior to both motors
        catapult1.setZeroPowerBehavior(behavior);
        catapult2.setZeroPowerBehavior(behavior);
        catapult1.setPower(power);
        catapult2.setPower(power);
    }

    /**
     * Retrieves the power setting for catapult motor 1 (for telemetry).
     * @return The current power of catapult motor 1.
     */
    public double getPower() {
        return catapult1.getPower();
    }
}
