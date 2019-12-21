package org.firstinspires.ftc.teamcode.hardware.general;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.type.Device;
import org.firstinspires.ftc.teamcode.hardware.type.Input;
import org.firstinspires.ftc.teamcode.hardware.type.Output;

// rev servo
public class ServoM extends Device<Servo> implements Input<Device.Percent>, Output<Device.Percent> {

	// initialize servo
	public ServoM(String name, HardwareMap map) {super(map.servo.get(name));}

	// sense position
	@Override public Device.Percent measure() {return new Percent(device.getPosition());}

	// start motion
	@Override public void start(Device.Percent motion) {device.setPosition(motion.value);}
}