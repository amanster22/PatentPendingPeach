package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import android.app.Activity;
import android.view.View;

@TeleOp(name = "FujiTeleOp", group = "PatentPending")
public class FujiTeleOp extends OpMode {
    // Declare OpMode members.
    private DcMotor rfMotor;
    private DcMotor lfMotor;
    private DcMotor rbMotor;
    private DcMotor lbMotor;
    private DcMotor hin1;
    private DcMotor hin2;
    private CRServo pinch;
    private CRServo hook;
    private View relativeLayout;
    // Declare speeds.
    private static final double driveSpeed = 1;
    private static final double hingeSpeed = 1;
    private static final double hookSpeed = 1;
    private static final double pinchSpeed = 1;
    // Declare motion.
    private boolean pinchOn;

    public void init() {
        // Initialize OpMode members.
        rfMotor = hardwareMap.dcMotor.get("rf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lfMotor = hardwareMap.dcMotor.get("lf");
        lbMotor = hardwareMap.dcMotor.get("lb");
        hin1 = hardwareMap.dcMotor.get("hin1");
        hin2 = hardwareMap.dcMotor.get("hin2");
        hook = hardwareMap.crservo.get("hook");
        pinch = hardwareMap.crservo.get("pinch");
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
        double leftForward = gamepad1.left_stick_y;
        double rightForward = gamepad1.right_stick_y;
        double sideways = (gamepad1.right_stick_x + gamepad1.left_stick_x) / 2;
        double hingeInput = gamepad2.right_stick_y;
        double hookInput = 0;
        double pinchInput = 0;

        if (gamepad2.dpad_up) {hookInput++;}
        if (gamepad2.dpad_down) {hookInput--;}

        if (gamepad2.right_bumper) {pinchInput++;}
        if (gamepad2.left_bumper) {pinchInput--;}

        // Declare drive motor speeds.
        final double rfSpeed = (- rightForward - sideways) / 2;
        final double rbSpeed = (- rightForward + sideways) / 2;
        final double lfSpeed = (+ leftForward - sideways) / 2;
        final double lbSpeed = (+ leftForward + sideways) / 2;

        // Set arm motor speeds.
        hin1.setPower(hingeInput * hingeSpeed);
        hin2.setPower(hingeInput * -hingeSpeed);
        hook.setPower(hookInput * hookSpeed);
        pinch.setPower(pinchInput * pinchSpeed);
        // Set drive motor speeds.
        rfMotor.setPower(Math.max(Math.min(rfSpeed * driveSpeed, 1), -1));
        rbMotor.setPower(Math.max(Math.min(rbSpeed * driveSpeed, 1), -1));
        lfMotor.setPower(Math.max(Math.min(lfSpeed * driveSpeed, 1), -1));
        lbMotor.setPower(Math.max(Math.min(lbSpeed * driveSpeed, 1), -1));

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
        hin1.setPower(0);
        hin2.setPower(0);
        hook.setPower(0);
        pinch.setPower(0);
    }
}