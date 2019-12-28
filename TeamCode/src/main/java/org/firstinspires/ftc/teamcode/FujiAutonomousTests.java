package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name="FujiTests", group="PatentPending")
public final class FujiAutonomousTests extends FujiAutonomous {

    @Override
    public final void runOpMode() {
        // UNCOMMENT THIS WHEN ENCODER MOVE WORKS
        robot = new Fuji(hardwareMap, telemetry);

        // square, tests linear movement
        robot.move(10, 0, 0);
        sleep(1000);
        robot.move(0, -10, 0);
        sleep(1000);
        robot.move(-10, 0, 0);
        sleep(1000);
        robot.move(0, 10, 0);
        sleep(1000);

        // zig zag, tests diagonal movement
        robot.move(10, -10, 0);
        sleep(1000);
        robot.move(0, 10, 0);
        sleep(1000);
        robot.move(-10, -10, 0);
        sleep(1000);
        robot.move(10, 10, 0);
        sleep(1000);
        robot.move(-10, 10, 0);
        sleep(1000);

        // turns, tests gyro sensor
        robot.turn(0.5, 0.5);
        sleep(1000);
        robot.turn(.25, 0.5);
        sleep(1000);
        robot.turn(0.5, 0.5);
        sleep(1000);
        robot.turn(0, 0.5);
        sleep(1000);
        robot.turn(0.5, 0.5);
        sleep(1000);
        robot.turn(0.75, 0.5);
        sleep(1000);
        robot.turn(0.5, 0.5);
        sleep(1000);
        robot.turn(1, 0.5);
        sleep(1000);
        robot.turn(0.5, 0.5);
        sleep(1000);
    }
}
