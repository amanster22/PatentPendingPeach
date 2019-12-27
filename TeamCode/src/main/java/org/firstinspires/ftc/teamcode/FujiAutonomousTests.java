package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name="FujiTests", group="PatentPending")
public class FujiAutonomousTests extends FujiAutonomous {

    //class that run autonomous tests for movement, turning, and sensors
    public void runOpMode() {
        robot = new Fuji(hardwareMap, telemetry);

        //square, tests translational movement
        robot.EncoderMove(10, 0, 0);
        robot.EncoderMove(0, -10, 0);
        robot.EncoderMove(-10, 0, 0);
        robot.EncoderMove(0, 10, 0);

        //x-like zig zag to test diagonal movement
        robot.EncoderMove(10, -10, 0);
        robot.EncoderMove(0, 10, 0);
        robot.EncoderMove(-10, -10, 0);
        robot.EncoderMove(10, 0, 0);
        robot.EncoderMove(-10, 10, 0);

        //test stationary turning with gyro
        robot.GryoTurnTo(0.5);
        robot.GryoTurnTo(0.25);
        robot.GryoTurnTo(0.5);
        robot.GryoTurnTo(0);
        robot.GryoTurnTo(0.5);
        robot.GryoTurnTo(0.75);
        robot.GryoTurnTo(0.5);
        robot.GryoTurnTo(1);
        robot.GryoTurnTo(0.5);
    }
}
