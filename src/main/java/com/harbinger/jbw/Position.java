package com.harbinger.jbw;

import static com.harbinger.jbw.Position.Resolution.BUILD;
import static com.harbinger.jbw.Position.Resolution.PIXEL;

/**
 * Represents a location in (x, y) coordinate space on the map.
 */
public class Position {

    public static final Position NONE = new Position(1000, 1000, BUILD);
    public static final Position UNKNOWN = new Position(1000, 1001, BUILD);
    public static final Position INVALID = new Position(1000, 1002, BUILD);

    /**
     * Indicates the different resolutions a position can have.
     */
    public static enum Resolution {
        PIXEL(1),
        WALK(8),
        BUILD(32);

        /** Length in pixels */
        public final int scale;

        private Resolution(final int scale) {
            this.scale = scale;
        }
    }

    private final int x;
    private final int y;

    /**
     * Constructs and initializes the position to represent the x and y coordinate.
     *
     * @param x
     *            the x-coordinate
     *
     * @param y
     *            the y-coordinate
     *
     * @param resolution
     *            the resolution that is being used for the specified x and y coordinates
     */
    public Position(final int x, final int y, final Resolution resolution) {
        this.x = x * resolution.scale;
        this.y = y * resolution.scale;
    }

    /**
     * @param resolution
     *            the resolution to use when returning the x-coordinate
     *
     * @return the x-coordinate at the specified resolution
     */
    public int getX(final Resolution resolution) {
        return x / resolution.scale;
    }

    /**
     * @param resolution
     *            the resolution to use when returning the y-coordinate
     *
     * @return the y-coordinate at the specified resolution
     */
    public int getY(final Resolution resolution) {
        return y / resolution.scale;
    }

    /**
     * Convenience method for getting the x-coordinate at the {@code PIXEL} resolution. Equivalent
     * to {@code getX(Resolution.PIXEL);}.
     *
     * @return the x-coordinate at the {@code PIXEL} resolution
     */
    public int getPX() {
        return getX(Resolution.PIXEL);
    }

    /**
     * Convenience method for getting the y-coordinate at the {@code PIXEL} resolution. Equivalent
     * to {@code getY(Resolution.PIXEL);}.
     *
     * @return the y-coordinate at the {@code PIXEL} resolution
     */
    public int getPY() {
        return getY(Resolution.PIXEL);
    }

    /**
     * Convenience method for getting the x-coordinate at the {@code WALK} resolution. Equivalent to
     * {@code getX(Resolution.WALK)}.
     *
     * @return the x-coordinate at the {@code WALK} resolution
     */
    public int getWX() {
        return getX(Resolution.WALK);
    }

    /**
     * Convenience method for getting the y-coordinate at the {@code WALK} resolution. Equivalent to
     * {@code getY(Resolution.WALK)}.
     *
     * @return the y-coordinate at the {@code WALK} resolution
     */
    public int getWY() {
        return getY(Resolution.WALK);
    }

    /**
     * Convenience method for getting the x-coordinate at the {@code BUILD} resolution. Equivalent
     * to {@code getX(Resolution.BUILD)}.
     *
     * @return the x-coordinate at the {@code BUILD} resolution
     */
    public int getBX() {
        return getX(Resolution.BUILD);
    }

    /**
     * Convenience method for getting the y-coordinate at the {@code BUILD} resolution. Equivalent
     * to {@code getY(Resolution.BUILD)}.
     *
     * @return the y-coordinate at the {@code BUILD} resolution
     */
    public int getBY() {
        return getY(Resolution.BUILD);
    }

    /**
     * Returns the distance to the target position in pixels.
     *
     * @param target
     *            the target position
     *
     * @param resolution
     *            the resolution to use when returning the distance
     *
     * @return the distance to the target position at the specified resolution
     */
    public double getDistance(final Position target, final Resolution resolution) {
        final int dx = x - target.x;
        final int dy = y - target.y;
        return Math.sqrt((dx * dx) + (dy * dy)) / resolution.scale;
    }

    /**
     * Calculates the approximate distance to a target position.
     *
     * <p>
     * Uses Starcraft's approximated distance function, which is reasonably accurate yet avoids the
     * square root operation and saves some CPU cycles.
     *
     * @param target
     *            the target position
     *
     * @param resolution
     *            the resolution to use when returning the approximate distance
     *
     * @return the approximate distance between to the target position at the specified resolution
     */
    public int getApproxDistance(final Position target, final Resolution resolution) {
        final int dx = Math.abs(x - target.x);
        final int dy = Math.abs(y - target.y);

        final int min = Math.min(dx, dy);
        final int max = Math.max(dx, dy);

        if (min < (max >> 2)) {
            return max;
        }

        final int minCalc = (3 * min) >> 3;
        return (((minCalc >> 5) + minCalc + max) - (max >> 4) - (max >> 6)) / resolution.scale;
    }

    /**
     * Returns a new Position that represents the effect of moving this position by delta (treated
     * as a vector from the origin).
     *
     * @param x
     *            the x delta in pixels
     *
     * @param y
     *            the y delta in pixels
     *
     * @return the new translated position
     */
    public Position translated(final int x, final int y) {
        return translated(new Position(x, y, PIXEL));
    }

    /**
     * Returns a new Position that represents the effect of moving this position by delta (treated
     * as a vector from the origin).
     *
     * @param delta
     *            the delta to be applied
     *
     * @return the new translated position
     */
    public Position translated(final Position delta) {
        return new Position(x + delta.x, y + delta.y, PIXEL);
    }

    /**
     * @param map
     *            the GameMap to verify Position against
     * 
     * @return true if the Position is on the GameMap; false otherwise
     */
    public boolean isValid(final GameMap map) {
        if ((x < 0) || (y < 0)) {
            return false;
        } else {
            return (x < map.getSize().getX(PIXEL)) && (y < map.getSize().getY(PIXEL));
        }
    }

    /**
     * @return the hash code for this position
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + x;
        result = (prime * result) + y;
        return result;
    }

    /**
     * Indicates whether or not two positions are equal.
     *
     * @param obj
     *            the object to be compared
     *
     * @return true if the object to be compared is an instance of Position and points to the same
     *         location, at the pixel resolution resolution, as this position; false otherwise
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
        final Position other = (Position) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    /**
     * @return the String representation of this position
     */
    @Override
    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
