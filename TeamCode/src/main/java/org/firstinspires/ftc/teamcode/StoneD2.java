package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="StoneD2", group="PatentPending")
public class StoneD2 extends FujiAuto {

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
        encoderTurn(-0.01);
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
        encoderDrive(-2.2, -1.25);
        startGrab();

//        encoderTurn(0.25); save incase we need to migrate back to spinning and moving backwards

        //- (ROBOT_EDGE_INCH / 2) save for later if needed

        //go to foundation
//        encoderDrive(-3,0);
        encoderDrive(0,-STONE_BRIDGE_DISTANCE_INCH - (currentStone * STONE_LENGTH_INCH) - (TILE_LENGTH*1.5));

//        encoderDrive(3,0);

        // Drop stone.
        stopGrab();






        // go back to bridge and to stone line and to the next skystone
//        encoderDrive(-3,0);
//        encoderTurn(-0.01);
        encoderDrive(0, STONE_BRIDGE_DISTANCE_INCH + (TILE_LENGTH*1.5));
//      encoderTurn(-0.01);
        nextStone(2.5 + currentStone);
//        encoderDrive(3,0);

        //grab second stone
        startGrab();
//        encoderTurn(0.25);
        //bring stone to foundation
        encoderDrive(-3,0);
        encoderDrive(0, -STONE_BRIDGE_DISTANCE_INCH - (STONE_LENGTH_INCH * (3+currentStone)) - (TILE_LENGTH*1.5) - STONE_LENGTH_INCH);
        encoderDrive(3,  0);
//        encoderTurn(-0.235);
        //drop stone
        stopGrab();



        
        //park
        encoderDrive(-1, (TILE_LENGTH*1.5) + STONE_LENGTH_INCH);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override
    final void startGrab() {
        //this block moves to grab stone
        hook1.setPower(1);
        hook2.setPower(1);
        pin.setPower(1);
        sleep(1700);


        //this block moves to pick it up and hold it
        pin.setPower(1);
        hook1.setPower(-1);
        hook2.setPower(-1);
        sleep(1700);
        pin.setPower(0.3);
        hook1.setPower(0);
        hook2.setPower(0);

    }

    @Override
    final void stopGrab() {

        hook1.setPower(1);
        hook2.setPower(1);
        sleep(1000);
        pin.setPower(-1);
        sleep(500);
        hook1.setPower(-1);
        hook2.setPower(-1);
        sleep(1000);
        hook1.setPower(0);
        hook2.setPower(0);
        pin.setPower(0);
    }
}
