package jbw.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jbw.types.UnitType.UnitTypes;

/**
 * Represents a StarCraft tech (research) type.
 * 
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/TechType
 */
public class TechType {

    private static Map<Integer, TechType> idToTechType = new HashMap<>();

    public static class TechTypes {
        public static final TechType Stim_Packs = new TechType(0);
        public static final TechType Lockdown = new TechType(1);
        public static final TechType EMP_Shockwave = new TechType(2);
        public static final TechType Spider_Mines = new TechType(3);
        public static final TechType Scanner_Sweep = new TechType(4);
        public static final TechType Tank_Siege_Mode = new TechType(5);
        public static final TechType Defensive_Matrix = new TechType(6);
        public static final TechType Irradiate = new TechType(7);
        public static final TechType Yamato_Gun = new TechType(8);
        public static final TechType Cloaking_Field = new TechType(9);
        public static final TechType Personnel_Cloaking = new TechType(10);
        public static final TechType Burrowing = new TechType(11);
        public static final TechType Infestation = new TechType(12);
        public static final TechType Spawn_Broodlings = new TechType(13);
        public static final TechType Dark_Swarm = new TechType(14);
        public static final TechType Plague = new TechType(15);
        public static final TechType Consume = new TechType(16);
        public static final TechType Ensnare = new TechType(17);
        public static final TechType Parasite = new TechType(18);
        public static final TechType Psionic_Storm = new TechType(19);
        public static final TechType Hallucination = new TechType(20);
        public static final TechType Recall = new TechType(21);
        public static final TechType Stasis_Field = new TechType(22);
        public static final TechType Archon_Warp = new TechType(23);
        public static final TechType Restoration = new TechType(24);
        public static final TechType Disruption_Web = new TechType(25);
        public static final TechType Undefined26 = new TechType(26);
        public static final TechType Mind_Control = new TechType(27);
        public static final TechType Dark_Archon_Meld = new TechType(28);
        public static final TechType Feedback = new TechType(29);
        public static final TechType Optical_Flare = new TechType(30);
        public static final TechType Maelstrom = new TechType(31);
        public static final TechType Lurker_Aspect = new TechType(32);
        // 33 is undefined
        public static final TechType Healing = new TechType(34);
        // 35-43 are undefined
        public static final TechType None = new TechType(44);
        public static final TechType Unknown = new TechType(45);
        public static final TechType Nuclear_Strike = new TechType(46);

        public static TechType getTechType(final int id) {
            return idToTechType.get(id);
        }

        public static Collection<TechType> getAllTechTypes() {
            return Collections.unmodifiableCollection(idToTechType.values());
        }
    }

    public static final int NUM_ATTRIBUTES = 10;

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

    private TechType(final int id) {
        this.id = id;
        idToTechType.put(id, this);
    }

    public void initialize(final int[] data, int index, final String name) {
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

    public String getName() {
        return name;
    }

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
        return UnitTypes.getUnitType(whatResearchesTypeId);
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
