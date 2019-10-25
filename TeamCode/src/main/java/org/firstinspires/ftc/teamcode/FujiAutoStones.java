package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.graphics.Color;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="FujiAutoStones", group="PatentPending")
public class FujiAutoStones extends LinearOpMode {

    // Declare timer.
    private ElapsedTime runtime = new ElapsedTime();
    // Declare OpMode members.
    private DcMotor rfMotor;
    private DcMotor lfMotor;
    private DcMotor rbMotor;
    private DcMotor lbMotor;
    private DcMotor extender;
    private DcMotor hinge;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;

    // Declare constants.
    private static final double PI = 3.1415;
    private static final double ROOT_TWO = 1.4142;
    // Declare drive measurements.
    private static final double DRIVE_SPEED = 1;
    private static final double TIMEOUT_SEC = 10;
    // Declare wheel measurements.
    private static final double GEAR_RATIO = 1; // Gear ratio on the motors, should be greater than 1 if gearing faster.
    private static final double WHEEL_DIAMETER_INCH = 3.7;
    private static final double INCH_PER_WHEEL_REV = WHEEL_DIAMETER_INCH * PI;
    // Declare robot measurements.
    private static final double ROBOT_EDGE_INCH = 17.8;
    private static final double ROBOT_DIAGONAL_INCH = 19;
    private static final double INCH_PER_ROBOT_REV = ROBOT_DIAGONAL_INCH * PI;
    // Declare distance measurements.
    private static final double COUNT_PER_MOTOR_REV = 1120; // REV Motor Encoder.
    private static final double COUNT_PER_WHEEL_REV = COUNT_PER_MOTOR_REV / GEAR_RATIO;
    private static final double COUNT_PER_INCH = COUNT_PER_WHEEL_REV / INCH_PER_WHEEL_REV;
    // Declare color measurements.
    private static final double COLOR_SENSOR_SCALE_FACTOR = 255;
    private static final float[] COLOR_SENSOR_HSV = {0F, 0F, 0F};
    // Declare field measurements.
    private static final double STONE_BRIDGE_DISTANCE_INCH = 23.3;
    private static final double STONE_WALL_DISTANCE_INCH = 47;
    private static final double STONE_LENGTH_INCH = 8;
    private static final double SKYSTONE_DISTANCE_STONES = 3;

    @Override
    public void runOpMode() {

        // Declare stone sensing.
        int currentStone = 0;

        // Initialize OpMode members.
        rfMotor = hardwareMap.dcMotor.get("rf");
        lfMotor = hardwareMap.dcMotor.get("lf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lbMotor = hardwareMap.dcMotor.get("lb");
        extender = hardwareMap.dcMotor.get("ext");
        hinge = hardwareMap.dcMotor.get("hin");
        sensorColor = hardwareMap.colorSensor.get("color");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "color");

        telemetry.addData("Motors", "resetting encoders.");
        telemetry.update();
        sleep(500);

        rfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lbMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hinge.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        hinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Motors", "encoders done resetting.");
        telemetry.update();

        // Wait for game to start (driver presses PLAY).
        waitForStart();

        telemetry.addData("Path", "started.");
        telemetry.update();

	    // Go up to stones.
        encoderDrive(STONE_WALL_DISTANCE_INCH - ROBOT_EDGE_INCH, 0);
        // Drive sideways until the robot reaches the end of the stone line.
        sensorDrive(0, -1, 3);
        // Move to the middle of the first stone.
        encoderDrive (0, STONE_LENGTH_INCH / 2);

        // Start sensing stones.
        while (!isSkystone()) {
            currentStone++;
            if (currentStone >= SKYSTONE_DISTANCE_STONES) {
                encoderDrive (0, -STONE_LENGTH_INCH * (SKYSTONE_DISTANCE_STONES - 1));
                currentStone = 0;
            } else {
                encoderDrive (0, STONE_LENGTH_INCH);
            }
        }

        // Park under bridge.
        encoderDrive(STONE_WALL_DISTANCE_INCH - ROBOT_EDGE_INCH, 0);
        encoderDrive(0, -currentStone * STONE_LENGTH_INCH - STONE_LENGTH_INCH / 2 - STONE_BRIDGE_DISTANCE_INCH);
        encoderDrive(0, ROBOT_EDGE_INCH / 2);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    private void encoderMove(double rfInch, double lfInch, double rbInch, double lbInch) {
        // Ensure that the OpMode is still active.
        if (opModeIsActive()) {
            // Set targets.
            rfMotor.setTargetPosition((int)(rfInch * COUNT_PER_INCH) + rfMotor.getCurrentPosition());
            rbMotor.setTargetPosition((int)(rbInch * COUNT_PER_INCH) + rbMotor.getCurrentPosition());
            lfMotor.setTargetPosition((int)(lfInch * COUNT_PER_INCH) + lfMotor.getCurrentPosition());
            lbMotor.setTargetPosition((int)(lbInch * COUNT_PER_INCH) + lbMotor.getCurrentPosition());
            // Set motors to RUN_TO_POSITION mode.
            rfMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lfMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rbMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lbMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Reset the timer.
            runtime.reset();
            // Start motion.
            rfMotor.setPower(DRIVE_SPEED);
            lfMotor.setPower(DRIVE_SPEED);
            rbMotor.setPower(DRIVE_SPEED);
            lbMotor.setPower(DRIVE_SPEED);
            // Keep looping while we are still active and any motors are running.
            telemetry.addData("Move", "started moving.");
            telemetry.update();
            while (opModeIsActive() &&
                   runtime.seconds() < TIMEOUT_SEC &&
                  (rfMotor.isBusy() || lfMotor.isBusy() || rbMotor.isBusy() || lbMotor.isBusy())) {}
            telemetry.addData("Move", "done moving.");
            telemetry.update();
            // Stop motion.
            rfMotor.setPower(0);
            lfMotor.setPower(0);
            rbMotor.setPower(0);
            lbMotor.setPower(0);
            // Turn off RUN_TO_POSITION mode.
            rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    private void encoderTurn(double revolutions) {
        encoderMove(revolutions * INCH_PER_ROBOT_REV,
                    revolutions * INCH_PER_ROBOT_REV,
                    revolutions * INCH_PER_ROBOT_REV,
                    revolutions * INCH_PER_ROBOT_REV);
    }

    private void encoderDrive(double forInch, double horiInch) {
        encoderMove((+ forInch - horiInch) / ROOT_TWO,
                    (+ forInch + horiInch) / ROOT_TWO,
                    (- forInch - horiInch) / ROOT_TWO,
                    (- forInch + horiInch) / ROOT_TWO);
    }

    private void sensorDrive(double forSpeed, double horiSpeed, double distance) {
        rfMotor.setPower((+ forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        rbMotor.setPower((+ forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        lfMotor.setPower((- forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        lbMotor.setPower((- forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        while (sensorDistance.getDistance(DistanceUnit.INCH) < distance) {}
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
    }

    private boolean isSkystone() {
        // Declare BlockID.
        boolean blockID;
        // Get HSV value.
        telemetry.addData("Color Sensor", "sensing block.");
        Color.RGBToHSV((int)(sensorColor.red() * COLOR_SENSOR_SCALE_FACTOR),
                       (int)(sensorColor.green() * COLOR_SENSOR_SCALE_FACTOR),
                       (int)(sensorColor.blue() * COLOR_SENSOR_SCALE_FACTOR),
                        COLOR_SENSOR_HSV);
        telemetry.addData("Value", COLOR_SENSOR_HSV[2]);
        // Check which stone is sensed.
        blockID = COLOR_SENSOR_HSV[2] <= 80;
        telemetry.addData("Block ID", blockID);
        // Return sensed block.
        telemetry.update();
        sleep(1000);
        return blockID;
    }
/*
    public void armGrab(){
        encoderDrive(-7.0,0.0); //backup 7 inches
        encoderTurn(0.5);//180 turn
        armMove(DRIVE_SPEED, 0.0, 0.0, 0.0);
    }

    private void armMove(double speed, double hinRev, double extenderInch, double timeout) {

        // Ensure that the opMode is still active.
        if (opModeIsActive()) {

            // Declare motor targets.
            double hingeInch = hinRev * INCH_PER_ARM_REV; //set to inches per a hypotetical full arm rotation using some calculations
            // Set targets.
            hinge.setTargetPosition((int)(hingeInch * COUNT_PER_INCH) + rfMotor.getCurrentPosition());
            extender.setTargetPosition((int)(extenderInch * COUNT_PER_INCH) + rbMotor.getCurrentPosition());
            // Set motors to RUN_TO_POSITION mode.
            hinge.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // Reset the timer.
            runtime.reset();

            // Start motion.
            hinge.setPower(speed);
            extender.setPower(speed);
            
            // keep looping while we are still active and any motors are running.
            telemetry.addData("Update", "Started moving.");
            telemetry.update();
            while (opModeIsActive() &&
                   runtime.seconds() < timeout &&
                  (rfMotor.isBusy() || lfMotor.isBusy() || rbMotor.isBusy() || lbMotor.isBusy())) {}
            telemetry.addData("Update", "Done moving.");
            telemetry.update();
            // Stop all motion.
            hinge.setPower(0);
            extender.setPower(0);

            // Turn off RUN_USING_ENCODER mode.
            hinge.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
*/
}