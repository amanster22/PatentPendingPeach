package org.firstinspires.ftc.teamcode.hardware.general;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hardware.type.Device;
import org.firstinspires.ftc.teamcode.hardware.type.Input;

// rev color sensor
public class Color extends Device<ColorSensor> implements Input<Color.HSV> {

	// initialize sensor
	public Color(String name, HardwareMap map) {super(map.get(ColorSensor.class, name));}

	// sense color
	@Override public HSV measure() {
		float[] hsv = new float[3];
		android.graphics.Color.RGBToHSV(
			255 * device.red(),
			255 * device.green(),
			255 * device.blue(),
		hsv);
		return new HSV(new Percent((double)hsv[0] / 360), new Percent((double)hsv[1]), new Percent((double)hsv[2]));
	}

	// color value stored in hsv
	public static class HSV {

		// hsv values
		public Percent h;
		public Percent s;
		public Percent v;

		// initialize color
		public HSV(Percent h, Percent s, Percent v) {
			this.h = h;
			this.s = s;
			this.v = v;
		}
	}
}