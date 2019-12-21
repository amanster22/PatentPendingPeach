package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoBridgeParkBlue", group="PatentPending")
public class FujiAutoBridgeParkBlue extends FujiAuto {

    @Override
    public void runOpMode() {

        initMotors();
        telemetry.addData("Path", "started.");
        telemetry.update();

        encoderDrive(25, 0);
        encoderDrive(0, 30);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override final void startGrab() {}
    @Override final void stopGrab() {}
}
