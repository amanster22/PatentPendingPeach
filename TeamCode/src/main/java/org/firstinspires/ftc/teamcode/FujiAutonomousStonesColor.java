package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

public class FujiAutonomousStonesColor extends FujiAutonomous {
    @Override
    public void runOpMode() throws InterruptedException {
        double skystone;
        double current;
        current = 1; //robot always begins its plans thinking its on the first stone
        robot = new Fuji(hardwareMap, telemetry);

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

        robot.move(((skystone - 1 + 0.5) * STONE_LENGTH) - ((current - 1 + 0.5) * STONE_LENGTH), 0); //final minus initial
        robot.spin(1);
        robot.move(0, 10);
        robot.move(0, -10);
        robot.move(-skystone * STONE_LENGTH - STONE_BRIDGE_DISTANCE_INCH, 0);
        //drop stone now
    }
}
