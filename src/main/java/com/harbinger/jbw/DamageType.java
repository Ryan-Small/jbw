package com.harbinger.jbw;

/**
 * Represents a type of damage that could be generated from a weapon.
 */
public enum DamageType {

    INDEPENDENT(0),
    EXPLOSIVE(1),
    CONCUSSIVE(2),
    NORMAL(3),
    IGNORE_ARMOR(4),
    NONE(5),
    UNKNOWN(6);

    static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public static DamageType getDamageType(final int id) {
        for (final DamageType type : DamageType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private DamageType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * @return name for this type of damage
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of damage
     */
    public int getId() {
        return id;
    }
}
