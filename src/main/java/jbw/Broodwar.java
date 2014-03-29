package jbw;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

import jbw.model.*;
import jbw.model.Map;
import jbw.types.*;
import jbw.types.BulletType.BulletTypes;
import jbw.types.DamageType.DamageTypes;
import jbw.types.ExplosionType.ExplosionTypes;
import jbw.types.OrderType.OrderTypes;
import jbw.types.RaceType.RaceTypes;
import jbw.types.TechType.TechTypes;
import jbw.types.UnitCommandType.UnitCommandTypes;
import jbw.types.UnitSizeType.UnitSizeTypes;
import jbw.types.UnitType.UnitTypes;
import jbw.types.UpgradeType.UpgradeTypes;
import jbw.types.WeaponType.WeaponTypes;
import jbw.util.BWColor;

public class Broodwar implements IdLookup {

    static {
        final File dll = new File("src/main/resources/", "client-bridge-x86.dll");
        try {
            System.load(dll.getAbsolutePath());
        } catch (final UnsatisfiedLinkError ex) {
            if (!dll.exists()) {
                System.err.println("Native code library not found: " + dll.getAbsolutePath());
            }
            System.err.println("Native code library failed to load: " + ex.toString());
        }
    }

    /** listener for BWAPI callback events */
    private final BroodwarListener listener;

    /** indicates that BWTA should be enabled */
    private final boolean enableBWTA;

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

    private final HashMap<Integer, Unit> units = new HashMap<>();
    private final ArrayList<Unit> playerUnits = new ArrayList<>();
    private final ArrayList<Unit> alliedUnits = new ArrayList<>();
    private final ArrayList<Unit> enemyUnits = new ArrayList<>();
    private final ArrayList<Unit> neutralUnits = new ArrayList<>();

    private Player self;
    private Player neutralPlayer;
    private final HashMap<Integer, Player> players = new HashMap<>();
    private final ArrayList<Player> allies = new ArrayList<>();
    private final ArrayList<Player> enemies = new ArrayList<>();

    private Map map;
    private int gameFrame = 0;

    /**
     * Instantiates a BWAPI instance, but does not connect to the bridge. To connect, the start
     * method must be invoked.
     * 
     * @param listener
     *            listener for BWAPI callback events.
     * 
     * @param enableBWTA
     *            {@code true} if BWTA should be enabled; {@code false} otherwise
     */
    public Broodwar(final BroodwarListener listener, final boolean enableBWTA) {
        this.listener = listener;
        this.enableBWTA = enableBWTA;
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

    public int getFrameCount() {
        return gameFrame;
    }

    public Player getSelf() {
        return self;
    }

    public Player getNeutralPlayer() {
        return neutralPlayer;
    }

    public Collection<Player> getPlayers() {
        return Collections.unmodifiableCollection(players.values());
    }

    public List<Player> getAllies() {
        return Collections.unmodifiableList(allies);
    }

    public List<Player> getEnemies() {
        return Collections.unmodifiableList(enemies);
    }

    @Override
    public Player getPlayer(final int playerID) {
        return players.get(playerID);
    }

    @Override
    public Unit getUnit(final int unitId) {
        return units.get(unitId);
    }

    public Collection<Unit> getAllUnits() {
        return Collections.unmodifiableCollection(units.values());
    }

    public List<Unit> getMyUnits() {
        return Collections.unmodifiableList(playerUnits);
    }

    public List<Unit> getAlliedUnits() {
        return Collections.unmodifiableList(alliedUnits);
    }

    public List<Unit> getEnemyUnits() {
        return Collections.unmodifiableList(enemyUnits);
    }

    public List<Unit> getNeutralUnits() {
        return Collections.unmodifiableList(neutralUnits);
    }

    public List<Unit> getUnits(final Player p) {
        final List<Unit> pUnits = new ArrayList<Unit>();
        for (final Unit u : units.values()) {
            if (u.getPlayer() == p) {
                pUnits.add(u);
            }
        }
        return pUnits;
    }

    public List<Unit> getUnitsOnTile(final int x, final int y) {
        // Most tiles will have 0 units on tile.
        final List<Unit> units = new ArrayList<Unit>(0);
        for (final int id : getUnitIdsOnTile(x, y)) {
            units.add(getUnit(id));
        }
        return units;
    }

    /**
     * Returns the map currently being used.
     * 
     * @return the map currently being used
     */
    public Map getMap() {
        return map;
    }

    /**
     * Loads type data from BWAPI.
     */
    private void loadTypeData() {
        // race types
        final int[] raceTypeData = getRaceTypes();
        for (int index = 0; index < raceTypeData.length; index += RaceType.NUM_ATTRIBUTES) {
            final int id = raceTypeData[index];
            RaceTypes.getRaceType(id).initialize(raceTypeData, index, getRaceTypeName(id));
        }

        // unit types
        final int[] unitTypeData = getUnitTypes();
        for (int index = 0; index < unitTypeData.length; index += UnitType.NUM_ATTRIBUTES) {
            final int id = unitTypeData[index];
            UnitTypes.getUnitType(id).initialize(unitTypeData, index, getUnitTypeName(id),
                    getRequiredUnits(id));
        }

        // tech types
        final int[] techTypeData = getTechTypes();
        for (int index = 0; index < techTypeData.length; index += TechType.NUM_ATTRIBUTES) {
            final int id = techTypeData[index];
            TechTypes.getTechType(id).initialize(techTypeData, index, getTechTypeName(id));
        }

        // upgrade types
        final int[] upgradeTypeData = getUpgradeTypes();
        for (int index = 0; index < upgradeTypeData.length; index += UpgradeType.NUM_ATTRIBUTES) {
            final int id = upgradeTypeData[index];
            UpgradeTypes.getUpgradeType(id).initialize(upgradeTypeData, index,
                    getUpgradeTypeName(id));
        }

        // weapon types
        final int[] weaponTypeData = getWeaponTypes();
        for (int index = 0; index < weaponTypeData.length; index += WeaponType.NUM_ATTRIBUTES) {
            final int id = weaponTypeData[index];
            WeaponTypes.getWeaponType(id).initialize(weaponTypeData, index, getWeaponTypeName(id));
        }

        // unit size types
        final int[] unitSizeTypeData = getUnitSizeTypes();
        for (int index = 0; index < unitSizeTypeData.length; index += UnitSizeType.NUM_ATTRIBUTES) {
            final int id = unitSizeTypeData[index];
            UnitSizeTypes.getUnitSizeType(id).initialize(unitSizeTypeData, index,
                    getUnitSizeTypeName(id));
        }

        // bullet types
        final int[] bulletTypeData = getBulletTypes();
        for (int index = 0; index < bulletTypeData.length; index += BulletType.NUM_ATTRIBUTES) {
            final int id = bulletTypeData[index];
            BulletTypes.getBulletType(id).initialize(bulletTypeData, index, getBulletTypeName(id));
        }

        // damage types
        final int[] damageTypeData = getDamageTypes();
        for (int index = 0; index < damageTypeData.length; index += DamageType.NUM_ATTRIBUTES) {
            final int id = damageTypeData[index];
            DamageTypes.getDamageType(id).initialize(damageTypeData, index, getDamageTypeName(id));
        }

        // explosion types
        final int[] explosionTypeData = getExplosionTypes();
        for (int index = 0; index < explosionTypeData.length; index += ExplosionType.NUM_ATTRIBUTES) {
            final int id = explosionTypeData[index];
            ExplosionTypes.getExplosionType(id).initialize(explosionTypeData, index,
                    getExplosionTypeName(id));
        }

        // unitCommand types
        final int[] unitCommandTypeData = getUnitCommandTypes();
        for (int index = 0; index < unitCommandTypeData.length; index +=
                UnitCommandType.NUM_ATTRIBUTES) {
            final int id = unitCommandTypeData[index];
            UnitCommandTypes.getUnitCommandType(id).initialize(unitCommandTypeData, index,
                    getUnitCommandTypeName(id));
        }

        // order types
        final int[] orderTypeData = getOrderTypes();
        for (int index = 0; index < orderTypeData.length; index += OrderType.NUM_ATTRIBUTES) {
            final int id = orderTypeData[index];
            OrderTypes.getOrderType(id).initialize(orderTypeData, index, getOrderTypeName(id));
        }
    }

    private void loadMapData() {

        final String mapName = new String(getMapName(), CHARACTER_SET);
        final String fileName = getMapFileName();
        final String hash = getMapHash();
        final int x = getMapWidth();
        final int y = getMapHeight();
        final int[] z = getHeightData();
        final int[] buildable = getBuildableData();
        final int[] walkable = getWalkableData();

        map = new Map(mapName, fileName, hash, x, y, z, buildable, walkable);

        if (enableBWTA) {
            loadMapDetails();
        }
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

    /**
     * Convenience method to write integers to a out each part of BWTA map data to a stream.
     * 
     * @param writer
     *            {@code BufferedWriter} to write to
     * 
     * @param data
     *            integers to write
     * 
     * @throws IOException
     *             if the data cannot be written
     */
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

    /**
     * Convenience method to read a line of integers separated by commas.
     * 
     * @param reader
     *            {@code BufferedReader} to read from
     * 
     * @return integers that were read from the line or {@code null} when end-of-stream is reached
     * 
     * @throws IOException
     *             if the data cannot be read
     */
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
    private void javaPrint(final String msg) {
        // TODO: Allow agent to decide how best to print the message.
        System.out.println("Bridge: " + msg);
    }

    /**
     * Notifies the client and event listener that a connection has been formed to the bridge.
     * 
     * <p>
     * C++ callback function.
     */
    private void connected() {
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
    private void gameStarted() {
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
    private void gameUpdate() {
        // update game state
        gameFrame = getFrame();
        if (!isReplay()) {
            self.update(getPlayerUpdate(self.getId()));
            self.updateResearch(getResearchStatus(self.getId()), getUpgradeStatus(self.getId()));
        } else {
            for (final Integer playerID : players.keySet()) {
                players.get(playerID).update(getPlayerUpdate(playerID));
                players.get(playerID).updateResearch(getResearchStatus(playerID),
                        getUpgradeStatus(playerID));
            }
        }
        // update units
        final int[] unitData = getAllUnitsData();
        final HashSet<Integer> deadUnits = new HashSet<Integer>(units.keySet());
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
    private void gameEnded() {
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
    private void eventOccurred(final int eventTypeId, final int p1, final int p2, final String p3) {

        final EventType event = EventType.getEventType(eventTypeId);
        switch (event) {
            case MatchStart :
                listener.matchStart();
                break;

            case MatchEnd :
                listener.matchEnd(p1 == 1);
                break;

            case MatchFrame :
                listener.matchFrame();
                break;

            case MenuFrame :
                // Not currently used.
                break;

            case SendText :
                listener.sendText(p3);
                break;

            case ReceiveText :
                listener.receiveText(p3);
                break;

            case PlayerLeft :
                listener.playerLeft(players.get(p1));
                break;

            case NukeDetect :
                if ((p1 == -1) || (p2 == -1)) {
                    listener.nukeDetect(Location.UNKNOWN);
                } else {
                    listener.nukeDetect(new Location(p1, p2));
                }
                break;

            case UnitDiscover :
                listener.unitDiscover(units.get(p1));
                break;

            case UnitEvade :
                listener.unitEvade(units.get(p1));
                break;

            case UnitShow :
                listener.unitShow(units.get(p1));
                break;

            case UnitHide :
                listener.unitHide(units.get(p1));
                break;

            case UnitCreate :
                listener.unitCreate(units.get(p1));
                break;

            case UnitDestroy :
                listener.unitDestroy(units.get(p1));
                break;

            case UnitMorph :
                listener.unitMorph(units.get(p1));
                break;

            case UnitRenegade :
                listener.unitRenegade(units.get(p1));
                break;

            case SaveGame :
                listener.saveGame(p3);
                break;

            case UnitComplete :
                listener.unitComplete(units.get(p1));
                break;

            case PlayerDropped :
                listener.playerDropped(players.get(p1));
                break;

            case None :
                // Not currently used.
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
    private void keyPressed(final int keyCode) {
        listener.keyPressed(keyCode);
    }

    private native void startClient(final Broodwar jniBWAPI);

    private native int getFrame();

    public native int getReplayFrameTotal();

    private native int[] getPlayersData();

    private native int[] getPlayerUpdate(int playerId);

    private native byte[] getPlayerName(int playerId);

    private native int[] getResearchStatus(int playerId);

    private native int[] getUpgradeStatus(int playerId);

    private native int[] getAllUnitsData();

    private native int[] getRaceTypes();

    private native String getRaceTypeName(int unitTypeId);

    private native int[] getUnitTypes();

    private native String getUnitTypeName(int unitTypeId);

    private native int[] getRequiredUnits(int unitTypeId);

    private native int[] getTechTypes();

    private native String getTechTypeName(int techId);

    private native int[] getUpgradeTypes();

    private native String getUpgradeTypeName(int upgradeId);

    private native int[] getWeaponTypes();

    private native String getWeaponTypeName(int weaponId);

    private native int[] getUnitSizeTypes();

    private native String getUnitSizeTypeName(int sizeId);

    private native int[] getBulletTypes();

    private native String getBulletTypeName(int bulletId);

    private native int[] getDamageTypes();

    private native String getDamageTypeName(int damageId);

    private native int[] getExplosionTypes();

    private native String getExplosionTypeName(int explosionId);

    private native int[] getUnitCommandTypes();

    private native String getUnitCommandTypeName(int unitCommandId);

    private native int[] getOrderTypes();

    private native String getOrderTypeName(int unitCommandId);

    private native int[] getUnitIdsOnTile(int tx, int ty);

    private native void analyzeTerrain();

    private native int getMapWidth();

    private native int getMapHeight();

    private native byte[] getMapName();

    private native String getMapFileName();

    private native String getMapHash();

    // TODO: Change to getMapHeightData();
    private native int[] getHeightData();

    /** Returns the regionId for each map tile */
    private native int[] getRegionMap();

    private native int[] getWalkableData();

    private native int[] getBuildableData();

    private native int[] getChokePoints();

    private native int[] getRegions();

    private native int[] getPolygon(int regionId);

    private native int[] getBaseLocations();

    // utility commands
    public native void drawHealth(boolean enable);

    public native void drawTargets(boolean enable);

    public native void drawIDs(boolean enable);

    public native void enableUserInput();

    public native void enablePerfectInformation();

    public native void setGameSpeed(int speed);

    public native void setFrameSkip(int frameSkip);

    public native void leaveGame();

    // draw commands
    private native void drawBox(int left, int top, int right, int bottom, int color, boolean fill,
            boolean screenCoords);

    public void drawBox(final Position topLeft, final Position bottomRight, final BWColor bWColor,
            final boolean fill, final boolean screenCoords) {
        drawBox(topLeft.getPX(), topLeft.getPY(), bottomRight.getPX(), bottomRight.getPY(),
                bWColor.getID(), fill, screenCoords);
    }

    private native void drawCircle(int x, int y, int radius, int color, boolean fill,
            boolean screenCoords);

    public void drawCircle(final Position p, final int radius, final BWColor bWColor,
            final boolean fill, final boolean screenCoords) {
        drawCircle(p.getPX(), p.getPY(), radius, bWColor.getID(), fill, screenCoords);
    }

    private native void drawLine(int x1, int y1, int x2, int y2, int color, boolean screenCoords);

    public void drawLine(final Position start, final Position end, final BWColor bWColor,
            final boolean screenCoords) {
        drawLine(start.getPX(), start.getPY(), end.getPX(), end.getPY(), bWColor.getID(),
                screenCoords);
    }

    private native void drawDot(int x, int y, int color, boolean screenCoords);

    public void drawDot(final Position p, final BWColor bWColor, final boolean screenCoords) {
        drawDot(p.getPX(), p.getPY(), bWColor.getID(), screenCoords);
    }

    private native void drawText(int x, int y, String msg, boolean screenCoords);

    public void drawText(final Position a, final String msg, final boolean screenCoords) {
        drawText(a.getPX(), a.getPY(), msg, screenCoords);
    }

    // Extended Commands
    private native boolean isVisible(int tileX, int tileY);

    public boolean isVisible(final Position p) {
        return isVisible(p.getBX(), p.getBY());
    }

    private native boolean isExplored(int tileX, int tileY);

    public boolean isExplored(final Position p) {
        return isExplored(p.getBX(), p.getBY());
    }

    private native boolean isBuildable(int tx, int ty, boolean includeBuildings);

    public boolean isBuildable(final Position p, final boolean includeBuildings) {
        return isBuildable(p.getBX(), p.getBY(), includeBuildings);
    }

    public native boolean hasCreep(int tileX, int tileY);

    public native boolean hasPower(int tileX, int tileY);

    public native boolean hasPower(int tileX, int tileY, int unitTypeId);

    public native boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight);

    public native boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight,
            int unitTypeId);

    public native boolean hasPowerPrecise(int x, int y);

    public native boolean hasPath(int fromX, int fromY, int toX, int toY);

    public native boolean hasPath(int unitId, int targetId);

    public native boolean hasPath(int unitId, int toX, int toY);

    public native int[] getLoadedUnits(int unitId);

    public native int[] getInterceptors(int unitId);

    public native int[] getLarva(int unitId);

    public native boolean canBuildHere(int tileX, int tileY, int unitTypeId, boolean checkExplored);

    public native boolean canBuildHere(int unitId, int tileX, int tileY, int unitTypeId,
            boolean checkExplored);

    public boolean canMake(final UnitType unitType) {
        return canMake(unitType.getId());
    }

    public native boolean canMake(int unitTypeId);

    public native boolean canMake(int unitId, int unitTypeId);

    public boolean canResearch(final TechType techType) {
        return canResearch(techType.getId());
    }

    public native boolean canResearch(int techTypeId);

    public native boolean canResearch(int unitId, int techTypeId);

    public native boolean canUpgrade(int upgradeTypeId);

    public native boolean canUpgrade(int unitId, int upgradeTypeId);

    public native void printText(String message);

    public native void sendText(String message);

    public native void setCommandOptimizationLevel(int level);

    public native boolean isReplay();

    private native boolean isVisibleToPlayer(int unitId, int playerId);

    public boolean isVisibleToPlayer(final Unit u, final Player p) {
        return isVisibleToPlayer(u.getId(), p.getId());
    }

    public native int getLastError();

    public native int getRemainingLatencyFrames();

}
