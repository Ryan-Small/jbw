package com.harbinger.jbw;

import com.harbinger.jbw.Position.Resolution;
import com.harbinger.jbw.Type.Race;
import com.harbinger.jbw.Type.Tech;
import com.harbinger.jbw.Type.Upgrade;

/**
 * Represents a StarCraft player.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Player
 */
public class Player {

    public static final int NUM_ATTRIBUTES = 11;

    private final int id;
    private final int raceId;
    private final int typeId;
    private final int startLocationX;
    private final int startLocationY;
    private final boolean self;
    private final boolean ally;
    private final boolean enemy;
    private final boolean neutral;
    private final boolean observer;
    private final int color;
    private final String name;

    private int minerals;
    private int gas;
    private int supplyUsed;
    private int supplyTotal;
    private int cumulativeMinerals;
    private int cumulativeGas;
    private int unitScore;
    private int killScore;
    private int buildingScore;
    private int razingScore;

    private final boolean[] researching;
    private final boolean[] researched;
    private final boolean[] upgrading;
    private final int[] upgradeLevel;

    Player(final int[] data, int index, final String name) {
        id = data[index++];
        raceId = data[index++];
        typeId = data[index++];
        startLocationX = data[index++];
        startLocationY = data[index++];
        self = (data[index++] == 1);
        ally = (data[index++] == 1);
        enemy = (data[index++] == 1);
        neutral = (data[index++] == 1);
        observer = (data[index++] == 1);
        color = data[index++];
        this.name = name;
        // Initialise technology records
        int highestIDTechType = 0;
        for (final Tech t : Tech.values()) {
            highestIDTechType = Math.max(highestIDTechType, t.getId());
        }
        researching = new boolean[highestIDTechType + 1];
        researched = new boolean[highestIDTechType + 1];
        int highestIDUpgradeType = 0;
        for (final Upgrade ut : Upgrade.values()) {
            highestIDUpgradeType = Math.max(highestIDUpgradeType, ut.getId());
        }
        upgrading = new boolean[highestIDUpgradeType + 1];
        upgradeLevel = new int[highestIDUpgradeType + 1];
    }

    void update(final int[] data) {
        int index = 0;
        minerals = data[index++];
        gas = data[index++];
        supplyUsed = data[index++];
        supplyTotal = data[index++];
        cumulativeMinerals = data[index++];
        cumulativeGas = data[index++];
        unitScore = data[index++];
        killScore = data[index++];
        buildingScore = data[index++];
        razingScore = data[index++];
    }

    void updateResearch(final int[] techData, final int[] upgradeData) {
        for (int i = 0; i < techData.length; i += 3) {
            final int techTypeID = techData[i];
            researched[techTypeID] = (techData[i + 1] == 1);
            researching[techTypeID] = (techData[i + 2] == 1);
        }

        for (int i = 0; i < upgradeData.length; i += 3) {
            final int upgradeTypeID = upgradeData[i];
            upgradeLevel[upgradeTypeID] = upgradeData[i + 1];
            upgrading[upgradeTypeID] = (upgradeData[i + 2] == 1);
        }
    }

    int getId() {
        return id;
    }

    public Race getRace() {
        return Race.getRaceType(raceId);
    }

    // TODO Should return a PlayerType
    int getTypeId() {
        return typeId;
    }

    /**
     * Returns the starting tile position of the Player. Note: the position may be equal to
     * Positions.Invalid / Positions.None / Positions.Unknown.
     */
    public Position getStartLocation() {
        return new Position(startLocationX, startLocationY, Resolution.BUILD);
    }

    public boolean isSelf() {
        return self;
    }

    public boolean isAlly() {
        return ally;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public boolean isNeutral() {
        return neutral;
    }

    public boolean isObserver() {
        return observer;
    }

    public int getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public int getMinerals() {
        return minerals;
    }

    public int getGas() {
        return gas;
    }

    public int getSupplyUsed() {
        return supplyUsed;
    }

    public int getSupplyTotal() {
        return supplyTotal;
    }

    public int getCumulativeMinerals() {
        return cumulativeMinerals;
    }

    public int getCumulativeGas() {
        return cumulativeGas;
    }

    public int getUnitScore() {
        return unitScore;
    }

    public int getKillScore() {
        return killScore;
    }

    public int getBuildingScore() {
        return buildingScore;
    }

    public int getRazingScore() {
        return razingScore;
    }

    public boolean isResearched(final Tech tech) {
        return researched[tech.getId()];
    }

    public boolean isResearching(final Tech tech) {
        return researching[tech.getId()];
    }

    public int getUpgradeLevel(final Upgrade upgrade) {
        return upgradeLevel[upgrade.getId()];
    }

    public boolean isUpgrading(final Upgrade upgrade) {
        return upgrading[upgrade.getId()];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
