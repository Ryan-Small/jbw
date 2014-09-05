package com.harbinger.jbw.example;

import com.harbinger.jbw.BroodwarAgent;
import com.harbinger.jbw.Position;
import com.harbinger.jbw.Type.UnitType;
import com.harbinger.jbw.Unit;

/**
 * Serves as an example agent to introduce JavaBroodwar to new developers.
 *
 * <p>
 * The agent uses a six-pool strategy, thus requiring the agent to be playing Zerg. The agent will
 * attempt to build the Spawning Pool under the initial Overlord. Some starting locations on various
 * maps (usually the bottom-right) start the Overlord in a position that prevent the Spawning Pool
 * from being built. The agent does cheat by enabling user input and perfect information.
 */
public class SixPoolAgent extends BroodwarAgent {

    public static void main(final String[] args) {
        new SixPoolAgent().launch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void matchEnd(final boolean isWinner) {
        terminateBroodwar();
    }

    /**
     * Initialize the agent and configure any game options here.
     */
    @Override
    public void matchStart() {
        broodwar.setFrameDelay(0);
        broodwar.enableUserInput();
        broodwar.enablePerfectInformation();
    }

    /**
     * Handle the agent's logic here.
     */
    @Override
    public void matchFrame() {
        // TODO: Why are supply numbers being doubled?
        final int supplyUsed = broodwar.getAgent().getSupplyUsed() / 2;
        final int supplyTotal = broodwar.getAgent().getSupplyTotal() / 2;

        // build units
        if (supplyUsed >= supplyTotal) {
            morph(UnitType.Zerg_Overlord);

        } else if (supplyUsed < 6) {
            morph(UnitType.Zerg_Drone);

        } else if (broodwar.canMake(UnitType.Zerg_Zergling)) {
            morph(UnitType.Zerg_Zergling);
        }

        // build spawning pool
        if (isSpawningPoolNeeded() && broodwar.canMake(UnitType.Zerg_Spawning_Pool)) {
            final Unit drone = getUnit(UnitType.Zerg_Drone);
            final Unit overlord = getUnit(UnitType.Zerg_Overlord);
            if ((drone != null) && (overlord != null)) {
                final Position position = overlord.getTargetPosition();
                drone.build(UnitType.Zerg_Spawning_Pool, position);
            }
        }

        // handle idle units
        for (final Unit unit : broodwar.getUnits()) {
            if (unit.isIdle() && (unit.getType() == UnitType.Zerg_Drone)) {
                for (final Unit minerals : broodwar.getNeutralUnits()) {
                    if ((minerals.getType() == UnitType.Resource_Mineral_Field)) {
                        if (unit.getDistance(minerals) < 300) {
                            unit.rightClick(minerals);
                            break;
                        }
                    }
                }
            } else if (unit.isIdle() && (unit.getType() == UnitType.Zerg_Zergling)) {
                for (final Unit enemy : broodwar.getEnemyUnits()) {
                    unit.attack(enemy.getTargetPosition());
                    break;
                }
            }
        }
    }

    private Unit getUnit(final UnitType unitType) {
        for (final Unit unit : broodwar.getUnits()) {
            if (unit.getType() == unitType) {
                return unit;
            }
        }
        return null;
    }

    private boolean isSpawningPoolNeeded() {
        return getUnit(UnitType.Zerg_Spawning_Pool) == null;
    }

    private boolean morph(final UnitType unitType) {
        final Unit larva = getUnit(UnitType.Zerg_Larva);
        if (larva != null) {
            return larva.morph(unitType);
        }
        return false;
    }
}
