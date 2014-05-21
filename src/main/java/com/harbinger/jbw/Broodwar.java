package com.harbinger.jbw;

import com.harbinger.jbw.Position.Positions;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

/**
 * Provides access to the Broodwar game.
 *
 * <p>
 *
 */
public class Broodwar {

    static {
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

    /** The character set to use when decoding Strings. */
    private static final Charset CHARACTER_SET = getCharset();

    private static Charset getCharset() {
        try {
            return Charset.forName("Cp949");
        } catch (final UnsupportedCharsetException e) {
            System.err.println("Korean character set not available.");
            return StandardCharsets.ISO_8859_1;
        }
    }

    private final BroodwarListener listener;

    // private final boolean enableTerrainAnalysis;

    private final HashMap<Integer, Unit> units = new HashMap<>();
    private final ArrayList<Unit> playerUnits = new ArrayList<>();
    private final ArrayList<Unit> alliedUnits = new ArrayList<>();
    private final ArrayList<Unit> enemyUnits = new ArrayList<>();
    private final ArrayList<Unit> neutralUnits = new ArrayList<>();

    private final HashMap<Integer, Player> players = new HashMap<>();
    private final ArrayList<Player> allies = new ArrayList<>();
    private final ArrayList<Player> enemies = new ArrayList<>();

    private Player self;
    private Player neutralPlayer;

    private GameMap map;
    private int gameFrame = 0;

    /**
     * Instantiates a BWAPI instance, but does not connect to the bridge. To connect, the start
     * method must be invoked.
     *
     * @param listener
     *            listener for BWAPI callback events.
     */
    public Broodwar(final BroodwarListener listener) {
        this.listener = listener;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(new ArrayList<>(players.values()));
    }

    public List<Player> getAllies() {
        return Collections.unmodifiableList(allies);
    }

    public List<Player> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    public Player getMyself() {
        return self;
    }

    public Player getNeutralPlayer() {
        return neutralPlayer;
    }

    public Player getPlayer(final int playerId) {
        return players.get(playerId);
    }

    public List<Unit> getUnits() {
        return Collections.unmodifiableList(new ArrayList<>(units.values()));
    }

    public List<Unit> getAlliedUnits() {
        return Collections.unmodifiableList(alliedUnits);
    }

    public List<Unit> getEnemyUnits() {
        return Collections.unmodifiableList(enemyUnits);
    }

    public List<Unit> getMyUnits() {
        return Collections.unmodifiableList(playerUnits);
    }

    public List<Unit> getNeutralUnits() {
        return Collections.unmodifiableList(neutralUnits);
    }

    public Unit getUnit(final int unitId) {
        return units.get(unitId);
    }

    public List<Unit> getUnits(final Player player) {
        final List<Unit> playerUnits = new ArrayList<>();
        for (final Unit unit : units.values()) {
            if (unit.getPlayer() == player) {
                playerUnits.add(unit);
            }
        }
        return playerUnits;
    }

    public int getFrameCount() {
        return gameFrame;
    }

    public List<Unit> getUnitsOnTile(final int x, final int y) {
        // Most tiles will have 0 units on tile.
        final List<Unit> units = new ArrayList<>(0);
        for (final int id : getUnitIdsOnTile(x, y)) {
            units.add(getUnit(id));
        }
        return units;
    }

    private native int[] getUnitIdsOnTile(final int tx, final int ty);

    private void loadTypeData() {
        // race types
        final int[] raceTypeData = getRaceTypes();
        for (int index = 0; index < raceTypeData.length; index += RaceType.NUM_ATTRIBUTES) {
            final int id = raceTypeData[index];
            RaceType.getRaceType(id).initialize(raceTypeData, index, getRaceTypeName(id));
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
        for (int index = 0; index < techTypeData.length; index += TechType.NUM_ATTRIBUTES) {
            final int id = techTypeData[index];
            TechType.getTechType(id).initialize(techTypeData, index, getTechTypeName(id));
        }

        // upgrade types
        final int[] upgradeTypeData = getUpgradeTypes();
        for (int index = 0; index < upgradeTypeData.length; index += UpgradeType.NUM_ATTRIBUTES) {
            final int id = upgradeTypeData[index];
            UpgradeType.getUpgradeType(id).initialize(upgradeTypeData, index,
                    getUpgradeTypeName(id));
        }

        // weapon types
        final int[] weaponTypeData = getWeaponTypes();
        for (int index = 0; index < weaponTypeData.length; index += WeaponType.NUM_ATTRIBUTES) {
            final int id = weaponTypeData[index];
            WeaponType.getWeaponType(id).initialize(weaponTypeData, index, getWeaponTypeName(id));
        }

        // unit size types
        final int[] unitSizeTypeData = getUnitSizeTypes();
        for (int index = 0; index < unitSizeTypeData.length; index += UnitSizeType.NUM_ATTRIBUTES) {
            final int id = unitSizeTypeData[index];
            UnitSizeType.getUnitSizeType(id).initialize(unitSizeTypeData, index,
                    getUnitSizeTypeName(id));
        }

        // bullet types
        final int[] bulletTypeData = getBulletTypes();
        for (int index = 0; index < bulletTypeData.length; index += BulletType.NUM_ATTRIBUTES) {
            final int id = bulletTypeData[index];
            BulletType.getBulletType(id).initialize(bulletTypeData, index, getBulletTypeName(id));
        }

        // damage types
        final int[] damageTypeData = getDamageTypes();
        for (int index = 0; index < damageTypeData.length; index += DamageType.NUM_ATTRIBUTES) {
            final int id = damageTypeData[index];
            DamageType.getDamageType(id).initialize(damageTypeData, index, getDamageTypeName(id));
        }

        // explosion types
        final int[] explosionTypeData = getExplosionTypes();
        for (int index = 0; index < explosionTypeData.length; index += ExplosionType.NUM_ATTRIBUTES) {
            final int id = explosionTypeData[index];
            ExplosionType.getExplosionType(id).initialize(explosionTypeData, index,
                    getExplosionTypeName(id));
        }

        // unitCommand types
        final int[] unitCommandTypeData = getUnitCommandTypes();
        for (int index = 0; index < unitCommandTypeData.length; index += CommandType.NUM_ATTRIBUTES) {
            final int id = unitCommandTypeData[index];
            CommandType.getCommandType(id).initialize(unitCommandTypeData, index,
                    getUnitCommandTypeName(id));
        }

        // order types
        final int[] orderTypeData = getOrderTypes();
        for (int index = 0; index < orderTypeData.length; index += OrderType.NUM_ATTRIBUTES) {
            final int id = orderTypeData[index];
            OrderType.getOrderType(id).initialize(orderTypeData, index, getOrderTypeName(id));
        }
    }

    /**
     * Returns the map currently being used.
     *
     * @return the map currently being used
     */
    public GameMap getMap() {
        return map;
    }

    private void loadMapData() {
        final String mapName = new String(getMapName(), CHARACTER_SET);
        final String fileName = getMapFileName();
        final String hash = getMapHash();
        final int x = getMapWidth();
        final int y = getMapHeight();
        final int[] z = getMapDepth();
        final int[] buildable = getBuildableData();
        final int[] walkable = getWalkableData();

        map = new GameMap(mapName, fileName, hash, x, y, z, buildable, walkable);

        // if (enableTerrainAnalysis) {
        loadMapDetails();
        // }
    }

    private void loadMapDetails() {
        final String mapDataCacheFileName = map.getName() + ".jbwta";
        final File mapDataCacheFile = new File("bwta/", mapDataCacheFileName);

        if (mapDataCacheFile.exists()) {
            try {
                final FileReader fr = new FileReader(mapDataCacheFile);
                final BufferedReader br = new BufferedReader(fr);

                final int[] regionMap = readMapData(br);
                final int[] regions = readMapData(br);
                final int[] chokePoints = readMapData(br);
                final int[] bases = readMapData(br);

                int[] data = null;
                final HashMap<Integer, int[]> polygons = new HashMap<>();
                while ((data = readMapData(br)) != null) {
                    final int id = data[0];
                    final int size = data.length;
                    polygons.put(id, Arrays.copyOfRange(data, 1, size));
                }

                br.close();

                map.initialize(regionMap, regions, polygons, chokePoints, bases);
                return;

            } catch (final IOException ex) {
                System.err.println("Map data could not be loaded.");
                System.err.println(ex.getMessage());
            }
        }

        analyzeTerrain();
        final int[] regionMap = getRegionMap();
        final int[] regions = getRegions();
        final int[] chokePoints = getChokePoints();
        final int[] bases = getBaseLocations();
        final HashMap<Integer, int[]> polygons = new HashMap<>();
        for (int i = 0; i < regions.length; i += Region.NUM_ATTRIBUTES) {
            final int id = regions[i];
            polygons.put(id, getPolygon(id));
        }

        try {
            if (!mapDataCacheFile.getParentFile().exists()) {
                mapDataCacheFile.getParentFile().mkdirs();
            }
            final FileWriter fw = new FileWriter(mapDataCacheFile);
            final BufferedWriter bw = new BufferedWriter(fw);

            writeMapData(bw, regionMap);
            writeMapData(bw, regions);
            writeMapData(bw, chokePoints);
            writeMapData(bw, bases);
            for (final int id : polygons.keySet()) {
                bw.write("" + id + ",");
                writeMapData(bw, polygons.get(id));
            }

            bw.close();

            map.initialize(regionMap, regions, polygons, chokePoints, bases);

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
     * Utility function for printing to the java console from C++.
     *
     * <p>
     * C++ callback function.
     */
    void javaPrint(final String msg) {
        // TODO: Allow agent to decide how best to print the message.
        System.out.println("Bridge: " + msg);
    }

    /**
     * Connects the client to BWAPI.
     *
     * <p>
     * This method will block until Broodwar has been closed.
     */
    public void start() {
        startClient(this);
    }

    private native void startClient(final Broodwar broodwar);

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
        gameFrame = getFrame();
        loadMapData();
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
        gameFrame = getFrame();
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
                    listener.nukeDetect(Positions.Unknown);
                } else {
                    listener.nukeDetect(new Position(p1, p2));
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

    public native int getFrame();

    public native int getRemainingLatencyFrames();

    public native int getReplayFrameTotal();

    public native boolean isReplay();

    public native int getLastError();

    public native void leaveGame();

    public native void enableUserInput();

    public native void enablePerfectInformation();

    public native void setGameSpeed(final int speed);

    public native void setFrameSkip(final int frameSkip);

    public native void setCommandOptimizationLevel(final int level);

    public native void sendText(final String message);

    private native int[] getLoadedUnits(final int unitId);

    private native int[] getInterceptors(final int unitId);

    private native int[] getLarva(final int unitId);

    public boolean isVisible(final Position position) {
        return isVisible(position.getBX(), position.getBY());
    }

    private native boolean isVisible(final int tileX, final int tileY);

    public boolean isVisibleToPlayer(final Unit unit, final Player player) {
        return isVisibleToPlayer(unit.getId(), player.getId());
    }

    private native boolean isVisibleToPlayer(final int unitId, final int playerId);

    public boolean isExplored(final Position position) {
        return isExplored(position.getBX(), position.getBY());
    }

    private native boolean isExplored(final int tileX, final int tileY);

    public boolean isBuildable(final Position position, final boolean includeBuildings) {
        return isBuildable(position.getBX(), position.getBY(), includeBuildings);
    }

    // TODO: Consolidate canResearch and canUpgrade and canMake?

    private native boolean isBuildable(final int tileX, final int tileY,
            final boolean includeBuildings);

    public boolean canBuildHere(final Position p, final UnitType ut, final boolean checkExplored) {
        return canBuildHere(p.getBX(), p.getBY(), ut.getId(), checkExplored);
    }

    private native boolean canBuildHere(final int tileX, final int tileY, final int unitTypeId,
            final boolean checkExplored);

    public boolean canBuildHere(final Unit u, final Position p, final UnitType ut,
            final boolean checkExplored) {
        return canBuildHere(u.getId(), p.getBX(), p.getBY(), ut.getId(), checkExplored);
    }

    private native boolean canBuildHere(final int unitId, final int tileX, final int tileY,
            final int unitTypeId, boolean checkExplored);

    public boolean hasCreep(final Position position) {
        return hasCreep(position.getBX(), position.getBY());
    }

    private native boolean hasCreep(final int tileX, final int tileY);

    public boolean hasPower(final Position position) {
        return hasPower(position, UnitType.None);
    }

    public boolean hasPower(final Position position, final UnitType unitType) {
        return hasPower(position.getBX(), position.getBY(), unitType.getId());
    }

    private native boolean hasPower(final int tileX, final int tileY, final int unitTypeId);

    public boolean hasPower(final Position position, final int tileWidth, final int tileHeight) {
        return hasPower(position, tileWidth, tileHeight, UnitType.None);
    }

    public boolean hasPower(final Position position, final int tileWidth, final int tileHeight,
            final UnitType unitType) {
        return hasPower(position.getBX(), position.getBY(), tileWidth, tileHeight, unitType.getId());
    }

    private native boolean hasPower(final int tileX, final int tileY, final int tileWidth,
            final int tileHeight, final int unitTypeId);

    public boolean hasPowerPrecise(final Position position) {
        return hasPowerPrecise(position, UnitType.None);
    }

    public boolean hasPowerPrecise(final Position position, final UnitType unitType) {
        return hasPowerPrecise(position.getPX(), position.getPY(), unitType.getId());
    }

    private native boolean hasPowerPrecise(final int x, final int y, final int unitTypeId);

    public boolean hasPath(final Position from, final Position to) {
        return hasPath(from.getPX(), from.getPY(), to.getPX(), to.getPY());
    }

    private native boolean hasPath(final int fromX, final int fromY, final int toX, final int toY);

    public boolean hasPath(final Unit u, final Unit target) {
        return hasPath(u.getId(), target.getId());
    }

    private native boolean hasPath(final int unitID, final int targetID);

    public boolean hasPath(final Unit u, final Position to) {
        return hasPath(u.getId(), to.getPX(), to.getPY());
    }

    private native boolean hasPath(final int unitID, final int toX, final int toY);

    public boolean canResearch(final TechType techType) {
        return canResearch(techType.getId());
    }

    private native boolean canResearch(final int techTypeId);

    public boolean canMake(final UnitType unitType) {
        return canMake(unitType.getId());
    }

    private native boolean canMake(final int unitTypeId);

    public boolean canMake(final UnitType unitType1, final UnitType unitType2) {
        return canMake(unitType1.getId(), unitType2.getId());
    }

    private native boolean canMake(final int unitId, final int unitTypeId);

    /**
     * Checks all the
     *
     * @param unit
     * @param techType
     * @return
     */
    public boolean canResearch(final Unit unit, final TechType techType) {
        return canResearch(unit.getId(), techType.getId());
    }

    private native boolean canResearch(final int unitId, final int techTypeId);

    /**
     * Checks that the UpgradeType can be acquired.
     *
     * @param unitType
     *            the UpgradeType to check
     *
     * @return true if the UpgradeType can be acquired; false otherwise
     */
    public boolean canUpgrade(final UpgradeType unitType) {
        return canUpgrade(unitType.getId());
    }

    private native boolean canUpgrade(int upgradeTypeId);

    /**
     * Checks that the Unit can perform the UpgradeType.
     *
     * @param unit
     *            the Unit that will be used to acquire the upgrade
     *
     * @param upgradeType
     *            the UpgradeType to check
     *
     * @return true if the UpgradeType can be acquired through Unit; false otherwise
     */
    public boolean canUpgrade(final Unit unit, final UpgradeType upgradeType) {
        return canUpgrade(unit.getId(), upgradeType.getId());
    }

    private native boolean canUpgrade(final int unitId, final int upgradeTypeId);

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

    private native String getMapHash();

    private native int getMapWidth();

    private native int getMapHeight();

    private native int[] getMapDepth();

    private native int[] getRegionMap();

    private native int[] getWalkableData();

    private native int[] getBuildableData();

    private native int[] getChokePoints();

    private native int[] getRegions();

    private native int[] getPolygon(final int regionId);

    private native int[] getBaseLocations();

    // *********************************************************************************************
    // Drawing Commands
    //
    // TODO: Negative width and height values cannot be used.
    //
    // TODO: Make available other colors.
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
        drawTextMap(position.getPX(), position.getPY(), text);
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
        drawTextMap(position.getPX(), position.getPY(), color.getControlCharacters() + text);
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
        drawTextScreen(position.getPX(), position.getPY(), text);
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
        drawTextScreen(position.getPX(), position.getPY(), color.getControlCharacters() + text);
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
        drawLineMap(p1.getPX(), p1.getPY(), p2.getPX(), p2.getPY(), color.getId());
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
        drawLineScreen(p1.getPX(), p1.getPY(), p2.getPX(), p2.getPY(), color.getId());
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
        drawRectangleMap(position.getPX(), position.getPY(), width, height, color.getId(), fill);
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
        drawRectangleScreen(position.getPX(), position.getPY(), width, height, color.getId(), fill);
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
        drawEllipseMap(position.getPX(), position.getPY(), xRadius, yRadius, color.getId(), fill);
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
        drawEllipseScreen(position.getPX(), position.getPY(), xRadius, yRadius, color.getId(), fill);
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
        drawEllipseMap(position.getPX(), position.getPY(), radius, radius, color.getId(), fill);
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
        drawEllipseScreen(position.getPX(), position.getPY(), radius, radius, color.getId(), fill);
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
}
