package jnibwapi.types;

/**
 * Represents a StarCraft race type.
 * 
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Race
 */
public class RaceType {

    public static final int NUM_ATTRIBUTES = 6;

    private String name;
    private final int id;
    private final int workerID;
    private final int centerID;
    private final int refineryID;
    private final int transportID;
    private final int supplyProviderID;

    public enum RaceTypes {
        Zerg,
        Terran,
        Protoss,
        // NOTE: Changes in BWAPI4 to:
        // Unused = 3,4,5, Random = 6, None = 7, Unknown = 8
        Random,
        Other,
        None,
        Unknown;
        public int getID() {
            return ordinal();
        }
    }

    public RaceType(final int[] data, int index) {
        id = data[index++];
        workerID = data[index++];
        centerID = data[index++];
        refineryID = data[index++];
        transportID = data[index++];
        supplyProviderID = data[index++];
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

    public int getWorkerId() {
        return workerID;
    }

    public int getCenterId() {
        return centerID;
    }

    public int getRefineryId() {
        return refineryID;
    }

    public int getTransportId() {
        return transportID;
    }

    public int getSupplyProviderId() {
        return supplyProviderID;
    }
}
