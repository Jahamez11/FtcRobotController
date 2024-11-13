package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorTouch;
import org.firstinspires.ftc.robotcore.internal.camera.delegating.DelegatingCaptureSequence;

@TeleOp(name = "ttcontrollertest.java", group = "LinearOpMode")
public class ttcontrollertest extends LinearOpMode {

    private static Object Servo;

    private static final double ARMMAXSPEED = 1;
    private static final double ROTATIONMAXSPEED = 1;


    // This function is executed when this Op Mode is selected from the Driver Station

    private ElapsedTime runtime = new ElapsedTime();


    private DcMotor FRONTLEFT_DRIVE = null;

    private DcMotor FRONTRIGHT_DRIVE = null;

    private DcMotor BACKLEFT_DRIVE = null;

    private DcMotor BACKRIGHT_DRIVE = null;
    private DcMotor ARM_ROTATION = null;
    private DcMotor ARM_EXTENSION = null;
    private CRServo LEFT_GRABBER = null;
    private CRServo RIGHT_GRABBER = null;

    private CRServo LIFT_SERVO1 = null;

    private CRServo LIFT_SERVO2 = null;
    private TouchSensor rotate_zero = null;

    @Override

    public void runOpMode() {

        FRONTLEFT_DRIVE = hardwareMap.get(DcMotor.class, "FRONTLEFT_DRIVE");

        FRONTRIGHT_DRIVE = hardwareMap.get(DcMotor.class, "FRONTRIGHT_DRIVE");

        BACKLEFT_DRIVE = hardwareMap.get(DcMotor.class, "BACKLEFT_DRIVE");

        BACKRIGHT_DRIVE = hardwareMap.get(DcMotor.class, "BACKRIGHT_DRIVE");

        ARM_EXTENSION = hardwareMap.get(DcMotor.class, "ARMEXTENSION");
        ARM_ROTATION = hardwareMap.get(DcMotor.class, "ARMROTATION");
        LEFT_GRABBER = hardwareMap.get(CRServo.class, "LEFT_GRABBER");
        RIGHT_GRABBER = hardwareMap.get(CRServo.class, "RIGHT_GRABBER");
        // LIFT_SERVO1 = hardwareMap.get(CRServo.class, "LIFT_SERVO1");
        // LIFT_SERVO2 = hardwareMap.get(CRServo.class, "LIFT_SERVO2");
        rotate_zero = hardwareMap.get(TouchSensor.class, "ROTATE_0");

        ARM_EXTENSION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ARM_ROTATION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_ROTATION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        FRONTLEFT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        BACKLEFT_DRIVE.setDirection(DcMotor.Direction.REVERSE);
        FRONTRIGHT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        BACKRIGHT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        ARM_EXTENSION.setDirection(DcMotorSimple.Direction.REVERSE);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            double arm_extension_pwr = -gamepad2.left_stick_y;
            double arm_rotation_pwr = -gamepad2.right_stick_y;
            double max;
            // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
            double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            arm_extension_pwr *= ARMMAXSPEED;
            arm_rotation_pwr *= ROTATIONMAXSPEED;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;


            }


            FRONTRIGHT_DRIVE.setPower(rightFrontPower * 0.5);
            FRONTLEFT_DRIVE.setPower(leftFrontPower * 0.5);
            BACKRIGHT_DRIVE.setPower(rightBackPower * 0.5);
            BACKLEFT_DRIVE.setPower(leftBackPower * 0.5);

            boolean donothing = false;
            telemetry.addData("Ypower", arm_extension_pwr);
            if (ARM_EXTENSION.getCurrentPosition() <= 4000) {
                donothing = false; //Pass power as controlled
                telemetry.addData("Arm_extention", "Less than 4000");
            } else if (ARM_EXTENSION.getCurrentPosition() < 0 && arm_extension_pwr > 0) {
                donothing = false;
            } else if (arm_extension_pwr < 0) {
                donothing = false; //Pass power as controlled
                telemetry.addData("Arm_extention Pwr", "ArmPower>0");
            } else {
                donothing = true;
                arm_extension_pwr = 0;
            }
            if (!rotate_zero.isPressed() && arm_rotation_pwr < 0) {
                arm_rotation_pwr = 0;
                telemetry.addData("Arm_rotation", "Less than 0");
            }


            telemetry.addData("ARM_EXTENSION_VALUE", arm_extension_pwr);
            telemetry.addData("ARM_ROTATION_VALUE", arm_rotation_pwr);
            ARM_EXTENSION.setPower(arm_extension_pwr);
            ARM_ROTATION.setPower(arm_rotation_pwr);

            if (gamepad2.left_bumper) {
                LEFT_GRABBER.setPower(0.05);
                RIGHT_GRABBER.setPower(0.55);

            } else {
                LEFT_GRABBER.setPower(0.9);
                RIGHT_GRABBER.setPower(-0.1);

                if (gamepad2.right_bumper) {
                    LEFT_GRABBER.setPower(0.55);
                    RIGHT_GRABBER.setPower(0.05);

                } else {
                    LEFT_GRABBER.setPower(0.9);
                    RIGHT_GRABBER.setPower(-0.1);
                }
                telemetry.addData("Arm Position", ARM_EXTENSION.getCurrentPosition());
                telemetry.addData("Arm Rotation Position", ARM_ROTATION.getCurrentPosition());


                telemetry.addData("Ypower", arm_rotation_pwr);
                telemetry.update();
            }

        }
    }
}