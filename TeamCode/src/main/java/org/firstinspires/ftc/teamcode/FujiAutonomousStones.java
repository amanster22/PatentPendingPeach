package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;


@Autonomous(name = "FujiStones", group = "PatentPending")
public class FujiAutonomousStones extends FujiAutonomous {
    @Override
    public final void runOpMode() {
        double skystone1;
        double current;
        robot = new Fuji(hardwareMap, telemetry);
        robot.Upto(5, 0.5, 0, 1, false); //drive upto the stone line with a dist of 5 away
        robot.Upto(7, 0.5, -1, 0, true); // drive sideways until the the distance is greater than 7

        robot.EncoderMove(0.5 * STONE_LENGTH, 0, 0);
        current = 1;
        boolean output = robot.testSkystone();
        //get the stone
        if (output) {
            skystone1 = 1;
        } else {
            robot.EncoderMove(STONE_LENGTH, 0, 0);
            current = 2;
            output = robot.testSkystone();
            if (output) {
                skystone1 = 2;
            } else {
                skystone1 = 3;
            }
        }

        robot.EncoderMove(((skystone1 - 1) * STONE_LENGTH - ROBOT_EDGE_LENGTH) - (0.5 + (current - 1) * STONE_LENGTH), 0, 0); //final minus initial
        robot.GryoTurnTo(0.25);
        robot.EncoderMove(-10, 0, 0);
        robot.EncoderMove(0, 0.5 * STONE_LENGTH, 0);
        robot.EncoderMove(10, 0, 0);
        // then move robot until it sees blue tape until bridge
        // after that turn around and drop off stone a little past the bridge
        // stop/return for second stone

    }
}
