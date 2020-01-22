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
    // field measurements
    private static final double stoneHeight = 4;
    // speeds
    private static final double driveSpeed = 1;
    private static final double liftSpeed = 0.5;
    private static final double liftMax = 6 * stoneHeight;
    // target positions
    private double liftPos = 0;
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
        double lift = gamepad2.right_stick_y;
        boolean dropStone = gamepad2.x;
        boolean hookUp = gamepad1.left_bumper;
        boolean hookDown = gamepad1.right_bumper;
        boolean pinchDown = gamepad2.right_bumper;
        boolean pinchUp = gamepad2.left_bumper;

        // process input

        if (Math.abs(vert) < 0.1) {vert = 0;}
        if (Math.abs(hori) < 0.1) {hori = 0;}

//        if (liftUp && liftPos < liftMax) {
//            liftPos += stoneHeight;
//        }
//        if (liftDown && liftPos > 0) {
//            liftPos -= stoneHeight;
//        }

//        if (liftPos < robot.slide.measure()) {lift = 1;}
//        if (liftPos > robot.slide.measure()) {lift = -1;}



        try {
            //output values for robot movement
            robot.driveTrain.start(new DriveTrain.Vector(hori * driveSpeed, vert * driveSpeed, turn * driveSpeed).speeds());
            robot.lift.start(lift * liftSpeed);
            if (hookUp) {robot.hook(1);}
            if (hookDown) {robot.hook(0);}
            if (dropStone) {
                robot.dropStone.start(0.5);
                robot.dropStone.start(0.7);
            }

            if (pinchDown) {
                robot.pinch.start(1.0);
            } else if (pinchUp) {
                robot.pinch.start(0.25);
            }


        } catch (Exception e) {
            telemetry.addData("Error", e.getMessage());
            telemetry.addData("info", e.getStackTrace()[0].toString());
            telemetry.update();
        }
    }

    @Override
    public final void start() {}

    @Override
    public final void stop() {
        robot.driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
        robot.lift.start(0.0);
        robot.hook(robot.hook1.measure());
    }
}