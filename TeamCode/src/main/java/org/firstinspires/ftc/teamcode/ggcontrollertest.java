package org.firstinspires.ftc.teamcode;

import android.view.animation.GridLayoutAnimationController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "ggcontrollertest", group = "LinearOpMode")
public class ggcontrollertest extends LinearOpMode {

    private DcMotor LEFT_DRIVEAsDcMotor;
    private DcMotor RIGHT_DRIVEAsDcMotor;
    private DcMotor ARM_ROTATIONDcMotor;
    private DcMotor ARM_EXTENSIONDcMotor;

    private CRServo PLANE_LAUNCHER;


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

        LEFT_DRIVEAsDcMotor = hardwareMap.get(DcMotor.class, "LEFT_DRIVE");
        RIGHT_DRIVEAsDcMotor = hardwareMap.get(DcMotor.class, "RIGHT_DRIVE");
        ARM_ROTATIONDcMotor = hardwareMap.get(DcMotor.class, "ARM_ROTATION");
        LEFT_DRIVEAsDcMotor.setDirection(DcMotor.Direction.FORWARD);
        ARM_ROTATIONDcMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RIGHT_DRIVEAsDcMotor.setDirection(DcMotor.Direction.REVERSE);
        //armExtension.setDirection(DCMotor.Direction.Reverse);
        // Put initialization blocks here.
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                telemetry.update();

                // X = yaw. Y = axial
                Axial = gamepad1.left_stick_y;
                Yaw = gamepad1.left_stick_x;
                //Increase Speed based on trigger;
                Speed_multiplier = gamepad1.right_trigger;
                robot_speed = min_speed + ((1 - min_speed) * Speed_multiplier);
                leftBackPower = Axial + Yaw;
                rightBackPower = Axial - Yaw;
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
                LEFT_DRIVEAsDcMotor.setPower(leftBackPower);
                RIGHT_DRIVEAsDcMotor.setPower(rightBackPower);
            }
        }
    }
}
