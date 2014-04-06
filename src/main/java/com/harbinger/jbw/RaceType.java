package com.harbinger.jbw;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * http://code.google.com/p/bwapi/wiki/Race
 */
public class RaceType {

    private static Map<Integer, RaceType> idToRaceType = new HashMap<>();

    public static class RaceTypes {
        public static final RaceType Zerg = new RaceType(0);
        public static final RaceType Terran = new RaceType(1);
        public static final RaceType Protoss = new RaceType(2);
        // NOTE: Changes in BWAPI4 to:
        // Unused = 3,4,5, Random = 6, None = 7, Unknown = 8
        public static final RaceType Random = new RaceType(3);
        public static final RaceType Other = new RaceType(4);
        public static final RaceType None = new RaceType(5);
        public static final RaceType Unknown = new RaceType(6);

        public static RaceType getRaceType(final int id) {
            return idToRaceType.get(id);
        }

        public static Collection<RaceType> getAllRaceTypes() {
            return Collections.unmodifiableCollection(idToRaceType.values());
        }
    }

    public static final int NUM_ATTRIBUTES = 6;

    private String name;
    private final int id;
    private int workerId;
    private int centerId;
    private int refineryId;
    private int transportId;
    private int supplyProviderId;

    private RaceType(final int id) {
        this.id = id;
        idToRaceType.put(id, this);
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

    public String getName() {
        return name;
    }

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
