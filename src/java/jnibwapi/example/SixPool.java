package jnibwapi.example;

import java.util.HashSet;

import jnibwapi.AbstractAgent;
import jnibwapi.model.Unit;
import jnibwapi.types.UnitType.UnitTypes;

public class SixPool extends AbstractAgent {

    /** Used for mineral splits. */
    private final HashSet<Integer> claimed = new HashSet<Integer>();

    /** Number of drones. Game starts with four. */
    private int droneCount = 4;

    /** When should the next Overlord be spawned? */
    private int supplyCap = 0;

    /** Has a drone been assigned to building a pool? */
    private final int poolDrone = -1;

    public static void main(final String[] args) {
        new SixPool(true).start();
    }

    public SixPool(final boolean enableBwta) {
        super(enableBwta);
    }

    @Override
    public void matchStart() {
        game.setGameSpeed(10);
        game.enableUserInput();
        game.enablePerfectInformation();
    }

    @Override
    public void matchFrame() {
        // spawn a drone
        if ((droneCount < 6) && (game.canMake(UnitTypes.Zerg_Drone.ordinal()))) {
            for (final Unit unit : game.getMyUnits()) {
                if (unit.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                    if (game.morph(unit.getID(), UnitTypes.Zerg_Drone.getID())) {
                        droneCount++;
                    }
                }
            }
        }

        // collect minerals
        for (final Unit unit : game.getMyUnits()) {
            if (unit.getTypeID() == UnitTypes.Zerg_Drone.ordinal()) {
                if (unit.isIdle() && (unit.getID() != poolDrone)) {
                    for (final Unit minerals : game.getNeutralUnits()) {
                        if ((minerals.getTypeID() == UnitTypes.Resource_Mineral_Field.ordinal())
                                && !claimed.contains(minerals.getID())) {
                            final double distance =
                                    Math.sqrt(Math.pow(minerals.getX() - unit.getX(), 2)
                                            + Math.pow(minerals.getY() - unit.getY(), 2));

                            if (distance < 300) {
                                game.rightClick(unit.getID(), minerals.getID());
                                claimed.add(minerals.getID());
                                break;
                            }
                        }
                    }
                }
            }
        }

        // build a spawning pool
        if (!haveSpawningPool() && game.canMake(UnitTypes.Zerg_Spawning_Pool.ordinal())) {
            for (final Unit potentialDrone : game.getMyUnits()) {
                if (potentialDrone.getTypeID() == UnitTypes.Zerg_Drone.ordinal()) {
                    for (final Unit unit : game.getMyUnits()) {
                        if (unit.getTypeID() == UnitTypes.Zerg_Overlord.ordinal()) {
                            game.build(potentialDrone.getID(), unit.getTileX(), unit.getTileY(),
                                    UnitTypes.Zerg_Spawning_Pool.getID());
                            break;
                        }
                    }
                    break;
                }
            }
        }

        // spawn overlords
        if (((game.getSelf().getSupplyUsed() + 2) >= game.getSelf().getSupplyTotal())
                && (game.getSelf().getSupplyTotal() > supplyCap)) {
            if (game.canMake(UnitTypes.Zerg_Overlord.ordinal())) {
                for (final Unit larva : game.getMyUnits()) {
                    if (larva.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                        game.morph(larva.getID(), UnitTypes.Zerg_Overlord.getID());
                        supplyCap = game.getSelf().getSupplyTotal();
                    }
                }
            }
        }
        // spawn zerglings
        else if (game.canMake(UnitTypes.Zerg_Zergling.ordinal())) {
            for (final Unit unit : game.getMyUnits()) {
                if ((unit.getTypeID() == UnitTypes.Zerg_Spawning_Pool.getID())
                        && unit.isCompleted()) {
                    for (final Unit larva : game.getMyUnits()) {
                        if (larva.getTypeID() == UnitTypes.Zerg_Larva.getID()) {
                            game.morph(larva.getID(), UnitTypes.Zerg_Zergling.getID());
                        }
                    }
                }
            }
        }

        // attack
        for (final Unit unit : game.getMyUnits()) {
            if ((unit.getTypeID() == UnitTypes.Zerg_Zergling.getID()) && unit.isIdle()) {
                for (final Unit enemy : game.getEnemyUnits()) {
                    game.attack(unit.getID(), enemy.getX(), enemy.getY());
                    break;
                }
            }
        }
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : game.getMyUnits()) {
            if (unit.getTypeID() == UnitTypes.Zerg_Spawning_Pool.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
