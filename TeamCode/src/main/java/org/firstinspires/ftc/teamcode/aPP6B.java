package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
//registering opMode
bumper-Boolean=tRUE OR fALSE
trigger-Float=【0，1】
sticks-Float=[-1,1]
a,b,x,y-Boolean= True or False
 */
@Autonomous(name = "Patent Pending Auto 6.1-Depot+Crater")
public class aPP6B extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    //clear motor objects
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private DcMotor motorArm;
    private DcMotor motorLatch;
    private Servo servoLeft;
    private Servo servoRight;
    /////////////////////
    //double clawOffset = 0;                       // Servo mid position
    //final double CLAW_SPEED = 0.02;                   // sets rate to move servo

   // final double MID_SERVO = 0.5;
    //static final double FORWARD_SPEED = 0.6;
    //static final double TURN_SPEED = 0.5;
    //origional 0.45 and -0.45
    //final double ARM_UP_POWER = 0.45;
    // final double ARM_DOWN_POWER = -0.45;
    //final double ARM_DOWN_POWER = -0.45;
    //define color sensor variable
    ColorSensor sensorColor;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("mLeft");
        motorRight = hardwareMap.dcMotor.get("mRight");

        motorLatch = hardwareMap.dcMotor.get("mRetract");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        motorArm = hardwareMap.dcMotor.get("mArm");

        servoLeft = hardwareMap.servo.get("sLeft");
        servoRight = hardwareMap.servo.get("sRight");
        //initialize colorsensor
        // get a reference to the color sensor.
        sensorColor = hardwareMap.get(ColorSensor.class, "color");


        // hsvValues is an array that will hold the hue, saturation, and value information.
       // float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
       // final float values[] = hsvValues;

        // sometimes it helps to multiply the raw RGB values with a scale factor
        // to amplify/attentuate the measured values.
        //final double SCALE_FACTOR = 255;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

//      any code put before a wait will be run when the init button is pressedl. https://www.youtube.com/watch?v=OT_PGYIFBGE
        telemetry.addData("PP:", "Ready");    //
        telemetry.update();

        waitForStart();
        runtime.reset();

//Lower Robot
        motorLatch.setPower(-0.3);
        while (opModeIsActive() && (runtime.seconds() < 2.6)) {
            telemetry.addData("Path", "Step 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        motorLatch.setPower(0);

        sleep(1500);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setPower(0.3);
        motorRight.setPower(0.3);
        runtime.reset();
        //Push Forward
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        //small turn
        sleep(2000);
        motorRight.setPower(0.15);
        motorLeft.setPower(-0.15);
        runtime.reset();
        //Turn to release the latch
        while (opModeIsActive() && (runtime.seconds() < 0.01)) {
            telemetry.addData("Path", "Step 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
//        sleep(2000);
//
//
//        motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorLeft.setPower(0.4);
//        motorRight.setPower(0.4);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.15)) {
//            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        sleep(1000);
//
//
//        motorLeft.setPower(0);
//        motorRight.setPower(0);
//
//        motorLatch.setPower(0.3);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 2)) {
//            telemetry.addData("Path", "Step 1: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        motorLatch.setPower(0);
//
//        sleep(1500);
//
//        //small turn
////WAS 0.2
//        motorRight.setPower(-0.2);
//        motorLeft.setPower(0);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.4)) {
//            telemetry.addData("Path", "Step 2: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        sleep(2000);
//        motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorLeft.setPower(0.4);
//        motorRight.setPower(0.4);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.15)) {
//            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//
//        }
//        sleep(2000);
//
//
//        motorRight.setPower(0);
//        motorLeft.setPower(0);
//        runtime.reset();
//        sleep(1000);
//        motorArm.setPower(0.9);
//        runtime.reset();
//        sleep(1000);
//        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
//            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorLeft.setPower(-0.3);
//        motorRight.setPower(-0.3);
//        runtime.reset();
//        //Push Forward
//        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
//            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        sleep(1000);
//        motorRight.setPower(0);
//        motorLeft.setPower(-0.6);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.4)) {
//            telemetry.addData("Path", "Step 2: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        sleep(1000);
//        motorRight.setPower(0);
//        motorLeft.setPower(-0.6);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.2)) {
//            telemetry.addData("Path", "Step 2: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }
//        motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        motorLeft.setPower(0.3);
//        motorRight.setPower(0.3);
//        runtime.reset();
//        sleep(1000);
//        //Push Forward
//        while (opModeIsActive() && (runtime.seconds() < 4.5)) {
//            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }



        idle();

    }
}





