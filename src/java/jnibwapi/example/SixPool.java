package jnibwapi.example;

import java.util.HashSet;

import jnibwapi.BWAPIEventListener;
import jnibwapi.JNIBWAPI;
import jnibwapi.model.Unit;
import jnibwapi.types.UnitType.UnitTypes;

public class SixPool extends BWAPIEventListener.Adaptor {

    private final JNIBWAPI bwapi = new JNIBWAPI(this, true);

    /** Used for mineral splits. */
    private final HashSet<Integer> claimed = new HashSet<Integer>();

    /** Number of drones. Game starts with four. */
    private int droneCount = 4;

    /** When should the next Overlord be spawned? */
    private int supplyCap = 0;

    /** Has a drone been assigned to building a pool? */
    private final int poolDrone = -1;

    public static void main(final String[] args) {
        new SixPool().launchBwapi();
    }

    public SixPool() {
    }

    private void launchBwapi() {
        /* Start BWAPI on a separate thread to prevent blocking. */
        final Runnable bwapiRunnable = new Runnable() {

            @Override
            public void run() {
                bwapi.start();
            }
        };
        final Thread bwapiThread = new Thread(bwapiRunnable);
        bwapiThread.start();
    }

    @Override
    public void matchStart() {
        bwapi.setGameSpeed(10);
        bwapi.enableUserInput();
        bwapi.enablePerfectInformation();
    }

    @Override
    public void matchFrame() {
        // spawn a drone
        if ((droneCount < 6) && (bwapi.canMake(UnitTypes.Zerg_Drone.ordinal()))) {
            for (final Unit unit : bwapi.getMyUnits()) {
                if (unit.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                    if (bwapi.morph(unit.getID(), UnitTypes.Zerg_Drone.getID())) {
                        droneCount++;
                    }
                }
            }
        }

        // collect minerals
        for (final Unit unit : bwapi.getMyUnits()) {
            if (unit.getTypeID() == UnitTypes.Zerg_Drone.ordinal()) {
                if (unit.isIdle() && (unit.getID() != poolDrone)) {
                    for (final Unit minerals : bwapi.getNeutralUnits()) {
                        if ((minerals.getTypeID() == UnitTypes.Resource_Mineral_Field.ordinal())
                                && !claimed.contains(minerals.getID())) {
                            final double distance =
                                    Math.sqrt(Math.pow(minerals.getX() - unit.getX(), 2)
                                            + Math.pow(minerals.getY() - unit.getY(), 2));

                            if (distance < 300) {
                                bwapi.rightClick(unit.getID(), minerals.getID());
                                claimed.add(minerals.getID());
                                break;
                            }
                        }
                    }
                }
            }
        }

        // build a spawning pool
        if (!haveSpawningPool() && bwapi.canMake(UnitTypes.Zerg_Spawning_Pool.ordinal())) {
            for (final Unit potentialDrone : bwapi.getMyUnits()) {
                if (potentialDrone.getTypeID() == UnitTypes.Zerg_Drone.ordinal()) {
                    for (final Unit unit : bwapi.getMyUnits()) {
                        if (unit.getTypeID() == UnitTypes.Zerg_Overlord.ordinal()) {
                            bwapi.build(potentialDrone.getID(), unit.getTileX(), unit.getTileY(),
                                    UnitTypes.Zerg_Spawning_Pool.getID());
                            break;
                        }
                    }
                    break;
                }
            }
        }

        // spawn overlords
        if (((bwapi.getSelf().getSupplyUsed() + 2) >= bwapi.getSelf().getSupplyTotal())
                && (bwapi.getSelf().getSupplyTotal() > supplyCap)) {
            if (bwapi.canMake(UnitTypes.Zerg_Overlord.ordinal())) {
                for (final Unit larva : bwapi.getMyUnits()) {
                    if (larva.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                        bwapi.morph(larva.getID(), UnitTypes.Zerg_Overlord.getID());
                        supplyCap = bwapi.getSelf().getSupplyTotal();
                    }
                }
            }
        }
        // spawn zerglings
        else if (bwapi.canMake(UnitTypes.Zerg_Zergling.ordinal())) {
            for (final Unit unit : bwapi.getMyUnits()) {
                if ((unit.getTypeID() == UnitTypes.Zerg_Spawning_Pool.getID())
                        && unit.isCompleted()) {
                    for (final Unit larva : bwapi.getMyUnits()) {
                        if (larva.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                            bwapi.morph(larva.getID(), UnitTypes.Zerg_Zergling.getID());
                        }
                    }
                }
            }
        }

        // attack
        for (final Unit unit : bwapi.getMyUnits()) {
            if ((unit.getTypeID() == UnitTypes.Zerg_Zergling.getID()) && unit.isIdle()) {
                for (final Unit enemy : bwapi.getEnemyUnits()) {
                    bwapi.attack(unit.getID(), enemy.getX(), enemy.getY());
                    break;
                }
            }
        }
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : bwapi.getMyUnits()) {
            if (unit.getTypeID() == UnitTypes.Zerg_Spawning_Pool.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
