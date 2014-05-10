package com.harbinger.jbw;

/**
 * http://code.google.com/p/bwapi/wiki/ExplosionType
 */
public enum ExplosionType {

    NONE(0),
    NORMAL(1),
    RADIAL_SPLASH(2),
    ENEMY_SPLASH(3),
    LOCKDOWN(4),
    NUCLEAR_MISSILE(5),
    PARASITE(6),
    BROODLINGS(7),
    EMP_SHOCKWAVE(8),
    IRRADIATE(9),
    ENSNARE(10),
    PLAGUE(11),
    STASIS_FIELD(12),
    DARK_SWARM(13),
    CONSUME(14),
    YAMATO_GUN(15),
    RESTORATION(16),
    DISRUPTION_WEB(17),
    CORROSIVE_ACID(18),
    MIND_CONTROL(19),
    FEEDBACK(20),
    OPTICAL_FLARE(21),
    MAELSTROM(22),
    UNDEFINED23(23),
    AIR_SPLASH(24),
    UNKNOWN(25);

    static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public static ExplosionType getExplosionType(final int id) {
        for (final ExplosionType type : ExplosionType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private ExplosionType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * @return name for this type of explosion
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of explosion
     */
    public int getId() {
        return id;
    }
}
