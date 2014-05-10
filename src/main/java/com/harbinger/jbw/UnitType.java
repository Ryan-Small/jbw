package com.harbinger.jbw;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a StarCraft unit type.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/UnitType
 */
public enum UnitType {

    Terran_Marine(0),
    Terran_Ghost(1),
    Terran_Vulture(2),
    Terran_Goliath(3),
    Undefined4(4), // Goliath Turret
    Terran_Siege_Tank_Tank_Mode(5),
    Undefined6(6), // Siege Tank Turret (Tank Mode)
    Terran_SCV(7),
    Terran_Wraith(8),
    Terran_Science_Vessel(9),
    Hero_Gui_Montag(10),
    Terran_Dropship(11),
    Terran_Battlecruiser(12),
    Terran_Vulture_Spider_Mine(13),
    Terran_Nuclear_Missile(14),
    Terran_Civilian(15),
    Hero_Sarah_Kerrigan(16),
    Hero_Alan_Schezar(17),
    Undefined18(18), // Alan Schezar Turret
    Hero_Jim_Raynor_Vulture(19),
    Hero_Jim_Raynor_Marine(20),
    Hero_Tom_Kazansky(21),
    Hero_Magellan(22),
    Hero_Edmund_Duke_Tank_Mode(23),
    Undefined24(24), // Edmund Duke Turret (Tank Mode)
    Hero_Edmund_Duke_Siege_Mode(25),
    Undefined26(26), // Edmund Duke Turret (Siege Mode)
    Hero_Arcturus_Mengsk(27),
    Hero_Hyperion(28),
    Hero_Norad_II(29),
    Terran_Siege_Tank_Siege_Mode(30),
    Undefined31(31), // Siege Tank Turret (Siege Mode)
    Terran_Firebat(32),
    Spell_Scanner_Sweep(33),
    Terran_Medic(34),
    Zerg_Larva(35),
    Zerg_Egg(36),
    Zerg_Zergling(37),
    Zerg_Hydralisk(38),
    Zerg_Ultralisk(39),
    Zerg_Broodling(40),
    Zerg_Drone(41),
    Zerg_Overlord(42),
    Zerg_Mutalisk(43),
    Zerg_Guardian(44),
    Zerg_Queen(45),
    Zerg_Defiler(46),
    Zerg_Scourge(47),
    Hero_Torrasque(48),
    Hero_Matriarch(49),
    Zerg_Infested_Terran(50),
    Hero_Infested_Kerrigan(51),
    Hero_Unclean_One(52),
    Hero_Hunter_Killer(53),
    Hero_Devouring_One(54),
    Hero_Kukulza_Mutalisk(55),
    Hero_Kukulza_Guardian(56),
    Hero_Yggdrasill(57),
    Terran_Valkyrie(58),
    Zerg_Cocoon(59),
    Protoss_Corsair(60),
    Protoss_Dark_Templar(61),
    Zerg_Devourer(62),
    Protoss_Dark_Archon(63),
    Protoss_Probe(64),
    Protoss_Zealot(65),
    Protoss_Dragoon(66),
    Protoss_High_Templar(67),
    Protoss_Archon(68),
    Protoss_Shuttle(69),
    Protoss_Scout(70),
    Protoss_Arbiter(71),
    Protoss_Carrier(72),
    Protoss_Interceptor(73),
    Hero_Dark_Templar(74),
    Hero_Zeratul(75),
    Hero_Tassadar_Zeratul_Archon(76),
    Hero_Fenix_Zealot(77),
    Hero_Fenix_Dragoon(78),
    Hero_Tassadar(79),
    Hero_Mojo(80),
    Hero_Warbringer(81),
    Hero_Gantrithor(82),
    Protoss_Reaver(83),
    Protoss_Observer(84),
    Protoss_Scarab(85),
    Hero_Danimoth(86),
    Hero_Aldaris(87),
    Hero_Artanis(88),
    Critter_Rhynadon(89),
    Critter_Bengalaas(90),
    Special_Cargo_Ship(91),
    Special_Mercenary_Gunship(92),
    Critter_Scantid(93),
    Critter_Kakaru(94),
    Critter_Ragnasaur(95),
    Critter_Ursadon(96),
    Zerg_Lurker_Egg(97),
    Hero_Raszagal(98),
    Hero_Samir_Duran(99),
    Hero_Alexei_Stukov(100),
    Special_Map_Revealer(101),
    Hero_Gerard_DuGalle(102),
    Zerg_Lurker(103),
    Hero_Infested_Duran(104),
    Spell_Disruption_Web(105),
    Terran_Command_Center(106),
    Terran_Comsat_Station(107),
    Terran_Nuclear_Silo(108),
    Terran_Supply_Depot(109),
    Terran_Refinery(110),
    Terran_Barracks(111),
    Terran_Academy(112),
    Terran_Factory(113),
    Terran_Starport(114),
    Terran_Control_Tower(115),
    Terran_Science_Facility(116),
    Terran_Covert_Ops(117),
    Terran_Physics_Lab(118),
    Undefined119(119), // Starbase (Unused)
    Terran_Machine_Shop(120),
    Undefined121(121), // Repair Bay (Unused)
    Terran_Engineering_Bay(122),
    Terran_Armory(123),
    Terran_Missile_Turret(124),
    Terran_Bunker(125),
    Special_Crashed_Norad_II(126),
    Special_Ion_Cannon(127),
    Powerup_Uraj_Crystal(128),
    Powerup_Khalis_Crystal(129),
    Zerg_Infested_Command_Center(130),
    Zerg_Hatchery(131),
    Zerg_Lair(132),
    Zerg_Hive(133),
    Zerg_Nydus_Canal(134),
    Zerg_Hydralisk_Den(135),
    Zerg_Defiler_Mound(136),
    Zerg_Greater_Spire(137),
    Zerg_Queens_Nest(138),
    Zerg_Evolution_Chamber(139),
    Zerg_Ultralisk_Cavern(140),
    Zerg_Spire(141),
    Zerg_Spawning_Pool(142),
    Zerg_Creep_Colony(143),
    Zerg_Spore_Colony(144),
    Undefined145(145), // Unused Zerg Building 1
    Zerg_Sunken_Colony(146),
    Special_Overmind_With_Shell(147),
    Special_Overmind(148),
    Zerg_Extractor(149),
    Special_Mature_Chrysalis(150),
    Special_Cerebrate(151),
    Special_Cerebrate_Daggoth(152),
    Undefined153(153), // Unused Zerg Building 2
    Protoss_Nexus(154),
    Protoss_Robotics_Facility(155),
    Protoss_Pylon(156),
    Protoss_Assimilator(157),
    Undefined158(158), // Unused Protoss Building 1
    Protoss_Observatory(159),
    Protoss_Gateway(160),
    Undefined161(161), // Unused Protoss Building 2
    Protoss_Photon_Cannon(162),
    Protoss_Citadel_of_Adun(163),
    Protoss_Cybernetics_Core(164),
    Protoss_Templar_Archives(165),
    Protoss_Forge(166),
    Protoss_Stargate(167),
    Special_Stasis_Cell_Prison(168),
    Protoss_Fleet_Beacon(169),
    Protoss_Arbiter_Tribunal(170),
    Protoss_Robotics_Support_Bay(171),
    Protoss_Shield_Battery(172),
    Special_Khaydarin_Crystal_Form(173),
    Special_Protoss_Temple(174),
    Special_XelNaga_Temple(175),
    Resource_Mineral_Field(176),
    Resource_Mineral_Field_Type_2(177),
    Resource_Mineral_Field_Type_3(178),
    Undefined179(179), // Cave (Unused)
    Undefined180(180), // Cave-in (Unused)
    Undefined181(181), // Cantina (Unused)
    Undefined182(182), // Mining Platform (Unused)
    Undefined183(183), // Independent Command Center (Unused)
    Special_Independant_Starport(184),
    Undefined185(185), // Independent Jump Gate (Unused)
    Undefined186(186), // Ruins (Unused)
    Undefined187(187), // Khaydarin Crystal Formation (Unused)
    Resource_Vespene_Geyser(188),
    Special_Warp_Gate(189),
    Special_Psi_Disrupter(190),
    Undefined191(191), // Zerg Marker (Unused)
    Undefined192(192), // Terran Marker (Unused)
    Undefined193(193), // Protoss Marker (Unused)
    Special_Zerg_Beacon(194),
    Special_Terran_Beacon(195),
    Special_Protoss_Beacon(196),
    Special_Zerg_Flag_Beacon(197),
    Special_Terran_Flag_Beacon(198),
    Special_Protoss_Flag_Beacon(199),
    Special_Power_Generator(200),
    Special_Overmind_Cocoon(201),
    Spell_Dark_Swarm(202),
    Special_Floor_Missile_Trap(203),
    Special_Floor_Hatch(204),
    Special_Upper_Level_Door(205),
    Special_Right_Upper_Level_Door(206),
    Special_Pit_Door(207),
    Special_Right_Pit_Door(208),
    Special_Floor_Gun_Trap(209),
    Special_Wall_Missile_Trap(210),
    Special_Wall_Flame_Trap(211),
    Special_Right_Wall_Missile_Trap(212),
    Special_Right_Wall_Flame_Trap(213),
    Special_Start_Location(214),
    Powerup_Flag(215),
    Powerup_Young_Chrysalis(216),
    Powerup_Psi_Emitter(217),
    Powerup_Data_Disk(218),
    Powerup_Khaydarin_Crystal(219),
    Powerup_Mineral_Cluster_Type_1(220),
    Powerup_Mineral_Cluster_Type_2(221),
    Powerup_Protoss_Gas_Orb_Type_1(222),
    Powerup_Protoss_Gas_Orb_Type_2(223),
    Powerup_Zerg_Gas_Sac_Type_1(224),
    Powerup_Zerg_Gas_Sac_Type_2(225),
    Powerup_Terran_Gas_Tank_Type_1(226),
    Powerup_Terran_Gas_Tank_Type_2(227),
    None(228),
    Undefined229(229), // All Units (BWAPI4)
    Undefined230(230), // Men (BWAPI4)
    Undefined231(231), // Buildings (BWAPI4)
    Undefined232(232), // Factories (BWAPI4)
    Unknown(233);

    static final int NUM_ATTRIBUTES = 57;
    private static final double fixedScale = 100.0;

    private final int id;
    private int raceId;
    private int whatBuildId;
    private int armorUpgradeId;
    private int requiredTechId;
    private int maxHitPoints;
    private int maxShields;
    private int maxEnergy;
    private int armor;
    private int mineralPrice;
    private int gasPrice;
    private int buildTime;
    private int supplyRequired;
    private int supplyProvided;
    private int spaceRequired;
    private int spaceProvided;
    private int buildScore;
    private int destroyScore;
    private int sizeID;
    private int tileWidth;
    private int tileHeight;
    private int dimensionLeft;
    private int dimensionUp;
    private int dimensionRight;
    private int dimensionDown;
    private int seekRange;
    private int sightRange;
    private int groundWeaponID;
    private int maxGroundHits;
    private int airWeaponID;
    private int maxAirHits;
    private double topSpeed;
    private int acceleration;
    private int haltDistance;
    private int turnRadius;
    private boolean produceCapable;
    private boolean attackCapable;
    private boolean canMove;
    private boolean flyer;
    private boolean regenerates;
    private boolean spellcaster;
    private boolean invincible;
    private boolean organic;
    private boolean mechanical;
    private boolean robotic;
    private boolean detector;
    private boolean resourceContainer;
    private boolean refinery;
    private boolean worker;
    private boolean requiresPsi;
    private boolean requiresCreep;
    private boolean burrowable;
    private boolean cloakable;
    private boolean building;
    private boolean addon;
    private boolean flyingBuilding;
    private boolean spell;

    private String name;
    private final Map<Integer, Integer> requiredUnits = new HashMap<>();

    public static UnitType getUnitType(final int id) {
        for (final UnitType type : UnitType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private UnitType(final int id) {
        this.id = id;
    }

    public void initialize(final int[] data, int index, final String name, final int[] requiredUnits) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        raceId = data[index++];
        whatBuildId = data[index++];
        requiredTechId = data[index++];
        armorUpgradeId = data[index++];
        maxHitPoints = data[index++];
        maxShields = data[index++];
        maxEnergy = data[index++];
        armor = data[index++];
        mineralPrice = data[index++];
        gasPrice = data[index++];
        buildTime = data[index++];
        supplyRequired = data[index++];
        supplyProvided = data[index++];
        spaceRequired = data[index++];
        spaceProvided = data[index++];
        buildScore = data[index++];
        destroyScore = data[index++];
        sizeID = data[index++];
        tileWidth = data[index++];
        tileHeight = data[index++];
        dimensionLeft = data[index++];
        dimensionUp = data[index++];
        dimensionRight = data[index++];
        dimensionDown = data[index++];
        seekRange = data[index++];
        sightRange = data[index++];
        groundWeaponID = data[index++];
        maxGroundHits = data[index++];
        airWeaponID = data[index++];
        maxAirHits = data[index++];
        topSpeed = data[index++] / fixedScale;
        acceleration = data[index++];
        haltDistance = data[index++];
        turnRadius = data[index++];
        produceCapable = data[index++] == 1;
        attackCapable = data[index++] == 1;
        canMove = data[index++] == 1;
        flyer = data[index++] == 1;
        regenerates = data[index++] == 1;
        spellcaster = data[index++] == 1;
        invincible = data[index++] == 1;
        organic = data[index++] == 1;
        mechanical = data[index++] == 1;
        robotic = data[index++] == 1;
        detector = data[index++] == 1;
        resourceContainer = data[index++] == 1;
        refinery = data[index++] == 1;
        worker = data[index++] == 1;
        requiresPsi = data[index++] == 1;
        requiresCreep = data[index++] == 1;
        burrowable = data[index++] == 1;
        cloakable = data[index++] == 1;
        building = data[index++] == 1;
        addon = data[index++] == 1;
        flyingBuilding = data[index++] == 1;
        spell = data[index++] == 1;

        this.name = name;
        for (int i = 0; i < requiredUnits.length; i += 2) {
            this.requiredUnits.put(requiredUnits[i], requiredUnits[i + 1]);
        }
    }

    public int getId() {
        return id;
    }

    public int getRaceId() {
        return raceId;
    }

    public int getWhatBuildId() {
        return whatBuildId;
    }

    public int getRequiredTechId() {
        return requiredTechId;
    }

    public int getArmorUpgradeId() {
        return armorUpgradeId;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getMaxShields() {
        return maxShields;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getArmor() {
        return armor;
    }

    public int getMineralPrice() {
        return mineralPrice;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public int getSupplyRequired() {
        return supplyRequired;
    }

    public int getSupplyProvided() {
        return supplyProvided;
    }

    public int getSpaceRequired() {
        return spaceRequired;
    }

    public int getSpaceProvided() {
        return spaceProvided;
    }

    public int getBuildScore() {
        return buildScore;
    }

    public int getDestroyScore() {
        return destroyScore;
    }

    public UnitSizeType getSize() {
        return UnitSizeType.values()[sizeID];
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getDimensionLeft() {
        return dimensionLeft;
    }

    public int getDimensionUp() {
        return dimensionUp;
    }

    public int getDimensionRight() {
        return dimensionRight;
    }

    public int getDimensionDown() {
        return dimensionDown;
    }

    public int getSeekRange() {
        return seekRange;
    }

    public int getSightRange() {
        return sightRange;
    }

    public int getGroundWeaponID() {
        return groundWeaponID;
    }

    public int getMaxGroundHits() {
        return maxGroundHits;
    }

    public int getAirWeaponID() {
        return airWeaponID;
    }

    public int getMaxAirHits() {
        return maxAirHits;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public int getHaltDistance() {
        return haltDistance;
    }

    public int getTurnRadius() {
        return turnRadius;
    }

    public boolean isProduceCapable() {
        return produceCapable;
    }

    public boolean isAttackCapable() {
        return attackCapable;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isFlyer() {
        return flyer;
    }

    public boolean isRegenerates() {
        return regenerates;
    }

    public boolean isSpellcaster() {
        return spellcaster;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isOrganic() {
        return organic;
    }

    public boolean isMechanical() {
        return mechanical;
    }

    public boolean isRobotic() {
        return robotic;
    }

    public boolean isDetector() {
        return detector;
    }

    public boolean isResourceContainer() {
        return resourceContainer;
    }

    public boolean isRefinery() {
        return refinery;
    }

    public boolean isWorker() {
        return worker;
    }

    public boolean isRequiresPsi() {
        return requiresPsi;
    }

    public boolean isRequiresCreep() {
        return requiresCreep;
    }

    public boolean isBurrowable() {
        return burrowable;
    }

    public boolean isCloakable() {
        return cloakable;
    }

    public boolean isBuilding() {
        return building;
    }

    public boolean isAddon() {
        return addon;
    }

    public boolean isFlyingBuilding() {
        return flyingBuilding;
    }

    public boolean isSpell() {
        return spell;
    }

    public String getName() {
        return name;
    }

    /** A map from UnitTypeID to quantity required (usually 1, but 2 for Archons) */
    public Map<Integer, Integer> getRequiredUnits() {
        return Collections.unmodifiableMap(requiredUnits);
    }

    public boolean isMineralField() {
        return (this == UnitType.Resource_Mineral_Field)
                || (this == UnitType.Resource_Mineral_Field_Type_2)
                || (this == UnitType.Resource_Mineral_Field_Type_3);
    }
}
