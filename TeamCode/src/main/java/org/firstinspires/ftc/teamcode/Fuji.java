package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.hardware.general.Color;
import org.firstinspires.ftc.teamcode.hardware.general.Distance;
import org.firstinspires.ftc.teamcode.hardware.general.Gyro;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.general.ServoM;
import org.firstinspires.ftc.teamcode.hardware.type.Device;

public final class Fuji {
    final private HardwareMap hardwaremap;
    final public Gyro gyro;
    final public DriveTrain drive;
    final public Motor rfMotor;
    final public Motor lfMotor;
    final public Motor rbMotor;
    final public Motor lbMotor;
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
        sensorColor = new Color("color", this.hardwaremap);
        sensorDistance = new Distance("distance", this.hardwaremap);
        drive = new DriveTrain(rfMotor, rbMotor, lfMotor, lbMotor);
    }
    /*
    Rest of this class is for high level robot functions, no logic.
    Put logic in Teleop and autonomous
    */
}