package org.firstinspires.ftc.teamcode;

abstract class FujiAutoPark extends FujiAuto {

    final void main(boolean RED) {
        if (RED) {setReverse();}

        initMotors();
        telemetry.addData("Path", "started.");
        telemetry.update();

        encoderDrive(0, 30);

        telemetry.addData("Path", "complete.");
        telemetry.update();
    }

    @Override final void startGrab() {}
    @Override final void stopGrab() {}
}
