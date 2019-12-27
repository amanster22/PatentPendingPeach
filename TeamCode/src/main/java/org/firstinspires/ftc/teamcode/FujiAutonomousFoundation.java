package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

public class FujiAutonomousFoundation extends FujiAutonomous {
    @Override
    public final void runOpMode() {
        robot = new Fuji(hardwareMap, telemetry);
        robot.Upto(2, 0.5, 0, 1, false); //drive upto the stone line with a dist of 5 away
        robot.Upto(7, 0.5, 1, 0, true); // drive sideways until the the distance is greater than 7
        //drive to the middle of foundation, lower servo holders, move it to the correct position with gyro/distance
    }
}
