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
    private double forward;
    private double side;
    private double turn;
    private double slide;
    private double lift;
    //change these speeds later
    final private double driveSpeed = 0.9;
    final private double slideSpeed = 0.3;
    final private double liftSpeed = 0.6;

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

        slide = gamepad2.right_stick_y;

        lift = gamepad2.left_stick_y;

        //process variables and inputs
        if (Math.abs(forward) < 0.1) {
            forward = 0;
        }

        if (Math.abs(side) < 0.1) {
            side = 0;
        }

        if (gamepad2.a) {
            //move lift up one stone
        } else if (gamepad2.b) {
            //move lift down one stone
        } else {
            //dont do anything
        }

        if (gamepad2.left_bumper) {
            //close pinch
        } else if (gamepad2.right_bumper) {
            //open pinch
        } else {
            //do nothing, don't move pinch
        }

        robot.driveTrain.start(new DriveTrain.Vector(side * driveSpeed, forward * driveSpeed, turn * driveSpeed).speeds());
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
        ;
    }
}