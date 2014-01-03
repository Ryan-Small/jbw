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
        broodwar.setGameSpeed(10);
        broodwar.enableUserInput();
        broodwar.enablePerfectInformation();
    }

    @Override
    public void matchFrame() {
        // spawn a drone
        if ((droneCount < 6) && (broodwar.canMake(UnitTypes.Zerg_Drone))) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if (unit.getTypeId() == UnitTypes.Zerg_Larva.getId()) {
                    if (broodwar.morph(unit, UnitTypes.Zerg_Drone)) {
                        droneCount++;
                    }
                }
            }
        }

        // collect minerals

        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getTypeId() == UnitTypes.Zerg_Drone.ordinal()) {
                if (unit.isIdle() && (unit.getId() != poolDrone)) {
                    for (final Unit minerals : broodwar.getNeutralUnits()) {
                        if ((minerals.getTypeId() == UnitTypes.Resource_Mineral_Field.ordinal())
                                && !claimed.contains(minerals.getId())) {
                            final double distance =
                                    Math.sqrt(Math.pow(minerals.getX() - unit.getX(), 2)
                                            + Math.pow(minerals.getY() - unit.getY(), 2));

                            if (distance < 300) {
                                broodwar.rightClick(unit, minerals);
                                claimed.add(minerals.getId());
                                break;
                            }
                        }
                    }
                }
            }
        }

        // build a spawning pool
        if (!haveSpawningPool() && broodwar.canMake(UnitTypes.Zerg_Spawning_Pool.ordinal())) {
            for (final Unit potentialDrone : broodwar.getMyUnits()) {
                if (potentialDrone.getTypeId() == UnitTypes.Zerg_Drone.ordinal()) {
                    for (final Unit unit : broodwar.getMyUnits()) {
                        if (unit.getTypeId() == UnitTypes.Zerg_Overlord.ordinal()) {
                            broodwar.build(potentialDrone, unit.getTileX(), unit.getTileY(),
                                    UnitTypes.Zerg_Spawning_Pool);
                            break;
                        }
                    }
                    break;
                }
            }
        }

        // spawn overlords
        if (((broodwar.getSelf().getSupplyUsed() + 2) >= broodwar.getSelf().getSupplyTotal())
                && (broodwar.getSelf().getSupplyTotal() > supplyCap)) {
            if (broodwar.canMake(UnitTypes.Zerg_Overlord.ordinal())) {
                for (final Unit larva : broodwar.getMyUnits()) {
                    if (larva.getTypeId() == UnitTypes.Zerg_Larva.getId()) {
                        broodwar.morph(larva, UnitTypes.Zerg_Overlord);
                        supplyCap = broodwar.getSelf().getSupplyTotal();
                    }
                }
            }
        }
        // spawn zerglings
        else if (broodwar.canMake(UnitTypes.Zerg_Zergling.ordinal())) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if ((unit.getTypeId() == UnitTypes.Zerg_Spawning_Pool.getId())
                        && unit.isCompleted()) {
                    for (final Unit larva : broodwar.getMyUnits()) {
                        if (larva.getTypeId() == UnitTypes.Zerg_Larva.getId()) {
                            broodwar.morph(larva, UnitTypes.Zerg_Zergling);
                        }
                    }
                }
            }
        }

        // attack
        for (final Unit unit : broodwar.getMyUnits()) {
            if ((unit.getTypeId() == UnitTypes.Zerg_Zergling.getId()) && unit.isIdle()) {
                for (final Unit enemy : broodwar.getEnemyUnits()) {
                    broodwar.attack(unit, enemy.getX(), enemy.getY());
                    break;
                }
            }
        }
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getTypeId() == UnitTypes.Zerg_Spawning_Pool.ordinal()) {
                return true;
            }
        }
        return false;
    }
}
