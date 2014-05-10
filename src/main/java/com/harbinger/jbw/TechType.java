package com.harbinger.jbw;

/**
 * Represents a StarCraft tech (research) type.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/TechType
 */
public enum TechType {

    STIM_PACKS(0),
    LOCKDOWN(1),
    EMP_SHOCKWAVE(2),
    SPIDER_MINES(3),
    SCANNER_SWEEP(4),
    TANK_SIEGE_MODE(5),
    DEFENSIVE_MATRIX(6),
    IRRADIATE(7),
    YAMATO_GUN(8),
    CLOAKING_FIELD(9),
    PERSONNEL_CLOAKING(10),
    BURROWING(11),
    INFESTATION(12),
    SPAWN_BROODLINGS(13),
    DARK_SWARM(14),
    PLAGUE(15),
    CONSUME(16),
    ENSNARE(17),
    PARASITE(18),
    PSIONIC_STORM(19),
    HALLUCINATION(20),
    RECALL(21),
    STASIS_FIELD(22),
    ARCHON_WARP(23),
    RESTORATION(24),
    DISRUPTION_WEB(25),
    UNDEFINED26(26),
    MIND_CONTROL(27),
    DARK_ARCHON_MELD(28),
    FEEDBACK(29),
    OPTICAL_FLARE(30),
    MAELSTROM(31),
    LURKER_ASPECT(32),
    UNDEFINED33(33),
    HEALING(34),
    UNDEFINED35(35),
    UNDEFINED36(36),
    UNDEFINED37(37),
    UNDEFINED38(38),
    UNDEFINED39(39),
    UNDEFINED40(40),
    UNDEFINED41(41),
    UNDEFINED42(42),
    UNDEFINED43(43),
    NONE(44),
    UNKNOWN(45),
    NUCLEAR_STRIKE(46);

    static final int NUM_ATTRIBUTES = 10;

    private String name;
    private final int id;
    private int raceId;
    private int mineralPrice;
    private int gasPrice;
    private int researchTime;
    private int energyUsed;
    private int whatResearchesTypeId;
    private int getWeaponId;
    private boolean targetsUnits;
    private boolean targetsPosition;

    public static TechType getTechType(final int id) {
        for (final TechType type : TechType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private TechType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        raceId = data[index++];
        mineralPrice = data[index++];
        gasPrice = data[index++];
        researchTime = data[index++];
        energyUsed = data[index++];
        whatResearchesTypeId = data[index++];
        getWeaponId = data[index++];
        targetsUnits = data[index++] == 1;
        targetsPosition = data[index++] == 1;

        this.name = name;
    }

    /**
     * @return name for this type of tech
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of tech
     */
    public int getId() {
        return id;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getMineralPrice() {
        return mineralPrice;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public int getResearchTime() {
        return researchTime;
    }

    public int getEnergyUsed() {
        return energyUsed;
    }

    public UnitType getWhatResearches() {
        return UnitType.getUnitType(whatResearchesTypeId);
    }

    public int getGetWeaponId() {
        return getWeaponId;
    }

    public boolean isTargetsUnits() {
        return targetsUnits;
    }

    public boolean isTargetsPosition() {
        return targetsPosition;
    }
}
