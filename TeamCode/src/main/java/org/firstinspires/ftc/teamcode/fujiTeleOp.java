package org.firstinspires.ftc.teamcode;


import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
//import org.firstinspires.ftc.robotcore.

@TeleOp(name = "FujiOS", group = "PatentPending")
public class fujiTeleOp extends OpMode {
//    private Camera camera;
    private DcMotor LFMotor;
    private DcMotor LBMotor;
    private DcMotor RBMotor;
    private DcMotor RFMotor;
    private DcMotor DualLLL;
    private DcMotor DualLLR;
    private DcMotor DualLRL;
    private DcMotor DualLRR;
//    private CRServo RServo;
//    private CRServo LServo;
//    private DcMotor HTrain;
    private double speed = 0.5;
    private boolean speed_on = false;
    private boolean servo_hold = false;
    private double claw_index = 0;
    private boolean intake = false;
//    private String phoneBackground="WHITE";



    private View relativeLayout = null;
    public void init() {
        //Runs when Driver hits Init
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();
//        initVuforia();

        LFMotor = hardwareMap.dcMotor.get("lf");
        LBMotor = hardwareMap.dcMotor.get("lb");
        RFMotor = hardwareMap.dcMotor.get("rb");
        RBMotor = hardwareMap.dcMotor.get("rf");
        DualLLL = hardwareMap.dcMotor.get("dlll");
        DualLLR = hardwareMap.dcMotor.get("dllr");
        DualLRL = hardwareMap.dcMotor.get("dlrl");
        DualLRR = hardwareMap.dcMotor.get("dlrr");
//        RServo = hardwareMap.crservo.get("crr");
//        LServo = hardwareMap.crservo.get("crl");
//        HTrain = hardwareMap.dcMotor.get("h");
    //        Color = hardwareMap.colorSensor.get("color")



        LFMotor.setPower(0);
        LBMotor.setPower(0);
        RFMotor.setPower(0);
        RBMotor.setPower(0);
//        RServo.setPower(0);
//        LServo.setPower(0);
//        HTrain.setPower(0);
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
        double left_forward = gamepad1.left_stick_y;
        double right_forward = gamepad1.right_stick_y;
        double sideways = 0.5*(gamepad1.right_stick_x + gamepad1.left_stick_x);
        speed = 0.5;
        intake = false;
//        double htrain_dir = 0;



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
            speed = speed + (gamepad1.right_trigger*0.5) - (gamepad1.left_trigger*0.5);
            if (speed < 0.1) {
                speed = 0.1;
            }
            if (speed > 0.9) {
                speed = 1;
            }
        }
        if (gamepad1.dpad_left) {
            DualLLL.setPower(-0.2);
            DualLLR.setPower(0.2);
            intake = true;
        }
        if (gamepad1.dpad_right) {
            DualLLL.setPower(0.2);
            DualLLR.setPower(-0.2);
            intake = true;
        }
        if (gamepad1.dpad_up){
            DualLRL.setPower(-speed);
            DualLRR.setPower(speed);
            intake = true;
        }
        if (gamepad1.dpad_down) {
            DualLRL.setPower(speed);
            DualLRR.setPower(-speed);
            intake = true;
        }
        if (!intake) {
            DualLLL.setPower(0);
            DualLLR.setPower(0);
            DualLRL.setPower(0);
            DualLRR.setPower(0);
        }
        //move motors based on speed and human xbox controller position
        RFMotor.setPower(speed*(Math.max(Math.min(-right_forward+sideways, 1), -1)));//+
        LBMotor.setPower(speed*(Math.max(Math.min(left_forward+sideways, 1), -1)));//-
        RBMotor.setPower(speed*(Math.max(Math.min(-right_forward-sideways, 1), -1)));//+
        LFMotor.setPower(speed*(Math.max(Math.min(left_forward-sideways, 1), -1)));//
        telemetry.addData("speed %", speed*100);
        relativeLayout.post(new Runnable(
        ) {
            public void run() {
                if(gamepad1.left_trigger > 0)
                    relativeLayout.setBackgroundColor(Color.RED);
                else if(gamepad1.right_trigger > 0)
                    relativeLayout.setBackgroundColor(Color.GREEN);
                else
                    relativeLayout.setBackgroundColor(Color.BLUE);

            }
        });
        //either hold servo or use human for manual servo control
//        if (servo_hold) {
//            RServo.setPower(0.5);
//            LServo.setPower(-0.5);
//        } else {
//            if (gamepad1.left_bumper) {
//                //open gripper
//                RServo.setPower(-1);
//                LServo.setPower(1);
//
//
//            } else if (gamepad1.right_bumper) {
//                //close gripper
//                RServo.setPower(1);
//                LServo.setPower(-1);
//
//            } else {
//                // no gripper movement
//                RServo.setPower(0);
//                LServo.setPower(0);
//                //            phoneBackground="BLACK";
//            }
//        }
        telemetry.addData("speed control", speed_on);
//        telemetry.addData("servo holding", servo_hold);
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
//        RServo.setPower(0);
//        LServo.setPower(0);
//        HTrain.setPower(0);
    }
//    public void initVuforia() {
//        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
//
//        parameters.vuforiaLicenseKey = VUFORIA_KEY;
//        parameters.cameraDirection = CameraDirection.BACK;
//
//        //  Instantiate the Vuforia engine
//        vuforia = ClassFactory.getInstance().createVuforia(parameters);
//    }
}
