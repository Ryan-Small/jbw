package jnibwapi.model;

import java.util.Map;

/**
 * http://code.google.com/p/bwta/wiki/Chokepoint
 */
public class ChokePoint {

    public static final int NUM_ATTRIBUTES = 9;
    public static final double fixedScale = 100.0;

    private final Position center;
    private final double radius;
    private final int firstRegionId;
    private final int secondRegionId;
    private final Position firstSide;
    private final Position secondSide;
    private final Region firstRegion;
    private final Region secondRegion;

    public ChokePoint(final int[] data, int index, final Map<Integer, Region> idToRegion) {
        final int centerX = data[index++];
        final int centerY = data[index++];
        center = new Position(centerX, centerY);
        radius = data[index++] / fixedScale;
        firstRegionId = data[index++];
        secondRegionId = data[index++];
        final int firstSideX = data[index++];
        final int firstSideY = data[index++];
        firstSide = new Position(firstSideX, firstSideY);
        final int secondSideX = data[index++];
        final int secondSideY = data[index++];
        secondSide = new Position(secondSideX, secondSideY);
        firstRegion = idToRegion.get(firstRegionId);
        secondRegion = idToRegion.get(secondRegionId);
    }

    public Region getOtherRegion(final Region region) {
        return region.equals(firstRegion) ? secondRegion : firstRegion;
    }

    public Region getFirstRegion() {
        return firstRegion;
    }

    public Region getSecondRegion() {
        return secondRegion;
    }

    public Position getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Position getFirstSide() {
        return firstSide;
    }

    public Position getSecondSide() {
        return secondSide;
    }
}
