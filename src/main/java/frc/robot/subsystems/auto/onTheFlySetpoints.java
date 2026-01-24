package frc.robot.subsystems.auto;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;

public enum onTheFlySetpoints {
    // Trench Locations
    TOP_LEFT(7, 17, onTheFlyOffsets.TRENCH_LEFT),
    TOP_RIGHT(6, 28, onTheFlyOffsets.TRENCH_RIGHT),
    BOT_LEFT(12, 22, onTheFlyOffsets.TRENCH_LEFT),
    BOT_RIGHT(1, 23, onTheFlyOffsets.TRENCH_RIGHT),
    // Neutral Zone Locations
    CLOSE_TOP(6, 17, onTheFlyOffsets.CLOSE_TOP),
    CLOSE_MID(4, 20, onTheFlyOffsets.CLOSE_MID),
    CLOSE_BOTTOM(1, 22, onTheFlyOffsets.CLOSE_BOTTOM),
    CENTER_TOP(6, 17, onTheFlyOffsets.CENTER_TOP),
    CENTER_MID(4, 20, onTheFlyOffsets.CENTER_MID),
    CENTER_BOTTOM(1, 22, onTheFlyOffsets.CENTER_BOTTOM),
    FAR_TOP(17, 6, onTheFlyOffsets.FAR_TOP),
    FAR_MID(20, 4, onTheFlyOffsets.FAR_MID),
    FAR_BOTTOM(22, 1, onTheFlyOffsets.FAR_BOTTOM);
    
    public static final AprilTagFieldLayout fieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltAndymark);

    public final int redAprilTag;
    public final int blueAprilTag;
    public final onTheFlyOffsets offset;
    public final Pose2d redAlignment;
    public final Pose2d blueAlignment;

    onTheFlySetpoints(int redAprilTag, int blueAprilTag, onTheFlyOffsets offset){
        this.redAprilTag = redAprilTag;
        this.blueAprilTag = blueAprilTag;
        this.offset = offset;
        this.redAlignment = getAlignmentPose(redAprilTag, this.offset);
        this.blueAlignment = getAlignmentPose(blueAprilTag, this.offset);
    }

    private static Pose2d getAlignmentPose(int apriltag, onTheFlyOffsets offset){
        return fieldLayout.getTagPose(apriltag).get().toPose2d().transformBy(offset.transform);
    }
}
