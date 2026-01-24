package frc.robot.subsystems.auto;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;

public enum OnTheFlySetpoints {
    // All directions are assumed relative to driver station (left far is to the left side of the field, furthest from the driver in their aliance)

    // Trench Locations
    TRENCH_LEFT_FAR(6, 28, OnTheFlyOffsets.TRENCH_OFFSET),
    TRENCH_LEFT_CLOSE(7, 17, OnTheFlyOffsets.TRENCH_OFFSET),
    TRENCH_RIGHT_FAR(1, 23, OnTheFlyOffsets.TRENCH_OFFSET),
    TRENCH_RIGHT_CLOSE(12, 22, OnTheFlyOffsets.TRENCH_OFFSET),
    // Ramp Locations
    RAMP_LEFT_FAR(5, 22, OnTheFlyOffsets.RAMP_OFFSET),
    RAMP_LEFT_CLOSE(8, 23, OnTheFlyOffsets.RAMP_OFFSET),
    RAMP_RIGHT_FAR(2, 17, OnTheFlyOffsets.RAMP_OFFSET),
    RAMP_RIGHT_CLOSE(12, 28, OnTheFlyOffsets.RAMP_OFFSET),
    // Neutral Zone Locations
    // Closest April Tag Alignment handles Y offset, only X offset is required
    NEUTRAL_CLOSE_TOP(6, 17, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    NEUTRAL_CLOSE_MID(4, 20, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    NEUTRAL_CLOSE_BOTTOM(1, 22, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    NEUTRAL_CENTER_TOP(6, 17, OnTheFlyOffsets.NEUTRAL_CENTER),
    NEUTRAL_CENTER_MID(4, 20, OnTheFlyOffsets.NEUTRAL_CENTER),
    NEUTRAL_CENTER_BOTTOM(1, 22, OnTheFlyOffsets.NEUTRAL_CENTER),
    NEUTRAL_FAR_TOP(17, 6, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    NEUTRAL_FAR_MID(20, 4, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    NEUTRAL_FAR_BOTTOM(22, 1, OnTheFlyOffsets.NEUTRAL_OFF_CENTER),
    // Climb Locations
    CLIMB_LEFT(16, 32, OnTheFlyOffsets.CLIMB_LEFT),
    CLIMB_RIGHT(15, 31, OnTheFlyOffsets.CLIMB_RIGHT),
    // Human Player Locations
    HUMAN_PLAYER(13, 29, OnTheFlyOffsets.HUMAN_PLAYER),
    // Depot Player Locations
    DEPOT(7, 23, OnTheFlyOffsets.DEPOT),
    // Default Score Location Offsets
    SCORE_LEFT(8, 24, OnTheFlyOffsets.SCORE_OFF_CENTER),
    SCORE_CENTER(9, 25, OnTheFlyOffsets.SCORE_CENTER),
    SCORE_RIGHT(11, 27, OnTheFlyOffsets.SCORE_OFF_CENTER);

    public static final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltAndymark);

    public final int redAprilTag;
    public final int blueAprilTag;
    public final OnTheFlyOffsets offset;
    public final Pose2d redAlignmentPose;
    public final Pose2d blueAlignmentPose;

    OnTheFlySetpoints(int redAprilTag, int blueAprilTag, OnTheFlyOffsets offset){
        this.redAprilTag = redAprilTag;
        this.blueAprilTag = blueAprilTag;
        this.offset = offset;
        this.redAlignmentPose = getAlignmentPose(redAprilTag, this.offset);
        this.blueAlignmentPose = getAlignmentPose(blueAprilTag, this.offset);
    }

    private static Pose2d getAlignmentPose(int apriltag, OnTheFlyOffsets offset){
        return fieldLayout.getTagPose(apriltag).get().toPose2d().transformBy(offset.transform);
    }
}
