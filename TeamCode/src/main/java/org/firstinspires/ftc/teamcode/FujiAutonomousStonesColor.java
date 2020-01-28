package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

public class FujiAutonomousStonesColor extends FujiAutonomous {
    @Override
    public void runOpMode() {
        //don't put claw movements in yet, just test color sensor and movements
        double skystone;
        double current;
        current = 1; //robot always begins its plans thinking its on the first stone
        robot = new Fuji(hardwareMap, telemetry);

        waitForStart();

        robot.drive(5, 0.5, 0, 1, false); //drive up to the stone line with a dist of 5 away
        robot.drive(7, 0.5, -1, 0, true); // drive sideways until the the distance is greater than 7

        robot.move(0.5 * STONE_LENGTH, 0);

        //get the stone
        if (robot.isSkystone()) {
            skystone = 1;
        } else {
            robot.move(STONE_LENGTH, 0);
            current = 2;
            if (robot.isSkystone()) {
                skystone = 2;
            } else {
                skystone = 3;
            }
        }
        robot.move(((skystone) * STONE_LENGTH) - ((current) * STONE_LENGTH), 5); //final minus initial
        //bring down the claw and pick up stone
        robot.hook(1);
        robot.move(0, -5);
        robot.move(-skystone * STONE_LENGTH - STONE_BRIDGE_DISTANCE_INCH, 0);
//        robot.move(- FOUNDATION_BRIDGE_DISTANCE_INCH - FOUNDATION_LENGTH_INCH/2, 5);
        //run to position on the arm, NO WHILE LOOP so it can be parallel
        //then drop stone
        robot.hook(0);
        //repeat
    }
}
