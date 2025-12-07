package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This Autonomous OpMode is designed to perform a simple task:
 * 1. Wait for a few seconds (a customisable delay).
 * 2. Fire the catapult.
 * 3. Hold the catapult in the stowed/braked position.
 */

@Autonomous(name = "AutoSimpleGoal", group = "Autonomous")
public class AutoSimpleGoal extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // Declare end-effector motors based on TeleOp code
    private DcMotor catapult1 = null;
    private DcMotor catapult2 = null;

    // Use the same power constants from your TeleOp code
    private double CATAPULT_UP_POWER = -1.0;
    private double CATAPULT_DOWN_POWER = 1.0; // Used for returning to the stowed/braked position

    // Configuration variables
    private static final double INITIAL_DELAY_SECONDS = 3.0; // Wait time before firing
    private static final double CATAPULT_SHOT_DURATION_SECONDS = 2.0; // How long to run the motor to fire

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");

        catapult1 = hardwareMap.get(DcMotor.class, "catapult1");
        catapult2 = hardwareMap.get(DcMotor.class, "catapult2");

        catapult1.setDirection(DcMotor.Direction.REVERSE);
        catapult2.setDirection(DcMotor.Direction.FORWARD);

        catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        catapult1.setPower(0.0);
        catapult2.setPower(0.0);

        telemetry.addData("Status", "Initialized and Ready to Go!");
        telemetry.addData("Goal", "Will wait for %2.1f seconds, then fire catapult for %2.1f seconds.",
                INITIAL_DELAY_SECONDS, CATAPULT_SHOT_DURATION_SECONDS);
        telemetry.update();

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            // --- STEP 1: INITIAL DELAY ---
            telemetry.addData("Status", "Waiting for %.1f seconds...", INITIAL_DELAY_SECONDS);
            telemetry.update();
            sleep((long) (INITIAL_DELAY_SECONDS * 1000)); // The sleep() method takes milliseconds

            // --- STEP 2: FIRE CATAPULT ---
            telemetry.addData("Status", "Firing Catapult!");
            telemetry.update();

            // Run the catapult motors with the UP power
            catapult1.setPower(CATAPULT_UP_POWER);
            catapult2.setPower(CATAPULT_UP_POWER);

            // Wait for the duration of the shot
            sleep((long) (CATAPULT_SHOT_DURATION_SECONDS * 1000));

            // Stop the motors immediately after the shot
            catapult1.setPower(0.0);
            catapult2.setPower(0.0);

            // --- STEP 3: RETURN TO STOWED/BRAKE (Optional based on mechanism) ---
            // If the catapult needs to be pulled back down to a stowed position for braking,
            // you can use the CATAPULT_DOWN_POWER for a short time, as seen in your TeleOp's `cata_to_down_time` logic.
            // Based on your TeleOp comment "If you try to turn off motors the catapult will not stay down",
            // we will simply leave the motors in BRAKE mode after stopping power, hoping the mechanism holds.

            telemetry.addData("Status", "Catapult Fired. Auto Complete.");
            telemetry.update();
        }

        // Wait for the autonomous period to end
        while (opModeIsActive()) {
            // Keep looping to prevent the OpMode from exiting immediately
            idle();
        }
    }
}
