import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MyFIRSTJavaOpMode extends LinearOpMode {
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
        // Put initialization blocks here
        waitForStart();
        // Put run blocks here
        while (opModeIsActive()) {
            // Put loop blocks here
            BACKLEFT_DRIVE.setPower (-1);
            BACKRIGHT_DRIVE.setPower (1);
            FRONTLEFT_DRIVE.setPower (-1);
            FRONTRIGHT_DRIVE.setPower (1);
            sleep (3000);
            BACKLEFT_DRIVE.setPower (0);
            BACKRIGHT_DRIVE.setPower (0);
            FRONTLEFT_DRIVE.setPower (0);
            FRONTRIGHT_DRIVE.setPower (0);
            sleep (100);
            armExtend.setPower (1);
            sleep (600);
            armExtend.setPower (0);
            sleep (1000);
            armTilt.setPower (1);
            sleep (100);
            armTilt.setPower (0);
            sleep (100);
            claw.setPower (1);
            sleep(3000);
            armTilt.setPower (-1);
            sleep(100);
            armTilt.setPower (0);
            sleep(100);
            claw.setPower (-1);
            sleep(100);
            armExtend.setPower (-1);
            sleep(1000);
            armExtend.setPower (0);
            sleep(100);
            backLeftDrive.setPower (1);
            backRightDrive.setPower (-1);
            sleep (2200);
            backLeftDrive.setPower (-1);
            backRightDrive.setPower (-1);
            sleep (3250);
            backLeftDrive.setPower (0);
            backRightDrive.setPower (0);
            sleep (100);
            backLeftDrive.setPower (-1);
            backRightDrive.setPower (1);
            sleep (8000);
            backLeftDrive.setPower (0);
            backRightDrive.setPower (0);
            break;