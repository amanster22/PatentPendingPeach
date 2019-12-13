package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "FujiTeleOp", group = "PatentPending")
public final class FujiTeleOp extends OpMode {

    // Declare OpMode members.
    private DcMotor rfMotor; // base
    private DcMotor lfMotor; // base
    private DcMotor rbMotor; // base
    private DcMotor lbMotor; // base
    private DcMotor hin1; // arm
    private DcMotor hin2; // arm

    private CRServo hook1; // pinch
    private CRServo hook2; // pinch
    private Servo pin; // pinch
    private DcMotor rightlift;
    private DcMotor leftlift;

    // Declare speeds.
    private double driveSpeedInput = 1;
    private double hinSpeedInput = 1;
    private boolean reverseInput = false;
    private static final double driveSpeed = -1;
    private static final double turnSpeed = -0.5;
    private static final double hinSpeed = 0.5;
    private static final double hookSpeed = 0.5;
    private static final double liftSpeed = 0.5;

    /*
    private int soundID = 0;
    private boolean soundPlaying = false;
    private SoundPlayer.PlaySoundParams params = new SoundPlayer.PlaySoundParams();
//    params.loopControl = 0;
//    params.waitForNonLoopingSoundsToFinish = true;
    */

    @Override
    public final void init() {

        // Initialize OpMode members.
        rfMotor = hardwareMap.dcMotor.get("rf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lfMotor = hardwareMap.dcMotor.get("lf");
        lbMotor = hardwareMap.dcMotor.get("lb");
        hin1 = hardwareMap.dcMotor.get("hin1");
        hin2 = hardwareMap.dcMotor.get("hin2");
        hook1 = hardwareMap.crservo.get("hook1");
        hook2 = hardwareMap.crservo.get("hook2");
        pin = hardwareMap.servo.get("pinch");
        rightlift = hardwareMap.dcMotor.get("rl");
        leftlift = hardwareMap.dcMotor.get("ll");
        stop();

        /*
        Context myApp = hardwareMap.appContext;

        soundID = myApp.getResources().getIdentifier("patentaudio.m4a", "raw", myApp.getPackageName());
        telemetry.addData("id",soundID);
        telemetry.update();

        if (soundID != 0) {

            // Signal that the sound is now playing.
            soundPlaying = true;

            // Start playing, and also Create a callback that will clear the playing flag when the sound is complete.
            SoundPlayer.getInstance().startPlaying(myApp, soundID, params, null,
                new Runnable() {
                    @Override
                    public void run() {
                        soundPlaying = false;
                    }
                }
            );
        }
        // Initialize relative layout to change the phone's background color.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity)hardwareMap.appContext).findViewById(relativeLayoutId);
        */
    }

    @Override
    public final void start() {
    }

    @Override
    public final void loop() {

        // Get input from the controller.
        double forwardInput = gamepad1.left_stick_y;
        double sidewaysInput = gamepad1.left_stick_x;
        double turnInput = gamepad1.right_stick_x;
        double hingeInput = gamepad2.left_stick_y;
        boolean armCloseInput = gamepad2.left_trigger > 0;
        boolean armOpenInput = gamepad2.left_bumper;
        double hookInput = gamepad2.right_stick_y;
        boolean pinCloseInput = gamepad2.right_trigger > 0;
        boolean pinOpenInput = gamepad2.right_bumper;
//hi
        if (gamepad1.dpad_up){driveSpeedInput = 1;}
        if (gamepad1.dpad_down) {driveSpeedInput = 0.25;}
        if (gamepad1.dpad_left || gamepad1.dpad_right) {driveSpeedInput = 0.5;}

        if (gamepad2.dpad_up) {hinSpeedInput = 1;}
        if (gamepad2.dpad_down) {hinSpeedInput = 0.5;}

        if (gamepad1.x) {reverseInput = true;}
        if (gamepad1.y) {reverseInput = false;}
        if (gamepad2.dpad_up){
            rightlift.setPower(liftSpeed);
            leftlift.setPower (-liftSpeed);
        }

        if (gamepad2.dpad_down){
            rightlift.setPower(-liftSpeed);
            leftlift.setPower (liftSpeed);
        }
        else {
            rightlift.setPower(0);
            leftlift.setPower(0);
        }

        // Add telemetry data.
        telemetry.addData("Drive Speed", driveSpeedInput);
        telemetry.addData("Hinge Speed", hinSpeedInput);
        telemetry.addData("Reverse", reverseInput);

        // Declare drive motor speeds.
        final double rfSpeed =
                (+ forwardInput + sidewaysInput) * (reverseInput ? -1 : 1) * driveSpeed * driveSpeedInput +
                turnInput * turnSpeed * driveSpeedInput;
        final double rbSpeed =
                (+ forwardInput - sidewaysInput) * (reverseInput ? -1 : 1) * driveSpeed * driveSpeedInput +
                turnInput * turnSpeed * driveSpeedInput;
        final double lfSpeed =
                (- forwardInput + sidewaysInput) * (reverseInput ? -1 : 1) * driveSpeed * driveSpeedInput +
                turnInput * turnSpeed * driveSpeedInput;
        final double lbSpeed =
                (- forwardInput - sidewaysInput) * (reverseInput ? -1 : 1) * driveSpeed * driveSpeedInput +
                turnInput * turnSpeed * driveSpeedInput;

        // Set attachment motor speeds.

        hin1.setPower(hingeInput * hinSpeed * hinSpeedInput);
        hin2.setPower(-hingeInput * hinSpeed * hinSpeedInput);
       // wrist.setPower(hingeInput * hinSpeed * hinSpeedInput);

//        if (armCloseInput) {arm.setPosition(1);}
//        if (armOpenInput) {arm.setPosition(-1);}

        hook1.setPower(hookInput * hookSpeed);
        hook2.setPower(hookInput * hookSpeed);

        if (pinCloseInput) {pin.setPosition(1);}
        if (pinOpenInput) {pin.setPosition(-1);}

        // Set drive motor speeds.
        rfMotor.setPower(Math.max(Math.min(rfSpeed, 1), -1));
        rbMotor.setPower(Math.max(Math.min(rbSpeed, 1), -1));
        lfMotor.setPower(Math.max(Math.min(lfSpeed, 1), -1));
        lbMotor.setPower(Math.max(Math.min(lbSpeed, 1), -1));

        /*
        // Set phone background color.
        relativeLayout.post(new Runnable() {public void run() {
            double totalSpeed = (rfSpeed + rbSpeed + lfSpeed + lbSpeed) / 4;
            relativeLayout.setBackgroundColor((int)(totalSpeed * 255));
        }});
        */

        // Update telemetry.
        telemetry.update();
    }

    @Override
    public final void stop() {
        // Stop all motion.
        rfMotor.setPower(0);
        rbMotor.setPower(0);
        lfMotor.setPower(0);
        lbMotor.setPower(0);
        hin1.setPower(0);
        hin2.setPower(0);
        //arm.setPosition(arm.getPosition());
        hook1.setPower(0);
        hook2.setPower(0);
        leftlift.setPower(0);
        rightlift.setPower(0);
    }
}
