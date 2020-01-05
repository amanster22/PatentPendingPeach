package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.Fuji;

@TeleOp(name="FujiTele", group="PatentPending")
public final class FujiTele extends OpMode {


    //teleop variables
    private Fuji robot;
    private int liftDirection;
    private double forward;
    private double side;
    private double turn;

    @Override
    public final void init() {
        //initialize and set robot behavior
        robot = new Fuji(hardwareMap, telemetry);
        robot.driveTrain.setZeroBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public final void loop() {

        //receive gamepad inputs and store
        forward = gamepad1.left_stick_y;

        side = gamepad1.left_stick_x;

        turn = gamepad1.right_stick_x;

        if (gamepad1.a) {
            liftDirection = 1;
        } else if (gamepad1.b) {
            liftDirection = -1;
        } else {
            liftDirection = 0;
        }


        //process variables and inputs
        if (Math.abs(forward) < 0.1) {
            forward = 0;
        }

        if (Math.abs(side) < 0.1) {
            side = 0;
        }

        robot.driveTrain.start(new DriveTrain.Vector(side, forward, turn).speeds());
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
        ;
    }
}