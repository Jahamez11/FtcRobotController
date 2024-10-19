package org.fistinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "ggcontrollertest.java", group = "LinearOpMode")
public class ggcontrollertest extends LinearOpMode {

    private static Object Servo;
    private CRServo PLANE_LAUNCHER;private CRServo lEFT_GRABBER;
    private DcMotor FRONTLEFTDRIVEAsDcMotor;
    private DcMotor FRONTRIGHTDRIVEAsDcMotor;
    private DcMotor ARM_ROTATIONDcMotor;
    private DcMotor ARM_EXTENSIONDcMotor;
    private float PLANE_LAUNCHERServo;
    private CRServo LEFT_GRABBER;
    private CRServo RIGHT_GRABBER;

    private static final double ARMMAXSPEED = 0.4;
    private static

    final double ROTATIONMAXSPEED = 0.05;


    // This function is executed when this Op Mode is selected from the Driver Station
    @Override
    public void runOpMode() {
        float Axial;
        float Yaw;
        float Speed_multiplier;
        float min_speed = (float) 0.25;
        float robot_speed;
        float leftBackPower;
        float rightBackPower;
        float max;
        float arm_extension_pwr;
        float arm_rotation_pwr;
        float plane_launcher;
        long launch_time = System.currentTimeMillis();
        float left_grabber;
        float Lateral;

        FRONTLEFTDRIVEAsDcMotor = hardwareMap.get(DcMotor.class, "LEFT_DRIVE");
        FRONTRIGHTDRIVEAsDcMotor = hardwareMap.get(DcMotor.class, "RIGHT_DRIVE");
        ARM_ROTATIONDcMotor = hardwareMap.get(DcMotor.class, "ARM_ROTATION");
        ARM_ROTATIONDcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_ROTATIONDcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ARM_EXTENSIONDcMotor = hardwareMap.get(DcMotor.class, "ARM_EXTENSION");
        ARM_EXTENSIONDcMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_EXTENSIONDcMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FRONTLEFTDRIVEAsDcMotor.setDirection(DcMotor.Direction.FORWARD);
        FRONTRIGHTDRIVEAsDcMotor.setDirection(DcMotor.Direction.REVERSE);
        PLANE_LAUNCHER = hardwareMap.get(CRServo.class, "PLANE_LAUNCHER");
        LEFT_GRABBER = hardwareMap.get(CRServo.class, "LEFT_GRABBER");
        RIGHT_GRABBER = hardwareMap.get(CRServo.class,"RIGHT_GRABBER");
        PLANE_LAUNCHER.setPower(0);
        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();

                // X = yaw. Y = axial//
                Axial = gamepad1.left_stick_y;
                Yaw = gamepad1.left_stick_x;
                Lateral =  gamepad1.left_stick_x;
                //Increase Speed based on trigger;
                Speed_multiplier = gamepad1.right_trigger;
                robot_speed = min_speed + ((1 - min_speed) * Speed_multiplier);
                rightBackPower = Axial + Yaw + Lateral;
                leftBackPower = Axial - Yaw - Lateral;
                if (Math.abs(leftBackPower) > Math.abs(rightBackPower)) {
                    max = Math.abs(leftBackPower);
                } else {
                    max = Math.abs(rightBackPower);
                }
                if (max > 1) {
                    leftBackPower = leftBackPower / max;
                    rightBackPower = rightBackPower / max;
                }
                leftBackPower *= robot_speed;
                rightBackPower *= robot_speed;
                FRONTLEFTDRIVEAsDcMotor.setPower(leftBackPower);
                FRONTRIGHTDRIVEAsDcMotor.setPower(rightBackPower);
                ARM_ROTATIONDcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                ARM_EXTENSIONDcMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                arm_extension_pwr = gamepad1.right_stick_y;
                arm_rotation_pwr = gamepad1.right_stick_x;
                arm_extension_pwr *= ARMMAXSPEED;
                arm_rotation_pwr *= ROTATIONMAXSPEED;
                ARM_EXTENSIONDcMotor.setPower(arm_extension_pwr);
                ARM_ROTATIONDcMotor.setPower(arm_rotation_pwr);
                if (gamepad1.y) {
                    //Launch Servo
                    if (launch_time < System.currentTimeMillis()) {
                        PLANE_LAUNCHER.setPower(1);
                    }
                } else {
                    //Don't Launch Servo
                    PLANE_LAUNCHER.setPower(0);
                    launch_time = System.currentTimeMillis() + 1000;
                    //Left Grab and Right grab
                }
                if(gamepad1.left_bumper) {
                    LEFT_GRABBER.setPower(0.05);
                    RIGHT_GRABBER.setPower(-0.05);

                } else {
                    LEFT_GRABBER.setPower(-1);
                    RIGHT_GRABBER.setPower(1);
                }
            }
        }
    }
}