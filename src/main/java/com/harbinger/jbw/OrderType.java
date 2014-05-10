package com.harbinger.jbw;

/**
 * http://code.google.com/p/bwapi/wiki/Order
 */
public enum OrderType {
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

    public static OrderType getOrderType(final int id) {
        for (final OrderType type : OrderType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private OrderType(final int id) {
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