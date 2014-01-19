/*
 * Copyright 2014 IDEXX Laboratories, Inc. All rights reserved.
 * IDEXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package jnibwapi.model;

/**
 *
 */
public class Location {

    public final int x;
    public final int y;

    public Location(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static class Walk extends Location {
        public Walk(final int x, final int y) {
            super(x, y);
        }
    }

    public static class Tile extends Location {

        public static final int TILE_SIZE = 32;

        public Tile(final int x, final int y) {
            super(x, y);
        }
    }

    /**
     * Returns a String representation of this location.
     * 
     * @return a string representation of this location
     */
    @Override
    public String toString() {
        return getClass().getName() + "[x = " + x + ", y = " + y + "]";
    }
}
