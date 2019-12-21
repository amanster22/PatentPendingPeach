package org.firstinspires.ftc.teamcode.hardware.type;

// rev component
public class Device<T> {

	// component object
	protected final T device;

	// initialize component
	public Device(T device) {this.device = device;}

	// double from -1 to 1
	public static class Range {

		// value
		public final double value;

		// initialize value
		public Range(double value) {
			if (value < -1 || value > 1) {throw new IllegalArgumentException("value out of range");}
			this.value = value;
		}
	}

	// double from 0 to 1
	public static class Percent extends Range {public Percent(double value) {super(value < 0 ? -2 : value);}}
}