package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

@TeleOp(name = "peachOS", group = "PatentPending")
public class PeachTreeTeleOp extends OpMode {
//    private Camera camera;
    private DcMotor LFMotor;
    private DcMotor LBMotor;
    private DcMotor RFMotor;
    private DcMotor RBMotor;
    private CRServo RServo;
    private CRServo LServo;
    private double speed = 0.5;
    private boolean speed_on = false;
    private boolean servo_hold = false;
//    private String phoneBackground="WHITE";



    private View relativeLayout = null;
    public void init() {
        //Runs when Driver hits Init


        LFMotor = hardwareMap.dcMotor.get("lf");
        LBMotor = hardwareMap.dcMotor.get("lb");
        RFMotor = hardwareMap.dcMotor.get("rf");
        RBMotor = hardwareMap.dcMotor.get("rb");
        RServo = hardwareMap.crservo.get("crr");
        LServo = hardwareMap.crservo.get("crl");



        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
        RServo.setPower(0);
        LServo.setPower(0);
        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
    }

    public void start() {
        //Runs when driver hits play
    }

    public void loop() {
        //Code running until robot is stopped
        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;
        speed = 0.5;

        //toggle servo holding, which will hold the item by applying constant servo pressure
        if (gamepad1.b) {
            servo_hold = true;
        } else if (gamepad1.a) {
            servo_hold = false;
        }
        //toggle speed control
        if (gamepad1.y) {
            speed_on = true;
        } else if (gamepad1.x) {
            speed_on = false;
        }
        //adjust speed if speed control is on, right trigger is a boost, left trigger is a brake, but not a complete brake
        if (speed_on) {
            speed = speed + (gamepad1.right_trigger*0.5) - (gamepad1.left_trigger*0.3);
            if (speed < 0.1) {
                speed = 0.1;
            }
            if (speed > 0.9) {
                speed = 1;
            }
        }
        //move motors based on speed and human xbox controller position
        LFMotor.setPower(-left * speed);
        LBMotor.setPower(-left * speed);
        RFMotor.setPower(right * speed);
        RBMotor.setPower(right * speed);
        telemetry.addData("speed %", speed*100);
        relativeLayout.post(new Runnable(
        ) {
            public void run() {
                if(gamepad1.left_trigger > 0)
                    relativeLayout.setBackgroundColor(Color.RED);
                else if(gamepad1.right_trigger>0)
                    relativeLayout.setBackgroundColor(Color.GREEN);
                else
                    relativeLayout.setBackgroundColor(Color.CYAN);

            }
        });
        //either hold servo or use human for manual servo control
        if (servo_hold) {
            RServo.setPower(0.5);
            LServo.setPower(-0.5);
        } else {
            if (gamepad1.left_bumper) {
                //open gripper
                RServo.setPower(-1);
                LServo.setPower(1);


            } else if (gamepad1.right_bumper) {
                //close gripper
                RServo.setPower(1);
                LServo.setPower(-1);

            } else {
                // no gripper movement
                RServo.setPower(0);
                LServo.setPower(0);
                //            phoneBackground="BLACK";
            }
        }
        telemetry.addData("speed control", speed_on);
//        relativeLayout.post(new Runnable(
//        ) {
//            public void run() {
//                if(phoneBackground=="RED")
//                    relativeLayout.setBackgroundColor(Color.RED);
//                else if(phoneBackground=="GREEN")
//                    relativeLayout.setBackgroundColor(Color.GREEN);
//                else if(phoneBackground=="BLACK")
//                    relativeLayout.setBackgroundColor(Color.BLACK);
//
//            }
//        });
        telemetry.update();
    }

    public void stop() {
        //Code is stopped
        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
        RServo.setPower(0);
        LServo.setPower(0);
    }
}
