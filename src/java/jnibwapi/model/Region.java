package jnibwapi.model;

import java.util.*;

/**
 * Represents a region in a StarCraft map.
 * 
 * For a description of fields see: http://code.google.com/p/bwta/wiki/Region
 */
public class Region {

    public static final int NUM_ATTRIBUTES = 3;

    private final int ID;
    private final int centerX;
    private final int centerY;
    private final int[] coordinates;
    private final Set<Region> connectedRegions = new HashSet<>();
    private final Set<ChokePoint> chokePoints = new HashSet<>();
    private Set<Region> allConnectedRegions = null;

    public Region(final int[] data, int index, final int[] coordinates) {
        ID = data[index++];
        centerX = data[index++];
        centerY = data[index++];
        this.coordinates = coordinates;
    }

    public int getID() {
        return ID;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int[] getCoordinates() {
        return Arrays.copyOf(coordinates, coordinates.length);
    }

    protected void addChokePoint(final ChokePoint chokePoint) {
        chokePoints.add(chokePoint);
    }

    public Set<ChokePoint> getChokePoints() {
        return Collections.unmodifiableSet(chokePoints);
    }

    protected void addConnectedRegion(final Region other) {
        connectedRegions.add(other);
    }

    public Set<Region> getConnectedRegions() {
        return Collections.unmodifiableSet(connectedRegions);
    }

    /** Get all transitively connected regions for a given region */
    public Set<Region> getAllConnectedRegions() {
        // Evaluate on first call
        if (allConnectedRegions == null) {
            allConnectedRegions = new HashSet<Region>();
            final LinkedList<Region> unexplored = new LinkedList<Region>();
            unexplored.add(this);
            while (!unexplored.isEmpty()) {
                final Region current = unexplored.remove();
                if (allConnectedRegions.add(current)) {
                    unexplored.addAll(current.getConnectedRegions());
                }
            }
        }
        return Collections.unmodifiableSet(allConnectedRegions);
    }

}
