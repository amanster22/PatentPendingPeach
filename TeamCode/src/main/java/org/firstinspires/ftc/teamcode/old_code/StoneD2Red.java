package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="StoneD2Red", group="PatentPending")
public class StoneD2Red extends StoneD2 {
    @Override
    public void runOpMode() {
        setReverse();
        super.runOpMode();
    }
}
