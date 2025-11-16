package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="AutoBack.java", group = "Fall2025")
//@Disable
public class AutoBack extends LinearOpMode {

    private DcMotor frontleft = null;  //  Used to control the left front drive wheel
    private DcMotor frontright = null;  //  Used to control the right front drive wheel
    private DcMotor backleft = null;  //  Used to control the left back drive wheel
    private DcMotor backright = null;  //  Used to control the right back drive wheel
    private DcMotor leftlaunch = null;
    private DcMotor rightlaunch = null;
    private DcMotor index = null;
    private CRServo rotate = null;
    private CRServo rotate2 = null;

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

        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        backleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontleft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontright.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while (opModeIsActive()) {
            backleft.setPower(0.8);
            backright.setPower(0.8);
            frontleft.setPower(0.8);
            frontright.setPower(0.8);
            sleep(300);
            backleft.setPower(0);
            backright.setPower(0);
            frontleft.setPower(0);
            frontright.setPower(0);
            sleep(50000);
        }
    }
}