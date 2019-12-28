package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name="FujiTests", group="PatentPending")
public class FujiAutonomousTests extends FujiAutonomous {

    //class that run autonomous tests for movement, turning, and sensors
    public void runOpMode() {
        robot = new Fuji(hardwareMap, telemetry);


        //UNCOMMENT THIS OUT WHEN ENCODER MOVE WORKS
//        //square, tests translational movement
//        telemetry.addData("running:", "square");
//        telemetry.update();
//        robot.EncoderMove(10, 0, 0);
//        robot.EncoderMove(0, -10, 0);
//        robot.EncoderMove(-10, 0, 0);
//        robot.EncoderMove(0, 10, 0);
//
//        //x-like zig zag to test diagonal movement
//        telemetry.addData("running:", "criss-cross");
//        telemetry.update();
//        robot.EncoderMove(10, -10, 0);
//        robot.EncoderMove(0, 10, 0);
//        robot.EncoderMove(-10, -10, 0);
//        robot.EncoderMove(10, 0, 0);
//        robot.EncoderMove(-10, 10, 0);

        //test stationary turning with gyro
        telemetry.addData("running:", "gryo turn about");
        telemetry.update();
        robot.GryoTurnTo(0.5);
        sleep(1000);
        robot.GryoTurnTo(0.25);
        sleep(1000);
        robot.GryoTurnTo(0.5);
        sleep(1000);
        robot.GryoTurnTo(0);
        sleep(1000);
        robot.GryoTurnTo(0.5);
        sleep(1000);
        robot.GryoTurnTo(0.75);
        sleep(1000);
        robot.GryoTurnTo(0.5);
        sleep(1000);
        robot.GryoTurnTo(1);
        sleep(1000);
        robot.GryoTurnTo(0.5);
    }
}
