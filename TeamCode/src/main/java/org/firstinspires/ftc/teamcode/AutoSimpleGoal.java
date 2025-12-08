package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This Autonomous OpMode is designed to perform a simple task:
 * 1. Wait for a few seconds (a customisable delay).
 * 2. Fire the catapult.
 * 3. Drive backward at full speed for 1 second.
 * 4. Stop.
 */

@Autonomous(name = "AutoSimpleGoal_Move", group = "Autonomous")
public class AutoSimpleGoal extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    // --- DRIVE MOTOR DECLARATIONS (Keep original descriptive names) ---
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    
    // Declare end-effector motors
    private DcMotor catapult1 = null;
    private DcMotor catapult2 = null;

    // Use the same power constants from your TeleOp code
    private double CATAPULT_UP_POWER = -1.0;

    // --- DRIVE CONSTANTS ---
    private static final double DRIVE_POWER = 1.0; // Full speed
    private static final double DRIVE_DURATION_SECONDS = 1.0; // Drive time after shot

    // Configuration variables
    private static final double INITIAL_DELAY_SECONDS = 3.0; // Wait time before firing
    private static final double CATAPULT_SHOT_DURATION_SECONDS = 2.0; // How long to run the motor to fire

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");

        // --- DRIVE MOTOR INITIALIZATION (Updated to use 'lf', 'lr', 'rf', 'rr' as hardware names) ---
        // The variable on the left stays the same, the name in quotes changes.
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf"); // Maps to hardware name "lf"
        leftBackDrive = hardwareMap.get(DcMotor.class, "lr");  // Maps to hardware name "lr"
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf"); // Maps to hardware name "rf"
        rightBackDrive = hardwareMap.get(DcMotor.class, "rr"); // Maps to hardware name "rr"

        // --- CATAPULT MOTOR INITIALIZATION ---
        catapult1 = hardwareMap.get(DcMotor.class, "catapult1");
        catapult2 = hardwareMap.get(DcMotor.class, "catapult2");

        // --- SET DRIVE DIRECTIONS (Based on TeleOp) ---
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        
        // --- SET CATAPULT DIRECTIONS (Based on TeleOp) ---
        catapult1.setDirection(DcMotor.Direction.REVERSE);
        catapult2.setDirection(DcMotor.Direction.FORWARD);

        // --- SET ZERO POWER BEHAVIOR AND INITIAL STOP ---
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFrontDrive.setPower(0.0);
        leftBackDrive.setPower(0.0);
        rightFrontDrive.setPower(0.0);
        rightBackDrive.setPower(0.0);
        catapult1.setPower(0.0);
        catapult2.setPower(0.0);

        telemetry.addData("Status", "Initialized and Ready to Go!");
        telemetry.addData("Sequence", "Delay %.1f s -> Shoot -> Drive Back %.1f s",
                INITIAL_DELAY_SECONDS, DRIVE_DURATION_SECONDS);
        telemetry.update();

        waitForStart();
        runtime.reset();

        if (opModeIsActive()) {
            // --- STEP 1: INITIAL DELAY ---
            telemetry.addData("Status", "Waiting for %.1f seconds...", INITIAL_DELAY_SECONDS);
            telemetry.update();
            sleep((long) (INITIAL_DELAY_SECONDS * 1000));

            // --- STEP 2: FIRE CATAPULT ---
            telemetry.addData("Status", "Firing Catapult!");
            telemetry.update();

            // Run catapult motors
            catapult1.setPower(CATAPULT_UP_POWER);
            catapult2.setPower(CATAPULT_UP_POWER);
            sleep((long) (CATAPULT_SHOT_DURATION_SECONDS * 1000));
            
            // Stop catapult motors
            catapult1.setPower(0.0);
            catapult2.setPower(0.0);

            // --- STEP 3: DRIVE BACKWARD ---
            telemetry.addData("Status", "Driving Backward at Full Power!");
            telemetry.update();

            // To move BACKWARD, we use NEGATIVE power (-1.0)
            double backwardPower = -DRIVE_POWER; 

            leftFrontDrive.setPower(backwardPower);
            leftBackDrive.setPower(backwardPower);
            rightFrontDrive.setPower(backwardPower);
            rightBackDrive.setPower(backwardPower);

            // Run for the specified duration (1.0 second)
            sleep((long) (DRIVE_DURATION_SECONDS * 1000));
            
            // --- STEP 4: STOP ROBOT ---
            telemetry.addData("Status", "Stopping Drive Motors.");
            telemetry.update();

            leftFrontDrive.setPower(0.0);
            leftBackDrive.setPower(0.0);
            rightFrontDrive.setPower(0.0);
            rightBackDrive.setPower(0.0);

            telemetry.addData("Status", "Auto Complete.");
            telemetry.update();
        }

        while (opModeIsActive()) {
            idle();
        }
    }
}
