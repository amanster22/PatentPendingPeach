package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiTests", group="PatentPending")
public class FujiAutonomousTests extends FujiAutonomous {
    //class that run autonomous tests for movement, turning, and sensors
    public void runOpMode() {
        //square, tests translational movement
        this.robot.EncoderMove(10, 0, 0);
        this.robot.EncoderMove(0, -10, 0);
        this.robot.EncoderMove(-10, 0, 0);
        this.robot.EncoderMove(0, 10, 0);

        //x-like zig zag to test diagonal movement
        this.robot.EncoderMove(10, -10, 0);
        this.robot.EncoderMove(0, 10, 0);
        this.robot.EncoderMove(-10, -10, 0);
        this.robot.EncoderMove(10, 0, 0);
        this.robot.EncoderMove(-10, 10, 0);

        //test stationary turning with gyro
        this.robot.GryoTurnTo(0.5);
        this.robot.GryoTurnTo(0.25);
        this.robot.GryoTurnTo(0.5);
        this.robot.GryoTurnTo(0);
        this.robot.GryoTurnTo(0.5);
        this.robot.GryoTurnTo(0.75);
        this.robot.GryoTurnTo(0.5);
        this.robot.GryoTurnTo(1);
        this.robot.GryoTurnTo(0.5);
    }
}
