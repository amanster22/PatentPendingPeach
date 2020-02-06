package org.firstinspires.ftc.teamcode.roadrun;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "Spline Test", group = "drive")
public class SplineTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDriveBase drive = new RevSampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;
        Trajectory test = drive.trajectoryBuilder()
                .splineTo(new Pose2d(30, 30, 0)) // add heading interpolator if a different driving/heading change is nessescary
                .build();
        drive.followTrajectorySync(test);

        sleep(2000);

        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .reverse()
                        .splineTo(new Pose2d(0, 0, 180))
                        .build()
        );
    }
}
