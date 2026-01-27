package frc.robot.subsystems.intake;

public class IntakeConstants {
  public static final double intakeRotationsToRackRatio =
      1.0; // Unkown ratio for the rotations of the motors (pinion) to the surface speed of the
  // intake (rack). Units to be determined.
  public static final double intakeMaxExtensionLength = 12; // TODO: find extension max
  public static final double intakeMinExtensionLength = 0; // TODO: find extension min
  public static int INTAKE_MOTOR_ID = 0;
  public static int RACK_MOTOR_ID = 0;

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final String canivoreSerial = "D75CCE723353385320202034111303FF";
    ;
  }
}
