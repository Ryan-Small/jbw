package com.harbinger.jbw;

/**
 * http://code.google.com/p/bwapi/wiki/UnitSizeType
 */
public enum UnitSizeType {

    INDEPENDENT(0),
    SMALL(1),
    MEDIUM(2),
    LARGE(3),
    NONE(4),
    UNKNOWN(5);

    static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public static UnitSizeType getUnitSizeType(final int id) {
        for (final UnitSizeType type : UnitSizeType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private UnitSizeType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * @return name for this type of size
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of size
     */
    public int getId() {
        return id;
    }
}
