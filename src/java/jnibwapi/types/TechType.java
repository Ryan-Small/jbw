package jnibwapi.types;

/**
 * Represents a StarCraft tech (research) type.
 * 
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/TechType
 */
public class TechType {

    public static final int NUM_ATTRIBUTES = 10;

    private String name;
    private final int id;
    private final int raceId;
    private final int mineralPrice;
    private final int gasPrice;
    private final int researchTime;
    private final int energyUsed;
    private final int whatResearchesTypeId;
    private final int getWeaponId;
    private final boolean targetsUnits;
    private final boolean targetsPosition;

    public enum TechTypes {
        Stim_Packs,
        Lockdown,
        EMP_Shockwave,
        Spider_Mines,
        Scanner_Sweep,
        Tank_Siege_Mode,
        Defensive_Matrix,
        Irradiate,
        Yamato_Gun,
        Cloaking_Field,
        Personnel_Cloaking,
        Burrowing,
        Infestation,
        Spawn_Broodlings,
        Dark_Swarm,
        Plague,
        Consume,
        Ensnare,
        Parasite,
        Psionic_Storm,
        Hallucination,
        Recall,
        Stasis_Field,
        Archon_Warp,
        Restoration,
        Disruption_Web,
        Undefined26,
        Mind_Control,
        Dark_Archon_Meld,
        Feedback,
        Optical_Flare,
        Maelstrom,
        Lurker_Aspect,
        Undefined33,
        Healing,
        Undefined35,
        Undefined36,
        Undefined37,
        Undefined38,
        Undefined39,
        Undefined40,
        Undefined41,
        Undefined42,
        Undefined43,
        None,
        Unknown,
        Nuclear_Strike;

        public int getId() {
            return ordinal();
        }
    }

    public TechType(final int[] data, int index) {
        id = data[index++];
        raceId = data[index++];
        mineralPrice = data[index++];
        gasPrice = data[index++];
        researchTime = data[index++];
        energyUsed = data[index++];
        whatResearchesTypeId = data[index++];
        getWeaponId = data[index++];
        targetsUnits = data[index++] == 1;
        targetsPosition = data[index++] == 1;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public int getWhatResearchesTypeID() {
        return whatResearchesTypeId;
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
