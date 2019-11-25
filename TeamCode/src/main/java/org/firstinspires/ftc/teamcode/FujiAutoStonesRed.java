package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoStonesRed", group="PatentPending")
public class FujiAutoStonesRed extends FujiAutoStonesBlue {
    @Override
    public void runOpMode() {
        setReverse();
        super.runOpMode();
    }
}