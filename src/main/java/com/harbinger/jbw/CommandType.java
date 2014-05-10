package com.harbinger.jbw;

/**
 * Represents a command that can be given to a unit.
 */
public enum CommandType {

    ATTACK_MOVE(0),
    ATTACK_UNIT(1),
    BUILD(2),
    BUILD_ADDON(3),
    TRAIN(4),
    MORPH(5),
    RESEARCH(6),
    UPGRADE(7),
    SET_RALLY_POSITION(8),
    SET_RALLY_UNIT(9),
    MOVE(10),
    PATROL(11),
    HOLD_POSITION(12),
    STOP(13),
    FOLLOW(14),
    GATHER(15),
    RETURN_CARGO(16),
    REPIAR(17),
    BURROW(18),
    UNBURROW(19),
    CLOAK(20),
    DECLOAK(21),
    SIEGE(22),
    UNSIEGE(23),
    LIFE(24),
    LAND(25),
    LOAD(26),
    UNLOAD(27),
    UNLOAD_ALL(28),
    UNLOAD_ALL_POSITION(29),
    RIGHT_CLICK_POSITION(30),
    RIGHT_CLICK_UNIT(31),
    HALT_CONSTRUCTION(32),
    CANCEL_CONSTRUCTION(33),
    CANCEL_ADDON(34),
    CANCEL_TRAIN(35),
    CANCEL_TRAIN_SLOT(36),
    CANCEL_MORPH(37),
    CANCEL_RESEARCH(38),
    CANCEL_UPGRADE(39),
    USE_TECH(40),
    USE_TECH_POSITION(41),
    USE_TECH_UNIT(42),
    PLACE_COP(43),
    NONE(44),
    UNKNOWN(45);

    static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public static CommandType getCommandType(final int id) {
        for (final CommandType type : CommandType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private CommandType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * @return name for this type of command
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of command
     */
    public int getId() {
        return id;
    }
}
