package org.firstinspires.ftc.teamcode;

abstract class FujiAutoFoundation extends FujiAuto {

    private static final long HOOK_WAIT = 2000;
    private static final int PARK_ERROR_MARGIN = 2;
    private static final int PULL_ERROR_MARGIN = 20;

    final void main(boolean WALL_PARK, boolean RED) {
        // Reverse the controlls if on red side.
        if (RED) {setReverse();}

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
        encoderDrive(-BRIDGE_WALL_DISTANCE_INCH + ROBOT_EDGE_INCH - PULL_ERROR_MARGIN,
                -BRIDGE_WALL_DISTANCE_INCH + ROBOT_EDGE_INCH - PULL_ERROR_MARGIN);
        // Drop foundation.
        stopGrab();
        // Park under bridge.
        if (WALL_PARK) {
            encoderDrive(0, FOUNDATION_BRIDGE_DISTANCE_INCH + FOUNDATION_LENGTH_INCH - (ROBOT_EDGE_INCH / 2));
        } else {
            encoderDrive(0, FOUNDATION_LENGTH_INCH  + PARK_ERROR_MARGIN);
            encoderDrive(ROBOT_EDGE_INCH + PARK_ERROR_MARGIN, 0);
            encoderDrive(0, FOUNDATION_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - PARK_ERROR_MARGIN);
        }

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab()  {
        hook1.setPower(1);
        hook2.setPower(1);
        sleep(HOOK_WAIT);
        hook1.setPower(0.5);
        hook2.setPower(0.5);
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