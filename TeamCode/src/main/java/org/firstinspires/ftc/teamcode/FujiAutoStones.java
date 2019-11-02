package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoStones", group="PatentPending")
public class FujiAutoStones extends FujiAuto {

    private static final double HOOK_WAIT = 1000;

    @Override
    public void runOpMode() {
        // Initialize OpMode.
        initMotors();
        telemetry.addData("Path", "started.");
        telemetry.update();

	    // Go up to stones.
        encoderDrive(STONE_WALL_DISTANCE_INCH - ROBOT_EDGE_INCH, 0);
        // Drive sideways until the robot reaches the end of the stone line.
        distanceDrive(0, -0.5, 6, false);
        // Drive sideways until the robot reaches a skystone.
        colorDrive(0, 0.5);
        // Grab stone.
        startGrab();
        // Drive to the end of the stone line.
        distanceDrive(0, -0.5, 6, false);
        // Go to build zone.
        encoderDrive(-STONE_WALL_DISTANCE_INCH + ROBOT_EDGE_INCH, -STONE_BRIDGE_DISTANCE_INCH - ROBOT_EDGE_INCH / 2);
        // Drop stone.
        stopGrab();
        // Park under bridge.
        encoderDrive(0, ROBOT_EDGE_INCH / 2);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    void startGrab() {
        hook.setPower(1);
        sleep((long)(1.1 * HOOK_WAIT));
        hook.setPower(0.1);
    }

    @Override
    void stopGrab() {
        hook.setPower(-1);
        sleep((long)HOOK_WAIT);
        hook.setPower(0);

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