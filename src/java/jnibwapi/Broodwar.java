package jnibwapi;

import java.awt.Point;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;

import jnibwapi.model.*;
import jnibwapi.model.Map;
import jnibwapi.types.*;
import jnibwapi.types.TechType.TechTypes;
import jnibwapi.types.UnitType.UnitTypes;

/**
 * JNI interface for the Brood War API.<br>
 * 
 * This focus of this interface is to provide the callback and game state query functionality in
 * BWAPI.<br>
 * 
 * Note: for thread safety and game state sanity, all native calls should be invoked from the
 * callback methods.<br>
 * 
 * For BWAPI documentation see: {@link http://code.google.com/p/bwapi/}<br>
 * 
 * API Pages<br>
 * Game: {@link http://code.google.com/p/bwapi/wiki/Game}<br>
 * Unit: {@link http://code.google.com/p/bwapi/wiki/Unit}<br>
 */
public class Broodwar {

    /* Load the JNI library. */
    static {
        // final String clientBridgeDll = "client-bridge-" + System.getProperty("os.arch");
        final String clientBridgeDll = "client-bridge-x86";
        try {
            System.loadLibrary(clientBridgeDll);

        } catch (final UnsatisfiedLinkError ex) {
            final File dll = new File(clientBridgeDll + ".dll");
            if (!dll.exists()) {
                System.err.println("Native code library not found: " + dll.getAbsolutePath());
            }
            System.err.println("Native code library failed to load: " + ex.toString());
        }
    }

    /** The listener for BWAPI callback events */
    private final BroodwarListener listener;

    /** Indicates of BWTA should be enabled */
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

    private final HashSet<Integer> allyIds = new HashSet<>();
    private final HashSet<Integer> enemyIds = new HashSet<>();

    private final HashMap<Integer, Player> players = new HashMap<>();
    private final ArrayList<Player> allies = new ArrayList<>();
    private final ArrayList<Player> enemies = new ArrayList<>();

    private final HashMap<Integer, UnitType> unitTypes = new HashMap<>();
    private final HashMap<Integer, RaceType> raceTypes = new HashMap<>();
    private final HashMap<Integer, TechType> techTypes = new HashMap<>();
    private final HashMap<Integer, UpgradeType> upgradeTypes = new HashMap<>();
    private final HashMap<Integer, WeaponType> weaponTypes = new HashMap<>();
    private final HashMap<Integer, UnitSizeType> unitSizeTypes = new HashMap<>();
    private final HashMap<Integer, BulletType> bulletTypes = new HashMap<>();
    private final HashMap<Integer, DamageType> damageTypes = new HashMap<>();
    private final HashMap<Integer, ExplosionType> explosionTypes = new HashMap<>();
    private final HashMap<Integer, UnitCommandType> unitCommandTypes = new HashMap<>();
    private final HashMap<Integer, OrderType> orderTypes = new HashMap<>();
    private final HashMap<Integer, EventType> eventTypes = new HashMap<>();

    private int gameFrame = 0;

    private Map map;

    private Player self;
    private Player neutralPlayer;

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

    // invokes the main native method
    private native void startClient(Broodwar jniBWAPI);

    private native int getFrame();

    public native int getReplayFrameTotal();

    private native int[] getPlayersData();

    private native int[] getPlayerUpdate(int playerID);

    /** Returns string as a byte[] to properly handle ASCII-extended characters */
    private native byte[] getPlayerName(int playerID);

    private native int[] getResearchStatus(int playerID);

    private native int[] getUpgradeStatus(int playerID);

    private native int[] getAllUnitsData();

    private native int[] getRaceTypes();

    private native String getRaceTypeName(int unitTypeID);

    private native int[] getUnitTypes();

    private native String getUnitTypeName(int unitTypeID);

    private native int[] getRequiredUnits(int unitTypeID);

    private native int[] getTechTypes();

    private native String getTechTypeName(int techID);

    private native int[] getUpgradeTypes();

    private native String getUpgradeTypeName(int upgradeID);

    private native int[] getWeaponTypes();

    private native String getWeaponTypeName(int weaponID);

    private native int[] getUnitSizeTypes();

    private native String getUnitSizeTypeName(int sizeID);

    private native int[] getBulletTypes();

    private native String getBulletTypeName(int bulletID);

    private native int[] getDamageTypes();

    private native String getDamageTypeName(int damageID);

    private native int[] getExplosionTypes();

    private native String getExplosionTypeName(int explosionID);

    private native int[] getUnitCommandTypes();

    private native String getUnitCommandTypeName(int unitCommandID);

    private native int[] getOrderTypes();

    private native String getOrderTypeName(int unitCommandID);

    private native int[] getUnitIdsOnTile(int tx, int ty);

    private native void analyzeTerrain();

    private native int getMapWidth();

    private native int getMapHeight();

    /** Returns string as a byte[] to properly handle ASCII-extended characters */
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

    private native int[] getPolygon(int regionID);

    private native int[] getBaseLocations();

    // unit commands: http://code.google.com/p/bwapi/wiki/Unit

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
    public native void drawBox(int left, int top, int right, int bottom, int color, boolean fill,
            boolean screenCoords);

    public native void drawCircle(int x, int y, int radius, int color, boolean fill,
            boolean screenCoords);

    public native void drawLine(int x1, int y1, int x2, int y2, int color, boolean screenCoords);

    public void drawLine(final Point a, final Point b, final int color, final boolean screenCoords) {
        drawLine(a.x, a.y, b.x, b.y, color, screenCoords);
    }

    public native void drawDot(int x, int y, int color, boolean screenCoords);

    public native void drawText(int x, int y, String msg, boolean screenCoords);

    public void drawText(final Point a, final String msg, final boolean screenCoords) {
        drawText(a.x, a.y, msg, screenCoords);
    }

    // Extended Commands
    public native boolean isVisible(int tileX, int tileY);

    public native boolean isExplored(int tileX, int tileY);

    public native boolean isBuildable(int tx, int ty, boolean includeBuildings);

    public boolean isBuildable(final int tx, final int ty) {
        return isBuildable(tx, ty, false);
    }

    public native boolean hasCreep(int tileX, int tileY);

    public native boolean hasPower(int tileX, int tileY);

    public native boolean hasPower(int tileX, int tileY, int unitTypeID);

    public native boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight);

    public native boolean hasPower(int tileX, int tileY, int tileWidth, int tileHeight,
            int unitTypeID);

    public native boolean hasPowerPrecise(int x, int y);

    public native boolean hasPath(int fromX, int fromY, int toX, int toY);

    public native boolean hasPath(int unitID, int targetID);

    public native boolean hasPath(int unitID, int toX, int toY);

    public native boolean hasLoadedUnit(int unitID1, int unitID2);

    public native boolean canBuildHere(int tileX, int tileY, int unitTypeID, boolean checkExplored);

    public native boolean canBuildHere(int unitID, int tileX, int tileY, int unitTypeID,
            boolean checkExplored);

    public boolean canMake(final UnitTypes unitType) {
        return canMake(unitType.ordinal());
    }

    public native boolean canMake(int unitTypeID);

    public native boolean canMake(int unitID, int unitTypeID);

    public boolean canResearch(final TechTypes techType) {
        return canResearch(techType.ordinal());
    }

    public native boolean canResearch(int techTypeID);

    public native boolean canResearch(int unitID, int techTypeID);

    public native boolean canUpgrade(int upgradeTypeID);

    public native boolean canUpgrade(int unitID, int upgradeTypeID);

    public native void printText(String message);

    public native void sendText(String message);

    public native void setCommandOptimizationLevel(int level);

    public native boolean isReplay();

    private native boolean isVisibleToPlayer(int unitID, int playerID);

    public boolean isVisibleToPlayer(final Unit u, final Player p) {
        return isVisibleToPlayer(u.getId(), p.getId());
    }

    public native int getLastError();

    public native int getRemainingLatencyFrames();

    public UnitType getUnitType(final UnitTypes typeId) {
        return unitTypes.get(typeId);
    }

    public RaceType getRaceType(final int typeId) {
        return raceTypes.get(typeId);
    }

    public TechType getTechType(final int typeId) {
        return techTypes.get(typeId);
    }

    public UpgradeType getUpgradeType(final int upgradeId) {
        return upgradeTypes.get(upgradeId);
    }

    public WeaponType getWeaponType(final int weaponId) {
        return weaponTypes.get(weaponId);
    }

    public UnitSizeType getUnitSizeType(final int sizeId) {
        return unitSizeTypes.get(sizeId);
    }

    public BulletType getBulletType(final int bulletId) {
        return bulletTypes.get(bulletId);
    }

    public DamageType getDamageType(final int damageId) {
        return damageTypes.get(damageId);
    }

    public ExplosionType getExplosionType(final int explosionId) {
        return explosionTypes.get(explosionId);
    }

    public UnitCommandType getUnitCommandType(final int unitCommandId) {
        return unitCommandTypes.get(unitCommandId);
    }

    public OrderType getOrderType(final int orderId) {
        return orderTypes.get(orderId);
    }

    public Collection<UnitType> unitTypes() {
        return Collections.unmodifiableCollection(unitTypes.values());
    }

    public Collection<RaceType> raceTypes() {
        return Collections.unmodifiableCollection(raceTypes.values());
    }

    public Collection<TechType> techTypes() {
        return Collections.unmodifiableCollection(techTypes.values());
    }

    public Collection<UpgradeType> upgradeTypes() {
        return Collections.unmodifiableCollection(upgradeTypes.values());
    }

    public Collection<WeaponType> weaponTypes() {
        return Collections.unmodifiableCollection(weaponTypes.values());
    }

    public Collection<UnitSizeType> unitSizeTypes() {
        return Collections.unmodifiableCollection(unitSizeTypes.values());
    }

    public Collection<BulletType> bulletTypes() {
        return Collections.unmodifiableCollection(bulletTypes.values());
    }

    public Collection<DamageType> damageTypes() {
        return Collections.unmodifiableCollection(damageTypes.values());
    }

    public Collection<ExplosionType> explosionTypes() {
        return Collections.unmodifiableCollection(explosionTypes.values());
    }

    public Collection<UnitCommandType> unitCommandTypes() {
        return Collections.unmodifiableCollection(unitCommandTypes.values());
    }

    public Collection<OrderType> orderTypes() {
        return Collections.unmodifiableCollection(orderTypes.values());
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

    public Player getPlayer(final int playerId) {
        return players.get(playerId);
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

    public Unit getUnit(final int unitID) {
        return units.get(unitID);
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
            if (u.getOwner().getId() == p.getId()) {
                pUnits.add(u);
            }
        }
        return Collections.unmodifiableList(pUnits);
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
        for (int i = 0; i < raceTypeData.length; i += RaceType.NUM_ATTRIBUTES) {
            final RaceType type = new RaceType(raceTypeData, i);
            type.setName(getRaceTypeName(type.getId()));
            raceTypes.put(type.getId(), type);
        }

        // unit types
        final int[] unitTypeData = getUnitTypes();
        for (int i = 0; i < unitTypeData.length; i += UnitType.NUM_ATTRIBUTES) {
            final String name = getUnitTypeName(unitTypeData[i]);
            final int[] requiredUnits = getRequiredUnits(unitTypeData[i]);
            final UnitType type = new UnitType(unitTypeData, i, name, requiredUnits);
            unitTypes.put(type.getId(), type);
        }

        // tech types
        final int[] techTypeData = getTechTypes();
        for (int i = 0; i < techTypeData.length; i += TechType.NUM_ATTRIBUTES) {
            final TechType type = new TechType(techTypeData, i);
            type.setName(getTechTypeName(type.getId()));
            techTypes.put(type.getId(), type);
        }

        // upgrade types
        final int[] upgradeTypeData = getUpgradeTypes();
        for (int i = 0; i < upgradeTypeData.length; i += UpgradeType.NUM_ATTRIBUTES) {
            final UpgradeType type = new UpgradeType(upgradeTypeData, i);
            type.setName(getUpgradeTypeName(type.getId()));
            upgradeTypes.put(type.getId(), type);
        }

        // weapon types
        final int[] weaponTypeData = getWeaponTypes();
        for (int i = 0; i < weaponTypeData.length; i += WeaponType.NUM_ATTRIBUTES) {
            final WeaponType type = new WeaponType(weaponTypeData, i);
            type.setName(getWeaponTypeName(type.getId()));
            weaponTypes.put(type.getId(), type);
        }

        // unit size types
        final int[] unitSizeTypeData = getUnitSizeTypes();
        for (int i = 0; i < unitSizeTypeData.length; i += UnitSizeType.NUM_ATTRIBUTES) {
            final UnitSizeType type = new UnitSizeType(unitSizeTypeData, i);
            type.setName(getUnitSizeTypeName(type.getId()));
            unitSizeTypes.put(type.getId(), type);
        }

        // bullet types
        final int[] bulletTypeData = getBulletTypes();
        for (int i = 0; i < bulletTypeData.length; i += BulletType.NUM_ATTRIBUTES) {
            final BulletType type = new BulletType(bulletTypeData, i);
            type.setName(getBulletTypeName(type.getId()));
            bulletTypes.put(type.getId(), type);
        }

        // damage types
        final int[] damageTypeData = getDamageTypes();
        for (int i = 0; i < damageTypeData.length; i += DamageType.NUM_ATTRIBUTES) {
            final DamageType type = new DamageType(damageTypeData, i);
            type.setName(getDamageTypeName(type.getId()));
            damageTypes.put(type.getId(), type);
        }

        // explosion types
        final int[] explosionTypeData = getExplosionTypes();
        for (int i = 0; i < explosionTypeData.length; i += ExplosionType.NUM_ATTRIBUTES) {
            final ExplosionType type = new ExplosionType(explosionTypeData, i);
            type.setName(getExplosionTypeName(type.getId()));
            explosionTypes.put(type.getId(), type);
        }

        // unitCommand types
        final int[] unitCommandTypeData = getUnitCommandTypes();
        for (int i = 0; i < unitCommandTypeData.length; i += UnitCommandType.NUM_ATTRIBUTES) {
            final UnitCommandType type = new UnitCommandType(unitCommandTypeData, i);
            type.setName(getUnitCommandTypeName(type.getId()));
            unitCommandTypes.put(type.getId(), type);
        }

        // order types
        final int[] orderTypeData = getOrderTypes();
        for (int i = 0; i < orderTypeData.length; i += OrderType.NUM_ATTRIBUTES) {
            final OrderType type = new OrderType(orderTypeData, i);
            type.setName(getOrderTypeName(type.getId()));
            orderTypes.put(type.getId(), type);
        }

        // event types
        for (final EventType type : EventType.values()) {
            eventTypes.put(type.getId(), type);
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
        allyIds.clear();
        enemies.clear();
        enemyIds.clear();
        players.clear();

        final int[] playerData = getPlayersData();
        for (int i = 0; i < playerData.length; i += Player.NUMBER_OF_ATTRIBUTES) {

            final String name = new String(getPlayerName(playerData[i]), CHARACTER_SET);
            final Player player = new Player(playerData, i, name);

            players.put(player.getId(), player);

            if (player.isSelf()) {
                self = player;

            } else if (player.isAlly()) {
                allies.add(player);
                allyIds.add(player.getId());

            } else if (player.isEnemy()) {
                enemies.add(player);
                enemyIds.add(player.getId());

            } else if (player.isNeutral()) {
                neutralPlayer = player;
            }
        }

        units.clear();
        playerUnits.clear();
        alliedUnits.clear();
        enemyUnits.clear();
        neutralUnits.clear();
        final int[] unitData = getAllUnitsData();

        for (int i = 0; i < unitData.length; i += Unit.NUMBER_OF_ATTRIBUTES) {
            final int id = unitData[i];
            final Unit unit = new Unit(id);
            unit.update(unitData, i, this);

            units.put(id, unit);
            if ((self != null) && (unit.getOwner().getId() == self.getId())) {
                playerUnits.add(unit);

            } else if (allyIds.contains(unit.getOwner().getId())) {
                alliedUnits.add(unit);

            } else if (enemyIds.contains(unit.getOwner().getId())) {
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

        final int[] unitData = getAllUnitsData();
        final HashSet<Integer> deadUnits = new HashSet<Integer>(units.keySet());

        playerUnits.clear();
        alliedUnits.clear();
        enemyUnits.clear();
        neutralUnits.clear();

        for (int i = 0; i < unitData.length; i += Unit.NUMBER_OF_ATTRIBUTES) {
            final int id = unitData[i];

            deadUnits.remove(id);

            Unit unit = units.get(id);
            if (unit == null) {
                unit = new Unit(id);
                units.put(id, unit);
            }

            unit.update(unitData, i, this);

            if (self != null) {
                if (unit.getOwner().getId() == self.getId()) {
                    playerUnits.add(unit);

                } else if (allyIds.contains(unit.getOwner().getId())) {
                    alliedUnits.add(unit);

                } else if (enemyIds.contains(unit.getOwner().getId())) {
                    enemyUnits.add(unit);

                } else {
                    neutralUnits.add(unit);
                }
            } else if (allyIds.contains(unit.getOwner().getId())) {
                alliedUnits.add(unit);

            } else if (enemyIds.contains(unit.getOwner().getId())) {
                enemyUnits.add(unit);

            } else {
                neutralUnits.add(unit);
            }
        }

        for (final Integer unitId : deadUnits) {
            units.get(unitId).setDestroyed();
            units.remove(unitId);
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

        final EventType event = eventTypes.get(eventTypeId);
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
}
