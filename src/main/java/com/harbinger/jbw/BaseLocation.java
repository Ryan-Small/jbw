package com.harbinger.jbw;

import com.harbinger.jbw.Position.Type;

import java.util.Map;

/**
 * Represents a location on the map where it makes sense to place a base (e.g. near minerals).
 */
public class BaseLocation {

    public static final int NUM_ATTRIBUTES = 10;

    private final Position center;
    private final Position position;
    private final Region region;
    private final int minerals;
    private final int gas;
    private final boolean island;
    private final boolean mineralOnly;
    private final boolean startLocation;

    public BaseLocation(final int[] data, int index, final Map<Integer, Region> idToRegion) {
        final int x = data[index++];
        final int y = data[index++];
        center = new Position(x, y);
        final int tx = data[index++];
        final int ty = data[index++];
        position = new Position(tx, ty, Type.BUILD);
        final int regionID = data[index++];
        region = idToRegion.get(regionID);
        minerals = data[index++];
        gas = data[index++];
        island = (data[index++] == 1);
        mineralOnly = (data[index++] == 1);
        startLocation = (data[index++] == 1);
    }

    public Position getCenter() {
        return center;
    }

    /** The Position of the top left of the BaseLocation */
    public Position getPosition() {
        return position;
    }

    public Region getRegion() {
        return region;
    }

    public int getMinerals() {
        return minerals;
    }

    public int getGas() {
        return gas;
    }

    public boolean isIsland() {
        return island;
    }

    public boolean isMineralOnly() {
        return mineralOnly;
    }

    public boolean isStartLocation() {
        return startLocation;
    }
}
