package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "ggcontrollertest", group = "LinearOpMode")
public class ggcontrollertest extends LinearOpMode {

    private DcMotor left_back_driveAsDcMotor;
    private DcMotor right_back_driveAsDcMotor;

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        float Axial;
        float Yaw;
        float Speed_multiplier;
        float min_speed = (float)0.25;
        float robot_speed;
        float leftBackPower;
        float rightBackPower;
        float max;

        left_back_driveAsDcMotor = hardwareMap.get(DcMotor.class, "left_back_drive");
        right_back_driveAsDcMotor = hardwareMap.get(DcMotor.class, "right_back_drive");
        left_back_driveAsDcMotor.setDirection(DcMotor.Direction.FORWARD);
        //rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        right_back_driveAsDcMotor.setDirection(DcMotor.Direction.REVERSE);

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
                robot_speed = min_speed + ((1-min_speed) * Speed_multiplier);
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
                leftBackPower *=robot_speed;
                rightBackPower *=robot_speed;
                left_back_driveAsDcMotor.setPower(leftBackPower);
                right_back_driveAsDcMotor.setPower(rightBackPower);
            }
        }
    }
}

