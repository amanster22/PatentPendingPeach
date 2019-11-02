package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import android.graphics.Color;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

abstract class FujiAuto extends LinearOpMode {
    // Declare timer.
    private ElapsedTime runtime = new ElapsedTime();
    // Declare OpMode members.
    private DcMotor rfMotor;
    private DcMotor lfMotor;
    private DcMotor rbMotor;
    private DcMotor lbMotor;
    private ColorSensor sensorColor;
    private DistanceSensor sensorDistance;
    DcMotor hin1;
    DcMotor hin2;
    CRServo hook;
    CRServo pinch;

    // Declare constants.
    private static final double PI = 3.1415;
    private static final double ROOT_TWO = 1.4142;
    // Declare drive measurements.
    private static final double DRIVE_SPEED = 0.5;
    private static final double TIMEOUT_SEC = 10;
    private static final double ERROR_MARGIN = 1.2;
    // Declare wheel measurements.
    private static final double GEAR_RATIO = 1; // Should be > 1 if gearing faster.
    private static final double WHEEL_DIAMETER_INCH = 3.7;
    private static final double INCH_PER_WHEEL_REV = WHEEL_DIAMETER_INCH * PI;
    // Declare distance measurements.
    private static final double COUNT_PER_MOTOR_REV = 1120; // REV Motor Encoder.
    private static final double COUNT_PER_WHEEL_REV = COUNT_PER_MOTOR_REV / GEAR_RATIO;
    private static final double COUNT_PER_INCH = COUNT_PER_WHEEL_REV / INCH_PER_WHEEL_REV;
    // Declare robot measurements.
    private static final double ROBOT_DIAGONAL_INCH = 19;
    private static final double INCH_PER_ROBOT_REV = ROBOT_DIAGONAL_INCH * PI;
    // Declare non-private measurements.
    static final double ROBOT_EDGE_INCH = 17.8;
    static final double STONE_BRIDGE_DISTANCE_INCH = 23.3;
    static final double STONE_WALL_DISTANCE_INCH = 47;

    abstract void startGrab();
    abstract void stopGrab();

    private void encoderMove(double rfInch, double lfInch, double rbInch, double lbInch) {
        // Ensure that the OpMode is still active.
        if (opModeIsActive()) {
            // Set targets.
            rfMotor.setTargetPosition((int)(rfInch * COUNT_PER_INCH * ERROR_MARGIN) + rfMotor.getCurrentPosition());
            rbMotor.setTargetPosition((int)(rbInch * COUNT_PER_INCH * ERROR_MARGIN) + rbMotor.getCurrentPosition());
            lfMotor.setTargetPosition((int)(lfInch * COUNT_PER_INCH * ERROR_MARGIN) + lfMotor.getCurrentPosition());
            lbMotor.setTargetPosition((int)(lbInch * COUNT_PER_INCH * ERROR_MARGIN) + lbMotor.getCurrentPosition());
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
            // Keep looping while we are still active, any motors are running, and there is time left.
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

    final void encoderTurn(double revolutions) {
        encoderMove(
                revolutions * INCH_PER_ROBOT_REV,
                revolutions * INCH_PER_ROBOT_REV,
                revolutions * INCH_PER_ROBOT_REV,
                revolutions * INCH_PER_ROBOT_REV);
    }

    final void encoderDrive(double forInch, double horiInch) {
        encoderMove(
                (+ forInch - horiInch) / ROOT_TWO,
                (- forInch - horiInch) / ROOT_TWO,
                (+ forInch + horiInch) / ROOT_TWO,
                (- forInch + horiInch) / ROOT_TWO);
    }

    final void distanceDrive(double forSpeed, double horiSpeed, double distance, boolean upTo) {
        // Get distance. Distance sensor goes from 5cm to 25cm, roughly 1.9in to 9.8in.
        double senseD = sensorDistance.getDistance(DistanceUnit.INCH);
        telemetry.addData("Distance Sensor", senseD);
        telemetry.update();
        sleep(1000);
        // Start motion.
        rfMotor.setPower((+ forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        rbMotor.setPower((+ forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        lfMotor.setPower((- forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        lbMotor.setPower((- forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        // Wait until at correct distance.
        while ((senseD > distance && upTo) || (senseD < distance && !upTo) || Double.isNaN(senseD)) {
            senseD = sensorDistance.getDistance(DistanceUnit.INCH);
            telemetry.addData("Distance Sensor", senseD);
            telemetry.update();
        }
        // Stop motion.
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
    }

    final void colorDrive(double forSpeed, double horiSpeed) {
        // Start motion.
        rfMotor.setPower((+ forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        rbMotor.setPower((+ forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        lfMotor.setPower((- forSpeed - horiSpeed) / 2 * DRIVE_SPEED);
        lbMotor.setPower((- forSpeed + horiSpeed) / 2 * DRIVE_SPEED);
        // Wait until at skystone.
        while(!isSkystone()) {}
        // Stop motion.
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
    }

    private boolean isSkystone() {
        // Declare BlockID.
        boolean blockID;
        float[] hsv = {0F, 0F, 0F};
        // Get HSV value.
        telemetry.addData("Color Sensor", "sensing block.");
        Color.RGBToHSV(
                sensorColor.red() * 255,
                sensorColor.green() * 255,
                sensorColor.blue() * 255,
                hsv);
        telemetry.addData("Value", hsv[2]);
        // Check which stone is sensed.
        blockID = hsv[2] <= 80;
        telemetry.addData("Block ID", blockID);
        // Return sensed stone.
        telemetry.update();
        return blockID;
    }

    final void initMotors() {
        // Initialize OpMode members.
        rfMotor = hardwareMap.dcMotor.get("rf");
        lfMotor = hardwareMap.dcMotor.get("lf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lbMotor = hardwareMap.dcMotor.get("lb");
        hin1 = hardwareMap.dcMotor.get("hin1");
        hin2 = hardwareMap.dcMotor.get("hin2");
        hook = hardwareMap.crservo.get("hook");
        pinch = hardwareMap.crservo.get("pinch");
        sensorColor = hardwareMap.colorSensor.get("color");
        sensorDistance = hardwareMap.get(DistanceSensor.class, "color");
        // Reset encoders.
        telemetry.addData("Motors", "resetting encoders.");
        telemetry.update();
        sleep(1000);
        rfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lfMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rbMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lbMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Motors", "encoders done resetting.");
        telemetry.update();
        // Wait for game to start (driver presses PLAY).
        waitForStart();
    }
}
