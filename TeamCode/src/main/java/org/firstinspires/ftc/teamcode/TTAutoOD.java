package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.onbotjava.handlers.admin.ResetOnBotJava;

@Autonomous(name="TTAutoOD.java", group = "Fall2025")
//@Disable
public class TTAutoOD extends LinearOpMode {
   // private SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);
    private DcMotor frontleft;  //  Used to control the left front drive wheel
    private DcMotor frontright;  //  Used to control the right front drive wheel
    private DcMotor backleft;  //  Used to control the left back drive wheel
    private DcMotor backright;  //  Used to control the right back drive wheel
    private DcMotor leftlaunch;
    private DcMotor rightlaunch;
    private DcMotor index;
    private CRServo rotate;
    private CRServo rotate2;

    private SimplifiedOdometryRobotCustom odometry;

    @Override
    public void runOpMode() {

        frontleft = hardwareMap.get(DcMotor.class, "frontleft");
        frontright = hardwareMap.get(DcMotor.class, "frontright");
        backleft = hardwareMap.get(DcMotor.class, "backleft");
        backright = hardwareMap.get(DcMotor.class, "backright");
        leftlaunch = hardwareMap.get(DcMotor.class, "leftlaunch");
        rightlaunch = hardwareMap.get(DcMotor.class, "rightlaunch");
        index = hardwareMap.get(DcMotor.class, "index");
        rotate = hardwareMap.get(CRServo.class, "rotate");
        rotate2 = hardwareMap.get(CRServo.class, "rotate2");

        odometry = new SimplifiedOdometryRobotCustom(this, index, leftlaunch,rightlaunch);

        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            odometry.initialize(true);
            odometry.drive(0, 0, 0);
          //  odometry.turnTo(180,0.7,0.2);
          //  odometry.strafe(10, 0.7, 0.15);

        }
    }
}