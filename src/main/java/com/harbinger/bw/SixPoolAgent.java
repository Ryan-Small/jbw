package com.harbinger.bw;

import com.harbinger.jbw.BroodwarAgent;
import com.harbinger.jbw.Position;
import com.harbinger.jbw.Unit;
import com.harbinger.jbw.UnitType;

/**
 * Serves as an example agent.
 */
public class SixPoolAgent extends BroodwarAgent {

    public static void main(final String[] args) {
        new SixPoolAgent().start();
    }

    @Override
    public void matchStart() {
        broodwar.setGameSpeed(0);
        broodwar.enableUserInput();
        broodwar.enablePerfectInformation();
    }

    @Override
    public void matchFrame() {

        // send idle drones to harvest a nearby mineral patch
        for (final Unit unit : broodwar.getMyUnits()) {
            if ((unit.getType() == UnitType.Zerg_Drone) && unit.isIdle()) {
                for (final Unit minerals : broodwar.getNeutralUnits()) {
                    if ((minerals.getType() == UnitType.Resource_Mineral_Field)) {
                        if (unit.getDistance(minerals) < 300) {
                            unit.rightClick(minerals);
                            break;
                        }
                    }
                }
            }
        }

        // ensure that we have five drone
        if ((getDroneCount() < 6) && (broodwar.canMake(UnitType.Zerg_Drone))) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if (unit.getType() == UnitType.Zerg_Larva) {
                    unit.morph(UnitType.Zerg_Drone);
                    break;
                }
            }
        }

        // build a spawning pool under the overlord
        if (!haveSpawningPool() && broodwar.canMake(UnitType.Zerg_Spawning_Pool)) {
            for (final Unit potentialDrone : broodwar.getMyUnits()) {
                if (potentialDrone.getType() == UnitType.Zerg_Drone) {
                    for (final Unit unit : broodwar.getMyUnits()) {
                        if (unit.getType() == UnitType.Zerg_Overlord) {
                            final Position position = unit.getTargetPosition();
                            potentialDrone.build(UnitType.Zerg_Spawning_Pool, position);
                            break;
                        }
                    }
                    break;
                }
            }
        }

        // spawn zerglins unless we need overlords
        if (((broodwar.getMyself().getSupplyUsed() + 2) < broodwar.getMyself().getSupplyTotal())) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if ((unit.getType() == UnitType.Zerg_Spawning_Pool) && unit.isCompleted()) {
                    for (final Unit larva : broodwar.getMyUnits()) {
                        if (larva.getType() == UnitType.Zerg_Larva) {
                            larva.morph(UnitType.Zerg_Zergling);
                        }
                    }
                }
            }
        } else {
            if (broodwar.canMake(UnitType.Zerg_Overlord)) {
                for (final Unit larva : broodwar.getMyUnits()) {
                    if (larva.getType() == UnitType.Zerg_Larva) {
                        larva.morph(UnitType.Zerg_Overlord);
                    }
                }
            }
        }

        // send the units to attack the enemy
        for (final Unit unit : broodwar.getMyUnits()) {
            if ((unit.getType() == UnitType.Zerg_Zergling) && unit.isIdle()) {
                for (final Unit enemy : broodwar.getEnemyUnits()) {
                    unit.attack(enemy.getTargetPosition());
                    break;
                }
            }
        }
    }

    private int getDroneCount() {
        int droneCount = 0;
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getType() == UnitType.Zerg_Drone) {
                droneCount++;
            }
        }
        return droneCount;
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getType() == UnitType.Zerg_Spawning_Pool) {
                return true;
            }
        }
        return false;
    }
}
