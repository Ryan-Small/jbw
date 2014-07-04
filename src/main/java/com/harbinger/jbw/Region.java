package com.harbinger.jbw;

import com.harbinger.jbw.Position.Resolution;

import java.util.*;

/**
 * Represents a region of the map with a polygon boundary, and is connected to other regions via
 * {@link ChokePoint}.
 */
public class Region {

    static final int NUM_ATTRIBUTES = 3;

    private final int id;
    private final Position center;
    private final Position[] polygon;
    private final Set<Region> connectedRegions = new HashSet<>();
    private final Set<ChokePoint> chokePoints = new HashSet<>();
    private Set<Region> allConnectedRegions = null;

    Region(final int[] data, int index, final int[] coordinates) {
        id = data[index++];
        final int centerX = data[index++];
        final int centerY = data[index++];
        center = new Position(centerX, centerY, Resolution.PIXEL);
        polygon = new Position[coordinates.length / 2];
        for (int i = 0; i < coordinates.length; i += 2) {
            polygon[i / 2] = new Position(coordinates[i], coordinates[i + 1], Resolution.PIXEL);
        }
    }

    int getId() {
        return id;
    }

    void addChokePoint(final ChokePoint chokePoint) {
        chokePoints.add(chokePoint);
    }

    void addConnectedRegion(final Region other) {
        connectedRegions.add(other);
    }

    /**
     * @return the center of this region
     */
    public Position getCenter() {
        return center;
    }

    /**
     * @return the polygon border of this region
     */
    public Position[] getPolygon() {
        return Arrays.copyOf(polygon, polygon.length);
    }

    /**
     * @return the ChokePoints adjacent to this region
     */
    public Set<ChokePoint> getChokePoints() {
        return Collections.unmodifiableSet(chokePoints);
    }

    /**
     * @return the Regions connected to this region
     */
    public Set<Region> getConnectedRegions() {
        return Collections.unmodifiableSet(connectedRegions);
    }

    // Get all transitively connected regions for a given region
    public Set<Region> getAllConnectedRegions() {
        // Evaluate on first call
        if (allConnectedRegions == null) {
            allConnectedRegions = new HashSet<>();
            final LinkedList<Region> unexplored = new LinkedList<>();
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
