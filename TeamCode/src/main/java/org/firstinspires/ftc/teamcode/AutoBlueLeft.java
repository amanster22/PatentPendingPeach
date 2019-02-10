/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "BLUE LEFT", group = "BollesBot")
@Disabled
public class AutoBlueLeft extends LinearOpMode {
    //blue alliance left side and red jewel is backward
    /* Declare OpMode members. */
    HardwarePushbot robot = new HardwarePushbot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();


    static final double FORWARD_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorSlider;
    DcMotor clawmotor;
    Servo autoJewel;
    ColorSensor colorSensor;
String VuforiaPosition="";

    //Vuforia code
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        motorFrontRight = hardwareMap.dcMotor.get("front_right");
        motorFrontLeft = hardwareMap.dcMotor.get("front_left");
        motorBackLeft = hardwareMap.dcMotor.get("back_left");
        motorBackRight = hardwareMap.dcMotor.get("back_right");
        motorSlider = hardwareMap.dcMotor.get("slider");
        clawmotor = hardwareMap.dcMotor.get("claw");

        autoJewel = hardwareMap.servo.get("jewel");
        motorFrontLeft.setDirection(DcMotor.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotor.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        motorBackRight.setDirection(DcMotor.Direction.FORWARD);
        motorSlider.setDirection(DcMotor.Direction.REVERSE);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        // bLedOn represents the state of the LED.
        boolean bLedOn = true;

        // get a reference to our ColorSensor object.
        colorSensor = hardwareMap.get(ColorSensor.class, "colorsensor");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASyVB6//////AAAAGe9aN9ndM0j2hd9Fl+cwkfVe8SNqQfzluWcjXBCnpPLsGbmSgJ8FJBCZT2UexgBkATYZKds3325nV4IdUmoZXFA5jkDuz1P74uv+RTFq6VWanu6fn78CsvC/5gvBEV+VdlJRIka3hYxSr+Tz2sA72GApZx2ciuDPSydblfJAMGGtFqQ9VC7egTutolc4iMNmKs3KnKxlUJ3o/wGFJiT2cQ93cUHh+gGkbBlL+1diq+OQZsN0GUxGt8N8WW1PD82E1Eo/byb8BJaL4prNdFhwk0UIlfbkk6sPgf1jSDxUhLpDRD+qQ0In4I4w7IPesH2dHl0SVNGFYT0014YXkEgEta2LL8UpvEH8XrAIQsaGQdkf";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        relicTrackables.activate();

        while (opModeIsActive()&& (runtime.seconds() <= 5))
        {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);
                VuforiaPosition=vuMark.toString();
                telemetry.addData("Voforia Postion",VuforiaPosition);
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }
        sleep(2000);

        clawmotor.setPower(0.25);//close claw

        sleep(250);
        //lower color sensor servo
        autoJewel.setPosition(0.65);

        sleep(250);
        motorSlider.setPower(-0.7);

        sleep(500);
        motorSlider.setPower(0);
        sleep(250);

        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });

        while (opModeIsActive() && (runtime.seconds() <= 0.3)) {
            // check the status of the x button on either gamepad.
            bCurrState = gamepad1.x;

            // check for button state transitions.
            if (bCurrState && (bCurrState != bPrevState)) {

                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                colorSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);

            // send the info back to driver station using telemetry function.
            telemetry.addData("LED", bLedOn ? "On" : "Off");
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);

            // change the background color to match the color detected by the RGB sensor.
            // pass a reference to the hue, saturation, and value array as an argument
            // to the HSVToColor method.
            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
        }



        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way
        // Step 1:  Drive forward, Runtime.reset while loop with telemetry.update()

        if (colorSensor.blue() > 0)

        {
            //Drive Backward
            BollesTimeDrive(-(float) 0.3, 0);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <= 0.35)) {

                // send the info back to driver station using telemetry function.
                telemetry.addData("Path", "Leg 1 RED: %2.5f S Elapsed", runtime.seconds());

                telemetry.update();
            }
            autoJewel.setPosition(0);
            //Drive Forward
            BollesTimeDrive(0,(float) 0.5);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <= 0.15)) {

                // send the info back to driver station using telemetry function.
                telemetry.addData("Path", "Leg 1 RED: %2.5f S Elapsed", runtime.seconds());

                telemetry.update();
            }
            BollesTimeDrive((float) 0.3, 0);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <= (2.3))) {

                // send the info back to driver station using telemetry function.
                telemetry.addData("Path", "Leg 1 RED: %2.5f S Elapsed", runtime.seconds());

                telemetry.update();
            }
            BollesTimeDrive((float) 0.3, 0);
            if (VuforiaPosition.equalsIgnoreCase("Center")) {
                while (opModeIsActive() && (runtime.seconds() <= 0.6)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 CENTER: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
            else  if (VuforiaPosition.equalsIgnoreCase("Left")) {
                while (opModeIsActive() && (runtime.seconds() <= 0.5)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 LEFT: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
            else {
                //default is right
                while (opModeIsActive() && (runtime.seconds() <= 1.3)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 RIGHT: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
        } else

        {

            //Drive Forward
            BollesTimeDrive((float) 0.3, 0);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <= 1.2)) {

                // send the info back to driver station using telemetry function.
                telemetry.addData("Path", "Leg 1 RED: %2.5f S Elapsed", runtime.seconds());

                telemetry.update();
            }
            autoJewel.setPosition(0);
            //TURN RIGHT
            BollesTimeDrive(0,(float) 0.5);
            runtime.reset();
            while (opModeIsActive() && (runtime.seconds() <= 0.18)) {

                // send the info back to driver station using telemetry function.
                telemetry.addData("Path", "Leg 1 RED: %2.5f S Elapsed", runtime.seconds());

                telemetry.update();
            }
            //Drive Forward
            BollesTimeDrive((float) 0.3, 0);
            runtime.reset();
            if (VuforiaPosition.equalsIgnoreCase("Center")) {
                while (opModeIsActive() && (runtime.seconds() <= 0.6)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 CENTER: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
            else  if (VuforiaPosition.equalsIgnoreCase("Left")) {
                while (opModeIsActive() && (runtime.seconds() <= 0.5)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 LEFT: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
            else {
                //default is right
                while (opModeIsActive() && (runtime.seconds() <= 1.4)) {

                    // send the info back to driver station using telemetry function.
                    telemetry.addData("Path", "Leg 2 RIGHT: %2.5f S Elapsed", runtime.seconds());

                    telemetry.update();
                }
            }
        }

        clawmotor.setPower(-0.25);//open claw

        sleep(250);

        BollesTimeDrive((float) 0.3, 0);
        runtime.reset();
        while (

                opModeIsActive() && (runtime.seconds() < 0.3))

        {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        clawmotor.setPower(-0.25);//open claw

        sleep(250);

        BollesTimeDrive((float) 0.3, 0);
        runtime.reset();
        while (

                opModeIsActive() && (runtime.seconds() < 0.6))

        {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        // Step 3:  Drive Backwards for 1 Second
        BollesTimeDrive(-(float) 0.2, 0);
        runtime.reset();
        while (

                opModeIsActive() && (runtime.seconds() < 0.5))

        {
            telemetry.addData("Path", "Leg 4: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 4:  Stop and close the claw.
        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);


        telemetry.addData("Path", "Complete");
        telemetry.update();

        sleep(1000);

    }


    public void BollesTimeDrive(float left_stick_y, float right_stick_x) {

        float left_stick_x = 0;
        float gamepad1LeftY = left_stick_y;
        float gamepad1LeftX = -left_stick_x;
        float gamepad1RightX = right_stick_x;
        float gamepad1leftY = left_stick_y;

        // holonomic formulas

        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        motorFrontRight.setPower(FrontRight / 2);
        motorFrontLeft.setPower(FrontLeft / 2);
        motorBackLeft.setPower(BackLeft / 2);
        motorBackRight.setPower(BackRight / 2);
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

}
