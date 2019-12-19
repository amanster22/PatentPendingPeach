package teamcode.hardware;

import teamcode.hardware.type.Device;
import teamcode.hardware.type.Input;
import teamcode.hardware.type.Output;
import teamcode.hardware.general.Motor;

import com.qualcomm.robotcore.hardware.HardwareMap;

// quad-omni drive train
public class QuadOmni implements Input<QuadOmni.Square<Double>>, Output<QuadOmni.Square<Device.Range>> {

	// motors
	private final Motor rf;
	private final Motor rb;
	private final Motor lf;
	private final Motor lb;

	// initialize drive train
	public QuadOmni(Motor rf, Motor rb, Motor lf, Motor lb) {
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
		public Device.Range vert;
		public Device.Range hori;
		public Device.Range turn;

		// initialize speeds
		public Vector(Device.Range vert, Device.Range hori, Device.Range turn) {
			this.vert = vert;
			this.hori = hori;
			this.turn = turn;
		}

		// get wheel speeds
		public Square<Device.Range> speeds() {
			return new Square<Device.Range>(
				new Device.Range((+ vert.value + hori.value + turn.value) / 3),
				new Device.Range((+ vert.value - hori.value + turn.value) / 3),
				new Device.Range((- vert.value + hori.value + turn.value) / 3),
				new Device.Range((- vert.value - hori.value + turn.value) / 3));
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
