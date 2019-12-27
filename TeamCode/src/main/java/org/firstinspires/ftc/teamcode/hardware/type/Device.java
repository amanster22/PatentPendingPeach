package org.firstinspires.ftc.teamcode.hardware.type;

// rev component
public class Device<T> {

	// component object
	protected T device;

	// initialize component
	public Device(T device) {this.device = device;}

	// double
	public static class DevDouble {

		// value
		public final double value;

		// initialize value
		public DevDouble(double value) {this.value = value;}

		// create value
		public static DevDouble create(double value) {return new DevDouble(value);}
	}

	// double from -1 to 1
	public static class Range extends DevDouble {

		// initialize value
		public Range(double value) {
			super(value);
			if (value < -1 || value > 1) {throw new IllegalArgumentException("value out of range");}
		}

		// create value
		@Override
		public static Range create(double value) {return new Range(value);}
	}

	// double from 0 to 1
	public static class Percent extends Range {

		// initialize value
		public Percent(double value) {super(value < 0 ? -2 : value);}

		// create value
		@Override
		public static Percent create(double value) {return new Percent(value);}
	}
}