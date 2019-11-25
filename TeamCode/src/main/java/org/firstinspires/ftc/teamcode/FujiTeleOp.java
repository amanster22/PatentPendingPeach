package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import android.app.Activity;
import android.content.Context;
import android.view.View;

@TeleOp(name = "FujiTeleOp", group = "PatentPending")
public final class FujiTeleOp extends OpMode {
    // Declare OpMode members.
    private DcMotor rfMotor;
    private DcMotor lfMotor;
    private DcMotor rbMotor;
    private DcMotor lbMotor;
    private DcMotor hin1;
    private DcMotor hin2;
    private CRServo pinch;
    private CRServo hook1;
    private CRServo hook2;
    private View relativeLayout;
    // Declare speeds.
    private static double driveSpeed = 1;
    private static double hingeSpeed;
    private static final double turnSpeed = 0.5;
    private static final double hookSpeed = 0.5;
    private static final double pinchSpeed = -1;
    private boolean reverse = false;
    private int soundID = 0;
    private boolean soundPlaying = false;
    private SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
//    params.loopControl = 0;
//    params.waitForNonLoopingSoundsToFinish = true;

    public final void init() {
        // Initialize OpMode members.
        Context myApp = hardwareMap.appContext;
        rfMotor = hardwareMap.dcMotor.get("rf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lfMotor = hardwareMap.dcMotor.get("lf");
        lbMotor = hardwareMap.dcMotor.get("lb");
        hin1 = hardwareMap.dcMotor.get("hin1");
        hin2 = hardwareMap.dcMotor.get("hin2");
        hook1 = hardwareMap.crservo.get("hook1");
        hook2 = hardwareMap.crservo.get("hook2");
        pinch = hardwareMap.crservo.get("pinch");
        // Stop all motion.
        stop();
        soundID = myApp.getResources().getIdentifier("patentaudio.m4a", "raw", myApp.getPackageName());
        telemetry.addData("id",soundID);
        telemetry.update();

        if (soundID != 0) {

            // Signal that the sound is now playing.
            soundPlaying = true;

            // Start playing, and also Create a callback that will clear the playing flag when the sound is complete.
            SoundPlayer.getInstance().startPlaying(myApp, soundID, params, null,
                    new Runnable() {
                        public void run() {
                            soundPlaying = false;
                        }} );
        }
        // Initialize relative layout to change the phone's background color.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity)hardwareMap.appContext).findViewById(relativeLayoutId);
    }

    public final void start() {
    }

    public final void loop() {
        // Get input from the controller.
        double forward = gamepad1.left_stick_y;
        double sideways = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        double hingeInput = gamepad2.left_stick_y;
        double hookInput = gamepad2.right_stick_y;
        double pinchInput = 0;

        if (gamepad2.right_bumper) {pinchInput++;}
        if (gamepad2.left_bumper) {pinchInput--;}

        if (gamepad1.x) {reverse = true;}
        if (gamepad1.y) {reverse = false;}

        if (gamepad1.dpad_up){driveSpeed = 1;}
        if (gamepad1.dpad_down) {driveSpeed = 0.3;}
        if (gamepad1.dpad_left || gamepad1.dpad_right) {driveSpeed = 0.5;}

        if (gamepad2.right_trigger > 0) {hingeSpeed = 0.3;
        } else {hingeSpeed = 0.5;}

        // Add telemetry data.
        telemetry.addData("Speed", driveSpeed);
        telemetry.addData("Reverse", reverse);

        // Declare drive motor speeds.
        final double rfSpeed = (- forward - sideways) * (reverse ? -1 : 1) - turn * turnSpeed;
        final double rbSpeed = (- forward + sideways) * (reverse ? -1 : 1) - turn * turnSpeed;
        final double lfSpeed = (+ forward - sideways) * (reverse ? -1 : 1) - turn * turnSpeed;
        final double lbSpeed = (+ forward + sideways) * (reverse ? -1 : 1) - turn * turnSpeed;

        // Set arm motor speeds.
        hin1.setPower(hingeInput * -hingeSpeed);
        hin2.setPower(hingeInput * hingeSpeed);
        hook1.setPower(hookInput * hookSpeed);
        hook2.setPower(hookInput * hookSpeed);
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

        // Update telemetry.
        telemetry.update();
    }


    public final void stop() {
        // Stop all motion.
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        hin1.setPower(0);
        hin2.setPower(0);
        hook1.setPower(0);
        hook2.setPower(0);
        pinch.setPower(0);
    }
}
