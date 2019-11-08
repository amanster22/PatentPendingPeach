package org.firstinspires.ftc.teamcode;

abstract class FujiAutoFoundation extends FujiAuto {

    private static final long HOOK_WAIT = 2750;
    private static final int FOUNDATION_ERROR_MARGIN = 2;

    final void main(boolean WALL_PARK) {
        // Initialize OpMode.
        initMotors();
        telemetry.addData("Path", "started.");
        telemetry.update();

	    // Go up to foundation.
        prepSense(BRIDGE_WALL_DISTANCE_INCH);
	    // Drive sideways until the robot reaches the end of the foundation.
        endLine(1);
        // Drive to the middle of the foundation.
        encoderDrive(0, -FOUNDATION_LENGTH_INCH / 2);
        // Grab foundation.
        startGrab();
        // Drive to wall.
        prepSense(-BRIDGE_WALL_DISTANCE_INCH);
        // Drop foundation.
        stopGrab();
        // Park under bridge.
        if (WALL_PARK) {
            encoderDrive(0, FOUNDATION_BRIDGE_DISTANCE_INCH + (FOUNDATION_LENGTH_INCH / 2));
        } else {
            encoderDrive(0, (FOUNDATION_LENGTH_INCH / 2) + (ROBOT_EDGE_INCH / 2) + FOUNDATION_ERROR_MARGIN);
            encoderDrive(ROBOT_EDGE_INCH + FOUNDATION_ERROR_MARGIN, 0);
            encoderDrive(0, FOUNDATION_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - FOUNDATION_ERROR_MARGIN);
        }

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab()  {
        hook1.setPower(1);
        hook2.setPower(1);
        sleep(HOOK_WAIT);
        hook1.setPower(0.1);
        hook2.setPower(0.1);
    }

    @Override
    final void stopGrab() {
        hook1.setPower(-1);
        hook2.setPower(-1);
        sleep(HOOK_WAIT);
        hook1.setPower(0);
        hook2.setPower(0);
    }
}