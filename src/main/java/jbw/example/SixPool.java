package jbw.example;

import java.util.HashSet;

import jbw.BroodwarAgent;
import jbw.model.Unit;
import jbw.types.UnitType.UnitTypes;

public class SixPool extends BroodwarAgent {

    /** Used for mineral splits. */
    private final HashSet<Integer> claimed = new HashSet<Integer>();

    /** Number of drones. Game starts with four. */
    private final int droneCount = 4;

    /** When should the next Overlord be spawned? */
    private final int supplyCap = 0;

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

    private int lastFrameCount = 0;
    private long time = System.currentTimeMillis();

    /**
     * The agent's primary logic will be executed here.
     * 
     * {@inheritDoc}
     */
    @Override
    public void matchFrame() {

        // spawn a drone
        // if ((droneCount < 6) && (broodwar.canMake(UnitTypes.Zerg_Drone))) {
        // for (final Unit unit : broodwar.getMyUnits()) {
        // if (unit.getType() == UnitTypes.Zerg_Larva) {
        // if (unit.morph(UnitTypes.Zerg_Drone)) {
        // droneCount++;
        // }
        // }
        // }
        // }

        // collect minerals
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getType() == UnitTypes.Zerg_Drone) {
                if (unit.isIdle() && (unit.getId() != poolDrone)) {
                    for (final Unit minerals : broodwar.getNeutralUnits()) {
                        // if ((minerals.getType() == UnitTypes.Resource_Mineral_Field)
                        // && !claimed.contains(minerals.getId())) {
                        // final int mineralsX = minerals.getTargetPosition().getPX();
                        // final int mineralsY = minerals.getTargetPosition().getPY();
                        // final int unitX = unit.getTargetPosition().getPX();
                        // final int unitY = unit.getTargetPosition().getPY();
                        // final double distance =
                        // Math.sqrt(Math.pow(mineralsX - unitX, 2)
                        // + Math.pow(mineralsY - unitY, 2));
                        //
                        // if (distance < 300) {
                        // unit.rightClick(minerals);
                        // claimed.add(minerals.getId());
                        // break;
                        // }
                        // }
                    }
                }
            }
        }

        if (broodwar.getFrameCount() > lastFrameCount) {
            System.out.println("Time : " + (System.currentTimeMillis() - time));
            lastFrameCount = broodwar.getFrameCount();
            time = System.currentTimeMillis();
        }

        // // build a spawning pool
        // if (!haveSpawningPool() && broodwar.canMake(UnitTypes.Zerg_Spawning_Pool)) {
        // for (final Unit potentialDrone : broodwar.getMyUnits()) {
        // if (potentialDrone.getType() == UnitTypes.Zerg_Drone) {
        // for (final Unit unit : broodwar.getMyUnits()) {
        // if (unit.getType() == UnitTypes.Zerg_Overlord) {
        // final Position position = unit.getTargetPosition();
        // potentialDrone.build(UnitTypes.Zerg_Spawning_Pool, position);
        // break;
        // }
        // }
        // break;
        // }
        // }
        // }
        //
        // // spawn overlords
        // if (((broodwar.getSelf().getSupplyUsed() + 2) >= broodwar.getSelf().getSupplyTotal())
        // && (broodwar.getSelf().getSupplyTotal() > supplyCap)) {
        // if (broodwar.canMake(UnitTypes.Zerg_Overlord)) {
        // for (final Unit larva : broodwar.getMyUnits()) {
        // if (larva.getType() == UnitTypes.Zerg_Larva) {
        // larva.morph(UnitTypes.Zerg_Overlord);
        // supplyCap = broodwar.getSelf().getSupplyTotal();
        // }
        // }
        // }
        // }
        // // spawn zerglings
        // else if (broodwar.canMake(UnitTypes.Zerg_Zergling)) {
        // for (final Unit unit : broodwar.getMyUnits()) {
        // if ((unit.getType() == UnitTypes.Zerg_Spawning_Pool) && unit.isCompleted()) {
        // for (final Unit larva : broodwar.getMyUnits()) {
        // if (larva.getType() == UnitTypes.Zerg_Larva) {
        // larva.morph(UnitTypes.Zerg_Zergling);
        // }
        // }
        // }
        // }
        // }
        //
        // // attack
        // for (final Unit unit : broodwar.getMyUnits()) {
        // if ((unit.getType() == UnitTypes.Zerg_Zergling) && unit.isIdle()) {
        // for (final Unit enemy : broodwar.getEnemyUnits()) {
        // unit.attack(enemy.getTargetPosition());
        // break;
        // }
        // }
        // }
    }

    private boolean haveSpawningPool() {
        for (final Unit unit : broodwar.getMyUnits()) {
            if (unit.getType() == UnitTypes.Zerg_Spawning_Pool) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unitCreate(final Unit unit) {
        // System.out.println("Create: " + unit.getId() + unit.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitDiscover(final Unit unit) {
        // System.out.println("Discover: " + unit.getId() + unit.getType());
    }

    @Override
    public void unitShow(final Unit unit) {
        // System.out.println("Show: " + unit.getId() + "\t" + unit.getType());
    }
}
