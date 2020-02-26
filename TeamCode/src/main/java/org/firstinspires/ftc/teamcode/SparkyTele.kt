package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.FujiCode.Fuji
import org.firstinspires.ftc.teamcode.hardware.DriveTrain

@TeleOp(name = "FujiTele", group = "PatentPending")
class SparkyTele : OpMode() {
    // robot
    private lateinit var robot: StaticDischargeBot
    private var reverse = false
    // speeds
    private var driveSpeed = 1.0
    // field measurements
//    private val stoneHeight = 4.0
//    private val liftUpSpeed = 1.0
//    private val liftDownSpeed = 0.5

    private val maxAcceleration = 0.75
    private val timer = ElapsedTime()

    override fun init() {
        //initialize and set robot behavior
        robot = StaticDischargeBot(hardwareMap, telemetry, null)
        robot.driveTrain.setZeroBehavior(DcMotor.ZeroPowerBehavior.FLOAT)
        stop()
    }

    override fun loop() {

        // get gamepad input
        var vert = gamepad1.left_stick_y.toDouble()
        var hori = gamepad1.left_stick_x.toDouble()
        val turn = gamepad1.right_stick_x.toDouble()

        if (gamepad1.x) {
            reverse = true
        }
        if (gamepad1.y) {
            reverse = false
        }
        if (gamepad1.dpad_down) {
            driveSpeed = 0.5
        }
        if (gamepad1.dpad_up) {
            driveSpeed = 1.0
        }

        if (Math.abs(vert) < 0.1) {
            vert = 0.0
        }
        if (Math.abs(hori) < 0.1) {
            hori = 0.0
        }

        try {
            //output values for robot movement
            robot.driveTrain.start(DriveTrain.Vector(
                    hori * driveSpeed * (if (reverse) -1 else 1).toDouble(),
                    vert * driveSpeed * (if (reverse) -1 else 1).toDouble(),
                    turn * driveSpeed).speeds())
//            robot.lift.start(liftSpeed(lift))
//            if (hookUp) {
//                robot.hook(0.0)
//            }
//            if (hookDown) {
//                robot.hook(0.9)
//            }
//
//            if (pinchDown) {
//                robot.pinch.start(0.0)
//            } else if (pinchUp) {
//                robot.pinch.start(1.0)
//            }


        } catch (e: Exception) {
            telemetry.addData("Error", e.message)
            telemetry.addData("info", e.stackTrace[0].toString())
            telemetry.update()
        }

    }


    override fun start() {}

    override fun stop() {
        robot.driveTrain.start(DriveTrain.Vector(0.0, 0.0, 0.0).speeds())
//        robot.lift.start(0.0)
//        robot.dropStone.start(0.5)
//        robot.hook(robot.hook1.measure())
//        robot.pinch.start(1.0)
    }

//    private fun liftSpeed(speed: Double): Double {
//        return if (speed < 0) {
//            speed * liftUpSpeed
//        } else if (speed > 0) {
//            speed * liftDownSpeed
//        } else {
//            0.0
//        }
//    }
}