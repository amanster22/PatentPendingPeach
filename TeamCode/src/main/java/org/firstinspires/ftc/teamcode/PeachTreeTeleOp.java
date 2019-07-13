package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "peachOS", group = "PatentPending")
public class PeachTreeTeleOp extends OpMode {
    private DcMotor LFMotor;
    private DcMotor LBMotor;
    private DcMotor RFMotor;
    private DcMotor RBMotor;
    private CRServo RServo;
    private CRServo LServo;

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

        LFMotor.setPower(-left);
        LBMotor.setPower(-left);
        RFMotor.setPower(right);
        RBMotor.setPower(right);
        if (gamepad1.left_bumper) {
            RServo.setPower(-1);
            LServo.setPower(1);
            telemetry.addData("servo", "on");
        } else if (gamepad1.right_bumper) {
            RServo.setPower(1);
            LServo.setPower(-1);
            telemetry.addData("servo", "on");
        } else {
            RServo.setPower(0);
            LServo.setPower(0);
            telemetry.addData("servo", "off");
        }
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
