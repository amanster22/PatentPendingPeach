package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoFoundation", group="PatentPending")
public final class FujiAutoFoundation extends FujiAuto {

    private static final long HOOK_WAIT = 1000;

    @Override
    public void runOpMode() {
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
        encoderDrive(0, FOUNDATION_BRIDGE_DISTANCE_INCH + FOUNDATION_LENGTH_INCH / 2);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab()  {
        hook.setPower(1);
        sleep(HOOK_WAIT);
        hook.setPower(0.1);
    }

    @Override
    final void stopGrab() {
        hook.setPower(-1);
        sleep(HOOK_WAIT);
        hook.setPower(0);
    }
}