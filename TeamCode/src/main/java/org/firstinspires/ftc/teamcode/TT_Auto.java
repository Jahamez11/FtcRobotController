package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="TT_Auto", group="TFOD")
@Disabled
public class TT_Auto extends LinearOpMode {
    private DcMotor FRONTLEFT_DRIVE = null;

    private DcMotor FRONTRIGHT_DRIVE = null;

    private DcMotor BACKLEFT_DRIVE = null;

    private DcMotor BACKRIGHT_DRIVE = null;
    private com.qualcomm.robotcore.hardware.DcMotor ARM_ROTATION = null;
    private DcMotor ARM_EXTENSION = null;
    private CRServo LEFT_GRABBER = null;
    private CRServo RIGHT_GRABBER = null;

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


        ARM_EXTENSION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_EXTENSION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ARM_ROTATION.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ARM_ROTATION.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FRONTLEFT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        BACKLEFT_DRIVE.setDirection(DcMotor.Direction.REVERSE);
        FRONTRIGHT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        BACKRIGHT_DRIVE.setDirection(DcMotor.Direction.FORWARD);
        // Put initialization blocks here
        waitForStart();
        // Put run blocks here
    }
}