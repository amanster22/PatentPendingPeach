package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

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

	public void setTarget(Square<Double> inches) {
		rf.setTarget(inches.rf);
		rb.setTarget(inches.rb);
		lf.setTarget(inches.lf);
		lb.setTarget(inches.lb);
	}

	public void setMode(DcMotor.RunMode mode) {
		rf.setMode(mode);
		rb.setMode(mode);
		lf.setMode(mode);
		lb.setMode(mode);
	}

	// vector for x, y, and turn
	public static class Vector<T extends Device.DevDouble> {

		// directional speeds
		public T hori;
		public T vert;
		public T turn;

		// initialize speeds
		public Vector(T hori, T vert, T turn) {
			this.hori = hori;
			this.vert = vert;
			this.turn = turn;
		}

		// get wheel speeds
		public Square<Device.Range> speeds(double) {
			return new Square<Device.Range>(
				new Device.Range((- hori.value - vert.value - turn.value) / sum),
				new Device.Range((+ hori.value - vert.value - turn.value) / sum),
				new Device.Range((- hori.value + vert.value - turn.value) / sum),
				new Device.Range((+ hori.value + vert.value - turn.value) / sum));
		}
	}

	public static class Speeds extends Vector<Device.Range> {

		public Speeds(Device.Range hori, Device.Range vert, Device.Range turn) {
			sum = 0;
			if (hori.value != 0 || vert.value != 0) {sum += 2;}
			if (turn.value != 0) {sum += 1;}
			if (sum == 0) {sum = 3;}
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