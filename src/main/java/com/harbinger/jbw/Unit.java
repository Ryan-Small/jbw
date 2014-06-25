package com.harbinger.jbw;

import com.harbinger.jbw.Position.Resolution;

/**
 * This class is used to get information about individual units as well as issue order to units.
 * Each unit in the game has a unique Unit object, and Unit objects are not deleted until the end of
 * the match.
 *
 * <p>
 * Every Unit in the game is either accessible or inaccessible. To determine if an AI can access a
 * particular unit, BWAPI checks to see if Flag::CompleteMapInformation is enabled. So there are two
 * cases to consider - either the flag is enabled, or it is disabled:
 *
 * <p>
 * If Flag::CompleteMapInformation is disabled, then a unit is accessible if and only if it is
 * visible. Some properties of visible enemy units will not be made available to the AI (such as the
 * contents of visible enemy dropships). If a unit is not visible, UnitInterface::exists will return
 * false, regardless of whether or not the unit exists. This is because absolutely no state
 * information on invisible enemy units is made available. To determine if an enemy unit has been
 * destroyed, the agent must watch for AIModule::onUnitDestroy messages from BWAPI, which is only
 * called for visible units which get destroyed.
 *
 * <p>
 * If Flag::CompleteMapInformation is enabled, then all units that exist in the game are accessible,
 * and UnitInterface::exists is accurate for all units. Similarly AIModule::onUnitDestroy messages
 * are generated for all units that get destroyed, not just visible ones.
 *
 * <p>
 * If a Unit is not accessible, then only the getInitial__ functions will be available to the agent.
 * However for units that were owned by the player, getPlayer and getType will continue to work for
 * units that have been destroyed.
 *
 * For a description of fields see: http://code.google.com/p/bwapi/wiki/Unit
 */
public class Unit {

    // TODO: Create a null unit

    public static final int NUM_ATTRIBUTES = 123;

    private static final double FIXED_SCALE = 100.0;
    private static final double TO_DEGREES = 180.0 / Math.PI;

    private final Broodwar broodwar;

    private final int id;
    private int replayId;
    private int playerId;
    private int typeId;
    private int x;
    private int y;
    private int tileX;
    private int tileY;
    private double angle;
    private double velocityX;
    private double velocityY;
    private int hitPoints;
    private int shield;
    private int energy;
    private int resources;
    private int resourceGroup;
    private int lastCommandFrame;
    private int lastCommandId;
    private int lastAttackingPlayerId;
    private int initialTypeId;
    private int initialX;
    private int initialY;
    private int initialTileX;
    private int initialTileY;
    private int initialHitPoints;
    private int initialResources;
    private int killCount;
    private int acidSporeCount;
    private int interceptorCount;
    private int scarabCount;
    private int spiderMineCount;
    private int groundWeaponCooldown;
    private int airWeaponCooldown;
    private int spellCooldown;
    private int defenseMatrixPoints;
    private int defenseMatrixTimer;
    private int ensnareTimer;
    private int irradiateTimer;
    private int lockdownTimer;
    private int maelstromTimer;
    private int orderTimer;
    private int plagueTimer;
    private int removeTimer;
    private int stasisTimer;
    private int stimTimer;
    private int buildTypeId;
    private int trainingQueueSize;
    private int researchingTechId;
    private int upgradingUpgradeId;
    private int remainingBuildTimer;
    private int remainingTrainTime;
    private int remainingResearchTime;
    private int remainingUpgradeTime;
    private int buildUnitId;
    private int targetUnitId;
    private int targetX;
    private int targetY;
    private int orderId;
    private int orderTargetId;
    private int secondaryOrderId;
    private int rallyX;
    private int rallyY;
    private int rallyUnitId;
    private int addOnId;
    private int nydusExitUnitId;
    private int transportId;
    private int loadedUnitsCount;
    private int carrierUnitId;
    private int hatcheryUnitId;
    private int larvaCount;
    private int powerUpUnitId;
    private boolean exists;
    private boolean nukeReady;
    private boolean accelerating;
    private boolean attacking;
    private boolean attackFrame;
    private boolean beingConstructed;
    private boolean beingGathered;
    private boolean beingHealed;
    private boolean blind;
    private boolean braking;
    private boolean burrowed;
    private boolean carryingGas;
    private boolean carryingMinerals;
    private boolean cloaked;
    private boolean completed;
    private boolean constructing;
    private boolean defenseMatrixed;
    private boolean detected;
    private boolean ensnared;
    private boolean following;
    private boolean gatheringGas;
    private boolean gatheringMinerals;
    private boolean hallucination;
    private boolean holdingPosition;
    private boolean idle;
    private boolean interruptable;
    private boolean invincible;
    private boolean irradiated;
    private boolean lifted;
    private boolean loaded;
    private boolean lockedDown;
    private boolean maelstrommed;
    private boolean morphing;
    private boolean moving;
    private boolean parasited;
    private boolean patrolling;
    private boolean plagued;
    private boolean repairing;
    private boolean selected;
    private boolean sieged;
    private boolean startingAttack;
    private boolean stasised;
    private boolean stimmed;
    private boolean stuck;
    private boolean training;
    private boolean underAttack;
    private boolean underDarkSwarm;
    private boolean underDisruptionWeb;
    private boolean underStorm;
    private boolean unpowered;
    private boolean upgrading;
    private boolean visible;

    public Unit(final int id, final Broodwar broodwar) {
        this.id = id;
        this.broodwar = broodwar;
    }

    public void setDestroyed() {
        exists = false;
    }

    public void update(final int[] data, int index) {
        index++; // ID = data[index++];
        replayId = data[index++];
        playerId = data[index++];
        typeId = data[index++];
        x = data[index++];
        y = data[index++];
        tileX = data[index++];
        tileY = data[index++];
        angle = data[index++] / TO_DEGREES;
        velocityX = data[index++] / FIXED_SCALE;
        velocityY = data[index++] / FIXED_SCALE;
        hitPoints = data[index++];
        shield = data[index++];
        energy = data[index++];
        resources = data[index++];
        resourceGroup = data[index++];
        lastCommandFrame = data[index++];
        lastCommandId = data[index++];
        lastAttackingPlayerId = data[index++];
        initialTypeId = data[index++];
        initialX = data[index++];
        initialY = data[index++];
        initialTileX = data[index++];
        initialTileY = data[index++];
        initialHitPoints = data[index++];
        initialResources = data[index++];
        killCount = data[index++];
        acidSporeCount = data[index++];
        interceptorCount = data[index++];
        scarabCount = data[index++];
        spiderMineCount = data[index++];
        groundWeaponCooldown = data[index++];
        airWeaponCooldown = data[index++];
        spellCooldown = data[index++];
        defenseMatrixPoints = data[index++];
        defenseMatrixTimer = data[index++];
        ensnareTimer = data[index++];
        irradiateTimer = data[index++];
        lockdownTimer = data[index++];
        maelstromTimer = data[index++];
        orderTimer = data[index++];
        plagueTimer = data[index++];
        removeTimer = data[index++];
        stasisTimer = data[index++];
        stimTimer = data[index++];
        buildTypeId = data[index++];
        trainingQueueSize = data[index++];
        researchingTechId = data[index++];
        upgradingUpgradeId = data[index++];
        remainingBuildTimer = data[index++];
        remainingTrainTime = data[index++];
        remainingResearchTime = data[index++];
        remainingUpgradeTime = data[index++];
        buildUnitId = data[index++];
        targetUnitId = data[index++];
        targetX = data[index++];
        targetY = data[index++];
        orderId = data[index++];
        orderTargetId = data[index++];
        secondaryOrderId = data[index++];
        rallyX = data[index++];
        rallyY = data[index++];
        rallyUnitId = data[index++];
        addOnId = data[index++];
        nydusExitUnitId = data[index++];
        transportId = data[index++];
        loadedUnitsCount = data[index++];
        carrierUnitId = data[index++];
        hatcheryUnitId = data[index++];
        larvaCount = data[index++];
        powerUpUnitId = data[index++];
        exists = data[index++] == 1;
        nukeReady = data[index++] == 1;
        accelerating = data[index++] == 1;
        attacking = data[index++] == 1;
        attackFrame = data[index++] == 1;
        beingConstructed = data[index++] == 1;
        beingGathered = data[index++] == 1;
        beingHealed = data[index++] == 1;
        blind = data[index++] == 1;
        braking = data[index++] == 1;
        burrowed = data[index++] == 1;
        carryingGas = data[index++] == 1;
        carryingMinerals = data[index++] == 1;
        cloaked = data[index++] == 1;
        completed = data[index++] == 1;
        constructing = data[index++] == 1;
        defenseMatrixed = data[index++] == 1;
        detected = data[index++] == 1;
        ensnared = data[index++] == 1;
        following = data[index++] == 1;
        gatheringGas = data[index++] == 1;
        gatheringMinerals = data[index++] == 1;
        hallucination = data[index++] == 1;
        holdingPosition = data[index++] == 1;
        idle = data[index++] == 1;
        interruptable = data[index++] == 1;
        invincible = data[index++] == 1;
        irradiated = data[index++] == 1;
        lifted = data[index++] == 1;
        loaded = data[index++] == 1;
        lockedDown = data[index++] == 1;
        maelstrommed = data[index++] == 1;
        morphing = data[index++] == 1;
        moving = data[index++] == 1;
        parasited = data[index++] == 1;
        patrolling = data[index++] == 1;
        plagued = data[index++] == 1;
        repairing = data[index++] == 1;
        selected = data[index++] == 1;
        sieged = data[index++] == 1;
        startingAttack = data[index++] == 1;
        stasised = data[index++] == 1;
        stimmed = data[index++] == 1;
        stuck = data[index++] == 1;
        training = data[index++] == 1;
        underAttack = data[index++] == 1;
        underDarkSwarm = data[index++] == 1;
        underDisruptionWeb = data[index++] == 1;
        underStorm = data[index++] == 1;
        unpowered = data[index++] == 1;
        upgrading = data[index++] == 1;
        visible = data[index++] == 1;
    }

    @Override
    public Unit clone() {
        /*
         * Safe to use clone for this class because it has only primitive fields and a reference to
         * BWAPI, which should be shallow-copied. Beware when using equals or == with cloned Units
         * as they will be considered equal (and not ==) regardless of any changes in their
         * properties over time.
         */
        try {
            return (Unit) super.clone();
        } catch (final CloneNotSupportedException e) {
            // Should never happen, as this implements Cloneable and extends Object
            e.printStackTrace();
            return null;
        }
    }

    /** Returns the edge-to-edge distance between the current unit and the target unit. */
    public double getDistance(final Unit target) {
        if (!isExists() || (target == null) || !target.isExists()) {
            return Integer.MAX_VALUE;
        }

        if (this == target) {
            return 0;
        }

        int xDist = getLeft() - (target.getRight() + 1);
        if (xDist < 0) {
            xDist = target.getLeft() - (getRight() + 1);
            if (xDist < 0) {
                xDist = 0;
            }
        }
        int yDist = getTop() - (target.getBottom() + 1);
        if (yDist < 0) {
            yDist = target.getTop() - (getBottom() + 1);
            if (yDist < 0) {
                yDist = 0;
            }
        }
        return new Position(0, 0, Resolution.PIXEL).getDistance(new Position(xDist, yDist,
                Resolution.PIXEL), Resolution.PIXEL);
    }

    /** Returns the distance from the edge of the current unit to the target position. */
    public double getDistance(final Position target) {
        if (!isExists()) {
            return Integer.MAX_VALUE;
        }
        int xDist = getLeft() - (target.getX(Resolution.PIXEL) + 1);
        if (xDist < 0) {
            xDist = target.getX(Resolution.PIXEL) - (getRight() + 1);
            if (xDist < 0) {
                xDist = 0;
            }
        }
        int yDist = getTop() - (target.getY(Resolution.PIXEL) + 1);
        if (yDist < 0) {
            yDist = target.getY(Resolution.PIXEL) - (getBottom() + 1);
            if (yDist < 0) {
                yDist = 0;
            }
        }
        return new Position(0, 0, Resolution.PIXEL).getDistance(new Position(xDist, yDist,
                Resolution.PIXEL), Resolution.PIXEL);
    }

    public Position getTopLeft() {
        return new Position(getLeft(), getTop(), Resolution.PIXEL);
    }

    public Position getBottomRight() {
        return new Position(getRight(), getBottom(), Resolution.PIXEL);
    }

    public int getLeft() {
        return x - getType().getDimensionLeft();
    }

    public int getTop() {
        return y - getType().getDimensionUp();
    }

    public int getRight() {
        return x + getType().getDimensionRight();
    }

    public int getBottom() {
        return y + getType().getDimensionDown();
    }

    /**
     * Returns the unique ID for this unit.
     *
     * @return the unique ID for this unit
     */
    public int getId() {
        return id;
    }

    public int getReplayId() {
        return replayId;
    }

    public Player getPlayer() {
        return broodwar.getPlayer(playerId);
    }

    public UnitType getType() {
        return UnitType.getUnitType(typeId);
    }

    public double getAngle() {
        return angle;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getShields() {
        return shield;
    }

    public int getEnergy() {
        return energy;
    }

    public int getResources() {
        return resources;
    }

    public int getResourceGroup() {
        return resourceGroup;
    }

    public int getLastCommandFrame() {
        return lastCommandFrame;
    }

    public CommandType getLastCommand() {
        return CommandType.getCommandType(lastCommandId);
    }

    public Player getLastAttackingPlayer() {
        return broodwar.getPlayer(lastAttackingPlayerId);
    }

    public UnitType getInitialType() {
        return UnitType.getUnitType(initialTypeId);
    }

    public Position getInitialPosition() {
        return new Position(initialX, initialY, Resolution.PIXEL);
    }

    public int getInitialHitPoints() {
        return initialHitPoints;
    }

    public int getInitialResources() {
        return initialResources;
    }

    public int getKillCount() {
        return killCount;
    }

    public int getAcidSporeCount() {
        return acidSporeCount;
    }

    /** @see #getInterceptors() TODO */
    public int getInterceptorCount() {
        return interceptorCount;
    }

    public int getScarabCount() {
        return scarabCount;
    }

    public int getSpiderMineCount() {
        return spiderMineCount;
    }

    public int getGroundWeaponCooldown() {
        return groundWeaponCooldown;
    }

    public int getAirWeaponCooldown() {
        return airWeaponCooldown;
    }

    public int getSpellCooldown() {
        return spellCooldown;
    }

    public int getDefenseMatrixPoints() {
        return defenseMatrixPoints;
    }

    public int getDefenseMatrixTimer() {
        return defenseMatrixTimer;
    }

    public int getEnsnareTimer() {
        return ensnareTimer;
    }

    public int getIrradiateTimer() {
        return irradiateTimer;
    }

    public int getLockdownTimer() {
        return lockdownTimer;
    }

    public int getMaelstromTimer() {
        return maelstromTimer;
    }

    public int getOrderTimer() {
        return orderTimer;
    }

    public int getPlagueTimer() {
        return plagueTimer;
    }

    public int getRemoveTimer() {
        return removeTimer;
    }

    public int getStasisTimer() {
        return stasisTimer;
    }

    public int getStimTimer() {
        return stimTimer;
    }

    public UnitType getBuildType() {
        return UnitType.getUnitType(buildTypeId);
    }

    public int getTrainingQueueSize() {
        return trainingQueueSize;
    }

    public TechType getTech() {
        return TechType.getTechType(researchingTechId);
    }

    public UpgradeType getUpgrade() {
        return UpgradeType.getUpgradeType(upgradingUpgradeId);
    }

    public int getRemainingBuildTimer() {
        return remainingBuildTimer;
    }

    public int getRemainingTrainTime() {
        return remainingTrainTime;
    }

    public int getRemainingResearchTime() {
        return remainingResearchTime;
    }

    public int getRemainingUpgradeTime() {
        return remainingUpgradeTime;
    }

    public Unit getBuildUnit() {
        return broodwar.getUnit(buildUnitId);
    }

    public Unit getTarget() {
        return broodwar.getUnit(targetUnitId);
    }

    public Position getTargetPosition() {
        return new Position(targetX, targetY, Resolution.PIXEL);
    }

    public OrderType getOrder() {
        return OrderType.getOrderType(orderId);
    }

    public Unit getOrderTarget() {
        return broodwar.getUnit(orderTargetId);
    }

    public OrderType getSecondaryOrder() {
        return OrderType.getOrderType(secondaryOrderId);
    }

    public Position getRallyPosition() {
        return new Position(rallyX, rallyY, Resolution.PIXEL);
    }

    public Unit getRallyUnit() {
        return broodwar.getUnit(rallyUnitId);
    }

    public Unit getAddon() {
        return broodwar.getUnit(addOnId);
    }

    public Unit getNydusExit() {
        return broodwar.getUnit(nydusExitUnitId);
    }

    public Unit getTransport() {
        return broodwar.getUnit(transportId);
    }

    /** TODO @see #getLoadedUnits() */
    public int getLoadedUnitsCount() {
        return loadedUnitsCount;
    }

    public Unit getCarrier() {
        return broodwar.getUnit(carrierUnitId);
    }

    public Unit getHatchery() {
        return broodwar.getUnit(hatcheryUnitId);
    }

    /** TODO @see #getLarva() */
    public int getLarvaCount() {
        return larvaCount;
    }

    public Unit getPowerUp() {
        return broodwar.getUnit(powerUpUnitId);
    }

    public boolean isExists() {
        return exists;
    }

    public boolean isNukeReady() {
        return nukeReady;
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public boolean isAttackFrame() {
        return attackFrame;
    }

    public boolean isBeingConstructed() {
        return beingConstructed;
    }

    public boolean isBeingGathered() {
        return beingGathered;
    }

    public boolean isBeingHealed() {
        return beingHealed;
    }

    public boolean isBlind() {
        return blind;
    }

    public boolean isBraking() {
        return braking;
    }

    public boolean isBurrowed() {
        return burrowed;
    }

    public boolean isCarryingGas() {
        return carryingGas;
    }

    public boolean isCarryingMinerals() {
        return carryingMinerals;
    }

    public boolean isCloaked() {
        return cloaked;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isConstructing() {
        return constructing;
    }

    public boolean isDefenseMatrixed() {
        return defenseMatrixed;
    }

    public boolean isDetected() {
        return detected;
    }

    public boolean isEnsnared() {
        return ensnared;
    }

    public boolean isFollowing() {
        return following;
    }

    public boolean isGatheringGas() {
        return gatheringGas;
    }

    public boolean isGatheringMinerals() {
        return gatheringMinerals;
    }

    public boolean isHallucination() {
        return hallucination;
    }

    public boolean isHoldingPosition() {
        return holdingPosition;
    }

    public boolean isIdle() {
        return idle;
    }

    public boolean isInterruptable() {
        return interruptable;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public boolean isIrradiated() {
        return irradiated;
    }

    public boolean isLifted() {
        return lifted;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isLockedDown() {
        return lockedDown;
    }

    public boolean isMaelstrommed() {
        return maelstrommed;
    }

    public boolean isMorphing() {
        return morphing;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isParasited() {
        return parasited;
    }

    public boolean isPatrolling() {
        return patrolling;
    }

    public boolean isPlagued() {
        return plagued;
    }

    public boolean isRepairing() {
        return repairing;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isSieged() {
        return sieged;
    }

    public boolean isStartingAttack() {
        return startingAttack;
    }

    public boolean isStasised() {
        return stasised;
    }

    public boolean isStimmed() {
        return stimmed;
    }

    public boolean isStuck() {
        return stuck;
    }

    public boolean isTraining() {
        return training;
    }

    public boolean isUnderAttack() {
        return underAttack;
    }

    public boolean isUnderDarkSwarm() {
        return underDarkSwarm;
    }

    public boolean isUnderDisruptionWeb() {
        return underDisruptionWeb;
    }

    public boolean isUnderStorm() {
        return underStorm;
    }

    public boolean isUnpowered() {
        return unpowered;
    }

    public boolean isUpgrading() {
        return upgrading;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Orders this unit to attack move to a location. After issuing, orders will become
     * {@code OrderTypes.AttackMove}.
     *
     * @param position
     *            position to move to
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean attack(final Position position) {
        return attack(getId(), position.getX(Resolution.PIXEL), position.getY(Resolution.PIXEL));
    }

    private native boolean attack(final int unitId, final int x, final int y);

    /**
     * Orders this unit to attack another unit. After issuing, orders will become
     * {@code OrderTypes.AttackUnit}.
     *
     * @param target
     *            unit to attack
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean attack(final Unit target) {
        return attack(getId(), target.getId());
    }

    private native boolean attack(final int unitId, final int targetId);

    /**
     * Orders this unit to build.
     *
     * @param building
     *            building to build
     *
     * @param position
     *            position to build at
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean build(final UnitType building, final Position position) {
        return build(getId(), building.getId(), position.getX(Resolution.BUILD),
                position.getY(Resolution.BUILD));
    }

    private native boolean build(final int builderId, final int buildingId, final int tx,
            final int ty);

    /**
     * Orders this unit to build an addon.
     *
     * <p>
     * This unit should be a Terran building that can build addons of the specified type. The
     * {@link #build(UnitType, Position)}.
     *
     * @param addon
     *            unit to addon
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean buildAddon(final Unit addon) {
        return buildAddon(getId(), addon.getId());
    }

    private native boolean buildAddon(final int buildingId, final int addonId);

    /**
     * Orders this unit to add the specified unit type to the training queue. This command can also
     * be used to make interceptors and scarabs.
     *
     * @param unit
     *            unit to train
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean train(final UnitType unit) {
        return train(getId(), unit.getId());
    }

    private native boolean train(final int buildingId, final int addonId);

    /**
     * Orders this unit to morph into the specified unit type.
     *
     * @param target
     *            unit to build
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean morph(final UnitType target) {
        return morph(getId(), target.getId());
    }

    private native boolean morph(final int unitId, final int targetId);

    /**
     * Orders this unit to research the given tech.
     *
     * @param tech
     *            tech to research
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean research(final TechType tech) {
        return research(getId(), tech.getId());
    }

    private native boolean research(final int researcherId, final int techId);

    /**
     * Orders this unit to upgrade the specified type.
     *
     * @param upgrade
     *            upgrade to start
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean upgrade(final UpgradeType upgrade) {
        return upgrade(getId(), upgrade.getId());
    }

    private native boolean upgrade(final int unitId, final int updateId);

    /**
     * Orders this unit to set its rally point to the specified location.
     *
     * @param position
     *            position to rally to
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean setRallyPoint(final Position position) {
        return setRallyPoint(getId(), position.getX(Resolution.PIXEL),
                position.getY(Resolution.PIXEL));
    }

    private native boolean setRallyPoint(final int unitId, final int x, final int y);

    /**
     * Orders this unit to set its rally point to the specified target.
     *
     * @param target
     *            target to rally to
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean setRallyPoint(final Unit target) {
        return setRallyPoint(getId(), target.getId());
    }

    private native boolean setRallyPoint(final int unitId, final int targetId);

    /**
     * Orders this unit to move to the specified location. After issuing, the orders will become
     * {@code OrderTypes.Move}.
     *
     * @param position
     *            position to move to
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean move(final Position position) {
        return move(getId(), position.getX(Resolution.PIXEL), position.getY(Resolution.PIXEL));
    }

    private native boolean move(final int unitId, final int x, final int y);

    /**
     * Orders this unit to patrol between its current position and the specified location. After
     * issuing, the orders will become {@code OrderTpes.Patrol}.
     *
     * @param position
     *            position to patrol to
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean patrol(final Position position) {
        return patrol(getId(), position.getX(Resolution.PIXEL), position.getY(Resolution.PIXEL));
    }

    private native boolean patrol(final int unitId, final int x, final int y);

    /**
     * Orders this unit to hold its position. After issuing, the orders become
     * {@code OrderTypes.HoldPosition} while transitioning.
     *
     * <p>
     * Reavers and Carriers can only hold position if they have at least one Scarab or Interceptor.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean holdPosition() {
        return holdPosition(getId());
    }

    private native boolean holdPosition(final int unitId);

    /**
     * Orders this unit to stop. After issuing, the orders will become {@code OrderTypes.Stop}.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean stop() {
        return stop(getId());
    }

    private native boolean stop(final int unitId);

    /**
     * Orders this unit to follow the specified unit. After issuing, the orders will become
     * {@code OrderTypes.Follow}.
     *
     * @param target
     *            target unit to follow
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean follow(final Unit target) {
        return follow(getId(), target.getId());
    }

    public native boolean follow(final int unitId, final int targetId);

    /**
     * Orders this unit to gather the specified unit.
     *
     * <p>
     * This unit should be an Drone, Probe, or SCV. The target should be a mineral patch, Refinery,
     * Assimilator, or Extractor.
     *
     * @param target
     *            target to gather
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean gather(final Unit target) {
        return gather(getId(), target.getId());
    }

    private native boolean gather(final int unitId, final int targetId);

    /**
     * Orders this unit to return its cargo to a nearby resource depot such as a Command Center.
     * Only workers that are carrying minerals or gas can be ordered to return cargo.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean returnCargo() {
        return returnCargo(getId());
    }

    private native boolean returnCargo(final int unitId);

    /**
     * Orders the unit to repair another unit. After issuing, the orders will become
     * {@code OrderTypes.Repair} while transitioning.
     *
     * <p>
     * Only Terran SCVs can be ordered to repair, and the target must be a mechanical Terran unit or
     * building.
     *
     * @param target
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean repair(final Unit target) {
        return repair(getId(), target.getId());
    }

    private native boolean repair(final int unitId, final int targetId);

    /**
     * Orders this unit to burrow. After issuing, the orders will become
     * {@code OrderTypes.Burrowing} while transitioning.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean burrow() {
        return burrow(getId());
    }

    private native boolean burrow(final int unitId);

    /**
     * Orders this unit to unburrow. After issuing, the orders will become
     * {@code OrderTypes.Unburrowing} while transitioning.
     *
     * <p>
     * This unit should be a burrowed unit.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean unburrow() {
        return unburrow(getId());
    }

    private native boolean unburrow(final int unitId);

    /**
     * Orders this unit to cloak.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cloak() {
        return cloak(getId());
    }

    private native boolean cloak(final int unitId);

    /**
     * Orders this unit to decloak.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean decloak() {
        return decloak(getId());
    }

    private native boolean decloak(final int unitId);

    /**
     * Orders this unit to siege. After issuing, the orders will become {@code OrderTypes.Sieging}
     * while transitioning.
     *
     * <p>
     * This unit should be a Terran Siege Tank.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean siege() {
        return siege(getId());
    }

    private native boolean siege(final int unitId);

    /**
     * Orders this unit to unsiege. After issuing, the orders will become
     * {@code OrderTypes.Unsieging} while transitioning.
     *
     * <p>
     * This unit should be a Terran Siege Tank.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean unsiege() {
        return unsiege(getId());
    }

    private native boolean unsiege(final int unitId);

    /**
     * Orders this unit to lift.
     *
     * <p>
     * This unit should be a Terran building that can be lifted.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean lift() {
        return lift(getId());
    }

    private native boolean lift(final int unitId);

    /**
     * Orders this unit to land.
     *
     * <p>
     * This unit should be a lifted Terran building.
     *
     * @param position
     *            position to land the building
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean land(final Position position) {
        return land(getId(), position.getX(Resolution.BUILD), position.getY(Resolution.BUILD));
    }

    private native boolean land(final int unitId, final int x, final int y);

    /**
     * Orders this unit to load the target unit.
     *
     * @param target
     *            target to load
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean load(final Unit target) {
        return load(getId(), target.getId());
    }

    private native boolean load(final int unitId, final int targetId);

    /**
     * Orders this unit to unload the target unit.
     *
     * @param target
     *            target to unload
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean unload(final Unit target) {
        return unload(getId(), target.getId());
    }

    private native boolean unload(final int unitId, final int targetId);

    /**
     * Orders this unit to unload all units.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean unloadAll() {
        return unloadAll(getId());
    }

    private native boolean unloadAll(final int unitId);

    /**
     * Orders this unit to unload all units.
     *
     * <p>
     * This unit should be a Terran Dropship, Protoss Shuttle, or Zerg Overlord. If this unit is a
     * Terran Bunker, the units will be unloaded right outside the bunker.
     *
     * @param location
     *            location to unload Units
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean unloadAll(final Position position) {
        return unloadAll(getId(), position.getX(Resolution.PIXEL), position.getY(Resolution.PIXEL));
    }

    private native boolean unloadAll(final int unitId, final int x, final int y);

    /**
     * Works like the right-click in the GUI (e.g. right-click on a location to order the unit to
     * move).
     *
     * @param position
     *            position to right-click on
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean rightClick(final Position position) {
        return rightClick(getId(), position.getX(Resolution.PIXEL), position.getY(Resolution.PIXEL));
    }

    private native boolean rightClick(final int unitId, final int x, final int y);

    /**
     * Works like the right-click in the GUI (e.g. right-click on a mineral patch to order a worker
     * to mine, right-click on an enemy to attack it).
     *
     * @param target
     *            target to right-click on
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean rightClick(final Unit target) {
        return rightClick(getId(), target.getId());
    }

    private native boolean rightClick(final int unitId, final int targetId);

    /**
     * Orders this unit to halt construction
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean haltConstruction() {
        return haltConstruction(getId());
    }

    private native boolean haltConstruction(final int unitId);

    /**
     * Orders this unit to cancel the construction in progress.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelConstruction() {
        return cancelConstruction(getId());
    }

    private native boolean cancelConstruction(final int unitId);

    /**
     * Orders this unit to cancel the addon in progress.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelAddon() {
        return cancelAddon(getId());
    }

    private native boolean cancelAddon(final int unitId);

    /**
     * Orders this unit to cancel training in progress.
     *
     * @param slot
     *            slot position to cancel
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelTrain(final int slot) {
        return cancelTrain(getId(), slot);
    }

    private native boolean cancelTrain(final int unitId, final int slot);

    /**
     * Orders this unit to cancel the morphing in progress.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelMorph() {
        return cancelMorph(getId());
    }

    private native boolean cancelMorph(final int unitId);

    /**
     * Orders this unit to cancel the current research in progress.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelResearch() {
        return cancelResearch(getId());
    }

    private native boolean cancelResearch(final int unitId);

    /**
     * Orders this unit to cancel the current upgrade in progress.
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean cancelUpgrade() {
        return cancelUpgrade(getId());
    }

    private native boolean cancelUpgrade(final int unitId);

    /**
     * Orders this unit to use tech that doesn't require a target (e.g. Stimpack).
     *
     * @param tech
     *            tech to use
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean useTech(final TechType tech) {
        return useTech(getId(), tech.getId());
    }

    private native boolean useTech(final int unitId, final int typeId);

    /**
     * Orders this unit to use tech that requires a location (e.g. Spider Mines).
     *
     * @param tech
     *            tech to use
     *
     * @param position
     *            position to target the tech
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean useTech(final TechType tech, final Position position) {
        return useTech(getId(), tech.getId(), position.getX(Resolution.PIXEL),
                position.getY(Resolution.PIXEL));
    }

    private native boolean useTech(final int unitId, final int techId, final int x, final int y);

    /**
     * Orders this unit to use tech that requires another unit (e.g. Irradiate).
     *
     * @param tech
     *            tech to use
     *
     * @param target
     *            target to use the tech on
     *
     * @return {@code true} if the command can be completed; {@code false} otherwise
     */
    public boolean useTech(final TechType tech, final Unit target) {
        return useTech(getId(), tech.getId(), target.getId());
    }

    private native boolean useTech(final int unitId, final int typeId, final int targetId);

    public boolean placeCop(final Position position) {
        return placeCop(getId(), position.getX(Resolution.BUILD), position.getY(Resolution.BUILD));
    }

    private native boolean placeCop(final int unitId, final int x, final int y);

    @Override
    public int hashCode() {
        return id;
    }

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
        final Unit other = (Unit) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
