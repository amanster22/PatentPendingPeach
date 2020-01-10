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
        lift = gamepad2.right_stick_y;

        //process variables and inputs

        if (Math.abs(forward) < 0.1) {forward = 0;}
        if (Math.abs(side) < 0.1) {side = 0;}


        //LIFT CODE
        if (gamepad2.a) {
            // move lift up one stone
        } else if (gamepad2.b) {
            // move lift down one stone
        } else {
            // dont do anything
        }


        //PINCH CODE
        if (gamepad2.left_bumper) {
            // close pinch
        } else if (gamepad2.right_bumper) {
            // open pinch
        } else {
            // do nothing, don't move pinch
        }


        //FOUNDATION HOOK CODE
        if (gamepad1.left_bumper) {
            // move foundation hooks?
        } else if (gamepad1.right_bumper) {
            // move foundation hooks?
        } else {
            // move foundation hooks?
        }


        // FOUNDATION HOOK CODE
        if (gamepad1.left_bumper) {
        } // move
        else if (gamepad1.right_bumper) {
        } else {
        }

        //SLIDE CODE
        if (gamepad2.dpad_up) {
            slide = 1;
        } // move slide forwards

        else if (gamepad2.dpad_down) {
            slide = -1;
        }// move slide backwards

        else {
            slide = 0;
        } // dont move slide


        robot.driveTrain.start(new DriveTrain.Vector(side * driveSpeed, forward * driveSpeed, turn * driveSpeed).speeds());
        robot.slide.start(slideSpeed * slide);
        robot.lift.start(lift * liftSpeed);
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
        robot.slide.start(0.0);
        robot.lift.start(0.0);
    }
}