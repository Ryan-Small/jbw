package jnibwapi.example;

import java.util.HashSet;

import jnibwapi.BroodwarAgent;
import jnibwapi.model.Location;
import jnibwapi.model.Unit;
import jnibwapi.types.UnitType.UnitTypes;

public class SixPool extends BroodwarAgent {

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

    /**
     * Configure the game and do any agent initialization.
     * 
     * {@inheritDoc}
     */
    @Override
    public void matchStart() {
        // broodwar.setGameSpeed(0);
        broodwar.enableUserInput();
        broodwar.enablePerfectInformation();
    }

    /**
     * The agent's primary logic will be executed here.
     * 
     * {@inheritDoc}
     */
    @Override
    public void matchFrame() {
        // spawn a drone
        if ((droneCount < 6) && (broodwar.canMake(UnitTypes.Zerg_Drone))) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if (unit.getTypeId().getId() == UnitTypes.Zerg_Larva.getId()) {
                    if (unit.morph(UnitTypes.Zerg_Drone)) {
                        droneCount++;
                    }
                }
            }
        }

        // collect minerals

        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getTypeId() == UnitTypes.Zerg_Drone) {
                if (unit.isIdle() && (unit.getId() != poolDrone)) {
                    for (final Unit minerals : broodwar.getNeutralUnits()) {
                        if ((minerals.getTypeId() == UnitTypes.Resource_Mineral_Field)
                                && !claimed.contains(minerals.getId())) {
                            final int mineralsX = minerals.getLocation().x;
                            final int mineralsY = minerals.getLocation().y;
                            final int unitX = unit.getLocation().x;
                            final int unitY = unit.getLocation().y;
                            final double distance =
                                    Math.sqrt(Math.pow(mineralsX - unitX, 2)
                                            + Math.pow(mineralsY - unitY, 2));

                            if (distance < 300) {
                                unit.rightClick(minerals);
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
                if (potentialDrone.getTypeId() == UnitTypes.Zerg_Drone) {
                    for (final Unit unit : broodwar.getMyUnits()) {
                        if (unit.getTypeId() == UnitTypes.Zerg_Overlord) {
                            final int x = unit.getTileLocation().x;
                            final int y = unit.getTileLocation().y;
                            final Location.Tile position = new Location.Tile(x, y);
                            potentialDrone.build(UnitTypes.Zerg_Spawning_Pool, position);
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
                    if (larva.getTypeId() == UnitTypes.Zerg_Larva) {
                        larva.morph(UnitTypes.Zerg_Overlord);
                        supplyCap = broodwar.getSelf().getSupplyTotal();
                    }
                }
            }
        }
        // spawn zerglings
        else if (broodwar.canMake(UnitTypes.Zerg_Zergling.ordinal())) {
            for (final Unit unit : broodwar.getMyUnits()) {
                if ((unit.getTypeId() == UnitTypes.Zerg_Spawning_Pool) && unit.isCompleted()) {
                    for (final Unit larva : broodwar.getMyUnits()) {
                        if (larva.getTypeId() == UnitTypes.Zerg_Larva) {
                            larva.morph(UnitTypes.Zerg_Zergling);
                        }
                    }
                }
            }
        }

        // attack
        for (final Unit unit : broodwar.getMyUnits()) {
            if ((unit.getTypeId() == UnitTypes.Zerg_Zergling) && unit.isIdle()) {
                for (final Unit enemy : broodwar.getEnemyUnits()) {
                    final int x = enemy.getTileLocation().x;
                    final int y = enemy.getTileLocation().y;
                    final Location position = new Location(x * 32, y * 32);
                    unit.attack(position);
                    break;
                }
            }
        }
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getTypeId() == UnitTypes.Zerg_Spawning_Pool) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unitCreate(final Unit unit) {
        System.out.println("Create: " + unit.getId() + unit.getTypeId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitDiscover(final Unit unit) {
        System.out.println("Discover: " + unit.getId() + unit.getTypeId());
    }

    /**
     * This method enables the user to interact with the agent for various purposes (e.g. debugging,
     * controlling).
     */
    @Override
    public void keyPressed(final int keyCode) {

    }

    /**
     * This method can enable the user to interact with the agent for various purposes (e.g.
     * debugging, controlling).
     * 
     * {@inheritDoc}
     */
    @Override
    public void sendText(final String message) {

    }

    /**
     * This method enables other players or agents to interact with the agent for various purposes
     * (e.g. debugging, controlling).
     */
    @Override
    public void receiveText(final String message) {

    }

    @Override
    public void unitShow(final Unit unit) {
        System.out.println("Show: " + unit.getId() + "\t" + unit.getTypeId());
    }
}
