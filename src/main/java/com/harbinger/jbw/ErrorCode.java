package com.harbinger.jbw;

/**
 * Indicates the reason why a request could not be satisfied.
 *
 * @see Broodwar#getLastError()
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

    /** Indicates that there was an attempt to perform an invalid action. */
    INCOMPATIBLE_UNITTYPE(4),

    /** Indicates that the wrong tech type was used with the wrong {@code Unit.useTech} method. */
    INCOMPATIBLE_TECHTYPE(5),

    /**
     * Indicates that that an action was request that is inappropriate for this state (i.e. cancel
     * an upgrade that isn't upgrading).
     */
    INCOMPATIBLE_STATE(6),

    /** Indicates that the requested research has already been researched. */
    ALREADY_RESEARCHED(7),

    /** Indicates that the requested upgrade has already been upgraded. */
    FULLY_UPGRADED(8),

    /** Indicates that the requested research is currently in progress. */
    CURRENTLY_RESEARCHING(9),

    /** Indicates that the requested upgrade is currently in progress. */
    CURRENTLY_UPGRADING(10),

    /** Indicates that there is not enough minerals to initiate the requested action. */
    INSUFFICIENT_MINERALS(11),

    /** Indicates that there is not enough gas to initiate the requested action. */
    INSUFFICIENT_GAS(12),

    /** Indicates that there is not enough supply to initiate the requested action. */
    INSUFFICIENT_SUPPLY(13),

    /** Indicates that there is not enough energy to initiate the requested action. */
    INSUFFICIENT_ENERGY(14),

    /**
     * Indicates that the appropriate tech is not available to initiate the requested action (i.e.
     * attempting to train a Medic without an Academy).
     */
    INSUFFICIENT_TECH(15),

    /**
     * Indicates that there is not enough ammo to initiate the requested action (i.e. attempting to
     * lay down a Spider Mine when none are left)
     */
    INSUFFICIENT_AMMO(16),

    /**
     * Indicates that there is not enough space to initiate the requested action (i.e. attempting to
     * train more Interceptors than a Carrier can hold.
     */
    INSUFFICIENT_SPACE(17),

    /** Indicates that a building cannot be built because the requested position is invalid. */
    INVALID_TILE_POSITION(18),

    /**
     * Indicates that the building cannot be built because the requested position is not buildable
     * (i.e. a Unit is in the way).
     */
    UNBUILDABLE_LOCATION(19),

    /** Indicates that the building cannot be built because the worker cannot reach the position. */
    UNREACHABLE_LOCATION(20),

    /** Indicates that an immovable unit, that has been requested to attack, is out of range. */
    OUT_OF_RANGE(21),

    /**
     * Indicates that a Unit, ordered to attack, cannot attack the unit (i.e. attempting to order a
     * Vulture to attack a Scout.
     */
    UNABLE_TO_HIT(22),

    /**
     * Indicates that the requested information is unaccessible (i.e. attempting to access the
     * enemy's resource count.
     */
    ACCESS_DENIED(23),

    // TODO: Like when/
    /** Indicates that the function cannot find a required file. */
    FILE_NOT_FOUND(24),

    /** Indicates that a request contained a bad parameter. */
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
