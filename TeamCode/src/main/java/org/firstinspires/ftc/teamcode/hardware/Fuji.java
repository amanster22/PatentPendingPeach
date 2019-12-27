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
//    public final Motor spin1;
//    public final Motor spin2;
    public final Gyro gyro;
    public final Color color;
    public final Distance distance;
    private final double tolerance = 0.02; // 8 degrees

    // initialize robot
    public Fuji(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

        telemetry.addData("init", "start");
        telemetry.update();

        Motor rf = new Motor("rf", 1440, 1, 3, hardwareMap);
        Motor rb = new Motor("rb", 1440, 1, 3, hardwareMap);
        Motor lf = new Motor("lf", 1440, 1, 3, hardwareMap);
        Motor lb = new Motor("lb", 1440, 1, 3, hardwareMap);
        driveTrain = new DriveTrain(rf, rb, lf, lb);
//        spin1 = new Motor("spin1", 1440, hardwareMap);
//        spin2 = new Motor("spin2", 1440, hardwareMap);
        gyro = new Gyro("imu", hardwareMap);
        color = new Color("color", hardwareMap);
        distance = new Distance("distance", hardwareMap);

        telemetry.addData("init", "start");
        telemetry.update();
    }

    // drive
    public void drive(double hori, double vert, double turn) {
        driveTrain.start(new DriveTrain.Vector(
                new Device.Range(hori),
                new Device.Range(vert),
                new Device.Range(turn)
        ).speeds());
    }

    //used to go upto something with the distance sensor, or until it senses nothing in front if far is true
    //gyro stabilize in the while loop
    public void Upto(double target, double orientation, double side, double forward, boolean Far) {
        this.drive(side, forward, 0);
        if (Far) {
            while (distance.measure() < target) {
                //telemetry logging or something
                double error = this.getError(orientation);
                if  (Math.abs(error) > tolerance) {
                    error = this.getError(orientation);
                    double speed = error * 2; //error will be between -0.5 and 0.5, so this scales that to motor powers
                    this.drive(side, forward, -speed);
                }
            }
        } else {
            while (distance.measure() > target) {
                double error = this.getError(orientation);
                if  (Math.abs(error) > tolerance) {
                    error = this.getError(orientation);
                    double speed = error * 2; //error will be between -0.5 and 0.5, so this scales that to motor powers
                    // by keeping the speed proportional to the error, the robot will be less likely
                    // to over-correct
                    this.drive(side, forward, -speed);
                }
            }
        }
        this.drive(0,0,0);

    }
    //used to turn accurately with gyro
    public void GryoTurnTo(double orientation){
        double error = this.getError(orientation);
        telemetry.addData("error:", error);
        telemetry.update();
        while (Math.abs(error) > tolerance) {
            error = this.getError(orientation);
            telemetry.addData("error:", error);
            telemetry.update();
            double speed = error; //error will be between -0.5 and 0.5, so this scales that to motor powers
            this.drive(0, 0, -speed);
        }
    }

    //used to move and turn robot using encoders, may not be the most accurate so consistent testing is necessary
    public void EncoderMove(double hori, double vert, double turn) {
        Device.Range startWheel = new Device.Range(1);
        Device.Range stopWheel = new Device.Range(0);
        DriveTrain.Square<Device.Range> start = new DriveTrain.Square<Device.Range>(startWheel, startWheel, startWheel, startWheel);
        DriveTrain.Square<Device.Range> stop = new DriveTrain.Square<Device.Range>(stopWheel, stopWheel, stopWheel, stopWheel);
        DriveTrain.Square<Double> targets = new DriveTrain.Vector(new Device.Range(hori), new Device.Range(vert), new Device.Range(turn)).speeds();
        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveTrain.setTarget(targets);
        driveTrain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveTrain.start(start);
    }

    public double getError(double targetAngle) {

        double robotError;
        double current = gyro.measure().value;
        // calculate error in -179 to +180 range
        // value bewteen 0 and 1 around the circle
        robotError = targetAngle - current;
        if (robotError > 0.5) robotError -= 1;
        if (robotError <= -0.5) robotError += 1;
        return robotError;
    }
    /*
    Rest of this class is for high level robot functions, no logic.
    Put logic in Teleop and autonomous
    */
}