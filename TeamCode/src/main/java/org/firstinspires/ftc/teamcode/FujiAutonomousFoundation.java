package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name = "FujiFoundation", group = "PatentPending")
public class FujiAutonomousFoundation extends FujiAutonomous {

    @Override
    public void runOpMode() {
        robot = new Fuji(hardwareMap, telemetry);
        waitForStart();

        robot.drive(0, -0.3, 2, 0.5, false); // drive upto the stone line with a dist of 5 away
//        robot.
        robot.drive(0.3, 0, 7, 0.5, true); // drive sideways until the the distance is greater than 7

        robot.move(-FOUNDATION_LENGTH_INCH / 2, 3.0);
        robot.hook(1);  //lower servo holders
        sleep(500);

        //move it to the correct position with gyro/distance
        robot.move(0, -TILE_LENGTH);
        robot.turn(0.75);
        robot.move(0, 10);

        robot.hook(0);
        sleep(500);

        robot.move(0.5 * TILE_LENGTH, -2 * TILE_LENGTH);
    }
}
