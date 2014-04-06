package com.harbinger.jbw;

/**
 * see http://code.google.com/p/bwapi/wiki/Error for descriptions
 */
public class ErrorCode {

    /**
     * Indicates that there was an attempt to order a unit or request information about a unit that
     * does not exist.
     */
    // TODO: What if the unit did exist and was killed?
    public static final int UNIT_DOES_NOT_EXIST = 0;

    /**
     * Indicates that there was an attempt to request information about a unit that is not currently
     * visible or is dead.
     */
    public static final int UNIT_NOT_VISIBLE = 1;

    /**
     * Indicates that there was an attempt to order a unit that is not currently visible or is dead.
     */
    public static final int UNIT_NOT_OWNED = 2;

    /**
     * Indicates that an attempt to oder a unit to do something when it is in a state which prevents
     * it from performing the requested order.
     */
    public static final int UNIT_BUSY = 3;

    /** Indicates that there was an attempt to perform an imvalid action. */
    public static final int INCOMPATIBLE_UNITTYPE = 4;

    /** */
    public static final int INCOMPATIBLE_TECHTYPE = 5;

    /** */
    public static final int INCOMPATIBLE_STATE = 6;

    /** */
    public static final int ALREADY_RESEARCHED = 7;

    /** */
    public static final int FULLY_UPGRADED = 8;

    /** */
    public static final int CURRENTLY_RESEARCHING = 9;

    /** */
    public static final int CURRENTLY_UPGRADING = 10;

    /** */
    public static final int INSUFFICIENT_MINERALS = 11;

    /** */
    public static final int INSUFFICIENT_GAS = 12;

    /** */
    public static final int INSUFFICIENT_SUPPLY = 13;

    /** */
    public static final int INSUFFICIENT_ENERGY = 14;

    /** */
    public static final int INSUFFICIENT_TECH = 15;

    /** */
    public static final int INSUFFICIENT_AMMO = 16;

    /** */
    public static final int INSUFFICIENT_SPACE = 17;

    /** */
    public static final int INVALID_TILE_POSITION = 18;

    /** */
    public static final int UNBUILDABLE_LOCATION = 19;

    /** */
    public static final int UNREACHABLE_LOCATION = 20;

    /** */
    public static final int OUT_OF_RANGE = 21;

    /** */
    public static final int UNABLE_TO_HIT = 22;

    /** */
    public static final int ACCESS_DENIED = 23;

    /** */
    public static final int FILE_NOT_FOUND = 24;

    /** */
    public static final int INVALID_PARAMETER = 25;

    /** Indicates that no error has been encountered. */
    public static final int NONE = 26;

    /** Indicates that the error is unknown. */
    public static final int UNKNOWN = 27;
}
