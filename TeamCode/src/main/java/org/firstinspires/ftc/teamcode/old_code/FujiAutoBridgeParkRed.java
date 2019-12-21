package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="FujiAutoBridgeParkRed", group="PatentPending")
public class FujiAutoBridgeParkRed extends FujiAutoBridgeParkBlue {
    @Override
    public void runOpMode() {
        setReverse();
        super.runOpMode();
    }
}