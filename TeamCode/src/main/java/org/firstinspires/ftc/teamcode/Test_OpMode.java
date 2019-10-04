package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name= "test")
@Disabled

public class Test_OpMode extends OpMode {

    public DcMotor rfMotor;
    public DcMotor lfMotor;
    public DcMotor rbMotor;
    public DcMotor lbMotor;

    public void init(){
        rfMotor = hardwareMap.dcMotor.get("rf");
        lfMotor = hardwareMap.dcMotor.get("lf");
        rbMotor = hardwareMap.dcMotor.get("rb");
        lbMotor = hardwareMap.dcMotor.get("lb");
    }

    public void loop(){
        rfMotor.setPower(gamepad1.right_stick_y);
        lfMotor.setPower(gamepad1.left_stick_y);

        telemetry.addData("rf_motorpower", rfMotor.getPower());
        telemetry.addData("lf_motorpower", lfMotor.getPower());

        telemetry.update();
    }

}
