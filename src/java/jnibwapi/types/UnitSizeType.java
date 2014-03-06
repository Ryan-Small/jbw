package jnibwapi.types;

/**
 * Represents a StarCraft unit size type.
 * 
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/UnitSizeType
 */
public class UnitSizeType {

    public static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    public enum UnitSizeTypes {
        Independent,
        Small,
        Medium,
        Large,
        None,
        Unknown;

        public int getId() {
            return ordinal();
        }
    }

    public UnitSizeType(final int[] data, int index) {
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
