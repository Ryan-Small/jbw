package com.harbinger.jbw;

/**
 * Represents an individual bullets, missiles, spells, and generally any sort of non-melee attack.
 */
public enum BulletType {

    MELEE(0),
    FUSION_CUTTER(141),
    GAUSS_RIFLE(142),
    C_10_CANISTER_RIFLE(143),
    GEMINI_MISSILES(144),
    FRAGMENTATION_GRENDAE(145),
    LONGBOLT_MISSILE(146),
    UNDEFINED147(147),
    ATS_ATA_LASER_BATTERY(148),
    BURST_LASERS(149),
    ARCLITE_SHOCK_CANNON(150),
    EMP_MISSILE(151),
    DIAL_PHOTON_BLASTERS(152),
    PARTICLE_BEAM(153),
    ANTI_MATTER_MISSILE(154),
    PULSE_CANNON(155),
    PSIONIC_SHOCKWAVE(156),
    PSIONIC_STORM(157),
    YAMATO_GUN(158),
    PHASE_DISRUPTOR(159),
    STA_STS_CANNON_OVERLAY(160),
    SUNKEN_COLONY_TENTACLE(161),
    ACID_SPORE(163),
    GLAVE_WURM(165),
    SEEKER_SPORES(166),
    QUEEN_SPELL_CARRIER(167),
    PLAGUE_CLOUD(168),
    CONSUME(169),
    NEEDLE_SPINE(171),
    INVISIBLE(172),
    OPTICAL_FLARE_GRENADE(201),
    HALO_ROCKETS(202),
    SUBTERRANEAN_SPINES(203),
    CORROSIVE_ACID_SHOT(204),
    NEUTRON_FLARE(206),
    NONE(209),
    UNKNOWN(210);

    static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public static BulletType getBulletType(final int id) {
        for (final BulletType type : BulletType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private BulletType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    /**
     * @return name for this type of bullet
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of bullet
     */
    public int getId() {
        return id;
    }
}
