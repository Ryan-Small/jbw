/*
 * Copyright 2014 IDEXX Laboratories, Inc. All rights reserved. IDEXX PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.harbinger.jbw;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Type {

    /**
     * The types of sizes a Unit can have.
     */
    public enum UnitSize {

        INDEPENDENT(0),
        SMALL(1),
        MEDIUM(2),
        LARGE(3),
        NONE(4),
        UNKNOWN(5);

        static final int NUM_ATTRIBUTES = 1;

        private String name;
        private final int id;

        private UnitSize(final int id) {
            this.id = id;
        }

        static UnitSize getUnitSizeType(final int id) {
            for (final UnitSize type : UnitSize.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return null;
        }

        void initialize(final int[] data, int index, final String name) {
            if (id != data[index++]) {
                throw new IllegalArgumentException();
            }
            this.name = name;
        }

        /**
         * @return the name for this type of unit size
         */
        public String getName() {
            return name;
        }
    }

    /**
     * The possible types of damage a weapon can do.
     */
    public enum Damage {

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

        public static Damage getDamageType(final int id) {
            for (final Damage type : Damage.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return null;
        }

        private Damage(final int id) {
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
    }

    /**
     * http://code.google.com/p/bwapi/wiki/ExplosionType
     */
    public enum Explosion {

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

        public static Explosion getExplosionType(final int id) {
            for (final Explosion type : Explosion.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Explosion(final int id) {
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

    /**
     * http://code.google.com/p/bwapi/wiki/Race
     */
    public enum Race {

        ZERG(0),
        TERRAN(1),
        PROTOSS(2),
        RANDOM(3),
        OTHER(4),
        NONE(5),
        UNKNOWN(6);

        static final int NUM_ATTRIBUTES = 6;

        private String name;
        private final int id;
        private int workerId;
        private int centerId;
        private int refineryId;
        private int transportId;
        private int supplyProviderId;

        public static Race getRaceType(final int id) {
            for (final Race type : Race.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Race(final int id) {
            this.id = id;
        }

        public void initialize(final int[] data, int index, final String name) {
            if (id != data[index++]) {
                throw new IllegalArgumentException();
            }
            workerId = data[index++];
            centerId = data[index++];
            refineryId = data[index++];
            transportId = data[index++];
            supplyProviderId = data[index++];
            this.name = name;
        }

        /**
         * @return name for this type of race
         */
        public String getName() {
            return name;
        }

        /**
         * @return unique id for this type of race
         */
        public int getId() {
            return id;
        }

        public int getWorkerId() {
            return workerId;
        }

        public int getCenterId() {
            return centerId;
        }

        public int getRefineryId() {
            return refineryId;
        }

        public int getTransportId() {
            return transportId;
        }

        public int getSupplyProviderId() {
            return supplyProviderId;
        }
    }

    /**
     * Represents an individual bullets, missiles, spells, and generally any sort of non-melee
     * attack.
     */
    public enum Bullet {

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

        public static Bullet getBulletType(final int id) {
            for (final Bullet type : Bullet.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Bullet(final int id) {
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

    /**
     * Represents a command that can be given to a unit.
     */
    public enum Command {

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

        public static Command getCommandType(final int id) {
            for (final Command type : Command.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Command(final int id) {
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

    /**
     * Represents a StarCraft tech (research) type.
     *
     * For a description of fields see: http://code.google.com/p/bwapi/wiki/TechType
     */
    public enum Tech {

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

        public static Tech getTechType(final int id) {
            for (final Tech type : Tech.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Tech(final int id) {
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

    /**
     * Represents a StarCraft upgrade type.
     *
     * For a description of fields see: http://code.google.com/p/bwapi/wiki/UpgradeType
     */
    public enum Upgrade {
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

        public static Upgrade getUpgradeType(final int id) {
            for (final Upgrade type : Upgrade.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Upgrade(final int id) {
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

    /**
     * http://code.google.com/p/bwapi/wiki/Order
     */
    public enum Order {
        DIE(0),
        STOP(1),
        GUARD(2),
        PLAYERGUARD(3),
        TURRETGUARD(4),
        BUNKERGUARD(5),
        MOVE(6),
        ATTACKUNIT(10),
        ATTACKTILE(12),
        HOVER(13),
        ATTACKMOVE(14),
        INFESTEDCOMMANDCENTER(15),
        UNUSEDNOTHING(16),
        UNUSEDPOWERUP(17),
        TOWERGUARD(18),
        VULTUREMINE(20),
        NOTHING(23),
        NOTHING3(24),
        CASTINFESTATION(27),
        INFESTINGCOMMANDCENTER(29),
        PLACEBUILDING(30),
        BUILDPROTOSS2(32),
        CONSTRUCTINGBUILDING(33),
        REPAIR(34),
        PLACEADDON(36),
        BUILDADDON(37),
        TRAIN(38),
        RALLYPOINTUNIT(39),
        RALLYPOINTTILE(40),
        ZERGBIRTH(41),
        ZERGUNITMORPH(42),
        ZERGBUILDINGMORPH(43),
        INCOMPLETEBUILDING(44),
        BUILDNYDUSEXIT(46),
        ENTERNYDUSCANAL(47),
        FOLLOW(49),
        CARRIER(50),
        REAVERCARRIERMOVE(51),
        CARRIERIGNOER2(55),
        REAVER(58),
        TRAINFIGHTER(63),
        INTERCEPTORATTACK(64),
        SCARABATTACK(65),
        RECHARGESHIELDSUNIT(66),
        RECHARGESHIELDSBATTERY(67),
        SHIELDBATTERY(68),
        INTERCEPTORRETURN(69),
        BUILDINGLAND(71),
        BUILDINGLIFTOFF(72),
        DRONELIFTOFF(73),
        LIFTINGOFF(74),
        RESEARCHTECH(75),
        UPGRADE(76),
        LARVA(77),
        SPAWNINGLARVA(78),
        HARVEST1(79),
        HARVEST2(80),

        /** Unit is moving to a gas geyser. */
        MOVETOGAS(81),

        /** Unit is waiting to harvest the gas geyser. */
        WAITFORGAS(82),

        /** Unit is harvesting from the gas geyser. */
        HARVESTGAS(83),

        /** Unit is returning from the gas geyser. */
        RETURNGAS(84),

        /** Unit is moving to a mineral patch. */
        MOVETOMINERALS(85),

        /** Unit is waiting to use the mineral patch. */
        WAITFORMINERALS(86),

        /** Unit is harvesting minerals. */
        MININGMINERALS(87),
        HARVEST3(88),
        HARVEST4(89),

        /** Unit is returning minerals. */
        RETURNMINERALS(90),
        INTERRUPTED(91),
        ENTERTRANSPORT(92),
        PICKUPIDLE(93),
        PICKUPTRANSPORT(94),
        PICKUPBUNKER(95),
        PICKUP4(96),
        POWERUPIDLE(97),
        SIEGING(98),
        UNSIEGING(99),
        INITCREEPGROWTH(101),
        SPREADCREEP(102),
        STOPPINGCREEPGROWTH(103),
        GUARDIANASPECT(104),
        ARCHONWARP(105),
        COMPLETINGARCHONSUMMON(106),
        HOLDPOSITION(107),
        CLOAK(109),
        DECLOAK(110),
        UNLOAD(111),
        MOVEUNLOAD(112),
        FIREYAMATOGUN(113),
        CASTLOCKDOWN(115),
        BURROWING(116),
        BURROWED(117),
        UNBURROWING(118),
        CASTDARKSWARM(119),
        CASTPARASITE(120),
        CASTSPAWNBROODLINGS(121),
        CASTEMPSHOCKWAVE(122),
        NUKEWAIT(123),
        NUKETRAIN(124),
        NUKELAUNCH(125),
        NUKEPAINT(126),
        NUKEUNIT(127),
        CASTNUCLEARSTRIKE(128),
        NUKETRACK(129),
        CLOAKNEARBYUNITS(131),
        PLACEMINE(132),
        RIGHTCLICKACTION(133),
        CASTRECALL(137),
        TELEPORTTOLOCATION(138),
        CASTSCANNERSWEEP(139),
        SCANNER(140),
        CASTDEFENSIVEMATRIX(141),
        CASTPSIONICSTORM(142),
        CASTIRRADIATE(143),
        CASTPLAGUE(144),
        CASTCONSUME(145),
        CASTENSNARE(146),
        CASTSTASISFIELD(147),
        CASTHALLUCINATION(148),
        HALLUCINATION2(149),
        RESETCOLLISION(150),
        PATROL(152),
        CTFCOPINIT(153),
        CTFCOP1(154),
        CTFCOP2(155),
        COMPUTERAI(156),
        ATKMOVEEP(157),
        HARASSMOVE(158),
        AIPATROL(159),
        GUARDPOST(160),
        RESCUEPASSIVE(161),
        NEUTRAL(162),
        COMPUTERRETURN(163),
        SELFDESTRUCING(165),
        CRITTER(166),
        HIDDENGUN(167),
        OPENDOOR(168),
        CLOSEDOOR(169),
        HIDETRAP(170),
        REVEALTRAP(171),
        ENABLEDOODAD(172),
        DISABLEDOODAD(173),
        WARPIN(174),
        MEDIC(175),
        MEDICHEAL1(176),
        HEALMOVE(177),
        MEDICHEAL2(179),
        CASTRESTORATION(180),
        CASTDISRUPTIONWEB(181),
        CASTMINDCONTROL(182),
        DARKARCHONMELD(183),
        CASTFEEDBACK(184),
        CASTOPTICALFLARE(185),
        CASTMAELSTROM(186),
        JUNKYARDDOG(187),
        FATAL(188),
        NONE(189),
        UNKNOWN(190);

        static final int NUM_ATTRIBUTES = 1;

        private String name;
        private final int id;

        public static Order getOrderType(final int id) {
            for (final Order type : Order.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Order(final int id) {
            this.id = id;
        }

        void initialize(final int[] data, int index, final String name) {
            if (id != data[index++]) {
                throw new IllegalArgumentException();
            }
            this.name = name;
        }

        /**
         * @return name for this type of order
         */
        public String getName() {
            return name;
        }

        /**
         * @return unique id for this type of order
         */
        public int getId() {
            return id;
        }
    }

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

        public void initialize(final int[] data, int index, final String name,
                final int[] requiredUnits) {
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

        public UnitSize getSize() {
            return UnitSize.values()[sizeID];
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

    /**
     * http://code.google.com/p/bwapi/wiki/WeaponType
     */
    public enum Weapon {
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

        static Weapon getWeaponType(final int id) {
            for (final Weapon type : Weapon.values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }

        private Weapon(final int id) {
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
}
