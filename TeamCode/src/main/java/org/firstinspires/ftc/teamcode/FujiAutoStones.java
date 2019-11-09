package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

// @Autonomous(name="FujiAutoStones", group="PatentPending")
@Disabled
public final class FujiAutoStones extends FujiAuto {

    private static final long PINCH_WAIT = 1000;

    @Override
    public final void runOpMode() {
        // Initialize OpMode.
        int currentStone = 0;
        initMotors();
        telemetry.addData("Path", "started.");
        telemetry.update();

	    // Go up to stones.
        prepSense(BRIDGE_WALL_DISTANCE_INCH);
        // Drive sideways until the robot reaches the end of the stone line.
        endLine(-1);
        // Drive to the middle of the first stone.
        nextStone(0.5);
        // Start stone sensing.
        while (!isSkystone()) {
            currentStone++;
            if (currentStone >= SKYSTONE_DISTANCE_STONES) {
                nextStone(-SKYSTONE_DISTANCE_STONES + 1);
                currentStone = 0;
            } else {
                nextStone(1);
            }
        }
        // Grab stone.
        startGrab();
        // Drive to the end of the stone line.
        endLine(-1);
        // Go to build zone.
        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2));
        // Drop stone.
        stopGrab();
        // Park under bridge.
        encoderDrive(0, ROBOT_EDGE_INCH / 2);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab() {
        encoderDrive(-10, 0);
        encoderTurn(0.5);
        armMove(0.7);
        pinch.setPower(1);
        sleep(PINCH_WAIT);
        pinch.setPower(0.1);
        armMove(-0.7);
        encoderTurn(0.5);
        encoderDrive(10, 0);
    }

    @Override
    final void stopGrab() {
        armMove(0.7);
        pinch.setPower(-1);
        sleep(PINCH_WAIT);
        pinch.setPower(0);
        armMove(-0.7);

    }
}