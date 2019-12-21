package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.type.Device;

@TeleOp(name = "LilBot", group = "PatentPending")
public final class LilBot extends OpMode {

    private DriveTrain robot;

    @Override
    public final void init() {
        robot = new DriveTrain(
                new Motor("rf", 1, hardwareMap),
                new Motor("rb", 1, hardwareMap),
                new Motor("lf", 1, hardwareMap),
                new Motor("lb", 1, hardwareMap));
        stop();
    }

    @Override
    public final void start() {}

    @Override
    public final void loop() {
        robot.start(new DriveTrain.Vector(
                new Device.Range(gamepad1.left_stick_x),
                new Device.Range(gamepad1.left_stick_y),
                new Device.Range(gamepad1.right_stick_x)
        ).speeds());
    }

    @Override
    public final void stop() {
        robot.start(new DriveTrain.Vector(
                new Device.Range(0),
                new Device.Range(0),
                new Device.Range(0)
        ).speeds());
    }
}