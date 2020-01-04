package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.hardware.Fuji;

import java.util.List;

@Autonomous(name = "FujiStonesTensorflow", group = "PatentPending")
public class FujiAutonomousStonesTensorflow extends FujiAutonomous {

    @Override
    public void runOpMode() {
        double skystone;
        double current;
        current = 1; //robot always begins its plans thinking its on the first stone
//        robot = new Fuji(hardwareMap, telemetry); this is the code to init robot, but since we are testing on the phone it isnt needed

        initVuforia();
        initTfod();

        tfod.activate(); //may throw exception that tfod is null, meaning that the phones dont support tensorflow
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

        while (!isStarted()) {
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData("confidence: ", recognition.getConfidence());
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("center x coordinate (%d)", i), "%.03f",
                            (recognition.getLeft() + recognition.getRight()) / 2);
                }
                telemetry.update();

            }
        }
        //once the opmode starts, we should know what stone is the skystone, so we can immediate race off to it
    }
}
