package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit; // Import for AngleUnit
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation; // Import for Orientation

// Based on the sample: Basic: Omni Linear OpMode
@TeleOp(name = "Field Centric Control", group = "Teleop") // Renamed for clarity

public class TeleOpControlLinearOpMode extends LinearOpMode {

    // Declare OpMode members for each of the 4 motors.
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime catatime = new ElapsedTime();

    // Declare drive motors
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    // Declare end-effector members
    private DcMotor intake = null;
    private DcMotor catapult1 = null;
    private DcMotor catapult2 = null;
    private DcMotor foot = null;

    // Declare IMU member
    private BNO055IMU imu; // Declare the IMU

    // motor power 1 = 100% and 0.5 = 50%
    // (Existing subsystem power constants and enums remain the same)
    private double INTAKE_IN_POWER = 1.0;
    private double INTAKE_OUT_POWER = -0.9;
    private double INTAKE_OFF_POWER = 0.0;
    private double intakePower = INTAKE_OFF_POWER;

    private double FOOT_UP_POWER = 1.0;
    private double FOOT_DOWN_POWER = -0.85;
    private double FOOT_OFF_POWER = 0.0;
    private double footPower = FOOT_OFF_POWER;

    private double CATAPULT_UP_POWER = -1.0;
    private double CATAPULT_DOWN_POWER = 1.0;

    private enum CatapultModes {UP, DOWN, BRAKE}
    private CatapultModes pivotMode;
    private enum FootMode {UP, DOWN, BRAKE}
    private FootMode footmode;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        // --- Hardware Initialization (Same as before) ---
        leftFrontDrive = hardwareMap.get(DcMotor.class, "left_front_drive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "left_back_drive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "right_front_drive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "right_back_drive");

        intake = hardwareMap.get(DcMotor.class, "intake");
        catapult1 = hardwareMap.get(DcMotor.class, "catapult1");
        catapult2 = hardwareMap.get(DcMotor.class, "catapult2");
        foot = hardwareMap.get(DcMotor.class, "foot");

        // Set direction of wheel motors
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set direction of subsystem motors
        intake.setDirection(DcMotor.Direction.FORWARD);
        catapult1.setDirection(DcMotor.Direction.REVERSE);
        catapult2.setDirection(DcMotor.Direction.FORWARD);
        foot.setDirection(DcMotor.Direction.REVERSE);

        // Set initial subsystem behavior
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // --- IMU Initialization (NEW) ---
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS; // Use radians for simpler math
        imu = hardwareMap.get(BNO055IMU.class, "imu"); // Assuming "imu" in your config
        imu.initialize(parameters);

        telemetry.addData("Status", "IMU Calibrating...");
        telemetry.update();

        // Optional: Wait for IMU to calibrate if needed, though usually starts quickly
        while (!isStopRequested() && !imu.isSystemCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Status", "Initialized and Calibrated");
        telemetry.update();

        waitForStart();
        runtime.reset();
        catatime.reset();

        // --- Game Running Loop ---
        while (opModeIsActive()) {
            double max;

            // Get the raw joystick inputs
            double axial = -gamepad1.left_stick_y;  // Forward/Backward
            double lateral = gamepad1.left_stick_x; // Strafe Left/Right (Note: Reversed from your original lateral to match common convention)
            double yaw = -gamepad1.right_stick_x; // Rotation

            // --- Field Centric Logic (NEW) ---

            // 1. Get the current robot heading from the IMU (Z-axis rotation)
            // Use the absolute heading for rotation
            Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
            double robotHeading = angles.firstAngle;

            // Optional: Reset/Zero the heading when a button is pressed (e.g., dpad_up)
            if (gamepad1.dpad_up) {
                // Re-initialize IMU to effectively zero the angle
                imu.initialize(parameters);
                robotHeading = 0.0;
                telemetry.addData("IMU", "Heading Reset!");
            }

            // 2. Perform trigonometric rotation of the joystick inputs (axial and lateral)
            double rotatedX = lateral * Math.cos(-robotHeading) - axial * Math.sin(-robotHeading);
            double rotatedY = lateral * Math.sin(-robotHeading) + axial * Math.cos(-robotHeading);

            // Set the new calculated values for the drive motors
            axial = rotatedY;
            lateral = rotatedX;
            // Yaw remains the same as it is a robot-centric rotation

            // --- DRIVE CODE (Modified to use new axial/lateral) ---
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            // (Subsystem control logic remains the same)
            // INTAKE CODE
            boolean intakeInButton = gamepad1.left_trigger > 0.2;
            boolean intakeOutButton = gamepad1.left_bumper;

            if (intakeInButton && intakeOutButton) {
                intakeInButton = false;
            }

            if (intakeInButton) {
                intakePower = INTAKE_IN_POWER;
            } else if (intakeOutButton) {
                intakePower = INTAKE_OUT_POWER;
            } else {
                intakePower = INTAKE_OFF_POWER;
            }

            // FOOT CODE
            boolean footOutButton = gamepad1.a;
            boolean footUpButton = gamepad1.b;
            if (footOutButton && footUpButton) {
                footOutButton = false;
            }

            if (footOutButton) {
                footmode = FootMode.DOWN;
                footPower = FOOT_DOWN_POWER;
            } else if (footUpButton) {
                footmode = FootMode.UP;
                footPower = FOOT_UP_POWER;
            } else {
                footmode = FootMode.BRAKE;
                foot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            // CATAPULT CODE
            boolean catapultUpButton = gamepad1.right_bumper;
            boolean catapultDownButton = gamepad1.right_trigger > 0.2;
            if (catapultUpButton && catapultDownButton) {
                catapultUpButton = false;
            }

            if (catapultUpButton) {
                pivotMode = CatapultModes.UP;
                catapult1.setPower(CATAPULT_UP_POWER);
                catapult2.setPower(CATAPULT_UP_POWER);
            } else if (catapultDownButton) {
                pivotMode = CatapultModes.DOWN;
                catapult1.setPower(CATAPULT_DOWN_POWER);
                catapult2.setPower(CATAPULT_DOWN_POWER);
            } else {
                // Keep motors running down briefly/brake
                // (Your original cata_to_down_time logic is removed here as it blocks the TeleOp loop,
                // which is bad practice. We'll stick to a simple brake.)
                pivotMode = CatapultModes.BRAKE;
                catapult1.setPower(0.0);
                catapult2.setPower(0.0);
                catapult1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                catapult2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }


            // WRITE EFFECTORS - Send calculated power to wheels
            leftFrontDrive.setPower(leftFrontPower);
            rightFrontDrive.setPower(rightFrontPower);
            leftBackDrive.setPower(leftBackPower);
            rightBackDrive.setPower(rightBackPower);

            intake.setPower(intakePower);
            foot.setPower(footPower);

            String catapult_mode_str;
            if (pivotMode == CatapultModes.UP) {
                catapult_mode_str = "UP";
            } else if (pivotMode == CatapultModes.DOWN) {
                catapult_mode_str = "DOWN";
            } else {
                catapult_mode_str = "HOLD";
            }

            // UPDATE TELEMETRY (Added IMU data)
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("IMU Heading (Deg)", AngleUnit.DEGREES.fromRadians(robotHeading));
            telemetry.addData("Front Left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  Left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.addData("Catapult MODE", "%s", catapult_mode_str);
            telemetry.update();
        }
    }
}
