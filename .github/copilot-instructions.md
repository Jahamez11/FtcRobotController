# FTC Robot Controller - TechnoTacos Team

This is a FIRST Tech Challenge (FTC) robotics project for the 2025-2026 DECODE season using FTC SDK v11.0. The robot uses a mecanum drivetrain with odometry-based autonomous navigation.

## ⚠️ Non-Negotiable Rule

**DO NOT make any changes without explicit review and approval.** Always present proposed changes, explain the rationale, and wait for confirmation before editing any files. This ensures all modifications are understood and approved by the team before implementation.

## Project Architecture

### Module Structure
- **FtcRobotController/**: Core FTC SDK framework (rarely modified)
- **TeamCode/**: All team-specific robot code lives here
  - All OpModes and robot code: `/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`

### OpMode Patterns
All robot programs extend `LinearOpMode` and use one of two annotations:
- `@TeleOp(name="...", group="...")` - Driver-controlled modes (TTMain.java, TTMainOP.java)
- `@Autonomous(name="...", group="Fall2025")` - Pre-programmed autonomous routines (AutoTT*.java files)

OpModes follow this lifecycle:
1. Hardware initialization via `hardwareMap.get()`
2. `waitForStart()` - wait for driver station START button
3. `while (opModeIsActive())` - main loop with `sleep()` for timing

### Hardware Configuration
Standard hardware naming convention (must match Robot Controller config):
- **Motors**: `frontleft`, `frontright`, `backleft`, `backright`, `leftlaunch`, `rightlaunch`, `index`
- **Servos**: `rotate`, `rotate2` (continuous rotation servos)
- **Sensors**: IMU integrated into Control Hub, webcams for vision processing
- **Odometry**: GoBilda 32mm odometry pods for precise position tracking

Motor directions are critical - set in `runOpMode()`:
```java
frontleft.setDirection(DcMotor.Direction.REVERSE);
backleft.setDirection(DcMotor.Direction.REVERSE);
frontright.setDirection(DcMotor.Direction.FORWARD);
backright.setDirection(DcMotor.Direction.FORWARD);
```

**Vision System**: Webcams configured through `VisionPortal` for AprilTag detection and TensorFlow object recognition (see ConceptAprilTag.java and ConceptTensorFlowObjectDetection.java for reference implementations).

### Custom Odometry System
The team uses two custom odometry classes for precise autonomous movement:
- **SimplifiedOdometryRobot.java**: Base odometry implementation with proportional control
- **SimplifiedOdometryRobotCustom.java**: Extended version with dual strafe encoders

Key odometry parameters (tuned for GoBilda 32mm pods):
- `ODOM_INCHES_PER_COUNT = 1.89*Math.PI/2000.0`
- Proportional control gains: DRIVE_GAIN=0.03, STRAFE_GAIN=0.03, YAW_GAIN=0.018
- Tolerances: DRIVE_TOLERANCE=0.5", YAW_TOLERANCE=1.0°

Odometry usage pattern:
```java
SimplifiedOdometryRobotCustom odometry = new SimplifiedOdometryRobotCustom(this, index, leftlaunch, rightlaunch);
odometry.initialize(true);
odometry.drive(inches, maxPower, turnRate); // drive forward/back
odometry.strafe(inches, maxPower, turnRate); // strafe left/right
odometry.turnTo(heading, maxPower, tolerance);
```

## Development Workflow

### Building the Project
```bash
./gradlew build  # Build all modules (builds in ~16s)
```
The project uses Gradle 8.7.0 and Android Gradle Plugin 8.7.0. Build files at root rarely need modification.

### Deployment
Deploy to Robot Controller via Android Studio's "Run" button or:
```bash
./gradlew installDebug
```
The APK must be installed on the REV Control Hub (Android device).

### Testing & Development Workflow

**Pre-Deployment Testing**:
1. Always build locally first: `./gradlew build` (~16s, catches compilation errors)
2. Test new OpModes in Motor_Debug.java or create separate test files
3. Use telemetry extensively during development: `telemetry.addData("key", value)`
4. Verify hardware names match Robot Controller configuration before deployment

**Iterative Development**:
1. Make small, testable changes
2. Build and verify compilation
3. Deploy to robot: `./gradlew installDebug` or Android Studio Run
4. Test on Driver Station with telemetry monitoring
5. Commit working code to version control

**Common Issues**:
- **Motor not moving**: Check direction settings and power values (±1.0 range)
- **Odometry drift**: Verify encoder connections and `INVERT_*_ODOMETRY` constants
- **Hardware errors**: Confirm device names in code match Robot Controller config exactly
- **Build failures**: Check for missing imports, verify FTC SDK version compatibility

**Safety Practices**:
- Always set motor powers to 0 when stopping or encountering errors
- Test autonomous routines in safe environment before competition
- Keep active competition code in "Fall2025" group, archive old versions

## Code Conventions

### Autonomous Naming
Autonomous files follow pattern `AutoTT[variant][color/position].java`:
- `AutoTT1B.java` - Variant 1, Blue alliance
- `AutoTT1BR.java` - Variant 1, Blue alliance, Right position
- `AutoTT1Delay.java` - Variant 1 with delay
Groups like "Fall2025" organize OpModes in Driver Station

### TeleOp Drive Code
Mecanum drive uses POV control (tank drive + strafe):
```java
double axial = -gamepad1.left_stick_y;   // forward/back
double lateral = gamepad1.left_stick_x;  // strafe left/right
double yaw = gamepad1.right_stick_x;     // rotation

// Mecanum wheel power calculation
double frontLeftPower = axial + lateral + yaw;
double frontRightPower = axial - lateral - yaw;
double backLeftPower = axial - lateral + yaw;
double backRightPower = axial + lateral - yaw;
```
Power is normalized to prevent exceeding ±1.0, then scaled (0.6x default, 0.9x with right bumper).

### Competition-Specific Mechanics (DECODE Season)
- **Launch mechanism**: Dual motors (leftlaunch/rightlaunch) run at ±0.83 power to launch balls into basket
- **Indexer**: Single motor feeds balls into launcher, runs at -0.85 power
- **Rotation servos**: Paired servos (rotate/rotate2) run opposite directions (±1.0) for mechanism positioning

Typical scoring sequence (see AutoTT1B.java):
```java
leftlaunch.setPower(0.83);
rightlaunch.setPower(-0.83);
sleep(500);  // spin up launchers
index.setPower(-0.85);  // feed ball
rotate.setPower(-1);
rotate2.setPower(1);
sleep(3700);  // scoring duration
// stop all mechanisms
```

## Key Dependencies
Defined in `build.dependencies.gradle`:
- FTC SDK 11.0.0 (RobotCore, Hardware, Vision, Blocks, OnBotJava)
- AprilTag library updated for DECODE season
- TensorFlow for object detection (ConceptTensorFlowObjectDetection.java)

## Common Patterns
- Always call `hardwareMap.get()` before `waitForStart()`
- Use `sleep(milliseconds)` for timed sequences in autonomous
- Set all motor powers to 0 when stopping
- OpModes in "Fall2025" group are active competition code
- Files with "Concept" prefix are reference examples, not competition code
