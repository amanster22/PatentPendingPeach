package org.firstinspires.ftc.teamcode.roadrun;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
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

        /**
         * rr coordinates are rotated 90 degress ccw, and so its angles and coordinates are based off this different coordinate system
         * so, you can either work in this weird coordinate environment or create a function to map our coordinates to rr coordinates
         * and our angles to rr angles. This just tests the spline function and subsequent turning
         **/
        if (isStopRequested()) return;
        Trajectory test = drive.trajectoryBuilder()
                .splineToSplineHeading(new Pose2d(30, 30, 0), 0)
                .build();
        drive.followTrajectorySync(test);

        sleep(2000);

        drive.followTrajectorySync(
                drive.trajectoryBuilder()
                        .splineToSplineHeading(new Pose2d(0, 0, 180), 0)
                        .build()
        );
    }
}
