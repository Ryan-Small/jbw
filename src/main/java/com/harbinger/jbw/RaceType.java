package com.harbinger.jbw;

/**
 * http://code.google.com/p/bwapi/wiki/Race
 */
public enum RaceType {

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

    public static RaceType getRaceType(final int id) {
        for (final RaceType type : RaceType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    private RaceType(final int id) {
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
