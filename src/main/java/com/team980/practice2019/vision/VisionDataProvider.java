package com.team980.practice2019.vision;

public interface VisionDataProvider {

    /**
     * The source of the provided vision data.
     */
    String getSource();

    /**
     * <p>The center x-coordinate of the detected vision targets</p>
     * <p>Ranges from zero to 319</p>
     */
    double getTargetCenterCoord();

    /**
     * <p>The offset x-coordinate of the detected vision targets</p>
     * <p>Ranges from -160 to 160</p>
     */
    double getTargetCenterOffset();

    /**
     * <p>The combined width of the vision targets, in pixels</p>
     */
    double getTargetWidth();
}
