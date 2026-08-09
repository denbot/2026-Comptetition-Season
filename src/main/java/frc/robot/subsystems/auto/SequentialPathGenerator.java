package frc.robot.subsystems.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.OperatorConstants;
import frc.robot.RobotContainer;

public class SequentialPathGenerator {
    public static Command getSequentialPath(TargetPoses[] setpoints, Double[] angles, Double[] velocities){
        SequentialCommandGroup finalPath = new SequentialCommandGroup();

        for(int i = 0; i < setpoints.length; i++){
            Pose2d targetPose;
            if(RobotContainer.isBlue()) targetPose = setpoints[i].blueAlignmentPose;
            else targetPose = setpoints[i].redAlignmentPose;

            finalPath.addCommands(
                AutoBuilder.pathfindToPose(new Pose2d(targetPose.getX(), targetPose.getY(), new Rotation2d(angles[i])),
                OperatorConstants.pathfindingConstraints,
                velocities[i]));
        }
        return finalPath;
    }
    public static Command getSequentialPath(double [] endSpeeds, TargetPoses... setpoints){
        SequentialCommandGroup finalPath = new SequentialCommandGroup();

        for(int i = 0; i < setpoints.length; i++){
            Pose2d targetPose;
            if(RobotContainer.isBlue()) targetPose = setpoints[i].blueAlignmentPose;
            else targetPose = setpoints[i].redAlignmentPose;

            finalPath.addCommands(
                AutoBuilder.pathfindToPose(targetPose,
                OperatorConstants.pathfindingConstraints,
                endSpeeds[i]));
        }
        return finalPath;
    }
    
    public static Command getSequentialPath(double[] maxSpeeds, double [] endSpeeds, TargetPoses... setpoints){
        SequentialCommandGroup finalPath = new SequentialCommandGroup();

        for(int i = 0; i < setpoints.length; i++){
            Pose2d targetPose;
            if(RobotContainer.isBlue()) targetPose = setpoints[i].blueAlignmentPose;
            else targetPose = setpoints[i].redAlignmentPose;

            finalPath.addCommands(
                AutoBuilder.pathfindToPose(targetPose,
                new PathConstraints(maxSpeeds[i], maxSpeeds[i], Units.degreesToRadians(540), Units.degreesToRadians(720))));
        }
        return finalPath;
    }
}
