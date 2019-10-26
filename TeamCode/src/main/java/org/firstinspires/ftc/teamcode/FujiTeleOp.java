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
    private DcMotor extender;
    private DcMotor hinge;
    private CRServo pinch;
    private View relativeLayout;
    // Declare speeds.
    private static final double driveSpeed = 1;
    private static final double hingeSpeed = 0.5;
    private static final double extenderSpeed = 1;
    private static final double pinchSpeed = 1;

    public void init() {
        // Initialize OpMode members.
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
        double leftForward = gamepad1.left_stick_y;
        double rightForward = gamepad1.right_stick_y;
        double sideways = (gamepad1.right_stick_x + gamepad1.left_stick_x) / 2;
        double extenderInput= gamepad2.left_stick_y;
        double hingeInput = gamepad2.right_stick_y;
        double pinchInput = 0;
        if (gamepad2.dpad_up) {pinchInput++;}
        if (gamepad2.dpad_down) {pinchInput--;}
        // Declare drive motor speeds.
        final double rfSpeed = (- rightForward - sideways) / 2 * driveSpeed;
        final double rbSpeed = (- rightForward + sideways) / 2 * driveSpeed;
        final double lfSpeed = (+ leftForward - sideways) / 2 * driveSpeed;
        final double lbSpeed = (+ leftForward + sideways) / 2 * driveSpeed;
        // Set arm motor speeds.
        extender.setPower(extenderInput * extenderSpeed);
        hinge.setPower(hingeInput * hingeSpeed);
        pinch.setPower(pinchInput * pinchSpeed);
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
        pinch.setPower(0);
    }
}