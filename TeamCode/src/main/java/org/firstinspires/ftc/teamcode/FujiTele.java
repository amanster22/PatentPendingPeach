package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

@TeleOp(name="FujiTele", group="PatentPending")
public final class FujiTele extends OpMode {
    private Fuji robot;

    @Override
    public final void init() {
        robot = new Fuji(hardwareMap, telemetry);
    }

    @Override
    public final void loop() {
        double forward = gamepad1.left_stick_y;
        double side = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        if (Math.abs(forward) < 0.1) {
            forward = 0;
        }
        if (Math.abs(side) < 0.1) {
            side = 0;
        }
        robot.drive(side, forward, turn);
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.drive(0,0,0);
    }
}