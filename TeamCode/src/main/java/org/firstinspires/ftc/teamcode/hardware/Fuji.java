package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FujiAutonomous;
import org.firstinspires.ftc.teamcode.hardware.general.ServoM;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.general.Gyro;
import org.firstinspires.ftc.teamcode.hardware.general.Color;
import org.firstinspires.ftc.teamcode.hardware.general.Distance;

// robot
public final class Fuji {

    // OpMode members
    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;
    private final FujiAutonomous opMode;
    public final DriveTrain driveTrain;
    public final Motor lift;
    public final ServoM dropStone;
    public final ServoM pinch;
    public final ServoM hook1;
    public final ServoM hook2;
    public final Gyro gyro;
    public final Color stone;
    public final Distance distance;
    boolean autoSoundFound = false;
    int soundAutonomous1;
    int soundAutonomous2;
    int soundAutonomous3;
    int soundAutonomous4;
    // robot constants
    private static final double gyroAdjust = 4;

    // initialize robot
    public Fuji(HardwareMap hardwareMap, Telemetry telemetry, FujiAutonomous opMode) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.opMode = opMode;

        Motor rf = new Motor("rf", 1120, 1, 2.95, hardwareMap);
        Motor rb = new Motor("rb", 1120, 1, 2.95, hardwareMap);
        Motor lf = new Motor("lf", 1120, 1, 2.95, hardwareMap);
        Motor lb = new Motor("lb", 1120, 1, 2.95, hardwareMap);
        driveTrain = new DriveTrain(rf, rb, lf, lb);

//        soundAutonomous1 = hardwareMap.appContext.getResources().getIdentifier("fujispeakinga", "raw", hardwareMap.appContext.getPackageName());
//        soundAutonomous2 = hardwareMap.appContext.getResources().getIdentifier("fujispeakingb", "raw", hardwareMap.appContext.getPackageName());
//        soundAutonomous3 = hardwareMap.appContext.getResources().getIdentifier("fujispeakingc", "raw", hardwareMap.appContext.getPackageName());
//        soundAutonomous4 = hardwareMap.appContext.getResources().getIdentifier("fujispeakingd", "raw", hardwareMap.appContext.getPackageName());
//
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, soundAutonomous1);
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, soundAutonomous2);
//        SoundPlayer.getInstance().preload(hardwareMap.appContext, soundAutonomous3);
//        autoSoundFound = SoundPlayer.getInstance().preload(hardwareMap.appContext, soundAutonomous4);
//        telemetry.addData("auto resource", autoSoundFound ? "Found" : "NOT found\n Add autonomous.wav to /src/main/res/raw");
//        telemetry.update();


        //CHECK THESE VALUES **************************************************
        lift = new Motor("lift", 1120, 1, 2, hardwareMap);
        pinch = new ServoM("pinch", hardwareMap);
        hook1 = new ServoM("hook1", hardwareMap);
        hook2 = new ServoM("hook2", hardwareMap);
        gyro = new Gyro("imu", hardwareMap);
        dropStone = new ServoM("drop", hardwareMap);
//        tape = new Color("colorDown", hardwareMap);
        stone = new Color("colorFor", hardwareMap);
        distance = new Distance("distance", hardwareMap);
    }

    // turn with gyro, speed should be positive
    public void turn(double orientation) {
        while (Math.abs(headingError(orientation)) > 0.02 && opMode.opModeIsActive()) {
            telemetry.addData("Gyro Sensor", "turning");
            telemetry.addData("Angle", gyro.measure());
            telemetry.update();
            double turn = Range.clip(-headingError(orientation) * gyroAdjust, -1, 1);
            driveTrain.start(new DriveTrain.Vector(0, 0, turn).speeds());
        }
        driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
    }

    // drive with distance, ORIENTATION IS ONLY FOR KEEPING A HEADING, NOT FOR GOING TO A HEADING
    public void drive(double hori, double vert, double target, double orientation, boolean far) {
        while (far ? distance.measure() < target : distance.measure() > target && opMode.opModeIsActive()) {
            telemetry.addData("Distance Sensor", "driving");
            telemetry.addData("Distance", distance.measure());
            telemetry.addData("head error:", -headingError(orientation));
            telemetry.update();
            double turn = Range.clip(-headingError(orientation) * gyroAdjust, -1, 1);
            driveTrain.start(new DriveTrain.Vector(hori, vert, turn).speeds());
        }
        driveTrain.start(new DriveTrain.Vector(0, 0, 0).speeds());
    }

    // drive with encoders
    public void move(double hori, double vert) {
        telemetry.addData("Encoders", "moving");
        telemetry.addData("Horizontal", hori);
        telemetry.addData("Vertical", vert);
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*
        double length = Math.hypot(vert, hori);
        double angle = Math.atan2(vert, hori) - Math.PI/4;
        if (length == 0) {
            return;
        }
        telemetry.addData("operation", "length: %.8f, angle: %.8f", length, angle + Math.PI/4);

        double lf = length * -Math.cos(angle);
        double lb = length * -Math.sin(angle);
        double rf = length * Math.sin(angle);
        double rb = length * Math.cos(angle);

        telemetry.addData("drives", "lf: %.8f, lb: %.8f, rf: %.8f, rb: %.8f", lf, lb, rf, rb);
        telemetry.update();
        driveTrain.setTarget(new DriveTrain.Square<Double>(rf, rb, lf, lb));
        */


        driveTrain.setTarget(new DriveTrain.Direction(hori, -vert, 0).speeds());
        driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrain.start(new DriveTrain.Square<Double>(0.8, 0.8, 0.8, 0.8));

        // do gyro adjustment in here |
        //                            v
        while (driveTrain.isBusy() && opMode.opModeIsActive()) {}
        //turn (-headingError(angle)) * gyroAdjust)
        driveTrain.start(new DriveTrain.Square<Double>(0.0, 0.0, 0.0, 0.0));
        driveTrain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // if the color sensor sees a skystone
    public boolean isSkystone() {
        boolean block;
        telemetry.addData("Color Sensor", "sensing block");
        Color.HSV color = stone.measure();
        telemetry.addData("Hue", color.h());
        block = color.h() >= 0.1667; // 60 hue value divided by a hue range of 360
        telemetry.addData("Block", block);
        telemetry.update();
        return block;
    }

    // get current offset from target orientation
    public double headingError(double orientation) {
        double rawError = orientation - gyro.measure();
        if (rawError < -0.5) {rawError += 1;}
        if (rawError > 0.5) {rawError -= 1;}
        return rawError;
    }

    public void playAutoSound(int index) {
        if (autoSoundFound) {
            if (index == 1) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundAutonomous1);
            }
            if (index == 2) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundAutonomous2);
            }
            if (index == 3) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundAutonomous3);
            }
            if (index == 4) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundAutonomous4);
            }
            telemetry.addData("Sound", "auto");
            telemetry.update();
        }
    }

    public void hook(double target) {
        hook1.start(target);
        hook2.start(target);
    }
}