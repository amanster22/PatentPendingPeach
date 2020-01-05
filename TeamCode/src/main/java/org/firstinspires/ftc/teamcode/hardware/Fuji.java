package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.type.Device;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.general.Gyro;
import org.firstinspires.ftc.teamcode.hardware.general.Color;
import org.firstinspires.ftc.teamcode.hardware.general.Distance;

// robot
public final class Fuji {

    // OpMode members
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    public final DriveTrain driveTrain;
    public final Motor lift;
    //    public final Motor spin1;
//    public final Motor spin2;
    public final Gyro gyro;
    public final Color tape;
    public final Color stone;
    public final Distance distance;
    // robot constants
    private static final double gyroAdjust = 4; // turn at full speed when off by 45 degrees

    // initialize robot
    public Fuji(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        Motor rf = new Motor("rf", 1120, 1, 3, hardwareMap);
        Motor rb = new Motor("rb", 1120, 1, 3, hardwareMap);
        Motor lf = new Motor("lf", 1120, 1, 3, hardwareMap);
        Motor lb = new Motor("lb", 1120, 1, 3, hardwareMap);
        driveTrain = new DriveTrain(rf, rb, lf, lb);
//        spin1 = new Motor("spin1", 1120, hardwareMap);
//        spin2 = new Motor("spin2", 1120, hardwareMap);
        lift = new Motor("lift", 1120, hardwareMap);
        gyro = new Gyro("imu", hardwareMap);
        tape = new Color("colorDown", hardwareMap);
        stone = new Color("colorFor", hardwareMap);
        distance = new Distance("distance", hardwareMap);
    }

    // turn with gyro, speed should be positive
    public void turn(double orientation, double speed) {
        while (Math.abs(headingError(orientation)) > 0.01) {
            if (headingError(orientation) > 0) {
                speed = -speed;
            }
            telemetry.addData("Gyro Sensor", "turning");
            telemetry.addData("Angle", gyro.measure());
            telemetry.update();
            driveTrain.start(new DriveTrain.Vector(0, 0, speed).speeds());
        }
        driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
    }

    // drive with distance, ORIENTATION IS ONLY FOR KEEPING A HEADING, NOT FOR GOING TO A SPECIFIC HEADING
    public void drive(double hori, double vert, double target, double orientation, boolean far) {
        while (far ? distance.measure() < target : distance.measure() > target) {
            telemetry.addData("Distance Sensor", "driving");
            telemetry.addData("Distance", distance.measure());
            telemetry.update();
            driveTrain.start(new DriveTrain.Vector(hori, vert, (-headingError(orientation)) * gyroAdjust).speeds());
        }
        driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
    }

    // drive with encoders
    public void move(double hori, double vert) {
        telemetry.addData("Encoders", "moving");
        telemetry.addData("Horizontal", hori);
        telemetry.addData("Vertical", vert);
        telemetry.update();
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double length = Math.sqrt(vert * vert + hori * hori);
        double angle = Math.atan2(vert, hori);
        if (length == 0) {
            return;
        }

        double lf = length * Math.cos(angle - Math.PI * 0.25);
        double lb = length * Math.cos(angle - Math.PI * 0.75);
        double rf = length * Math.cos(angle - Math.PI * 1.75);
        double rb = length * Math.cos(angle - Math.PI * 1.25);

        driveTrain.setTarget(new DriveTrain.Square<Double>(rf, rb, lf, lb));

        driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveTrain.start(new DriveTrain.Square<Double>(1.0, 1.0, 1.0, 1.0));

        //do gyro adjustment in here |
        //                           v
        while (driveTrain.isBusy()) {
        }

        driveTrain.start(new DriveTrain.Square<Double>(0.0, 0.0, 0.0, 0.0));

        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // run dual linear slide
    public void spin(double speed) {
        telemetry.addData("Intake", "started");
        telemetry.addData("Speed", speed);
        telemetry.update();
//        spin1.start(1 * speed);
//        spin2.start(-1 * speed);
    }

    // if the color sensor sees a skystone
    public boolean isSkystone() {
        boolean block;
        telemetry.addData("Color Sensor", "sensing block");
        Color.HSV color = stone.measure();
        telemetry.addData("Hue", color.h());
        block = color.h() >= 60;
        telemetry.addData("Block", block);
        telemetry.update();
        return block;
    }

    //there is a reason to have this function william, don't delete it
    public double headingError(double orientation) {
        double rawError = orientation - gyro.measure();

        if (rawError < -0.5) {
            rawError += 1;
        }
        if (rawError > 0.5) {
            rawError -= 1;
        }
        return rawError;
    }
}