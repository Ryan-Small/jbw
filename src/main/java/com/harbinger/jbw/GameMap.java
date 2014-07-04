package com.harbinger.jbw;

import com.harbinger.jbw.Position.Resolution;
import com.harbinger.jbw.Type.UnitType;

import java.awt.Point;
import java.util.*;

/**
 * Stores information about a StarCraft map.
 */
public class GameMap {
    public static final int TILE_SIZE = 32;

    private final Position size;
    private final String name;
    private final String fileName;
    private final String hash;
    private final int[] heightMap;
    private final boolean[] buildable;
    private final boolean[] walkable;
    /** Walkability of build tiles */
    private final boolean[] lowResWalkable;

    // The following are set in initialize() method
    /** Region ID for each build tile */
    private int[] regionMap = null;
    private List<Region> regions = null;
    private List<ChokePoint> chokePoints = null;
    private List<BaseLocation> baseLocations = null;
    private HashMap<Integer, Region> idToRegion = null;

    public GameMap(final String name, final String fileName, final String hash, final int width,
            final int height, final int[] heightMap, final int[] buildable, final int[] walkable) {
        size = new Position(width, height, Resolution.BUILD);
        this.name = name;
        this.fileName = fileName;
        this.hash = hash;
        this.heightMap = heightMap;
        this.buildable = new boolean[buildable.length];
        this.walkable = new boolean[walkable.length];

        for (int i = 0; i < buildable.length; i++) {
            this.buildable[i] = (buildable[i] == 1);
        }

        for (int i = 0; i < walkable.length; i++) {
            this.walkable[i] = (walkable[i] == 1);
        }

        // Fill lowResWalkable for A* search
        lowResWalkable = new boolean[width * height];
        Arrays.fill(lowResWalkable, true);
        for (int wx = 0; wx < size.getX(Resolution.WALK); wx++) {
            for (int wy = 0; wy < size.getY(Resolution.WALK); wy++) {
                lowResWalkable[(wx / 4) + (width * (wy / 4))] &=
                        isWalkable(new Position(wx, wy, Resolution.WALK));
            }
        }
    }

    /** Initialise the map with regions and base locations */
    public void initialize(final int[] regionMapData, final int[] regionData,
            final HashMap<Integer, int[]> regionPolygons, final int[] chokePointData,
            final int[] baseLocationData) {
        // regionMap
        regionMap = regionMapData;

        // regions
        regions = new ArrayList<>();
        if (regionData != null) {
            for (int index = 0; index < regionData.length; index += Region.NUM_ATTRIBUTES) {
                final int[] coordinates = regionPolygons.get(regionData[index]);
                final Region region = new Region(regionData, index, coordinates);
                regions.add(region);
            }
        }
        idToRegion = new HashMap<>();
        for (final Region region : regions) {
            idToRegion.put(region.getId(), region);
        }

        // choke points
        chokePoints = new ArrayList<>();
        if (chokePointData != null) {
            for (int index = 0; index < chokePointData.length; index += ChokePoint.NUM_ATTRIBUTES) {
                final ChokePoint chokePoint = new ChokePoint(chokePointData, index, idToRegion);
                chokePoints.add(chokePoint);
            }
        }

        // base locations
        baseLocations = new ArrayList<>();
        if (baseLocationData != null) {
            for (int index = 0; index < baseLocationData.length; index +=
                    BaseLocation.NUM_ATTRIBUTES) {
                final BaseLocation baseLocation =
                        new BaseLocation(baseLocationData, index, idToRegion);
                baseLocations.add(baseLocation);
            }
        }

        // connect the region graph
        for (final ChokePoint chokePoint : chokePoints) {
            chokePoint.getFirstRegion().addChokePoint(chokePoint);
            chokePoint.getFirstRegion().addConnectedRegion(chokePoint.getSecondRegion());
            chokePoint.getSecondRegion().addChokePoint(chokePoint);
            chokePoint.getSecondRegion().addConnectedRegion(chokePoint.getFirstRegion());
        }
    }

    /** Get the map size as a Position object */
    public Position getSize() {
        return size;
    }

    /** @deprecated Width in build tiles (32px). Use {@link #getSize()} instead. */
    @Deprecated
    public int getWidth() {
        return size.getX(Resolution.BUILD);
    }

    /** @deprecated Height in build tiles (32px). Use {@link #getSize()} instead. */
    @Deprecated
    public int getHeight() {
        return size.getY(Resolution.BUILD);
    }

    /** @deprecated Height in build tiles (32px). Use {@link #getSize()} instead. */
    @Deprecated
    public int getWalkWidth() {
        return size.getX(Resolution.WALK);
    }

    /** @deprecated Height in build tiles (32px). Use {@link #getSize()} instead. */
    @Deprecated
    public int getWalkHeight() {
        return size.getY(Resolution.WALK);
    }

    /** The name of the current map */
    public String getName() {
        return name;
    }

    /** The file name of the current map / replay file */
    public String getFileName() {
        return fileName;
    }

    public String getHash() {
        return hash;
    }

    /** Converts a position to a 1-dimensional build tile array index for this map */
    private int getBuildTileArrayIndex(final Position p) {
        return p.getX(Resolution.BUILD) + (size.getX(Resolution.BUILD) * p.getY(Resolution.BUILD));
    }

    public int getGroundHeight(final Position p) {
        if (p.isValid(this)) {
            return heightMap[getBuildTileArrayIndex(p)];
        } else {
            return 0;
        }
    }

    /** Works only after initialize(). Returns null if the specified position is invalid. */
    public Region getRegion(final Position p) {
        if (p.isValid(this)) {
            return idToRegion.get(regionMap[getBuildTileArrayIndex(p)]);
        } else {
            return null;
        }
    }

    public boolean isBuildable(final Position p) {
        if (p.isValid(this)) {
            return buildable[getBuildTileArrayIndex(p)];
        } else {
            return false;
        }
    }

    public boolean isWalkable(final Position p) {
        if (p.isValid(this)) {
            return walkable[p.getX(Resolution.WALK)
                            + (size.getX(Resolution.WALK) * p.getY(Resolution.WALK))];
        } else {
            return false;
        }
    }

    /** Checks whether all 16 walk tiles in a build tile are walkable */
    public boolean isLowResWalkable(final Position p) {
        if (p.isValid(this)) {
            return lowResWalkable[getBuildTileArrayIndex(p)];
        } else {
            return false;
        }
    }

    /** Works only after initialize() */
    public List<Region> getRegions() {
        return Collections.unmodifiableList(regions);
    }

    /** Works only after initialize() */
    public Region getRegion(final int regionID) {
        return idToRegion.get(regionID);
    }

    /** Works only after initialize() */
    public List<ChokePoint> getChokePoints() {
        return Collections.unmodifiableList(chokePoints);
    }

    /** Works only after initialize() */
    public List<BaseLocation> getBaseLocations() {
        return Collections.unmodifiableList(baseLocations);
    }

    /** Works only after initialize() */
    public List<BaseLocation> getStartLocations() {
        final List<BaseLocation> startLocations = new ArrayList<>();
        for (final BaseLocation bl : baseLocations) {
            if (bl.isStartLocation()) {
                startLocations.add(bl);
            }
        }
        return startLocations;
    }

    /**
     * Find the shortest walkable distance, in pixels, between two tile positions or -1 if not
     * reachable. Works only after initialize(). Ported from BWTA.
     */
    public double getGroundDistance(final Position start, final Position end) {
        if (!isConnected(start, end)) {
            return -1;
        }
        return aStarSearchDistance(start.getX(Resolution.BUILD), start.getY(Resolution.BUILD),
                end.getX(Resolution.BUILD), end.getY(Resolution.BUILD));
    }

    /**
     * Based on map connectedness only. Ignores buildings. Works only after initialize(). Ported
     * from BWTA.
     */
    public boolean isConnected(final Position start, final Position end) {
        if (getRegion(start) == null) {
            return false;
        }
        if (getRegion(end) == null) {
            return false;
        }
        return getRegion(start).getAllConnectedRegions().contains(getRegion(end));
    }

    /**
     * Performs an A* search. Intended to be called from
     * {@link #getGroundDistance(int, int, int, int)}. Ported from BWTA.
     */
    private double aStarSearchDistance(final int startTx, final int startTy, final int endTx,
            final int endTy) {
        // Distance of 10 per build tile, or sqrt(10^2 + 10^2) ~= 14 diagonally
        final int mvmtCost = 10;
        final int mvmtCostDiag = 14;
        final PriorityQueue<AStarTile> openTiles = new PriorityQueue<>(); // min heap
        // Map from tile to distance
        final HashMap<Point, Integer> gmap = new HashMap<>();
        final HashSet<Point> closedTiles = new HashSet<>();
        final Point start = new Point(startTx, startTy);
        final Point end = new Point(endTx, endTy);
        openTiles.add(new AStarTile(start, 0));
        gmap.put(start, 0);
        while (!openTiles.isEmpty()) {
            final Point p = openTiles.poll().tilePos;
            if (p.equals(end)) {
                return (gmap.get(p) * TILE_SIZE) / (double) mvmtCost;
            }
            final int gvalue = gmap.get(p);
            closedTiles.add(p);
            // Explore the neighbours of p
            final int minx = Math.max(p.x - 1, 0);
            final int maxx = Math.min(p.x + 1, size.getX(Resolution.BUILD) - 1);
            final int miny = Math.max(p.y - 1, 0);
            final int maxy = Math.min(p.y + 1, size.getY(Resolution.BUILD) - 1);
            for (int x = minx; x <= maxx; x++) {
                for (int y = miny; y <= maxy; y++) {
                    if (!isLowResWalkable(new Position(x, y, Resolution.BUILD))) {
                        continue;
                    }
                    if ((p.x != x) && (p.y != y)
                            && !isLowResWalkable(new Position(p.x, y, Resolution.BUILD))
                            && !isLowResWalkable(new Position(x, p.y, Resolution.BUILD))) {
                        continue; // Not diagonally accessible
                    }
                    final Point t = new Point(x, y);
                    if (closedTiles.contains(t)) {
                        continue;
                    }

                    int g = gvalue + mvmtCost;
                    if ((x != p.x) && (y != p.y)) {
                        g = gvalue + mvmtCostDiag;
                    }
                    final int dx = Math.abs(x - end.x);
                    final int dy = Math.abs(y - end.y);
                    // Heuristic for remaining distance:
                    // min(dx, dy) is the minimum diagonal distance, so costs mvmtCostDiag
                    // abs(dx - dy) is the rest of the distance, so costs mvmtCost
                    final int h =
                            (Math.abs(dx - dy) * mvmtCost) + (Math.min(dx, dy) * mvmtCostDiag);
                    final int f = g + h;
                    if (!gmap.containsKey(t) || (gmap.get(t) > g)) {
                        gmap.put(t, g);
                        for (final Iterator<AStarTile> it = openTiles.iterator(); it.hasNext();) {
                            if (it.next().tilePos.equals(t)) {
                                it.remove();
                            }
                        }
                        openTiles.add(new AStarTile(t, f));
                    }
                }
            }
        }
        // Not found
        return -1;
    }

    private static class AStarTile implements Comparable<AStarTile> {
        Point tilePos;
        int distPlusCost;

        public AStarTile(final Point tile, final int distance) {
            tilePos = tile;
            distPlusCost = distance;
        }

        @Override
        public int compareTo(final AStarTile o) {
            return Integer.compare(distPlusCost, o.distPlusCost);
        }
    }

    /**
     * Debugging method to check terrain has been analysed properly. Taken from BWAPI's
     * ExampleAIClient
     */
    public void drawTerrainData(final Broodwar bwapi) {
        // iterate through all the base locations and draw their outlines
        for (final BaseLocation bl : bwapi.getMap().getBaseLocations()) {
            final Position p = bl.getPosition();

            // draw outline of base location
            bwapi.drawRectangleMap(p, 128, 96, BWColor.BLUE, false);

            // if this is an island expansion, draw a yellow circle around the base location
            if (bl.isIsland()) {
                bwapi.drawCircleMap(p.translated(new Position(2, 1, Resolution.BUILD)), 80,
                        BWColor.YELLOW, false);
            }

            // draw a circle at each mineral patch and a box at each vespene geyser
            for (final Unit u : bwapi.getNeutralUnits()) {
                final UnitType ut = u.getType();
                if (ut.isResourceContainer()) {
                    // if (ut.isMineralField()) {
                    // // Minerals
                    // bwapi.drawCircleMap(u.getTargetPosition(), 30, BWColor.Cyan, false);
                    // } else {
                    // // Geysers
                    // bwapi.drawRectangleMap(u.getTopLeft(), u.getBottomRight(), BWColor.Orange,
                    // false);
                    // }
                }
            }
        }

        // Iterate through all the regions and draw the polygon outline of it in green.
        for (final Region r : getRegions()) {
            final Position[] polygon = r.getPolygon();
            for (int i = 0; i < polygon.length; i++) {
                final Position point1 = polygon[i];
                final Position point2 = polygon[(i + 1) % polygon.length];
                bwapi.drawLineMap(point1, point2, BWColor.GREEN);
            }
        }

        // Visualise the chokepoints with red lines
        for (final ChokePoint cp : getChokePoints()) {
            final Position point1 = cp.getFirstSide();
            final Position point2 = cp.getSecondSide();
            bwapi.drawLineMap(point1, point2, BWColor.RED);
        }
    }
}
