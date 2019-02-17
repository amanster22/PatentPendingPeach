package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


/*
//registering opMode
bumper-Boolean=tRUE OR fALSE
trigger-Float=【0，1】
sticks-Float=[-1,1]
a,b,x,y-Boolean= True or False
 */
@TeleOp(name = "Patent Pending 2")
public class TeleOpPP2 extends LinearOpMode
{
    //clear motor objects
    private DcMotor motorLeft;
    private DcMotor motorRight;

    private DcMotor motorArm;
    private DcMotor motorRetract;
    private DcMotor motorExtend;
    private DcMotor motorAbjure;

    private Servo servoLeft;
    private Servo servoRight;
    double clawOffset = 0;                       // Servo mid position
    final double CLAW_SPEED = 0.02;                   // sets rate to move servo

    final double MID_SERVO = 0.5;

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
        motorRetract=hardwareMap.dcMotor.get("mRetract");
        servoLeft = hardwareMap.servo.get("sLeft");
        servoRight = hardwareMap.servo.get("sRight");

        motorExtend=hardwareMap.dcMotor.get("mExtend");
        motorAbjure=hardwareMap.dcMotor.get("mAbjure");
//      any code put before a wait will be run when the init button is pressedl. https://www.youtube.com/watch?v=OT_PGYIFBGE
        telemetry.addData("PP:", "Ready");    //
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //values for gamepad range from -1 to 1
            motorLeft.setPower(-gamepad2.left_stick_y);
            motorRight.setPower(-gamepad2.right_stick_y);

            motorLeft.setPower(gamepad1.right_stick_y);
            motorRight.setPower(gamepad1.left_stick_y);



       /*     if (gamepad1.a) {
                //servo has values from 0-100 degrees. Hence 0.8 is 80 degrees
                servoLeft.setPosition(0.8);

                servoRight.setPosition(-0.8);


            }
*/


            if (gamepad1.x)
                motorArm.setPower(ARM_UP_POWER);
            else if (gamepad1.y)
                motorArm.setPower(ARM_DOWN_POWER);
            else
                motorArm.setPower(0.0);

            if (gamepad2.x)
                motorRetract.setPower(ARM_UP_POWER);
            else if (gamepad2.y)
                motorRetract.setPower(ARM_DOWN_POWER);
            else
                motorRetract.setPower(0.0);

            if (gamepad1.dpad_up)
            {
                motorExtend.setPower(0.6);
                motorAbjure.setPower(-0.6);}
            else if(gamepad1.dpad_down) {
                motorExtend.setPower(-0.6);
                motorAbjure.setPower(0.6);
            }
            else {
                motorExtend.setPower(0);
                motorAbjure.setPower(0);
            }


            // Send telemetry message to signify robot running;
//            telemetry.addData("claw", "Offset = %.2f", clawOffset);
//            telemetry.addData("left", "%.2f", left);
//            telemetry.addData("right", "%.2f", right);
//            telemetry.update();

            // Use gamepad left & right Bumpers to open and close the claw

//            if (gamepad1.a)
//                clawOffset += CLAW_SPEED;
//            else if (gamepad1.b)
//                clawOffset -= CLAW_SPEED;
//
//            // Move both servos to new position.  Assume servos are mirror image of each other.
//            clawOffset = Range.clip(clawOffset, -0.5, 0.5);
//            servoRight.setPosition(MID_SERVO + clawOffset);
//            servoLeft.setPosition(MID_SERVO - clawOffset);
//            //gives hardware time to catch up
//            idle();
        }
    }
}


