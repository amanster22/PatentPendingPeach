package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="FujiTele", group="Patentpending")
public final class FujiTele extends OpMode {
    Fuji robot;
    @Override
    public final void init() {
        Fuji robot = new Fuji(hardwareMap);
    }
    @Override
    public final void loop() {
        robot.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }
    @Override
    public final void start() {}
    @Override
    public final void stop() {
        robot.drive(0,0,0);
    }
}