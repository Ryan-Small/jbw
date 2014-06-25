package com.harbinger.jbw;

/**
 * http://code.google.com/p/bwapi/wiki/WeaponType
 */
public enum WeaponType {
    GAUSS_RIFLE(0),
    GAUSS_RIFLE_JIM_RAYNOR(1),
    C_10_CANISTER_RIFLE(2),
    C_10_CANISTER_RIFLE_SARAH_KERRIGAN(3),
    FRAGMENTATION_GRENADE(4),
    FRAGMENTATION_GRENADE_JIM_RAYNOR(5),
    SPIDER_MINES(6),
    TWIN_AUTOCANNONS(7),
    HELLFIRE_MISSILE_PACK(8),
    TWIN_AUTOCANNONS_ALAN_SCHEZAR(9),
    HELLFIRE_MISSILE_PACK_ALAN_SCHEZAR(10),
    ARCLITE_CANNON(11),
    ARCLITE_CANNON_EDMUND_DUKE(12),
    FUSION_CUTTER(13),
    UNDEFINED14(14),
    GEMINI_MISSILES(15),
    BURST_LASERS(16),
    GEMINI_MISSILES_TOM_KAZANSKY(17),
    BURST_LASERS_TOM_KAZANSKY(18),
    ATS_LASER_BATTERY(19),
    ATA_LASER_BATTERY(20),
    ATS_LASER_BATTERY_HERO(21),
    ATA_LASER_BATTERY_HERO(22),
    ATS_LASER_BATTERY_HYPERION(23),
    ATA_LASER_BATTERY_HYPERION(24),
    FLAME_THROWER(25),
    FLAME_THROWER_GUI_MONTAG(26),
    ARCLITE_SHOCK_CANNON(27),
    ARCLITE_SHOCK_CANNON_EDMUND_DUKE(28),
    LONGBOLT_MISSILE(29),
    YAMATO_GUN(30),
    NUCLEAR_STRIKE(31),
    LOCKDOWN(32),
    EMP_SHOCKWAVE(33),
    IRRADIATE(34),
    CLAWS(35),
    CLAWS_DEVOURING_ONE(36),
    CLAWS_INFESTED_KERRIGAN(37),
    NEEDLE_SPINES(38),
    NEEDLE_SPINES_HUNTER_KILLER(39),
    KAISER_BLADES(40),
    KAISER_BLADES_TORRASQUE(41),
    TOXIC_SPORES(42),
    SPINES(43),
    UNDEFINED44(44),
    UNDEFINED45(45),
    ACID_SPORE(46),
    ACID_SPORE_KUKULZA(47),
    GLAVE_WURM(48),
    GLAVE_WURM_KUKULZA(49),
    UNDEFINED50(50),
    UNDEFINED51(51),
    SEEKER_SPORES(52),
    SUBTERRANEAN_TENTACLE(53),
    SUICIDE_INFESTED_TERRAN(54),
    SUICIDE_SCOURGE(55),
    PARASITE(56),
    SPAWN_BROODLINGS(57),
    ENSNARE(58),
    DARK_SWARM(59),
    PLAGUE(60),
    CONSUME(61),
    PARTICLE_BEAM(62),
    UNDEFINED63(63),
    PSI_BLADES(64),
    PSI_BLADES_FENIX(65),
    PHASE_DISRUPTOR(66),
    PHASE_DISRUPTOR_FENIX(67),
    UNDEFINED68(68),
    PSI_ASSAULT(69),
    PSIONIC_SHOCKWAVE(70),
    PSIONIC_SHOCKWAVE_TASSADAR_ZERATUL_ARCHON(71),
    UNDEFINED72(72),
    DUAL_PHOTON_BLASTERS(73),
    ANTI_MATTER_MISSILES(74),
    DUAL_PHOTON_BLASTERS_MOJO(75),
    ANTI_MATTER_MISSILES_MOJO(76),
    PHASE_DISRUPTOR_CANNON(77),
    PHASE_DISRUPTOR_CANNON_DANIMOTH(78),
    PULSE_CANNON(79),
    STS_PHOTON_CANNON(80),
    STA_PHOTON_CANNON(81),
    SCARAB(82),
    STASIS_FIELD(83),
    PSIONIC_STORM(84),
    WARP_BLADES_ZERATUL(85),
    WARP_BLADES_HERO(86),
    UNDEFINED87(87),
    UNDEFINED88(88),
    UNDEFINED89(89),
    UNDEFINED90(90),
    UNDEFINED91(91),
    UNDEFINED92(92),
    UNDEFINED93(93),
    UNDEFINED94(94),
    UNDEFINED95(95),
    UNDEFINED96(96),
    UNDEFINED97(97),
    UNDEFINED98(98),
    UNDEFINED99(99),
    NEUTRON_FLARE(100),
    DISRUPTION_WEB(101),
    RESTORATION(102),
    HALO_ROCKETS(103),
    CORROSIVE_ACID(104),
    MIND_CONTROL(105),
    FEEDBACK(106),
    OPTICAL_FLARE(107),
    MAELSTROM(108),
    SUBTERRANEAN_SPINES(109),
    UNDEFINED110(110),
    WARP_BLADES(111),
    C_10_CANISTER_RIFLE_SAMIR_DURAN(112),
    C_10_CANISTER_RIFLE_INFESTED_DURAN(113),
    DUAL_PHOTON_BLASTERS_ARTANIS(114),
    ANTI_MATTER_MISSILES_ARTANIS(115),
    C_10_CANISTER_RIFLE_ALEXEI_STUKOV(116),
    UNDEFINED117(117),
    UNDEFINED118(118),
    UNDEFINED119(119),
    UNDEFINED120(120),
    UNDEFINED121(121),
    UNDEFINED122(122),
    UNDEFINED123(123),
    UNDEFINED124(124),
    UNDEFINED125(125),
    UNDEFINED126(126),
    UNDEFINED127(127),
    UNDEFINED128(128),
    UNDEFINED129(129),
    NONE(130),
    UNKNOWN(131);

    static final int NUM_ATTRIBUTES = 24;

    private String name;
    private final int id;
    private int techID;
    private int whatUsesTypeID;
    private int damageAmount;
    private int damageBonus;
    private int damageCooldown;
    private int damageFactor;
    private int upgradeTypeID;
    private int damageTypeID;
    private int explosionType;
    private int minRange;
    private int maxRange;
    private int innerSplashRadius;
    private int medianSplashRadius;
    private int outerSplashRadius;
    private boolean targetsAir;
    private boolean targetsGround;
    private boolean targetsMechanical;
    private boolean targetsOrganic;
    private boolean targetsNonBuilding;
    private boolean targetsNonRobotic;
    private boolean targetsTerrain;
    private boolean targetsOrgOrMech;
    private boolean targetsOwn;

    static WeaponType getWeaponType(final int id) {
        for (final WeaponType type : WeaponType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private WeaponType(final int id) {
        this.id = id;
    }

    void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        techID = data[index++];
        whatUsesTypeID = data[index++];
        damageAmount = data[index++];
        damageBonus = data[index++];
        damageCooldown = data[index++];
        damageFactor = data[index++];
        upgradeTypeID = data[index++];
        damageTypeID = data[index++];
        explosionType = data[index++];
        minRange = data[index++];
        maxRange = data[index++];
        innerSplashRadius = data[index++];
        medianSplashRadius = data[index++];
        outerSplashRadius = data[index++];
        targetsAir = data[index++] == 1;
        targetsGround = data[index++] == 1;
        targetsMechanical = data[index++] == 1;
        targetsOrganic = data[index++] == 1;
        targetsNonBuilding = data[index++] == 1;
        targetsNonRobotic = data[index++] == 1;
        targetsTerrain = data[index++] == 1;
        targetsOrgOrMech = data[index++] == 1;
        targetsOwn = data[index++] == 1;

        this.name = name;
    }

    /**
     * @return name for this type of weapon
     */
    public String getName() {
        return name;
    }

    /**
     * @return unique id for this type of weapon
     */
    public int getId() {
        return id;
    }

    public int getTechId() {
        return techID;
    }

    public int getWhatUsesTypeId() {
        return whatUsesTypeID;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public int getDamageCooldown() {
        return damageCooldown;
    }

    public int getDamageFactor() {
        return damageFactor;
    }

    public int getUpgradeTypeID() {
        return upgradeTypeID;
    }

    public int getDamageTypeID() {
        return damageTypeID;
    }

    public int getExplosionType() {
        return explosionType;
    }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }

    public int getInnerSplashRadius() {
        return innerSplashRadius;
    }

    public int getMedianSplashRadius() {
        return medianSplashRadius;
    }

    public int getOuterSplashRadius() {
        return outerSplashRadius;
    }

    public boolean isTargetsAir() {
        return targetsAir;
    }

    public boolean isTargetsGround() {
        return targetsGround;
    }

    public boolean isTargetsMechanical() {
        return targetsMechanical;
    }

    public boolean isTargetsOrganic() {
        return targetsOrganic;
    }

    public boolean isTargetsNonBuilding() {
        return targetsNonBuilding;
    }

    public boolean isTargetsNonRobotic() {
        return targetsNonRobotic;
    }

    public boolean isTargetsTerrain() {
        return targetsTerrain;
    }

    public boolean isTargetsOrgOrMech() {
        return targetsOrgOrMech;
    }

    public boolean isTargetsOwn() {
        return targetsOwn;
    }
}
