package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="FujiAutoStones", group="PatentPending")
public final class FujiAutoStones extends FujiAuto {

    private static final long PINCH_WAIT = 3200;

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
//        endLine(-1);
        nextStone(-currentStone);
//        encoderDrive(-5, 0);
        // Go to build zone. backwards a little in order to not crash into bridge
        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2));

//        encoderDrive(-50, 0);
        // Drop stone.
        // Park under bridge.
//        encoderTurn(-0.25);
        stopGrab();
        encoderDrive(0, ROBOT_EDGE_INCH / 2);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab() {
        encoderDrive(-1, -3.5);
//        encoderTurn(0.45);
//        armMove(0.3);
//        pinch.setPower(1);
        hook1.setPower(1);
        hook2.setPower(1);
        sleep(PINCH_WAIT);
        hook1.setPower(0);
        hook2.setPower(0);
//        armMove(-0.15);
//        encoderTurn(0.65);
        encoderDrive(-10, 0); // make a varible for this later
    }

    @Override
    final void stopGrab() {
//        armMove(0.7);
//        pinch.setPower(-1);
        hook1.setPower(-1);
        hook2.setPower(-1);
        sleep(PINCH_WAIT);
        hook1.setPower(0);
        hook2.setPower(0);
//        armMove(-0.7);

    }
}