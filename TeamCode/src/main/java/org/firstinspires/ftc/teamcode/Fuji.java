package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardware.general.Color;
import org.firstinspires.ftc.teamcode.hardware.general.Distance;
import org.firstinspires.ftc.teamcode.hardware.general.Gyro;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.general.ServoM;
import org.firstinspires.ftc.teamcode.hardware.type.Device;
import org.firstinspires.ftc.teamcode.hardware.type.Input;

public final class Fuji {
    final private HardwareMap hardwaremap;
    final public Gyro gyro;
    final public DriveTrain drivetrain;
    final public Motor rfMotor;
    final public Motor lfMotor;
    final public Motor rbMotor;
    final public Motor lbMotor;
    final public Motor spin1;
    final public Motor spin2;
    final public Color sensorColor;
    final public Distance sensorDistance;

    //this is the constructor, pass in a hardware map to create a fuji robot instance
    public Fuji(HardwareMap map) {
        this.hardwaremap = map;
        gyro = new Gyro("imu", this.hardwaremap);
        rfMotor = new Motor("rf", 1, this.hardwaremap);
        lfMotor = new Motor("lf", 1, this.hardwaremap);
        rbMotor = new Motor("rb", 1, this.hardwaremap);
        lbMotor = new Motor("lb", 1, this.hardwaremap);
        spin1 = new Motor("spin1", 1, this.hardwaremap);
        spin2 = new Motor("spin2", 1, this.hardwaremap);
        sensorColor = new Color("color", this.hardwaremap);
        sensorDistance = new Distance("distance", this.hardwaremap);
        drivetrain = new DriveTrain(rfMotor, rbMotor, lfMotor, lbMotor);
    }

    public void drive(double side, double forward, double turn) {
        drivetrain.start(new DriveTrain.Vector(
                new Device.Range(side), // sideways
                new Device.Range(forward), // forwards
                new Device.Range(turn) // turn
        ).speeds());
    }
    //used to go upto something with the distance sensor, or until it senses nothing in front if far is true
    public void Upto(double distance, double side, double forward, double turn, boolean Far) {
        this.drive(side, forward, turn);
        if (Far) {
            while (sensorDistance.measure() < distance) {
                //telemetry logging or something
            }
        } else {
            while (sensorDistance.measure() > distance) {
                //telemetry logging or something
            }
        }
        this.drive(0,0,0);

    }

    public void GryoTurnTo(double orientation, boolean right){
        double current = this.gyro.measure().value; // value bewteen 0 and 1 around the circle
        double tolerance = 0.02; // 8 degress tolerance (move somewhere else later)
        if (right) {
            //fill in later
        } else {
            //fill in later
        }
    }
    /*
    Rest of this class is for high level robot functions, no logic.
    Put logic in Teleop and autonomous
    */
}