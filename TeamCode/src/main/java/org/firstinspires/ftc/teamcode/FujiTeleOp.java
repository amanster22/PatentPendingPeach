package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import android.app.Activity;
import android.view.View;

@TeleOp(name = "FujiTeleOp", group = "PatentPending")
public class FujiTeleOp extends OpMode {

    // Declare opMode members.
    private DcMotor rfMotor;
    private DcMotor lfMotor;
    private DcMotor rbMotor;
    private DcMotor lbMotor;
    private DcMotor extender;
    private DcMotor hinge;
    private CRServo pinch;
    private View relativeLayout;
    private double speed = 1;

    public void init() {
        // Initialize opMode members.
        rfMotor = hardwareMap.dcMotor.get("rf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lfMotor = hardwareMap.dcMotor.get("lf");
        lbMotor = hardwareMap.dcMotor.get("lb");
        extender = hardwareMap.dcMotor.get("ext");
        hinge = hardwareMap.dcMotor.get("hin");
        pinch = hardwareMap.crservo.get("pin");
        // Stop all motion.
        stop();
        // Initialize relative layout to change the phone's background color.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity)hardwareMap.appContext).findViewById(relativeLayoutId);
    }

    public void start() {
    }

    public void loop() {
        // Get input from the controller.
        final double leftForward = gamepad1.left_stick_y;
        final double rightForward = gamepad1.right_stick_y;
        final double sideways = (gamepad1.right_stick_x + gamepad1.left_stick_x) / 2;
        double extenderSpeed = gamepad2.left_stick_y;
        double hingeSpeed = gamepad2.right_stick_y;
        double pinchSpeed = 0;
        if (gamepad2.dpad_up) {pinchSpeed++;}
        if (gamepad2.dpad_down) {pinchSpeed--;}

        // Declare drive motor speeds.
        final double rfSpeed = (- rightForward - sideways) / 2 * speed;
        final double rbSpeed = (- rightForward + sideways) / 2 * speed;
        final double lfSpeed = (+ leftForward - sideways) / 2 * speed;
        final double lbSpeed = (+ leftForward + sideways) / 2 * speed;

        // Set arm motor speeds.
        extender.setPower(extenderSpeed);
        hinge.setPower(hingeSpeed);
        pinch.setPower(pinchSpeed);
        // Set drive motor speeds.
        rfMotor.setPower(Math.max(Math.min(rfSpeed, 1), -1));
        rbMotor.setPower(Math.max(Math.min(rbSpeed, 1), -1));
        lfMotor.setPower(Math.max(Math.min(lfSpeed, 1), -1));
        lbMotor.setPower(Math.max(Math.min(lbSpeed, 1), -1));

        // Set phone background color.
        relativeLayout.post(new Runnable() {public void run() {
            double totalSpeed = (rfSpeed + rbSpeed + lfSpeed + lbSpeed) / 4;
            relativeLayout.setBackgroundColor((int)(totalSpeed * 255));
        }});
    }

    public void stop() {
        // Stop all motion.
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        extender.setPower(0);
        hinge.setPower(0);
    }
}