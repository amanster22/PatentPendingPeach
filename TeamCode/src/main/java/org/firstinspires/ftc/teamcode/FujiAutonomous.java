package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hardware.Fuji;

public abstract class FujiAutonomous extends LinearOpMode {

    // robot
    public Fuji robot;

    // field constants.
    public static final double STONE_LENGTH = 8;
    public static final double ROBOT_EDGE_LENGTH = 17;
    public static final double STONE_BRIDGE_DISTANCE_INCH = 23.3;
    public static final double FOUNDATION_BRIDGE_DISTANCE_INCH = 34;
    public static final double BRIDGE_WALL_DISTANCE_INCH = 47;
    public static final double TILE_LENGTH = 25;
}