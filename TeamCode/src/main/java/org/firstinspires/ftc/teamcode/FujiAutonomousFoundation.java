package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name = "FujiFoundation", group = "PatentPending")
public class FujiAutonomousFoundation extends FujiAutonomous {

    @Override
    public void runOpMode() {
        robot = new Fuji(hardwareMap, telemetry);
        robot.drive(2, 0.5, 0, 1, false); // drive upto the stone line with a dist of 5 away
        robot.drive(7, 0.5, 1, 0, true); // drive sideways until the the distance is greater than 7
        robot.move(-FOUNDATION_LENGTH_INCH / 2, 0);
        //lower servo holders, move it to the correct position with gyro/distance
    }
}
