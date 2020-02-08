package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="CVAUTOTEST")
public class FujiAutonomousCV extends FujiAutonomousBase {

    @Override
    final public void runOpMode() throws InterruptedException {
        initCV();
        waitForStart();
        startCV();
        while (opModeIsActive()) {
            idle();
        }
        stopCV();
    }
}
