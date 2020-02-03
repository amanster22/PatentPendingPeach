package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.Fuji;
import org.firstinspires.ftc.teamcode.old_code.FujiAutonomous;
import org.firstinspires.ftc.teamcode.roadrunnerstuff.RevSampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunnerstuff.SampleMecanumDriveBase;

@Autonomous(name="FujiTests", group="PatentPending")
public final class FujiAutonomousTests extends FujiAutonomousBase {

    @Override
    public final void runOpMode() {
        // UNCOMMENT THIS IF SOUNDS ARE NEEDED
        robot = new Fuji(hardwareMap, telemetry, this);
        waitForStart();

//        square linear movement
//        robot.move(TILE_LENGTH, 0);
//        sleep(1000);
//        robot.move(0, -TILE_LENGTH);
//        sleep(1000);
//        robot.move(-TILE_LENGTH, 0);
//        sleep(1000);
//        robot.move(0, TILE_LENGTH);
//        sleep(1000);
        Trajectory test = robot.RoadRunnerDT.trajectoryBuilder()
                .splineTo(new Pose2d(30, 30, 0)) // add heading interpolator if a different driving/heading change is nessescary
                .build();

        robot.RoadRunnerDT.followTrajectorySync(test);


        // turns, gyro sensor
        while (opModeIsActive()) {
            telemetry.addData("gyro reading", robot.gyro.measure());
            telemetry.update();
        }


    }
}
