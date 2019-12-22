package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.hardware.general.Motor;
import org.firstinspires.ftc.teamcode.hardware.type.Device;
import org.firstinspires.ftc.teamcode.hardware.type.Input;
import org.firstinspires.ftc.teamcode.hardware.type.Output;

// quad-omni drive train
public class DriveTrain implements Input<DriveTrain.Square<Double>>, Output<DriveTrain.Square<Device.Range>> {

	// motors
	private final Motor rf;
	private final Motor rb;
	private final Motor lf;
	private final Motor lb;

	// initialize drive train
	public DriveTrain(Motor rf, Motor rb, Motor lf, Motor lb) {
		this.rf = rf;
		this.rb = rb;
		this.lf = lf;
		this.lb = lb;
	}

	// sense position
	@Override public Square<Double> measure() {return new Square<Double>(rf.measure(), rb.measure(), lf.measure(), lb.measure());}

	// start motion
	@Override public void start(Square<Device.Range> motion) {
		rf.start(motion.rf);
		rb.start(motion.rb);
		lf.start(motion.lf);
		lb.start(motion.lb);
	}

	// vector for x, y, and turn
	public static class Vector {

		// directional speeds
		public Device.Range hori;
		public Device.Range vert;
		public Device.Range turn;
		public double sum;

		// initialize speeds
		public Vector(Device.Range hori, Device.Range vert, Device.Range turn) {
			this.hori = hori;
			this.vert = vert;
			this.turn = turn;
			this.sum = this.hori.value + this.vert.value + this.turn.value;
			if (this.sum == 0) {
				this.sum = 3;
			}
		}

		// get wheel speeds
		public Square<Device.Range> speeds() {
			return new Square<Device.Range>(
				new Device.Range((- hori.value - vert.value - turn.value) / this.sum),
				new Device.Range((+ hori.value - vert.value - turn.value) / this.sum),
				new Device.Range((- hori.value + vert.value - turn.value) / this.sum),
				new Device.Range((+ hori.value + vert.value - turn.value) / this.sum));
		}
	}

	// set of data on four objects arranged in a square
	public static class Square<T> {

		// square
		public T rf;
		public T rb;
		public T lf;
		public T lb;

		// initialize square
		public Square(T rf, T rb, T lf, T lb) {
			this.rf = rf;
			this.rb = rb;
			this.lf = lf;
			this.lb = lb;
		}
	}
}