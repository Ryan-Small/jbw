package jnibwapi.types;

/**
 * Represents a StarCraft explosion type.
 * 
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/ExplosionType
 */
public class ExplosionType {

    public static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public enum ExplosionTypes {
        None,
        Normal,
        Radial_Splash,
        Enemy_Splash,
        Lockdown,
        Nuclear_Missile,
        Parasite,
        Broodlings,
        EMP_Shockwave,
        Irradiate,
        Ensnare,
        Plague,
        Stasis_Field,
        Dark_Swarm,
        Consume,
        Yamato_Gun,
        Restoration,
        Disruption_Web,
        Corrosive_Acid,
        Mind_Control,
        Feedback,
        Optical_Flare,
        Maelstrom,
        Undefined23,
        Air_Splash,
        Unknown;
        public int getID() {
            return ordinal();
        }
    }

    public ExplosionType(final int[] data, int index) {
        id = data[index++];
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
}
