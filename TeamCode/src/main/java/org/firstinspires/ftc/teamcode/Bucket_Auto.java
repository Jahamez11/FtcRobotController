/* Created by Phil Malone. 2023.
    This class illustrates my simplified Odometry Strategy.
    It implements basic straight line motions but with heading and drift controls to limit drift.
    See the readme for a link to a video tutorial explaining the operation and limitations of the code.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.invoke.WrongMethodTypeException;

/*
 * This OpMode illustrates an autonomous opmode using simple Odometry
 * All robot functions are performed by an external "Robot" class that manages all hardware interactions.
 * Pure Drive or Strafe motions are maintained using two Odometry Wheels.
 * The IMU gyro is used to stabilize the heading during all motions
 */

@Autonomous(name="Bucket Autonomous", group = "AUTO")
@Disabled
public class Bucket_Auto extends LinearOpMode {
    // get an instance of the "Robot" class.
   // private SimplifiedOdometryRobot robot =new  SimplifiedOdometryRobot(this);
    private DcMotor ARM_ROTATION = null;
    private DcMotor ARM_EXTENSION = null;
    private CRServo LEFT_GRABBER = null;
    private CRServo RIGHT_GRABBER = null;
    private TouchSensor rotate_zero = null;
    private double left_servo_close = 0.05;
    private double left_servo_open = 0.9;
    private double right_servo_close = 0.55;
    private double right_servo_open = -0.1;
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        // Initialize the robot hardware & Turn on telemetry
       // robot.initialize(true);

        // Wait for driver to press start
        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
      //  robot.resetHeading();  // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        if (opModeIsActive()) {
            ARM_EXTENSION = hardwareMap.get(DcMotor.class, "ARMEXTENSION");
            ARM_ROTATION = hardwareMap.get(DcMotor.class, "ARMROTATION");
            LEFT_GRABBER = hardwareMap.get(CRServo.class, "LEFT_GRABBER");
            RIGHT_GRABBER = hardwareMap.get(CRServo.class, "RIGHT_GRABBER");
            rotate_zero = hardwareMap.get(TouchSensor.class, "ROTATE_0");
            ARM_EXTENSION.setDirection(DcMotorSimple.Direction.REVERSE);



            ARM_EXTENSION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            ARM_ROTATION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ARM_ROTATION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Initialize the robot hardware & Turn on telemetry
           // robot.initialize(true);

            // Wait for driver to press start
            telemetry.addData(">", "Touch Play to run Auto");
            telemetry.update();

            waitForStart();
            //robot.resetHeading();  // Reset heading to set a baseline for Auto

            // Run Auto if stop was not pressed.
                // Note, this example takes more than 30 seconds to execute, so turn OFF the auto timer.

                // Drive a large rectangle, turning at each corner
//            robot.drive(  12, 0.60, 0.25);
//            robot.turnTo(90, 0.45, 0.5);
//            robot.drive(  2, 0.60, 0.25);
//            robot.turnTo(180, 0.45, 0.5);
//            robot.drive(  12, 0.60, 0.25);
//            robot.turnTo(270, 0.45, 0.5);
//            robot.drive(  72, 0.60, 0.25);
//            robot.turnTo(0, 0.45, 0.5);
//            sleep(500);


                ARM_ROTATION.setTargetPosition(1300);
                ARM_ROTATION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ARM_ROTATION.setPower(1);
              //  robot.drive(5, 0.21, 0.15);
              //  robot.strafe(9, 0.40, 0.15);
                LEFT_GRABBER.setPower(left_servo_open);
                RIGHT_GRABBER.setPower(right_servo_open);
                while (ARM_ROTATION.getCurrentPosition()<ARM_ROTATION.getTargetPosition()) {
                    ARM_ROTATION.setPower(1);
                }
                ARM_EXTENSION.setTargetPosition(3300);
                ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ARM_EXTENSION.setPower(1);
                while(ARM_EXTENSION.getCurrentPosition()<=ARM_EXTENSION.getTargetPosition()){
                    ARM_EXTENSION.setPower(1);
                }
              //  robot.drive(3.5, 0.60, 0.15);

                LEFT_GRABBER.setPower(left_servo_close);
                RIGHT_GRABBER.setPower(right_servo_close);
                double next_time = runtime.milliseconds()+500.0;
                while (runtime.milliseconds()<next_time){
                    telemetry.addData("Time",runtime.milliseconds());
                    telemetry.update();
                }
                ARM_ROTATION.setTargetPosition(-5000);
                ARM_ROTATION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                while(ARM_ROTATION.getCurrentPosition()>=ARM_ROTATION.getTargetPosition() & rotate_zero.isPressed()){
                    ARM_ROTATION.setPower(-1);
                    telemetry.addData("Button", rotate_zero.isPressed());
                    telemetry.addData("Target:Actual Rotate" ,"%d,%d", ARM_ROTATION.getTargetPosition(),ARM_ROTATION.getCurrentPosition());
                    telemetry.addData("Target:Actual ARM" ,"%d,%d", ARM_EXTENSION.getTargetPosition(),ARM_EXTENSION.getCurrentPosition());
                    telemetry.update();
                }
                ARM_EXTENSION.setTargetPosition(4300);
                ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ARM_EXTENSION.setPower(1);
                ARM_ROTATION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                while(rotate_zero.isPressed()){
                    telemetry.addData("Button", rotate_zero.isPressed());
                    telemetry.addData("Target:Actual Rotate" ,"%d,%d", ARM_ROTATION.getTargetPosition(),ARM_ROTATION.getCurrentPosition());
                    telemetry.addData("Target:Actual ARM" ,"%d,%d", ARM_EXTENSION.getTargetPosition(),ARM_EXTENSION.getCurrentPosition());
                    ARM_ROTATION.setPower(-0.2);
                    telemetry.update();
                }
                ARM_ROTATION.setPower(0);
                ARM_ROTATION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
              //  robot.turnTo(-45, 0.4, 0.1);
               // robot.strafe(6, 0.80, 0.1);
               // robot.drive(-11.5,0.6,0.1);
                LEFT_GRABBER.setPower(left_servo_open);
                RIGHT_GRABBER.setPower(right_servo_open);
               // robot.drive(-0.5,0.6,0.2);
                ARM_ROTATION.setTargetPosition(4500);
                ARM_ROTATION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ARM_ROTATION.setPower(1);
                ARM_EXTENSION.setTargetPosition(800);
                ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ARM_EXTENSION.setPower(-1);
               // robot.drive(30,0.9,0.15);
                ARM_EXTENSION.setPower(0);
              //  robot.turnTo(-90, 0.4, 0.15);
               // robot.strafe(36, 0.90, 0.15);
               // robot.drive(16,0.9,0.15);


        }
    }
}