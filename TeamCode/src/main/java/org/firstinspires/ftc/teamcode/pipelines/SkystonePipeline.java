package org.firstinspires.ftc.teamcode.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class SkystonePipeline extends OpenCvPipeline {
    Mat HSVmat = new Mat();
    Mat thresholdMat = new Mat();
    Mat contoursOnFrameMat = new Mat();
    List<MatOfPoint> contoursList = new ArrayList<>();
    int numContoursFound;

    enum Stage {
        HSVMAT,
        THRESHOLD,
        CONTOURS_OVERLAYED_ON_FRAME,
        RAW_IMAGE,
    }

    private Stage stageToRenderToViewport = Stage.HSVMAT;
    private Stage[] stages = Stage.values();

//    @Override
//    public void onViewportTapped() {
//        /*
//         * Note that this method is invoked from the UI thread
//         * so whatever we do here, we must do quickly.
//         */
//
//        int currentStageNum = stageToRenderToViewport.ordinal();
//
//        int nextStageNum = currentStageNum + 1;
//
//        if (nextStageNum >= stages.length) {
//            nextStageNum = 0;
//        }
//
//        stageToRenderToViewport = stages[nextStageNum];
//    }

    @Override
    public Mat processFrame(Mat input) {
        contoursList.clear();

        /*
         * This pipeline finds the contours of yellow blobs such as the Gold Mineral
         * from the Rover Ruckus game.
         */

//        Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);
//        Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);
//        Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);
//        Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
//        numContoursFound = contoursList.size();
//        input.copyTo(contoursOnFrameMat);
//        Imgproc.drawContours(contoursOnFrameMat, contoursList, -1, new Scalar(0, 0, 255), 3, 8);
//
        Imgproc.cvtColor(input, HSVmat, Imgproc.COLOR_RGB2HSV);
        Core.extractChannel(HSVmat, HSVmat, 1);

        return input;
    }
}