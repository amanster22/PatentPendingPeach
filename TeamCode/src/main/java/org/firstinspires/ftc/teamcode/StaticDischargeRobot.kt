package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.util.Range

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.hardware.DriveTrain
import org.firstinspires.ftc.teamcode.hardware.general.ServoM
import org.firstinspires.ftc.teamcode.hardware.general.Motor
import org.firstinspires.ftc.teamcode.hardware.general.Gyro
import org.firstinspires.ftc.teamcode.hardware.general.Color
import org.firstinspires.ftc.teamcode.hardware.general.Distance
import org.firstinspires.ftc.teamcode.roadrun.RevSampleMecanumDrive
import org.firstinspires.ftc.teamcode.roadrun.SampleMecanumDriveBase

// robot
class StaticDischargeRobot(private val hardwareMap: HardwareMap,
                           private val telemetry: Telemetry,
                           private val opMode: LinearOpMode) {


    val driveTrain: DriveTrain

    val gyro: Gyro
    val RoadRunnerDT: SampleMecanumDriveBase

    init {

        val rf = Motor("rf", 1120.0, 1.0, 2.95, hardwareMap)
        val rb = Motor("rb", 1120.0, 1.0, 2.95, hardwareMap)
        val lf = Motor("lf", 1120.0, 1.0, 2.95, hardwareMap)
        val lb = Motor("lb", 1120.0, 1.0, 2.95, hardwareMap)

        driveTrain = DriveTrain(rf, rb, lf, lb)

        RoadRunnerDT = RevSampleMecanumDrive(hardwareMap)

        //        lift = new Motor("lift", 1120, 1, 2, hardwareMap);
        //        pinch = new ServoM("pinch", hardwareMap);
        //        hook1 = new ServoM("hook1", hardwareMap);
        //        hook2 = new ServoM("hook2", hardwareMap);
        gyro = Gyro("imu", hardwareMap)
        //        dropStone = new ServoM("drop", hardwareMap);
        //        stone = new Color("colorFor", hardwareMap);
        //        distance = new Distance("distance", hardwareMap);
    }

    // turn with gyro, speed should be positive
    fun turn(orientation: Double) {
        while (Math.abs(headingError(orientation)) > 0.02 && opMode.opModeIsActive()) {
            telemetry.addData("Gyro Sensor", "turning")
            telemetry.addData("Angle", gyro.measure())
            telemetry.update()
            val turn = Range.clip(-headingError(orientation) * gyroAdjust, -1.0, 1.0)
            driveTrain.start(DriveTrain.Vector(0.0, 0.0, turn).speeds())
        }
        driveTrain.start(DriveTrain.Vector(0.0, 0.0, 0.0).speeds())
    }

    // drive with distance, ORIENTATION IS ONLY FOR KEEPING A HEADING, NOT FOR GOING TO A HEADING
    //    public void drive(double hori, double vert, double target, double orientation, boolean far) {
    //        while (far ? distance.measure() < target : distance.measure() > target && opMode.opModeIsActive()) {
    //            telemetry.addData("Distance Sensor", "driving");
    //            telemetry.addData("Distance", distance.measure());
    //            telemetry.addData("head error:", -headingError(orientation));
    //            telemetry.update();
    //            double turn = Range.clip(-headingError(orientation) * gyroAdjust, -1, 1);
    //            driveTrain.start(new DriveTrain.Vector(hori, vert, turn).speeds());
    //        }
    //        driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
    //    }

    // drive with encoders
    fun move(hori: Double, vert: Double) {
        telemetry.addData("Encoders", "moving")
        telemetry.addData("Horizontal", hori)
        telemetry.addData("Vertical", vert)
        telemetry.update()
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)


        driveTrain.setTarget(DriveTrain.Direction(hori, -vert, 0.0).speeds())
        driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION)

        driveTrain.start(DriveTrain.Square(0.8, 0.8, 0.8, 0.8))

        // do gyro adjustment in here |
        //                            v
        while (driveTrain.isBusy && opMode.opModeIsActive()) {
        }
        //turn (-headingError(angle)) * gyroAdjust)
        driveTrain.start(DriveTrain.Square(0.0, 0.0, 0.0, 0.0))
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
    }

    // get current offset from target orientation
    fun headingError(orientation: Double): Double {
        var rawError = orientation - gyro.measure()!!
        if (rawError < -0.5) {
            rawError += 1.0
        }
        if (rawError > 0.5) {
            rawError -= 1.0
        }
        return rawError
    }

    companion object {
        // robot constants
        private val gyroAdjust = 4.0
    }

    //    public void hook(double target) {
    //        hook1.start(target);
    //        hook2.start(target);
    //    }
}