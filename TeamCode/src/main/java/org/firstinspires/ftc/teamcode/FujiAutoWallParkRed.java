package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoWallParkRed", group="PatentPending")
public class FujiAutoWallParkRed extends FujiAutoWallParkBlue {
    @Override
    public void runOpMode() {
        setReverse();
        super.runOpMode();
    }
}