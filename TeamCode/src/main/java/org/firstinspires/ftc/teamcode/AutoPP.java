package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/*
//registering opMode
bumper-Boolean=tRUE OR fALSE
trigger-Float=【0，1】
sticks-Float=[-1,1]
a,b,x,y-Boolean= True or False
 */
@Autonomous(name = "Patent Pending Auto")
@Disabled
public class AutoPP extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    //clear motor objects
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private DcMotor motorArm;

    private Servo servoLeft;
    private Servo servoRight;
    double clawOffset = 0;                       // Servo mid position
    final double CLAW_SPEED = 0.02;                   // sets rate to move servo

    final double MID_SERVO = 0.5;
    static final double FORWARD_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    //origional 0.45 and -0.45
    final double ARM_UP_POWER = 0.45;
    // final double ARM_DOWN_POWER = -0.45;
    final double ARM_DOWN_POWER = -0.45;

    @Override
    public void runOpMode() throws InterruptedException {
        motorLeft = hardwareMap.dcMotor.get("mLeft");
        motorRight = hardwareMap.dcMotor.get("mRight");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        motorArm = hardwareMap.dcMotor.get("mArm");

        servoLeft = hardwareMap.servo.get("sLeft");
        servoRight = hardwareMap.servo.get("sRight");
//      any code put before a wait will be run when the init button is pressedl. https://www.youtube.com/watch?v=OT_PGYIFBGE
        telemetry.addData("PP:", "Ready");    //
        telemetry.update();

        waitForStart();

        // Step 1:  Drive forward for 3 seconds
        motorLeft.setPower(FORWARD_SPEED);
        motorRight.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.1)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        sleep(3000);
        motorLeft.setPower(-FORWARD_SPEED);
        motorRight.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //drop the team marker
        motorLeft.setPower(0);
        motorRight.setPower(0);
        servoLeft.setPosition(0.5);
        servoRight.setPosition(0.5);
        sleep(2000);
        //align the robot to wall
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setPower(-0.25);
        motorRight.setPower(-0.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.24)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        sleep(2000);
        motorLeft.setPower(0);
        motorRight.setPower(0);
        //Move robot to crater
        runtime.reset();
//       motorRight.setDirection(DcMotor.Direction.REVERSE);
        motorArm.setPower(0.3);
        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }



        motorArm.setPower(0);


        sleep(1500);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setPower(FORWARD_SPEED);
        motorRight.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.3)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        sleep(1000);


        motorLeft.setPower(0);
        motorRight.setPower(0);

        sleep(1500);
      /*  motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setPower(FORWARD_SPEED);
        motorRight.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }*/
        sleep(1000);


        idle();
    }
}



