package org.firstinspires.ftc.teamcode;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.Fuji;

@Autonomous(name="FujiTests", group="PatentPending")
public final class FujiAutonomousTests extends FujiAutonomous {

    @Override
    public final void runOpMode() {
        // UNCOMMENT THIS IF SOUNDS ARE NEEDED
        robot = new Fuji(hardwareMap, telemetry, this);
//        int index = 0;
//        int[] times = {2000, 6000, 3000, 3000};
        waitForStart();
//
//        while (opModeIsActive() && index < 4) {
//            robot.playAutoSound(index);
//            sleep(times[index]);
//            index +=1;
//        }
//        square linear movement
//        robot.move(TILE_LENGTH, 0);
//        sleep(1000);
//        robot.move(0, -TILE_LENGTH);
//        sleep(1000);
//        robot.move(-TILE_LENGTH, 0);
//        sleep(1000);
//        robot.move(0, TILE_LENGTH);
//        sleep(1000);
//
//        diagonal movement
//        robot.move(-TILE_LENGTH, TILE_LENGTH);
//        sleep(1000);
//        robot.move(-TILE_LENGTH, -TILE_LENGTH);
//        sleep(1000);
//        robot.move(TILE_LENGTH, TILE_LENGTH);
//        sleep(1000);
//        robot.move(TILE_LENGTH, -TILE_LENGTH);
//        sleep(1000);

        // turns, gyro sensor
        while (opModeIsActive()) {
            telemetry.addData("gyro reading", robot.gyro.measure());
            telemetry.update();
        }
//        robot.turn(0.5);
//        sleep(2000);
//        robot.turn(1.0);
    }
}
