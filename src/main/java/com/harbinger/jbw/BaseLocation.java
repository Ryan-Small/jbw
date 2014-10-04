package com.harbinger.jbw;

import com.harbinger.jbw.Position.Resolution;

/**
 * Represents a location on the map where it makes sense to place a base (e.g. near resources).
 */
public class BaseLocation {

    static final int NUM_ATTRIBUTES = 9;

    private final Position center;
    private final Position position;
    private final int minerals;
    private final int gas;
    private final boolean island;
    private final boolean mineralOnly;
    private final boolean startLocation;

    BaseLocation(final Position center, final Position position, final int minerals, final int gas,
            final boolean island, final boolean mineralsOnly, final boolean startLocation) {
        this.center = center;
        this.position = position;
        this.minerals = minerals;
        this.gas = gas;
        this.island = island;
        mineralOnly = mineralsOnly;
        this.startLocation = startLocation;
    }

    BaseLocation(final int[] data, int index) {
        final int x = data[index++];
        final int y = data[index++];
        center = new Position(x, y, Resolution.PIXEL);
        final int tx = data[index++];
        final int ty = data[index++];
        position = new Position(tx, ty, Resolution.BUILD);
        minerals = data[index++];
        gas = data[index++];
        island = (data[index++] == 1);
        mineralOnly = (data[index++] == 1);
        startLocation = (data[index++] == 1);
    }

    /**
     * @return the top-left (build) Position where a Hatchery, Nexus, or Command Center should be
     *         built
     */
    public Position getPosition() {
        return position;
    }

    /**
     * @return the center Position of where a Hatchery, Nexus, or Command Center should be built
     */
    public Position getCenter() {
        return center;
    }

    /**
     * @return the amount of minerals originally available at the start of the match
     */
    public int getMinerals() {
        return minerals;
    }

    /**
     * @return the amount of gas originally available at the start of the match
     */
    public int getGas() {
        return gas;
    }

    /**
     * @return true if this base is not reachable by ground from any other base; false otherwise
     */
    public boolean isIsland() {
        return island;
    }

    /**
     * @return true if this base only has mineral resources; false otherwise
     */
    public boolean isMineralOnly() {
        return mineralOnly;
    }

    /**
     * @return true if this base is a potential start location for a player; false otherwise
     */
    public boolean isStartLocation() {
        return startLocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((center == null) ? 0 : center.hashCode());
        result = (prime * result) + gas;
        result = (prime * result) + (island ? 1231 : 1237);
        result = (prime * result) + (mineralOnly ? 1231 : 1237);
        result = (prime * result) + minerals;
        result = (prime * result) + ((position == null) ? 0 : position.hashCode());
        result = (prime * result) + (startLocation ? 1231 : 1237);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseLocation other = (BaseLocation) obj;
        if (center == null) {
            if (other.center != null) {
                return false;
            }
        } else if (!center.equals(other.center)) {
            return false;
        }
        if (gas != other.gas) {
            return false;
        }
        if (island != other.island) {
            return false;
        }
        if (mineralOnly != other.mineralOnly) {
            return false;
        }
        if (minerals != other.minerals) {
            return false;
        }
        if (position == null) {
            if (other.position != null) {
                return false;
            }
        } else if (!position.equals(other.position)) {
            return false;
        }
        if (startLocation != other.startLocation) {
            return false;
        }
        return true;
    }
}
