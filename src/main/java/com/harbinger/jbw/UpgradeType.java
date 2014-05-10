package com.harbinger.jbw;

/**
 * Represents a StarCraft upgrade type.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/UpgradeType
 */
public enum UpgradeType {
    TERRAN_INFANTRY_ARMOR(0),
    TERRAN_VEHICLE_PLATING(1),
    TERRAN_SHIP_PLATING(2),
    ZERG_CARAPACE(3),
    ZERG_FLYER_CARAPACE(4),
    PROTOSS_GROUND_ARMOR(5),
    PROTOSS_AIR_ARMOR(6),
    TERRAN_INFANTRY_WEAPONS(7),
    TERRAN_VEHICLE_WEAPONS(8),
    TERRAN_SHIP_WEAPONS(9),
    ZERG_MELEE_ATTACKS(10),
    ZERG_MISSILE_ATTACKS(11),
    ZERG_FLYER_ATTACKS(12),
    PROTOSS_GROUND_WEAPONS(13),
    PRPTOSS_AIR_WEAPONS(14),
    PROTOSS_PLASMA_SHIELDS(15),
    U_238_SHELLS(16), // Marine Range
    ION_THRUSTERS(17), // Vulture Speed
    UNDEFINED18(18),
    TITAN_REACTOR(19), // Science Vessel Energy
    OCULAR_IMPLANTS(20), // Ghost Sight
    MOEBIUS_REACTOR(21), // Ghost Energy
    APOLLO_REACTOR(22), // Wraith Energy
    COLOSSUS_REACTOR(23), // Battle Cruiser Energy
    VENTRAL_SACS(24), // Overlord Transport
    ANTENNAE(25), // Overlord Sight
    PNEUMATIZED_CARAPACE(26), // Overlord Speed
    METABOLIC_BOOST(27), // Zergling Speed
    ADRENAL_GLANDS(28), // Zergling Attack
    MUSCULAR_AUGMENTS(29), // Hydralisk Speed
    GROOVED_SPINES(30), // Hydralisk Range
    GAMETE_MEIOSIS(31), // Queen Energy
    METASYNAPTIC_NODE(32), // Defiler Energy
    SINGULARITY_CHARGE(33), // Dragoon Range
    LEG_ENHANCEMENTS(34), // Zealot Speed
    SCARAB_DAMAGE(35),
    REAVER_CAPACITY(36),
    GRAVITIC_DRIVE(37), // Shuttle Speed
    SENSOR_ARRAY(38), // Observer Sight
    GRAVITIC_BOOSTERS(39), // Observer Speed
    KHAYDARIN_AMULET(40), // Templar Energy
    APIAL_SENSORS(41), // Scout Sight
    GRAVITIC_THRUSTERS(42), // Scout Speed
    CARRIER_CAPACITY(43),
    KHAYDARIN_CORE(44), // Arbiter Energy
    UNDEFINED45(45),
    UNDEFINED46(46),
    ARGUS_JEWEL(47), // Corsair Energy
    UNDEFINED48(48),
    ARGUS_TALISMAN(49), // Dark Archon Energy
    UNDEFINED50(50),
    CADUCEUS_REACTOR(51), // Medic Energy
    CHITINOUS_PLATING(52), // Ultralisk Armor
    ANABOLIC_SYNTHESIS(53), // Ultralisk Speed
    CHARON_BOOSTERS(54), // Goliath Range
    UNDEFINED55(55),
    UNDEFINED56(56),
    UNDEFINED57(57),
    UNDEFINED58(58),
    UNDEFINED59(59),
    UNDEFINED60(60),
    NONE(61),
    UNKNOWN(62);

    static final int NUM_ATTRIBUTES = 10;

    private String name;
    private final int id;
    private int raceId;
    private int mineralPriceBase;
    private int mineralPriceFactor;
    private int gasPriceBase;
    private int gasPriceFactor;
    private int upgradeTimeBase;
    private int upgradeTimeFactor;
    private int maxRepeats;
    private int whatUpgradesTypeId;

    public static UpgradeType getUpgradeType(final int id) {
        for (final UpgradeType type : UpgradeType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private UpgradeType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        raceId = data[index++];
        mineralPriceBase = data[index++];
        mineralPriceFactor = data[index++];
        gasPriceBase = data[index++];
        gasPriceFactor = data[index++];
        upgradeTimeBase = data[index++];
        upgradeTimeFactor = data[index++];
        maxRepeats = data[index++];
        whatUpgradesTypeId = data[index++];

        this.name = name;
    }

    /**
     * @return name for this type of upgrade
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of upgrade
     */
    public int getId() {
        return id;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getMineralPriceBase() {
        return mineralPriceBase;
    }

    public int getMineralPriceFactor() {
        return mineralPriceFactor;
    }

    public int getGasPriceBase() {
        return gasPriceBase;
    }

    public int getGasPriceFactor() {
        return gasPriceFactor;
    }

    public int getUpgradeTimeBase() {
        return upgradeTimeBase;
    }

    public int getUpgradeTimeFactor() {
        return upgradeTimeFactor;
    }

    public int getMaxRepeats() {
        return maxRepeats;
    }

    public int getWhatUpgradesTypeId() {
        return whatUpgradesTypeId;
    }
}
