/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="FujiAutoFoundation", group="PatentPending")
public class FujiAutoFoundation extends FujiAuto {

    // Declare field measurements.
    private static final double FOUNDATION_WALL_DISTANCE_INCH = 47.25;
    private static final double FOUNDATION_LENGTH_INCH = 34.5;

    @Override
    public void runOpMode() {

        initMotors();

        // Run autonomous.
        telemetry.addData("Path", "started.");
        telemetry.update();

	    // Go up to foundation.
        encoderDrive(FOUNDATION_WALL_DISTANCE_INCH - ROBOT_EDGE_INCH, 0.0);

	    // Drive sideways until the robot reaches the end of the foundation.
        sensorDrive(0.0,-1.0, 1, true);

        encoderDrive (DRIVE_SPEED, 0.0, -FOUNDATION_LENGTH_INCH / 2, 10.0);
        encoderTurn (DRIVE_SPEED, -0.25, 10.0);
        // Grab foundation here.
        encoderDrive(DRIVE_SPEED, 0.0, -FOUNDATION_WALL_DISTANCE_INCH + ROBOT_EDGE_INCH, 10.0);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    public void encoderTurn(double speed, double revolutions, double timeout) {

        // Ensure that the opMode is still active.
        if (opModeIsActive()) {

            // Declare motor targets.
            double rfInch = revolutions * INCH_PER_ROBOT_REV;
            double lfInch = revolutions * INCH_PER_ROBOT_REV;
            double rbInch = revolutions * INCH_PER_ROBOT_REV;
            double lbInch = revolutions * INCH_PER_ROBOT_REV;
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
            rfMotor.setPower(speed);
            lfMotor.setPower(speed);
            rbMotor.setPower(speed);
            lbMotor.setPower(speed);
            // keep looping while we are still active and any motors are running.
            telemetry.addData("Turn", "started turning.");
            telemetry.update();
            while (opModeIsActive() &&
                   runtime.seconds() < timeout &&
                  (rfMotor.isBusy() || lfMotor.isBusy() || rbMotor.isBusy() || lbMotor.isBusy())) {}
            telemetry.addData("Turn", "done turning.");
            telemetry.update();
            // Stop all motion.
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

    public void encoderDrive(double speed, double forInch, double horiInch, double timeout) {

        // Ensure that the opMode is still active.
        if (opModeIsActive()) {

            // Declare motor targets.
            double rfInch = (+ forInch - horiInch) / ROOT_TWO;
            double lfInch = (- forInch - horiInch) / ROOT_TWO;
            double rbInch = (+ forInch + horiInch) / ROOT_TWO;
            double lbInch = (- forInch + horiInch) / ROOT_TWO;
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
            rfMotor.setPower(speed);
            lfMotor.setPower(speed);
            rbMotor.setPower(speed);
            lbMotor.setPower(speed);
            // keep looping while we are still active and any motors are running.
            telemetry.addData("Move", "started moving.");
            telemetry.update();
            while (opModeIsActive() &&
                   runtime.seconds() < timeout &&
                  (rfMotor.isBusy() || lfMotor.isBusy() || rbMotor.isBusy() || lbMotor.isBusy())) {}
            telemetry.addData("Move", "done moving.");
            telemetry.update();
            // Stop all motion.
            rfMotor.setPower(0);
            lfMotor.setPower(0);
            rbMotor.setPower(0);
            lbMotor.setPower(0);

            // Turn off RUN_USING_ENCODER mode.
            rfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lfMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lbMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void driveOn (double forSpeed, double horiSpeed) {
        rfMotor.setPower((+forSpeed - horiSpeed) / 2);
        rbMotor.setPower((+forSpeed + horiSpeed) / 2);
        lfMotor.setPower((-forSpeed - horiSpeed) / 2);
        lbMotor.setPower((-forSpeed + horiSpeed) / 2);
    }
}*/