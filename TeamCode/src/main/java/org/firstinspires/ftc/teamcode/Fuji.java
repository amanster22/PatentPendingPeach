package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.general.Gyro;
import org.firstinspires.ftc.teamcode.hardware.DriveTrain;
import org.firstinspires.ftc.teamcode.hardware.general.Motor;

public final class Fuji {
    final private HardwareMap hardwaremap;
    final private Gyro gyro;

    //this is the constructor, pass in a hardware map to create a fuji robot instance
    public Fuji(HardwareMap map) {
        this.hardwaremap = map;
        gyro = new Gyro("imu", this.hardwaremap);

    }

}