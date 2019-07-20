package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import android.hardware.Camera;


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
    }

    public void start() {
        //Runs when driver hits play
    }

    public void loop() {
        //Code running until robot is stopped
        double left = -gamepad1.left_stick_y;
        double right = -gamepad1.right_stick_y;
        speed = 0.5;

        if (speed_on) {
            speed = speed + (gamepad1.right_trigger*0.5) - (gamepad1.left_trigger*0.3);
            if (speed < 0.1) {
                speed = 0.1;
            }
            if (speed > 0.9) {
                speed = 1;
            }
        }
        LFMotor.setPower(-left * speed);
        LBMotor.setPower(-left * speed);
        RFMotor.setPower(right * speed);
        RBMotor.setPower(right * speed);
        if (gamepad1.y) {
            speed_on = true;
        } else if (gamepad1.x) {
            speed_on = false;
        }
        telemetry.addData("speed %", speed*100);
        if (gamepad1.left_bumper) {
            RServo.setPower(-1);
            LServo.setPower(1);
        } else if (gamepad1.right_bumper) {
            RServo.setPower(1);
            LServo.setPower(-1);
        } else {
            RServo.setPower(0);
            LServo.setPower(0);
        }
        telemetry.addData("speed control", speed_on);
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
