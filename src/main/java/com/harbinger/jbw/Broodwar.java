package com.harbinger.jbw;

import static com.harbinger.jbw.Position.Resolution.BUILD;
import static com.harbinger.jbw.Position.Resolution.PIXEL;

import com.harbinger.jbw.Type.Bullet;
import com.harbinger.jbw.Type.Command;
import com.harbinger.jbw.Type.Damage;
import com.harbinger.jbw.Type.Explosion;
import com.harbinger.jbw.Type.Order;
import com.harbinger.jbw.Type.Race;
import com.harbinger.jbw.Type.Tech;
import com.harbinger.jbw.Type.UnitSize;
import com.harbinger.jbw.Type.UnitType;
import com.harbinger.jbw.Type.Upgrade;
import com.harbinger.jbw.Type.Weapon;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * Provides access to the Broodwar game.
 */
public class Broodwar {

    static {
        loadNativeLibrary();
    }

    private static final Charset CHARACTER_SET = getKoreanCharset();

    private final Map<Integer, Unit> units = new HashMap<>();
    private final List<Unit> playerUnits = new ArrayList<>();
    private final List<Unit> alliedUnits = new ArrayList<>();
    private final List<Unit> enemyUnits = new ArrayList<>();
    private final List<Unit> neutralUnits = new ArrayList<>();

    private final Map<Integer, Player> players = new HashMap<>();
    private final List<Player> allies = new ArrayList<>();
    private final List<Player> enemies = new ArrayList<>();

    private final BroodwarListener listener;

    private Player self;
    private Player neutralPlayer;

    private GameMap map;

    /**
     * Constructs the Broodwar with the listener to notify when game events occur.
     *
     * <p>
     * A {@link #connect() connection} to the Broodwar game will still need to be established before
     * the listener will be notified of the game events.
     *
     * @param listener
     *            listener to notify of game events
     */
    public Broodwar(final BroodwarListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }
        this.listener = listener;
    }

    /**
     * Connects to the actual Broodwar game.
     *
     * <p>
     * This method will block until the actual Broodwar game has been terminated.
     *
     * <p>
     * Although this method can be invoked any time, the connection will only be successfully
     * established when the game is in the Main Menu, Game Lobby, Mission Briefing, and Battle.net.
     */
    public void connect() {
        nativeConnect(this);
    }

    /**
     * Enables the user to interact with Broodwar game through the GUI, just as a player normally
     * would when playing the game.
     *
     * @throws IllegalStateException
     *             thrown if invoked at time other than the {@link BroodwarListener#matchStart()
     *             start} of the game
     */
    public void enableUserInput() throws IllegalStateException {
        if (getFrame() == 0) {
            nativeEnableUserInput();
        } else {
            throw new IllegalStateException("match has already begun");
        }
    }

    /**
     * Enables the agent to access all game information, not just the visible units.
     *
     * @throws IllegalStateException
     *             thrown if invoked at time other than the {@link BroodwarListener#matchStart()
     *             start} of the game
     */
    public void enablePerfectInformation() throws IllegalStateException {
        if (getFrame() == 0) {
            nativeEnablePerfectInformation();
        } else {
            throw new IllegalStateException("match has already begun");
        }
    }

    /**
     * @return the number of logical frames since the match started
     */
    public native int getFrame();

    /**
     * @return the remaining number of frames before a unit command sent in the current frame can be
     *         processed
     */
    public native int getRemainingLatencyFrames();

    /**
     * Sets that number of milliseconds to wait before executing the next frame. A Negative value
     * will reset the game speed.
     *
     * <p>
     * The default values are:
     * <ul>
     * <li>Fastest: 42ms/frame</li>
     * <li>Faster: 48ms/frame</li>
     * <li>Fast: 56ms/frame</li>
     * <li>Normal: 67ms/frame</li>
     * <li>Slow: 83ms/frame</li>
     * <li>Slower: 111ms/frame</li>
     * <li>Slowest: 167ms/frame</li>
     * </ul>
     *
     * <p>
     * Specifying a value of 0 will not guarantee that logical frames are executed as fast as
     * possible. If that is the intention, use this in combination with {@link #setFrameSkip(int)}.
     *
     * <p>
     * Changing this value will cause the execution of Use Map Settings scenario triggers to glitch.
     * This will only happen in campaign maps and custom scenarios (non-melee).
     *
     * @param frameRate
     *            the number of milliseconds to wait before executing the next frame
     */
    public native void setFrameDelay(final int frameRate);

    /**
     * Sets the number of graphical frames for every logical frame. A value of 0 or less will
     * default to one.
     *
     * <p>
     * This allows the game to run more logical frames per graphical frame, increasing the speed at
     * which the game runs.
     *
     * @param frameSkip
     *            the number of graphical frames per logical frame
     */
    public native void setFrameSkip(final int frameSkip);

    /**
     * Sets the optimization level used for processing commands. The optimization level can reduce
     * APM and the replay size by grouping units performing the same action.
     *
     * <ul>
     * <li>0 - No optimization (default)
     * <li>1 - Optimize attack unit, morph (units only), hold position, stop, follow, gather, return
     * cargo, repair, burrow, unburrow, cloak, decloak, siege, unsiege, right click unit, halt
     * construction, cancel train (Carrier/Reaver only), cancel train slot (Carrier/Reaver only),
     * cancel morph (units only), use tech, and use tech unit. Additionally perform the following
     * transformations:
     * <ul>
     * <li>Attack unit becomes right click unit if the target is an enemy</li>
     * <li>Move becomes right click position</li>
     * <li>Gather becomes right click unit if the target contains resources</li>
     * <li>Set rally position becomes right click position for buildings</li>
     * <li>Set rally unit becomes right click unit for buildings</li>
     * <li>Use tech unit for a Zerg Queen using infestation becomes right click unit when the target
     * is a Command Center</li>
     * </ul>
     * <li>2 - Optimize all of the above, as well as attack unit (towers), train (buildings), morph
     * (buildings), set rally unit, lift, unload all (bunkers only), cancel construction, cancel
     * addon, cancel train slot, cancel morph, cancel research, and cancel upgrade. This level will
     * flag BWAPI's commands as hacks in a replay analyzer.</li>
     * <li>3 - Optimize all of the above, as well as attack move, set rally position, move, patrol,
     * unload all position, right click position, and use tech position. These optimizations may
     * yield a different movement behavior than without.</li>
     * <li>4 - Optimize all of the above, but trim positions to be a multiple of 32 so that it may
     * group positions that are near each other. This creates less accurate move positions. In
     * addition, group Templars when they are ordered to merge with another Templar (includes both
     * High and Dark).</li>
     *
     * @param level
     *            the optimization level to process all future commands
     */
    public native void setCommandOptimizationLevel(final int level);

    /**
     * @return true if the current match is a replay; false otherwise
     */
    public native boolean isReplay();

    /**
     * @return the maximum number of frames in the replay
     */
    public native int getReplayFrameTotal();

    /**
     * @return the last error that was set
     */
    public ErrorCode getLastError() {
        final int index = getLastErrorCode();
        return ErrorCode.values()[index];
    }

    private native int getLastErrorCode();

    /**
     * Leaves the current match and goes to the after-game stats screen.
     */
    public native void leaveGame();

    /**
     * Sends text to other players. In single player games and replays, this will just print the
     * text on the screen. If the game is a single player match and not a replay, then this function
     * can be used to execute cheat codes, i.e. {@code sendText("show me the money")}.
     *
     * @param message
     *            the message to send
     */
    public native void sendText(final String message);

    /**
     * @return the current agent or null during replays
     */
    public Player getAgent() {
        return self;
    }

    /**
     * @return the neutral Player
     */
    public Player getNeutralPlayer() {
        return neutralPlayer;
    }

    /**
     * @return the players in the match that have not left or been defeated
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players.values());
    }

    /**
     * @return the agent's allies in the match that have not left or been defeated
     */
    public List<Player> getAllies() {
        return new ArrayList<>(allies);
    }

    /**
     * @return the agent's enemies in the match that have not left or been defeated
     */
    public List<Player> getEnemies() {
        return new ArrayList<>(enemies);
    }

    Player getPlayer(final int playerId) {
        return players.get(playerId);
    }

    /**
     * @return all accessible units
     */
    public List<Unit> getAllUnits() {
        return new ArrayList<>(units.values());
    }

    /**
     * @return all accessible units owned by the agent
     */
    public List<Unit> getUnits() {
        return new ArrayList<>(playerUnits);
    }

    /**
     * @return all accessible units owned by allies
     */
    public List<Unit> getAlliedUnits() {
        return new ArrayList<>(alliedUnits);
    }

    /**
     * @return all accessible units owned by enemies
     */
    public List<Unit> getEnemyUnits() {
        return new ArrayList<>(enemyUnits);
    }

    /**
     * @return all accessible units owned by the neutral player
     */
    public List<Unit> getNeutralUnits() {
        return new ArrayList<>(neutralUnits);
    }

    /**
     * Convenience method for retrieving all units owned by a specific player.
     *
     * @param player
     *            the Player whose Units to return
     *
     * @return all accessible units owned by the player
     */
    public List<Unit> getUnits(final Player player) {
        final List<Unit> playerUnits = new ArrayList<>();
        for (final Unit unit : units.values()) {
            if (unit.getPlayer() == player) {
                playerUnits.add(unit);
            }
        }
        return playerUnits;
    }

    Unit getUnit(final int unitId) {
        return units.get(unitId);
    }

    /**
     * @return the map that the current match is using
     */
    public GameMap getMap() {
        return map;
    }

    /**
     * Indicates if the specified build position is visible.
     *
     * @param position
     *            the position to check
     *
     * @return true if the build tile is visible; false otherwise
     */
    public boolean isVisible(final Position position) {
        return isVisible(position.getX(BUILD), position.getY(BUILD));
    }

    private native boolean isVisible(final int tileX, final int tileY);

    /**
     * Indicates if the specified location has been explored (i.e. was visible at some point during
     * the match).
     *
     * @param position
     *            the position to check
     *
     * @return true if the position has been explored; false otherwise
     */
    public boolean isExplored(final Position position) {
        return isExplored(position.getX(BUILD), position.getY(BUILD));
    }

    private native boolean isExplored(final int tileX, final int tileY);

    /**
     * Indicates if the specified build tile is buildable. Note that this just uses the static map
     * data.
     *
     * @param position
     *            the position to check
     *
     * @param includeBuildings
     *            true if the check if consider visible builds as being prohibitive; false otherwise
     *
     * @return true if the position is buildable; false otherwise
     */
    public boolean isBuildable(final Position position, final boolean includeBuildings) {
        // TODO: Consider units in this check.
        return isBuildable(position.getX(BUILD), position.getY(BUILD), includeBuildings);
    }

    private native boolean isBuildable(final int tileX, final int tileY,
            final boolean includeBuildings);

    /**
     * Indicates if a UnitType can be built at the position.
     *
     * @param position
     *            the top-left tile to build on
     *
     * @param unitType
     *            the UnitType that will be build
     *
     * @param checkExplored
     *            true if the check will include verifying that the area has been explored first;
     *            false otherwise
     *
     * @return true if the UnitType can be built at the position; false otherwise
     */
    public boolean canBuildHere(final Position position, final UnitType unitType,
            final boolean checkExplored) {
        return canBuildHere(position.getX(BUILD), position.getY(BUILD), unitType.getId(),
                checkExplored);
    }

    private native boolean canBuildHere(final int tileX, final int tileY, final int unitTypeId,
            final boolean checkExplored);

    /**
     * Indicates if a UnitType can be built at the position.
     *
     * @param unit
     *            the unit that will do the building
     *
     * @param position
     *            the top-left tile to build on
     *
     * @param unitType
     *            the UnitType that will be built
     *
     * @param checkExplored
     *            true if the check will include verifying that the area has been explored first;
     *            false otherwise
     *
     * @return true if the UnitType can be built at the position and by the unit; false otherwise
     */
    public boolean canBuildHere(final Unit unit, final Position position, final UnitType unitType,
            final boolean checkExplored) {
        return canBuildHere(unit.getId(), position.getX(BUILD), position.getY(BUILD),
                unitType.getId(), checkExplored);
    }

    private native boolean canBuildHere(final int unitId, final int tileX, final int tileY,
            final int unitTypeId, boolean checkExplored);

    /**
     * Indicates if the position has creep on it.
     *
     * @param position
     *            the build position to check for creep
     *
     * @return true if the build position has creep on it; false otherwise
     */
    public boolean hasCreep(final Position position) {
        return hasCreep(position.getX(BUILD), position.getY(BUILD));
    }

    private native boolean hasCreep(final int tileX, final int tileY);

    /**
     * Indicates if the position is powered by an owned Protoss Pylon.
     *
     * @param position
     *            the build position to check for power
     *
     * @return true if the position has power; false otherwise
     */
    public boolean hasPower(final Position position) {
        return hasPower(position, UnitType.None);
    }

    /**
     * Indicates if the unitType will be powered by an owned Protoss Pylon if placed at the given
     * build position.
     *
     * @param position
     *            the build position to check for power
     *
     * @param unitType
     *            the UnitType to check
     *
     * @return true if the UnitType will have power at the position; false otherwise
     */
    public boolean hasPower(final Position position, final UnitType unitType) {
        return hasPower(position.getX(BUILD), position.getY(BUILD), unitType.getId());
    }

    private native boolean hasPower(final int tileX, final int tileY, final int unitTypeId);

    /**
     * Indicates if the pixel position is powered by an owned Protoss Pylon.
     *
     * @param position
     *            the pixel position to check for power
     *
     * @return true if the position has power; false otherwise
     */
    public boolean hasPowerPrecise(final Position position) {
        return hasPowerPrecise(position.getX(PIXEL), position.getY(PIXEL), UnitType.None.getId());
    }

    private native boolean hasPowerPrecise(final int x, final int y, final int unitTypeId);

    /**
     * Indicates if there is a path between two positions.
     *
     * <p>
     * This only checks if the start position is connected to the end position. It does not check if
     * all units can actually travel from the start to the end.
     *
     * @param start
     *            the starting position
     *
     * @param end
     *            the ending position
     *
     * @return true if there is a path between the two positions; false otherwise
     */
    public boolean hasPath(final Position start, final Position end) {
        return hasPath(start.getX(PIXEL), start.getY(PIXEL), end.getX(PIXEL), end.getY(PIXEL));
    }

    private native boolean hasPath(final int fromX, final int fromY, final int toX, final int toY);

    /**
     * Indicates if there is a path between the two units.
     *
     * @param unit1
     *            the initial unit
     *
     * @param unit2
     *            the target unit
     *
     * @return true if the two units have a path between them; false otherwise
     */
    public boolean hasPath(final Unit unit1, final Unit unit2) {
        return hasPath(unit1.getId(), unit2.getId());
    }

    private native boolean hasPath(final int unitID, final int targetID);

    /**
     * Indicates if there is path between a Unit and a Position.
     *
     * @param unit
     *            the initial unit
     *
     * @param position
     *            the target position
     *
     * @return true if the Unit has a path to the target position; false otherwise
     */
    public boolean hasPath(final Unit unit, final Position position) {
        return hasPath(unit.getId(), position.getX(PIXEL), position.getY(PIXEL));
    }

    private native boolean hasPath(final int unitID, final int toX, final int toY);

    /**
     * Indicates if all the requirements have been met to allow researching of a technology.
     *
     * <p>
     * The requirements include resources, technology tree, availability, and required Units.
     *
     * @param techType
     *            the techType to check
     *
     * @return true if the TechType can be researched; false otherwise
     */
    public boolean canResearch(final Tech techType) {
        return canResearch(techType.getId());
    }

    private native boolean canResearch(final int techTypeId);

    /**
     * Indicates if all the requirements have been met to allow the researching of a technology.
     *
     * <p>
     * The requirements include resources, technology tree, availability, and required Units.
     *
     * @param unit
     *            the Unit that will be doing the researching
     *
     * @param techType
     *            the TechType to check
     *
     * @return true if the TechType can be researched; false otherwise
     */
    public boolean canResearch(final Unit unit, final Tech techType) {
        return canResearch(unit.getId(), techType.getId());
    }

    private native boolean canResearch(final int unitId, final int techTypeId);

    /**
     * Indicates if all the requirements have been met to allow the making of a UnitType.
     *
     * <p>
     * The requirements include resources, technology tree, availability, required Units, and
     * supply.
     *
     * @param unitType
     *            the UnitType to check
     *
     * @return true if the UnitType can be made; false otherwise
     */
    public boolean canMake(final UnitType unitType) {
        return canMake(unitType.getId());
    }

    private native boolean canMake(final int unitTypeId);

    /**
     * Indicates if all the requirements have been met to allow the making of a UnitType.
     *
     * <p>
     * The requirements include resources, technology tree, availability, required Units, and
     * supply.
     *
     * @param unit
     *            the Unit that will be doing the making
     *
     * @param unitType
     *            the UnitType to check
     *
     * @return true if the UnitType can be made, false otherwise
     */
    public boolean canMake(final Unit unit, final UnitType unitType) {
        return canMake(unitType.getId(), unitType.getId());
    }

    private native boolean canMake(final int unitId, final int unitTypeId);

    /**
     * Indicates if all the requirements have been met to allow the upgrading of an Update.
     *
     * <p>
     * The requirements include resources, technology tree, and availability.
     *
     * @param unitType
     *            the UpgradeType to check
     *
     * @return true if the UpgradeType can be acquired; false otherwise
     */
    public boolean canUpgrade(final Upgrade unitType) {
        return canUpgrade(unitType.getId());
    }

    private native boolean canUpgrade(int upgradeTypeId);

    /**
     * Indicates if all the requirements have been met to allow a specific Unit to perform an
     * Upgrade.
     *
     * <p>
     * The requirements include resources, technology tree, and availability.
     *
     * @param unit
     *            the Unit that will be used to acquire the upgrade
     *
     * @param upgradeType
     *            the UpgradeType to check
     *
     * @return true if the UpgradeType can be acquired through Unit; false otherwise
     */
    public boolean canUpgrade(final Unit unit, final Upgrade upgradeType) {
        return canUpgrade(unit.getId(), upgradeType.getId());
    }

    private native boolean canUpgrade(final int unitId, final int upgradeTypeId);

    // *********************************************************************************************
    // Drawing Commands
    //
    // TODO: Negative width and height values cannot be used.
    // TODO: Make available other colors.
    //
    // https://code.google.com/p/bwapi/wiki/Color
    // https://github.com/bwapi/bwapi/blob/master/bwapi/include/BWAPI/Color.h
    // https://github.com/bwapi/bwapi/blob/master/bwapi/BWAPILIB/Source/Color.cpp
    // *********************************************************************************************

    /**
     * Draws text on the map for a single frame.
     *
     * @param position
     *            the starting position of the text to be drawn, in pixels
     *
     * @param text
     *            the text to draw
     */
    public void drawTextMap(final Position position, final String text) {
        drawTextMap(position.getX(PIXEL), position.getY(PIXEL), text);
    }

    /**
     * Draws text on the map for a single frame.
     *
     * @param position
     *            the starting position of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     *
     * @param color
     *            the color of the text to be drawn
     */
    public void drawTextMap(final Position position, final String text, final BWColor color) {
        drawTextMap(position.getX(PIXEL), position.getY(PIXEL), color.getControlCharacters() + text);
    }

    /**
     * Draws text on the map for a single frame.
     *
     * @param x
     *            the starting x-axis coordinate of the text to be drawn, in pixels
     *
     * @param y
     *            the starting y-axis coordinate of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     */
    public native void drawTextMap(final int x, final int y, final String text);

    /**
     * Draws text on the map for a single frame.
     *
     * @param x
     *            the starting x-axis coordinate of the text to be drawn, in pixels
     *
     * @param y
     *            the starting y-axis coordinate of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     *
     * @param color
     *            the color of the text to be drawn
     */
    public void drawTextMap(final int x, final int y, final String text, final BWColor color) {
        drawTextMap(x, y, color.getControlCharacters() + text);
    }

    /**
     * Draws text on the screen for a single frame.
     *
     * @param position
     *            the starting position of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     */
    public void drawTextScreen(final Position position, final String text) {
        drawTextScreen(position.getX(PIXEL), position.getY(PIXEL), text);
    }

    /**
     * Draws text on the screen for a single frame.
     *
     * @param position
     *            the starting position of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     *
     * @param color
     *            the color of the text to be drawn
     */
    public void drawTextScreen(final Position position, final String text, final BWColor color) {
        drawTextScreen(position.getX(PIXEL), position.getY(PIXEL), color.getControlCharacters()
                + text);
    }

    /**
     * Draws text on the screen for a single frame.
     *
     * @param x
     *            the starting x-axis coordinate of the text to be drawn, in pixels
     *
     * @param y
     *            the starting y-axis coordinate of the text to be drawn, in pixels
     *
     * @param text
     *            the text to draw
     */
    public native void drawTextScreen(final int x, final int y, final String text);

    /**
     * Draws text on the map for a single frame.
     *
     * @param x
     *            that starting x-axis coordinate of the text to be drawn, in pixels
     *
     * @param y
     *            the starting y-axis coordinate of the text to be drawn, in pixels
     *
     * @param text
     *            the text to be drawn
     *
     * @param color
     *            the color of the text to be drawn
     */
    public void drawTextScreen(final int x, final int y, final String text, final BWColor color) {
        drawTextScreen(x, y, color.getControlCharacters() + text);
    }

    /**
     * Draws a line on the map for a single frame.
     *
     * @param p1
     *            the starting position of the line to be drawn, in pixels
     *
     * @param p2
     *            the ending position of the line to be drawn, in pixels
     *
     * @param color
     *            the color of the line to be drawn
     */
    public void drawLineMap(final Position p1, final Position p2, final BWColor color) {
        drawLineMap(p1.getX(PIXEL), p1.getY(PIXEL), p2.getX(PIXEL), p2.getY(PIXEL), color.getId());
    }

    /**
     * Draws a line on the map for a single frame.
     *
     * @param x1
     *            the starting x-axis coordinate of the line to be drawn, in pixels
     *
     * @param y1
     *            the starting y-axis coordinate of the line to be drawn, in pixels
     *
     * @param x2
     *            the ending x-axis coordinate of the line to be drawn, in pixels
     *
     * @param y2
     *            the ending y-axis coordinate of the line to be drawn, in pixels
     *
     * @param color
     *            the color of the line to be drawn
     */
    public void drawLineMap(final int x1, final int y1, final int x2, final int y2,
            final BWColor color) {
        drawLineMap(x1, y1, x2, y2, color.getId());
    }

    private native void drawLineMap(final int x1, final int y1, final int x2, final int y2,
            final int c);

    /**
     * Draws a line on the screen for a single frame.
     *
     * @param p1
     *            the starting position of the line to be drawn, in pixels
     *
     * @param p2
     *            the ending position of the line to be drawn, in pixels
     *
     * @param color
     *            the color of the line to be drawn
     */
    public void drawLineScreen(final Position p1, final Position p2, final BWColor color) {
        drawLineScreen(p1.getX(PIXEL), p1.getY(PIXEL), p2.getX(PIXEL), p2.getY(PIXEL),
                color.getId());
    }

    /**
     * Draws a line on the screen for a single frame.
     *
     * @param x1
     *            the starting x-axis coordinate of the line to be drawn, in pixels
     *
     * @param y1
     *            the starting y-axis coordinate of the line to be drawn, in pixels
     *
     * @param x2
     *            the ending x-axis coordinate of the line to be drawn, in pixels
     *
     * @param y2
     *            the ending y-axis coordinate of the line to be drawn, in pixels
     *
     * @param color
     *            the color of the line to be drawn
     */
    public void drawLineScreen(final int x1, final int y1, final int x2, final int y2,
            final BWColor color) {
        drawLineScreen(x1, y1, x2, y2, color.getId());
    }

    private native void drawLineScreen(final int x1, final int y1, final int x2, final int y2,
            final int color);

    /**
     * Draws a rectangle on the map for a single frame.
     *
     * @param position
     *            the top-left position of the rectangle to be drawn, in pixels
     *
     * @param width
     *            the width of the rectangle to be drawn, in pixels
     *
     * @param height
     *            the height of the rectangle to be drawn, in pixels
     *
     * @param color
     *            the color rectangle to be drawn
     *
     * @param fill
     *            true if the rectangle should be filled; false otherwise
     */
    public void drawRectangleMap(final Position position, final int width, final int height,
            final BWColor color, final boolean fill) {
        drawRectangleMap(position.getX(PIXEL), position.getY(PIXEL), width, height, color.getId(),
                fill);
    }

    /**
     * Draws a rectangle on the map for a single frame.
     *
     * @param x
     *            the top-left x-axis coordinate of the rectangle to be drawn, in pixels
     *
     * @param y
     *            the top-left y-axis coordinate of the rectangle to be drawn, in pixels
     *
     * @param width
     *            the width of the rectangle to be drawn, in pixels
     *
     * @param height
     *            the height of the rectangle to be drawn, in pixels
     *
     * @param color
     *            the color of the rectangle to be drawn
     *
     * @param fill
     *            true if the rectangle should be filled; false otherwise
     */
    public void drawRectangleMap(final int x, final int y, final int width, final int height,
            final BWColor color, final boolean fill) {
        drawRectangleMap(x, y, width, height, color.getId(), fill);
    }

    private native void drawRectangleMap(final int x, final int y, final int width,
            final int height, final int color, final boolean fill);

    /**
     * Draws a rectangle on the screen for a single frame.
     *
     * @param position
     *            the top-left position of the rectangle to be drawn, in pixels
     *
     * @param width
     *            the width of the rectangle to be drawn, in pixels
     *
     * @param height
     *            the height of the rectangle to be drawn, in pixels
     *
     * @param color
     *            the color of the rectangle to be drawn
     *
     * @param fill
     *            true if the rectangle should be filled; false otherwise
     */
    public void drawRectangleScreen(final Position position, final int width, final int height,
            final BWColor color, final boolean fill) {
        drawRectangleScreen(position.getX(PIXEL), position.getY(PIXEL), width, height,
                color.getId(), fill);
    }

    /**
     * Draws a rectangle on the screen for a single frame.
     *
     * @param x
     *            the top-left x-axis coordinate of the rectangle to be drawn, in pixels
     *
     * @param y
     *            the top-left y-axis coordinate of the rectangle to be drawn, in pixels
     *
     * @param width
     *            the width of the rectangle to be drawn, in pixels
     *
     * @param height
     *            the height of the rectangle to be drawn, in pixels
     *
     * @param color
     *            the color of the rectangle to be drawn
     *
     * @param fill
     *            true if the rectangle should be filled; false otherwise
     */
    public void drawRectangleScreen(final int x, final int y, final int width, final int height,
            final BWColor color, final boolean fill) {
        drawRectangleScreen(x, y, width, height, color.getId(), fill);
    }

    private native void drawRectangleScreen(final int x, final int y, final int width,
            final int height, final int color, final boolean fill);

    /**
     * Draws an ellipse on the map for a single frame.
     *
     * @param position
     *            the center position of the ellipse to be drawn, in pixels
     *
     * @param xRadius
     *            the x-radius of the ellipse, in pixels
     *
     * @param yRadius
     *            the y-radius of the ellipse, in pixels
     *
     * @param color
     *            the color of the ellipse to be drawn
     *
     * @param fill
     *            true if the ellipse should be filled; false otherwise
     */
    public void drawEllipseMap(final Position position, final int xRadius, final int yRadius,
            final BWColor color, final boolean fill) {
        drawEllipseMap(position.getX(PIXEL), position.getY(PIXEL), xRadius, yRadius, color.getId(),
                fill);
    }

    /**
     * Draws an ellipse on the map for a single frame.
     *
     * @param x
     *            the center x-axis coordinate of the ellipse to be drawn, in pixels
     *
     * @param y
     *            the center y-axis coordinate of the ellipse to be drawn, in pixels
     *
     * @param xRadius
     *            the x-radius of the ellipse, in pixels
     *
     * @param yRadius
     *            the y-radius of the ellipse, in pixels
     *
     * @param color
     *            the color of the ellipse to be drawn
     *
     * @param fill
     *            true if the ellipse should be filled; false otherwise
     */
    public void drawEllipseMap(final int x, final int y, final int xRadius, final int yRadius,
            final BWColor color, final boolean fill) {
        drawEllipseMap(x, y, xRadius, yRadius, color.getId(), fill);
    }

    private native void drawEllipseMap(final int x, final int y, final int xRadius,
            final int yRadius, final int color, final boolean fill);

    /**
     * Draws an ellipse on the screen for a single frame.
     *
     * @param position
     *            the center position of the ellipse to be drawn, in pixels
     * @param xRadius
     *            the x-radius of the ellipse, in pixels
     *
     * @param yRadius
     *            the y-radius of the ellipse to be drawn, in pixels
     *
     * @param color
     *            the color of the ellipse to be drawn
     *
     * @param fill
     *            true if the ellipse should be filled; false otherwise
     */
    public void drawEllipseScreen(final Position position, final int xRadius, final int yRadius,
            final BWColor color, final boolean fill) {
        drawEllipseScreen(position.getX(PIXEL), position.getY(PIXEL), xRadius, yRadius,
                color.getId(), fill);
    }

    /**
     * Draws an ellipse on the screen for a single frame.
     *
     * @param x
     *            the center x-axis coordinate of the ellipse to be drawn, in pixels
     *
     * @param y
     *            the center y-axis coordinate of the ellipse to be drawn, in pixels
     *
     * @param xRadius
     *            the x-radius of the ellipse, in pixels
     *
     * @param yRadius
     *            the y-radius of the ellipse, in pixels
     *
     * @param color
     *            the color of the ellipse to be drawn
     *
     * @param fill
     *            true if the ellipse should be filled; false otherwise
     */
    public void drawEllipseScreen(final int x, final int y, final int xRadius, final int yRadius,
            final BWColor color, final boolean fill) {
        drawEllipseScreen(x, y, xRadius, yRadius, color.getId(), fill);
    }

    private native void drawEllipseScreen(final int x, final int y, final int xRadius,
            final int yRadius, final int color, final boolean fill);

    /**
     * Draws a circle on the map for a single frame.
     *
     * @param position
     *            the center position of the circle to be drawn, in pixels
     *
     * @param radius
     *            the radius of the circle to be drawn, in pixels
     *
     * @param color
     *            the color of the circle to be drawn
     *
     * @param fill
     *            true if the circle should be filled; false otherwise
     */
    public void drawCircleMap(final Position position, final int radius, final BWColor color,
            final boolean fill) {
        drawEllipseMap(position.getX(PIXEL), position.getY(PIXEL), radius, radius, color.getId(),
                fill);
    }

    /**
     * Draws a circle on the map for a single frame.
     *
     * @param x
     *            the center x-axis coordinate of the circle to be drawn, in pixels
     *
     * @param y
     *            the center y-axis coordinate of the circle to be drawn, in pixels
     *
     * @param radius
     *            the radius of the circle to be drawn, in pixels
     *
     * @param color
     *            the color of the circle to be drawn
     *
     * @param fill
     *            true if the circle should be filled; false otherwise
     */
    public void drawCircleMap(final int x, final int y, final int radius, final BWColor color,
            final boolean fill) {
        drawEllipseMap(x, y, radius, radius, color.getId(), fill);
    }

    /**
     * Draws a circle on the screen for a single frame.
     *
     * @param position
     *            the center position of the circle to be drawn, in pixels
     *
     * @param radius
     *            the radius of the circle to be drawn, in pixels
     *
     * @param color
     *            the color of the circle to be drawn
     *
     * @param fill
     *            true if the circle should be filled; false otherwise
     */
    public void drawCircleScreen(final Position position, final int radius, final BWColor color,
            final boolean fill) {
        drawEllipseScreen(position.getX(PIXEL), position.getY(PIXEL), radius, radius,
                color.getId(), fill);
    }

    /**
     * Draws a circle on the screen for a single frame.
     *
     * @param x
     *            the center x-axis coordinate of the circle to be drawn, in pixels
     *
     * @param y
     *            the center y-axis coordinate of the circle to be drawn, in pixels
     *
     * @param radius
     *            the radius of the circle to be drawn, in pixels
     *
     * @param color
     *            the color of the circle to be drawn
     *
     * @param fill
     *            true if the circle should be filled; false otherwise
     */
    public void drawCircleScreen(final int x, final int y, final int radius, final BWColor color,
            final boolean fill) {
        drawEllipseScreen(x, y, radius, radius, color.getId(), fill);
    }

    // *********************************************************************************************
    // Static Methods
    // *********************************************************************************************

    private static void loadNativeLibrary() {
        final File dll = new File("src/main/resources/", "client-bridge-x86.dll");
        try {
            System.load(dll.getAbsolutePath());
        } catch (final UnsatisfiedLinkError ex) {
            if (!dll.exists()) {
                System.err.println("Native code library not found: " + dll.getAbsolutePath());
            } else {
                System.err.println("Native code library failed to load: " + ex.toString());
            }
        }
    }

    private static Charset getKoreanCharset() {
        try {
            return Charset.forName("Cp949");
        } catch (final UnsupportedCharsetException ex) {
            System.err.println("Korean character set not available.");
            return StandardCharsets.ISO_8859_1;
        }
    }

    // *********************************************************************************************
    // Callback Commands
    // *********************************************************************************************

    /**
     * Notifies the client and event listener that a connection has been formed to the bridge.
     *
     * <p>
     * C++ callback function.
     */
    void connected() {
        loadTypeData();
        listener.connected();
    }

    private void loadTypeData() {
        // race types
        final int[] raceTypeData = getRaceTypes();
        for (int index = 0; index < raceTypeData.length; index += Race.NUM_ATTRIBUTES) {
            final int id = raceTypeData[index];
            Race.getRaceType(id).initialize(raceTypeData, index, getRaceTypeName(id));
        }

        // unit types
        final int[] unitTypeData = getUnitTypes();
        for (int index = 0; index < unitTypeData.length; index += UnitType.NUM_ATTRIBUTES) {
            final int id = unitTypeData[index];
            UnitType.getUnitType(id).initialize(unitTypeData, index, getUnitTypeName(id),
                    getRequiredUnits(id));
        }

        // tech types
        final int[] techTypeData = getTechTypes();
        for (int index = 0; index < techTypeData.length; index += Tech.NUM_ATTRIBUTES) {
            final int id = techTypeData[index];
            Tech.getTechType(id).initialize(techTypeData, index, getTechTypeName(id));
        }

        // upgrade types
        final int[] upgradeTypeData = getUpgradeTypes();
        for (int index = 0; index < upgradeTypeData.length; index += Upgrade.NUM_ATTRIBUTES) {
            final int id = upgradeTypeData[index];
            Upgrade.getUpgradeType(id).initialize(upgradeTypeData, index, getUpgradeTypeName(id));
        }

        // weapon types
        final int[] weaponTypeData = getWeaponTypes();
        for (int index = 0; index < weaponTypeData.length; index += Weapon.NUM_ATTRIBUTES) {
            final int id = weaponTypeData[index];
            Weapon.getWeaponType(id).initialize(weaponTypeData, index, getWeaponTypeName(id));
        }

        // unit size types
        final int[] unitSizeTypeData = getUnitSizeTypes();
        for (int index = 0; index < unitSizeTypeData.length; index += UnitSize.NUM_ATTRIBUTES) {
            final int id = unitSizeTypeData[index];
            UnitSize.getUnitSizeType(id).initialize(unitSizeTypeData, index,
                    getUnitSizeTypeName(id));
        }

        // bullet types
        final int[] bulletTypeData = getBulletTypes();
        for (int index = 0; index < bulletTypeData.length; index += Bullet.NUM_ATTRIBUTES) {
            final int id = bulletTypeData[index];
            Bullet.getBulletType(id).initialize(bulletTypeData, index, getBulletTypeName(id));
        }

        // damage types
        final int[] damageTypeData = getDamageTypes();
        for (int index = 0; index < damageTypeData.length; index += Damage.NUM_ATTRIBUTES) {
            final int id = damageTypeData[index];
            Damage.getDamageType(id).initialize(damageTypeData, index, getDamageTypeName(id));
        }

        // explosion types
        final int[] explosionTypeData = getExplosionTypes();
        for (int index = 0; index < explosionTypeData.length; index += Explosion.NUM_ATTRIBUTES) {
            final int id = explosionTypeData[index];
            Explosion.getExplosionType(id).initialize(explosionTypeData, index,
                    getExplosionTypeName(id));
        }

        // unitCommand types
        final int[] unitCommandTypeData = getUnitCommandTypes();
        for (int index = 0; index < unitCommandTypeData.length; index += Command.NUM_ATTRIBUTES) {
            final int id = unitCommandTypeData[index];
            Command.getCommandType(id).initialize(unitCommandTypeData, index,
                    getUnitCommandTypeName(id));
        }

        // order types
        final int[] orderTypeData = getOrderTypes();
        for (int index = 0; index < orderTypeData.length; index += Order.NUM_ATTRIBUTES) {
            final int id = orderTypeData[index];
            Order.getOrderType(id).initialize(orderTypeData, index, getOrderTypeName(id));
        }
    }

    /**
     * Notifies the client that a game has started. This method is always called before
     * {@code BWAPIEventListener.matchStart()}, and is meant as a way of notifying the client to
     * initialize state.
     *
     * <p>
     * The listener is not notified of this invocation.
     *
     * <p>
     * C++ callback function.
     */
    void gameStarted() {
        self = null;
        allies.clear();
        enemies.clear();
        players.clear();

        final int[] playerData = getPlayersData();
        for (int index = 0; index < playerData.length; index += Player.NUM_ATTRIBUTES) {
            final String name = new String(getPlayerName(playerData[index]), CHARACTER_SET);
            final Player player = new Player(playerData, index, name);

            players.put(player.getId(), player);

            if (player.isSelf()) {
                self = player;
            } else if (player.isAlly()) {
                allies.add(player);
            } else if (player.isEnemy()) {
                enemies.add(player);
            } else if (player.isNeutral()) {
                neutralPlayer = player;
            }
        }

        // get unit data
        units.clear();
        playerUnits.clear();
        alliedUnits.clear();
        enemyUnits.clear();
        neutralUnits.clear();
        final int[] unitData = getAllUnitsData();

        for (int index = 0; index < unitData.length; index += Unit.NUM_ATTRIBUTES) {
            final int id = unitData[index];
            final Unit unit = new Unit(id, this);
            unit.update(unitData, index);

            units.put(id, unit);
            if ((self != null) && (unit.getPlayer() == self)) {
                playerUnits.add(unit);

            } else if (allies.contains(unit.getPlayer())) {
                alliedUnits.add(unit);

            } else if (enemies.contains(unit.getPlayer())) {
                enemyUnits.add(unit);

            } else {
                neutralUnits.add(unit);
            }
        }
        loadMapData();
    }

    private void loadMapData() {
        final String mapName = new String(getMapName(), CHARACTER_SET);
        final String fileName = getMapFileName();
        final int x = getMapWidth();
        final int y = getMapHeight();
        final int[] z = getMapDepth();
        final int[] buildable = getBuildableData();
        final int[] walkable = getWalkableData();

        map = new GameMap(mapName, fileName, x, y, z, buildable, walkable);
        loadMapDetails();
    }

    private void loadMapDetails() {
        final String mapDataCacheFileName = map.getName() + ".jbwta";
        final File mapDataCacheFile = new File("bwta/", mapDataCacheFileName);

        if (mapDataCacheFile.exists()) {
            try {
                final FileReader fr = new FileReader(mapDataCacheFile);
                final BufferedReader br = new BufferedReader(fr);
                final int[] bases = readMapData(br);

                int[] data = null;
                final HashMap<Integer, int[]> polygons = new HashMap<>();
                while ((data = readMapData(br)) != null) {
                    final int id = data[0];
                    final int size = data.length;
                    polygons.put(id, Arrays.copyOfRange(data, 1, size));
                }

                br.close();

                map.setBaseLocations(bases);
                return;

            } catch (final IOException ex) {
                System.err.println("Map data could not be loaded.");
                System.err.println(ex.getMessage());
            }
        }

        analyzeTerrain();
        final int[] bases = getBaseLocations();

        try {
            if (!mapDataCacheFile.getParentFile().exists()) {
                mapDataCacheFile.getParentFile().mkdirs();
            }
            final FileWriter fw = new FileWriter(mapDataCacheFile);
            final BufferedWriter bw = new BufferedWriter(fw);

            writeMapData(bw, bases);
            bw.close();

            map.setBaseLocations(bases);

        } catch (final Exception ex) {
            System.err.println("Map data could not be cached.");
            System.err.println(ex.getMessage());
        }
    }

    private static void writeMapData(final Writer writer, final int[] data) throws IOException {
        boolean first = true;
        for (final int val : data) {
            if (first) {
                first = false;
                writer.write("" + val);
            } else {
                writer.write("," + val);
            }
        }
        writer.write("\n");
    }

    private static int[] readMapData(final BufferedReader reader) throws IOException {

        final String line = reader.readLine();
        if (line == null) {
            return null;
        }

        final String[] stringData = line.split(",");
        final int[] data = new int[stringData.length];
        if ((stringData.length > 0) && !stringData[0].equals("")) {
            for (int i = 0; i < stringData.length; i++) {
                data[i] = Integer.parseInt(stringData[i]);
            }
        }
        return data;
    }

    /**
     * Notifies the client that game data has been updated. This method is always called before
     * {@code BWAPIEventListener.matchFrame()}, and is meant as a way of notifying the client to
     * update the game state.
     *
     * <p>
     * The listener is not notified of this invocation.
     *
     * <p>
     * C++ callback function.
     */
    void gameUpdate() {
        // update game state
        if (!isReplay()) {
            self.update(getPlayerUpdate(self.getId()));
            self.updateResearch(getResearchStatus(self.getId()), getUpgradeStatus(self.getId()));
        } else {
            for (final Integer playerId : players.keySet()) {
                players.get(playerId).update(getPlayerUpdate(playerId));
                players.get(playerId).updateResearch(getResearchStatus(playerId),
                        getUpgradeStatus(playerId));
            }
        }
        // update units
        final int[] unitData = getAllUnitsData();
        final HashSet<Integer> deadUnits = new HashSet<>(units.keySet());
        playerUnits.clear();
        alliedUnits.clear();
        enemyUnits.clear();
        neutralUnits.clear();

        for (int index = 0; index < unitData.length; index += Unit.NUM_ATTRIBUTES) {
            final int id = unitData[index];

            deadUnits.remove(id);

            Unit unit = units.get(id);
            if (unit == null) {
                unit = new Unit(id, this);
                units.put(id, unit);
            }

            unit.update(unitData, index);

            if (self != null) {
                if (unit.getPlayer() == self) {
                    playerUnits.add(unit);
                } else if (allies.contains(unit.getPlayer())) {
                    alliedUnits.add(unit);
                } else if (enemies.contains(unit.getPlayer())) {
                    enemyUnits.add(unit);
                } else {
                    neutralUnits.add(unit);
                }
            } else if (allies.contains(unit.getPlayer())) {
                alliedUnits.add(unit);
            } else if (enemies.contains(unit.getPlayer())) {
                enemyUnits.add(unit);
            } else {
                neutralUnits.add(unit);
            }
        }

        // update the unit lists
        for (final Integer unitID : deadUnits) {
            units.get(unitID).setDestroyed();
            units.remove(unitID);
        }
    }

    /**
     * Notifies the event listener that the game has terminated.
     *
     * <p>
     * C++ callback function.
     */
    void gameEnded() {
        // TODO: Implement gameEnded for listener.
    }

    /**
     * Sends BWAPI callback events to the event listener.
     *
     * <p>
     * The meaning of the parameters is dependent on the event type itself. In some cases, none of
     * the parameters are used.
     *
     * <p>
     * C++ callback function.
     *
     * @param eventTypeId
     *            id of the event that occurred
     *
     * @param p1
     *            first parameter for the event
     *
     * @param p2
     *            second parameter for the event
     *
     * @param p3
     *            third parameter for the event
     */
    void eventOccurred(final int eventTypeId, final int p1, final int p2, final String p3) {

        final EventType event = EventType.getEventType(eventTypeId);
        switch (event) {
            case MATCH_START :
                listener.matchStart();
                break;

            case MATCH_END :
                listener.matchEnd(p1 == 1);
                break;

            case MATCH_FRAME :
                listener.matchFrame();
                break;

            case MENU_FRAME :
                // Not currently used.
                break;

            case SEND_TEXT :
                listener.sendText(p3);
                break;

            case RECEIVE_TEXT :
                listener.receiveText(p3);
                break;

            case PLAYER_LEFT :
                listener.playerLeft(players.get(p1));
                break;

            case NUKE_DETECT :
                if ((p1 == -1) || (p2 == -1)) {
                    listener.nukeDetect(Position.UNKNOWN);
                } else {
                    listener.nukeDetect(new Position(p1, p2, PIXEL));
                }
                break;

            case UNIT_DISCOVER :
                listener.unitDiscover(units.get(p1));
                break;

            case UNIT_EVADE :
                listener.unitEvade(units.get(p1));
                break;

            case UNIT_SHOW :
                listener.unitShow(units.get(p1));
                break;

            case UNIT_HIDE :
                listener.unitHide(units.get(p1));
                break;

            case UNIT_CREATE :
                listener.unitCreate(units.get(p1));
                break;

            case UNIT_DESTROY :
                listener.unitDestroy(units.get(p1));
                break;

            case UNIT_MORPH :
                listener.unitMorph(units.get(p1));
                break;

            case UNIT_RENEGADE :
                listener.unitRenegade(units.get(p1));
                break;

            case SAVE_GAME :
                listener.saveGame(p3);
                break;

            case UNIT_COMPLETE :
                listener.unitComplete(units.get(p1));
                break;

            case PLAYER_DROPPED :
                listener.playerDropped(players.get(p1));
                break;

            case NONE :
                // Not currently used.
                break;

            default :
                break;
        }
    }

    private enum EventType {
        MATCH_START,
        MATCH_END,
        MATCH_FRAME,
        MENU_FRAME,
        SEND_TEXT,
        RECEIVE_TEXT,
        PLAYER_LEFT,
        NUKE_DETECT,
        UNIT_DISCOVER,
        UNIT_EVADE,
        UNIT_SHOW,
        UNIT_HIDE,
        UNIT_CREATE,
        UNIT_DESTROY,
        UNIT_MORPH,
        UNIT_RENEGADE,
        SAVE_GAME,
        UNIT_COMPLETE,
        PLAYER_DROPPED,
        NONE;

        public static EventType getEventType(final int id) {
            return EventType.values()[id];
        }
    }

    /**
     * Notifies the event listener that a key was pressed.
     *
     * <p>
     * C++ callback function.
     *
     * @param keyCode
     *            key pressed by the user
     */
    void keyPressed(final int keyCode) {
        listener.keyPressed(keyCode);
    }

    /**
     * Utility function for printing to the java console from C++.
     *
     * <p>
     * C++ callback function.
     */
    void javaPrint(final String msg) {
        // TODO: Allow agent to decide how best to print the message.
        System.out.println("Bridge: " + msg);
    }

    private native void nativeConnect(final Broodwar broodwar);

    private native void nativeEnableUserInput();

    private native void nativeEnablePerfectInformation();

    // *********************************************************************************************
    // Data Commands
    // *********************************************************************************************

    private native int[] getPlayersData();

    private native int[] getAllUnitsData();

    private native int[] getPlayerUpdate(final int playerId);

    private native byte[] getPlayerName(final int playerId);

    private native int[] getResearchStatus(final int playerId);

    private native int[] getUpgradeStatus(final int playerId);

    private native int[] getRaceTypes();

    private native String getRaceTypeName(final int unitTypeId);

    private native int[] getUnitTypes();

    private native String getUnitTypeName(final int unitTypeId);

    private native int[] getRequiredUnits(final int unitTypeId);

    private native int[] getTechTypes();

    private native String getTechTypeName(final int techId);

    private native int[] getUpgradeTypes();

    private native String getUpgradeTypeName(final int upgradeId);

    private native int[] getWeaponTypes();

    private native String getWeaponTypeName(final int weaponId);

    private native int[] getUnitSizeTypes();

    private native String getUnitSizeTypeName(final int sizeId);

    private native int[] getBulletTypes();

    private native String getBulletTypeName(final int bulletId);

    private native int[] getDamageTypes();

    private native String getDamageTypeName(final int damageId);

    private native int[] getExplosionTypes();

    private native String getExplosionTypeName(final int explosionId);

    private native int[] getUnitCommandTypes();

    private native String getUnitCommandTypeName(final int unitCommandId);

    private native int[] getOrderTypes();

    private native String getOrderTypeName(final int unitCommandId);

    // *********************************************************************************************
    // Map Commands
    // *********************************************************************************************

    private native void analyzeTerrain();

    private native byte[] getMapName();

    private native String getMapFileName();

    private native int getMapWidth();

    private native int getMapHeight();

    private native int[] getMapDepth();

    private native int[] getWalkableData();

    private native int[] getBuildableData();

    private native int[] getBaseLocations();
}
