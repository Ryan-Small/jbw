package com.harbinger.jbw;

/**
 * see http://code.google.com/p/bwapi/wiki/Error for descriptions
 */
public enum ErrorCode {

    /**
     * Indicates that there was an attempt to order a unit or request information about a unit that
     * does not exist.
     */
    // TODO: What if the unit did exist and was killed?
    UNIT_DOES_NOT_EXIST(0),

    /**
     * Indicates that there was an attempt to request information about a unit that is not currently
     * visible or is dead.
     */
    UNIT_NOT_VISIBLE(1),

    /**
     * Indicates that there was an attempt to order a unit that is not currently visible or is dead.
     */
    UNIT_NOT_OWNED(2),

    /**
     * Indicates that an attempt to oder a unit to do something when it is in a state which prevents
     * it from performing the requested order.
     */
    UNIT_BUSY(3),

    /** Indicates that there was an attempt to perform an imvalid action. */
    INCOMPATIBLE_UNITTYPE(4),

    /** */
    INCOMPATIBLE_TECHTYPE(5),

    /** */
    INCOMPATIBLE_STATE(6),

    /** */
    ALREADY_RESEARCHED(7),

    /** */
    FULLY_UPGRADED(8),

    /** */
    CURRENTLY_RESEARCHING(9),

    /** */
    CURRENTLY_UPGRADING(10),

    /** */
    INSUFFICIENT_MINERALS(11),

    /** */
    INSUFFICIENT_GAS(12),

    /** */
    INSUFFICIENT_SUPPLY(13),

    /** */
    INSUFFICIENT_ENERGY(14),

    /** */
    INSUFFICIENT_TECH(15),

    /** */
    INSUFFICIENT_AMMO(16),

    /** */
    INSUFFICIENT_SPACE(17),

    /** */
    INVALID_TILE_POSITION(18),

    /** */
    UNBUILDABLE_LOCATION(19),

    /** */
    UNREACHABLE_LOCATION(20),

    /** */
    OUT_OF_RANGE(21),

    /** */
    UNABLE_TO_HIT(22),

    /** */
    ACCESS_DENIED(23),

    /** */
    FILE_NOT_FOUND(24),

    /** */
    INVALID_PARAMETER(25),

    /** Indicates that no error has been encountered. */
    NONE(26),

    /** Indicates that the error is unknown. */
    UNKNOWN(27);

    private final int id;

    private ErrorCode(final int id) {
        this.id = id;
    }
}
