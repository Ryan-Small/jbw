package com.harbinger.jbw;

/** Generalised representation of a position for JNIBWAPI. Immutable. */
public class Position {

    public static class Positions {
        public static final Position Invalid = new Position(1000, 1000, Type.BUILD);
        public static final Position None = new Position(1000, 1001, Type.BUILD);
        public static final Position Unknown = new Position(1000, 1002, Type.BUILD);
    }

    public static enum Type {
        PIXEL(1),
        WALK(8),
        BUILD(32);

        /** Length in pixels */
        public final int scale;

        private Type(final int size) {
            scale = size;
        }
    }

    private final int x;
    private final int y;

    /**
     * Creates a new Position representing the given x and y as Pixel, Walk Tile, or Build Tile
     * coordinates (depending on the Type given).
     */
    public Position(final int x, final int y, final Type type) {
        this.x = x * type.scale;
        this.y = y * type.scale;
    }

    /** Creates a new Position representing the given x and y as Pixel coordinates. */
    public Position(final int x, final int y) {
        this(x, y, Type.PIXEL);
    }

    /** Returns the x-coordinate, in pixels */
    public int getPX() {
        return x / Type.PIXEL.scale;
    }

    /** Returns the y-coordinate, in pixels */
    public int getPY() {
        return y / Type.PIXEL.scale;
    }

    /** Returns the x-coordinate, in walk tiles */
    public int getWX() {
        return x / Type.WALK.scale;
    }

    /** Returns the y-coordinate, in walk tiles */
    public int getWY() {
        return y / Type.WALK.scale;
    }

    /** Returns the x-coordinate, in build tiles */
    public int getBX() {
        return x / Type.BUILD.scale;
    }

    /** Returns the y-coordinate, in build tiles */
    public int getBY() {
        return y / Type.BUILD.scale;
    }

    /**
     * Returns the distance to the target position in Pixel coordinates.
     * 
     * @see #getApproxPDistance(Position)
     */
    public double getPDistance(final Position target) {
        final int dx = x - target.x;
        final int dy = y - target.y;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    public double getWDistance(final Position target) {
        return getPDistance(target) / Type.WALK.scale;
    }

    public double getBDistance(final Position target) {
        return getPDistance(target) / Type.BUILD.scale;
    }

    /**
     * Get an approximate distance to the target position in Pixel coordinates.
     * 
     * Uses Starcraft's approximated distance function, which is reasonably accurate yet avoids a
     * sqrt operation and saves some CPU cycles.
     * 
     * @see #getPDistance(Position)
     **/
    public int getApproxPDistance(final Position target) {
        int min = Math.abs(x - target.x);
        int max = Math.abs(y - target.y);
        if (max < min) {
            final int temp = max;
            max = min;
            min = temp;
        }

        if (min < (max >> 2)) {
            return max;
        }

        final int minCalc = (3 * min) >> 3;
        return (((minCalc >> 5) + minCalc + max) - (max >> 4) - (max >> 6));
    }

    public int getApproxWDistance(final Position target) {
        return getApproxPDistance(target) / Type.WALK.scale;
    }

    public int getApproxBDistance(final Position target) {
        return getApproxPDistance(target) / Type.BUILD.scale;
    }

    /**
     * Returns true if the position is on the map. Note: if map parameter is null, this function
     * will check validity against the largest (256x256) map size.
     */
    public boolean isValid(final GameMap map) {
        if ((x < 0) || (y < 0)) {
            return false;
        }
        if (map == null) {
            return (getBX() < 256) && (getBY() < 256);
        } else {
            return (x < map.getSize().getPX()) && (y < map.getSize().getPY());
        }
    }

    /**
     * Returns a <b>new</b> Position in the closest valid map position. Worked out at Build Tile
     * resolution, like in BWAPI. Note: if map parameter is null, this function will check validity
     * against the largest (256x256) map size.
     */
    public Position makeValid(final GameMap map) {
        if (isValid(map)) {
            return this;
        }
        //
        int newBtX = Math.max(getBX(), 0);
        int newBtY = Math.max(getBY(), 0);
        if (map == null) {
            newBtX = Math.min(newBtX, 256 - 1);
            newBtY = Math.min(newBtY, 256 - 1);
        } else {
            newBtX = Math.min(newBtX, map.getSize().getBX() - 1);
            newBtY = Math.min(newBtY, map.getSize().getBY() - 1);
        }
        return new Position(newBtX, newBtY, Type.BUILD);
    }

    /**
     * Returns a <b>new</b> Position that represents the effect of moving this position by delta
     * (treated as a vector from the origin)
     */
    public Position translated(final Position delta) {
        return new Position(x + delta.x, y + delta.y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + x;
        result = (prime * result) + y;
        return result;
    }

    /** Positions are the same as long as they have the same Pixel coordinates. */
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
        final Position other = (Position) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
