package jnibwapi.types;

/**
 * http://code.google.com/p/bwapi/wiki/DamageType
 */
public class DamageType {

    public static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public enum DamageTypes {
        Independent,
        Explosive,
        Concussive,
        Normal,
        Ignore_Armor,
        None,
        Unknown;

        public int getId() {
            return ordinal();
        }
    }

    public DamageType(final int[] data, int index) {
        id = data[index++];
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
