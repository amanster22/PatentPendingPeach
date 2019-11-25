package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="StoneD2", group="PatentPending")
public class StoneD2 extends FujiAuto {

    private static final long PINCH_WAIT = 2000;

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
//        nextStone(-currentStone);
        encoderDrive(-10, 0);
        // Go to build zone. backwards a little in order to not crash into bridge
//        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2));

//        encoderDrive(-50, 0);
        // Drop stone.
        // Park under bridge.
        encoderTurn(0.25);
//        encoderDrive(0, ROBOT_EDGE_INCH / 2);
//        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - 20);
        encoderDrive(-STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - 10 - (currentStone * STONE_LENGTH_INCH), 0);
        encoderTurn(-0.235);
        stopGrab();
//        encoderDrive(0, 15);
//        nextStone(currentStone + 2 + 0.5);
        encoderDrive(0, STONE_BRIDGE_DISTANCE_INCH + 10 + (ROBOT_EDGE_INCH / 2));
//        encoderTurn(-0.01);
        nextStone(3 + currentStone);
//        upTo();
        encoderDrive(15, 0);
        startGrab();
        encoderDrive(-12, 0);
        encoderTurn(0.25);
//        encoderDrive(0, ROBOT_EDGE_INCH / 2);
//        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - 20);
        encoderDrive(-STONE_BRIDGE_DISTANCE_INCH - (ROBOT_EDGE_INCH / 2) - 15-(STONE_LENGTH_INCH * (3+currentStone)), 0);
        encoderTurn(-0.26);
        stopGrab();
        encoderDrive(0, 20);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab() {
        encoderDrive(-0.6, -2.75);
//        encoderTurn(0.45);
//        armMove(0.3);
//        pinch.setPower(1);
        hook1.setPower(1);
        hook2.setPower(1);
        sleep(PINCH_WAIT);
        hook1.setPower(0);
        hook2.setPower(0);
//        DRIVE_SPEED = DRIVE_SPEED / 1.2;
//        armMove(-0.15);
//        encoderTurn(0.65);
//        DRIVE_SPEED = DRIVE_SPEED * 1.2;
    }

    @Override
    final void stopGrab() {
//        armMove(0.7);
//        pinch.setPower(-1);
        hook1.setPower(-1);
        hook2.setPower(-1);
        sleep(PINCH_WAIT/2);
        hook1.setPower(0);
        hook2.setPower(0);
//        armMove(-0.7);
    }
}