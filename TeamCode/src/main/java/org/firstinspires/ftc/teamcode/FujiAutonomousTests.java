package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name="FujiTests", group="PatentPending")
public final class FujiAutonomousTests extends FujiAutonomous {

    @Override
    public final void runOpMode() {
        // UNCOMMENT THIS WHEN ENCODER MOVE WORKS
        robot = new Fuji(hardwareMap, telemetry);
        waitForStart();
        robot.playAutoSound();

        // square, tests linear movement
        robot.move(TILE_LENGTH, 0);
        sleep(1000);
        robot.move(0, -TILE_LENGTH);
        sleep(1000);
        robot.move(-TILE_LENGTH, 0);
        sleep(1000);
        robot.move(0, TILE_LENGTH);
        sleep(1000);

        // zig zag, tests diagonal movement
        robot.move(TILE_LENGTH, -TILE_LENGTH);
        sleep(1000);
        robot.move(-TILE_LENGTH, -TILE_LENGTH);
        sleep(1000);
        robot.move(TILE_LENGTH, TILE_LENGTH);
        sleep(1000);
        robot.move(-TILE_LENGTH, TILE_LENGTH);
        sleep(1000);

        // turns, tests gyro sensor
        robot.turn(0.5);
        sleep(1000);
        robot.turn(.25);
        sleep(1000);
        robot.turn(0.5);
        sleep(1000);
        robot.turn(0);
        sleep(1000);
        robot.turn(0.5);
        sleep(1000);
        robot.turn(0.75);
        sleep(1000);
        robot.turn(0.5);
        sleep(1000);
        robot.turn(1);
        sleep(1000);
        robot.turn(0.5);
        sleep(1000);
    }
}
