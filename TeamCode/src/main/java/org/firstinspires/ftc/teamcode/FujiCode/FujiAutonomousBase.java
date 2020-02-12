package org.firstinspires.ftc.teamcode.FujiCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.FujiCode.Fuji;
import org.firstinspires.ftc.teamcode.pipelines.SkystonePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public abstract class FujiAutonomousBase extends LinearOpMode {

    // robot
    public Fuji robot;

    // field constants.
    public static final double TILE_LENGTH = 23;

    OpenCvCamera cam;
    SkystonePipeline pipeline = new SkystonePipeline(); // set pipeline here, after creating it in pipelines folder


    public void initCV() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View
        //cam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(camName.class, "cam 1"));

        cam.openCameraDevice();

        /*
         * Specify the image processing pipeline we wish to invoke upon receipt
         * of a frame from the camera. Note that switching pipelines on-the-fly
         * (while a streaming session is in flight) *IS* supported.
         */
        cam.setPipeline(pipeline);
    }

    public void startCV() {
        if (cam != null) {
            cam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            cam.resumeViewport();
        }
    }

    public void stopCV() {
        if (cam != null) {
            cam.stopStreaming();
            cam.pauseViewport();
        }
    }
}
