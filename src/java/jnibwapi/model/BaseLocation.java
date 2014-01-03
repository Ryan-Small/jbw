package jnibwapi.model;

/**
 * Represents a location on the map where it makes sense to place a base (e.g. near minerals).
 */
public class BaseLocation {

    public static final int NUM_ATTRIBUTES = 10;

    private final int x;
    private final int y;
    private final int tileX;
    private final int tileY;
    private final int regionId;
    private final int minerals;
    private final int gas;
    private final boolean island;
    private final boolean mineralOnly;
    private final boolean startLocation;

    public BaseLocation(final int[] data, int index) {
        x = data[index++];
        y = data[index++];
        tileX = data[index++];
        tileY = data[index++];
        regionId = data[index++];
        minerals = data[index++];
        gas = data[index++];
        island = (data[index++] == 1);
        mineralOnly = (data[index++] == 1);
        startLocation = (data[index++] == 1);
    }

    /**
     * Returns the center x position of the base location.
     * 
     * @return center x position of the base location
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the center y position of the base location.
     * 
     * @return center y position of the base location
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the x tile position of the base location.
     * 
     * @return x tile position of the base location
     */
    public int getTileX() {
        return tileX;
    }

    /**
     * Returns the y tile position of the base location.
     * 
     * @return y tile position of the base location
     */
    public int getTileY() {
        return tileY;
    }

    // TODO: Comment and return region over int.
    public int getRegionId() {
        return regionId;
    }

    /**
     * Returns the total mineral resource count of all mineral patches.
     * 
     * @return total mineral resource count of all mineral patches
     */
    public int getMinerals() {
        return minerals;
    }

    /**
     * Returns the total gas resource count of all vespene geysers.
     * 
     * @return total gas resource count of all vespene geysers
     */
    public int getGas() {
        return gas;
    }

    /**
     * Returns {@code true} if the base location is not reachable by ground from any other base
     * location; {@code false} otherwise.
     * 
     * @return {@code true} if the base location is not reachable by ground from any other base
     *         location; {@code false} otherwise
     */
    public boolean isIsland() {
        return island;
    }

    /**
     * Returns {@code true} if the base location is mineral-only; {@code false} otherwise.
     * 
     * @return {@code true} if the base location is mineral-only; {@code false} otherwise
     */
    public boolean isMineralOnly() {
        return mineralOnly;
    }

    /**
     * Returns {@true} if the base location is a start location; {@code false} otherwise.
     * 
     * @return {@true} if the base location is a start location; {@code false} otherwise
     */
    public boolean isStartLocation() {
        return startLocation;
    }
}
