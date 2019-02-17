package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

//hellooooooooooooooooooooooooooooooooooooooo
//


@Autonomous(name = "PP AUTO V1.1")
//@Disabled
public class TFTest extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final VuforiaLocalizer.CameraDirection CAMERA_DIRECTION = CameraDirection.FRONT;

    private ElapsedTime runtime = new ElapsedTime();
    //clear motor objects
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private DcMotor motorArm;
    private DcMotor motorLatch;


   /////////////////////////////////////
    private static final String VUFORIA_KEY = "AdDXLt3/////AAABmZbiIZDoMksbg6nqJc3deqEd+M9xq3k2f+FgTzaPiAad7oT1lr/YPo2zIOgo/ufXH9xFZ3n3HhO2pMJ96x1NZfM6C4Y+hSgk5bXAxomE7lI571xHlpGumFh8jns+8NA/llYnvjRl6GBpBLIj0+qltMMkRWNja+JpTOQQIGPXGNR/QER7VNQ2i6spWHnzkqNaQLfwJ24qcRhfKTN83yWiaYUGppkWQy34vdJ2XHW8LpuztSBY4EnI9U1tkN+TEHc9nFPPHS6sfi184UQEVQ8HPrFfJni1YJrxe8//+XisQkgFBZkB/SR7UdcVrUqB+dGk4epjHXlMmSn3XHM7AX747Nh+4+T9eXbXc6s0Qu4vKzcY";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

        motorLeft = hardwareMap.dcMotor.get("mLeft");
        motorRight = hardwareMap.dcMotor.get("mRight");

        motorLatch = hardwareMap.dcMotor.get("mRetract");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        motorArm = hardwareMap.dcMotor.get("mArm");

        telemetry.addData("PP:", "Ready, Hi Aman, Zekun, Charlie, PK, and Spencer. Lets do this!");    //
        telemetry.update();


        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        runtime.reset();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }
            //unlatching();
            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }

                            if (goldMineralX != -1 && silverMineral1X != -1 ) {
                                if (goldMineralX < silverMineral1X ) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    unlatching();
                                    runLeft();

                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");

                                    sleep(1000);
                                    telemetry.addData("Ready to run", "Center");
                                    unlatching();
                                    runMiddle();
                                }
                                }
                                else
                                    {
                                telemetry.addData("Gold Mineral Position", "Right");
                                unlatching();
                                runRight();
                                    }
                            }
                        }
                        telemetry.update();
                    }
                }

        }

        if (tfod != null) {
            tfod.shutdown();
        }
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }



    private void runLeft()
    {
        sleep(800);
        motorRight.setPower(0.3);
        motorLeft.setPower(-0.3);
        runtime.reset();
        //Turn to release the latch
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Left", "Running1", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(1000);
        motorRight.setPower(-0.5);
        motorLeft.setPower(-0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Left", "Running %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);



/*
        sleep(1500);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setPower(0.3);
        motorRight.setPower(0.3);
        runtime.reset();
        //Push Forward
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Step 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        //small turn
        sleep(2000);
        motorRight.setPower(0.15);
        motorLeft.setPower(-0.15);
        runtime.reset();
        //Turn to release the latch
        while (opModeIsActive() && (runtime.seconds() < 0.01)) {
            telemetry.addData("Path", "Step 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        */
    }

    private void runMiddle()
    {
        sleep(1000);
        motorRight.setPower(-0.5);
        motorLeft.setPower(-0.5);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.8)) {
            telemetry.addData("Path", "Middle: Running %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }

    private void runRight()
    {
        sleep(800);
        motorRight.setPower(-0.3);
        motorLeft.setPower(0.3);
        runtime.reset();
        //Turn to release the latch
        while (opModeIsActive() && (runtime.seconds() < 0.6)) {
            telemetry.addData("Right", "Running %2.5f", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(1000);
        motorRight.setPower(-0.5);
        motorLeft.setPower(-0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Right", "Running %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
    }




    private void unlatching()
    {

        motorLatch.setPower(-0.55);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 4.7)) {
            telemetry.addData("Path", "Step 1: Unlatching %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLatch.setPower(0);

        sleep(800);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setPower(0.3);
        motorRight.setPower(0.3);
        runtime.reset();
        //Push Forward
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Step 2: Small Turn %2.5f", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        //small turn
        sleep(800);
        motorRight.setPower(0.3);
        motorLeft.setPower(-0.3);
        runtime.reset();
        //Turn to release the latch
        while (opModeIsActive() && (runtime.seconds() < 0.7)) {
            telemetry.addData("Path", "Step 3: Turn %2.5f", runtime.seconds());
            telemetry.update();
        }

        motorLeft.setPower(0);
        motorRight.setPower(0);
        //////////////////////////////
        sleep(700);
        motorLeft.setPower(-0.3);
        motorRight.setPower(-0.3);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.3)) {
            telemetry.addData("Path", "Step 4: Turn %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(100);
        motorRight.setPower(-0.3);
        motorLeft.setPower(0.3);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.6)) {
            telemetry.addData("Path", "Step 4: Turnback %2.5f", runtime.seconds());
            telemetry.update();
        }
        motorLeft.setPower(0);
        motorRight.setPower(0);
        runtime.reset();
    }




}