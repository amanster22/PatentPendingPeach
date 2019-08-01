package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name="NectarineTimeAuto", group="Pushbot")
//@Disabled
public class AmanAuto extends LinearOpMode {
    private ElapsedTime     runtime = new ElapsedTime();
    private DcMotor LFMotor;
    private DcMotor LBMotor;
    private DcMotor RFMotor;
    private DcMotor RBMotor;
    private DcMotor HTrain;



    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() {

        LFMotor = hardwareMap.dcMotor.get("lf");
        LBMotor = hardwareMap.dcMotor.get("lb");
        RFMotor = hardwareMap.dcMotor.get("rf");
        RBMotor = hardwareMap.dcMotor.get("rb");
        HTrain = hardwareMap.dcMotor.get("m");
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 3 seconds
        LBMotor.setPower(-0.5);
        RBMotor.setPower(0.5);
        LFMotor.setPower(-0.5);
        RFMotor.setPower(0.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // Step 2:  Spin right for 1.3 seconds
        LBMotor.setPower(0);
        RBMotor.setPower(0);
        LFMotor.setPower(0);
        RFMotor.setPower(0);
        HTrain.setPower(0.6);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
//
//        // Step 3:  Drive Backwards for 1 Second
        HTrain.setPower(-0.6);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }
}
