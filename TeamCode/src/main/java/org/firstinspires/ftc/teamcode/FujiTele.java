package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.Fuji;

@TeleOp(name="FujiTele", group="PatentPending")
public final class FujiTele extends OpMode {


    // robot
    private Fuji robot;
    // speeds
    private static final double driveSpeed = 0.9;
    private static final double slideSpeed = 0.3;
    private static final double liftSpeed = 0.6;
    private static final double liftMax = 6;
    private static final double slideMax = 5;
    // target positions
    private double liftPos = 0;
    private boolean slideOut = false;
    // field measurements
    private static final double stoneHeight = 4;

    @Override
    public final void init() {
        //initialize and set robot behavior
        robot = new Fuji(hardwareMap, telemetry);
        robot.driveTrain.setZeroBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        stop();
    }

    @Override
    public final void loop() {

        // get gamepad input
        double vert = gamepad1.left_stick_y;
        double hori = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        boolean liftUp = gamepad2.a;
        boolean liftDown = gamepad2.b;
        boolean slideUp = gamepad2.dpad_up;
        boolean slideDown = gamepad2.dpad_down;
        boolean hookUp = gamepad2.left_bumper;
        boolean hookDown = gamepad2.right_bumper;

        // declare output values
        double lift = 0;
        double slide = 0;

        // process input

        if (Math.abs(vert) < 0.1) {vert = 0;}
        if (Math.abs(hori) < 0.1) {hori = 0;}

        if (liftUp && liftPos > 0) {liftPos += stoneHeight;}
        if (liftDown && liftPos < liftMax) {liftPos -= stoneHeight;}

        if (liftPos < robot.slide.measure()) {lift = 1;}
        if (liftPos > robot.slide.measure()) {lift = -1;}

        if (slideUp) {slideOut = true;}
        if (slideDown) {slideOut = false;}

        if (slideOut && robot.slide.measure() > 0) {slide = 1;}
        if (!slideOut && robot.slide.measure() < slideMax) {slide = -1;}

        robot.driveTrain.start(new DriveTrain.Vector(hori * driveSpeed, vert * driveSpeed, turn * driveSpeed).speeds());
        robot.slide.start(slide * slideSpeed);
        robot.lift.start(lift * liftSpeed);
        if (hookUp) {robot.hook(1);}
        if (hookDown) {robot.hook(0);}
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
        robot.slide.start(0.0);
        robot.lift.start(0.0);
        robot.hook(robot.hook1.measure());
    }
}