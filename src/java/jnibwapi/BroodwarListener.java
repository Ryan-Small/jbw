package jnibwapi;

/**
 * This is the interface that an agent needs to implement to be notified of game events.
 * 
 * Create -> Discover -> Show -> Complete -> Hide -> Evade -> Destroy
 */
public interface BroodwarListener {

    /**
     * Called when the agent has connected to BWAPI.
     */
    public void connected();

    /**
     * Called only once at the beginning of a game (frame 0).
     * 
     * <p>
     * The agent can do any initializes here.
     */
    public void matchStart();

    /**
     * Called once for every logical frame in Broodwar.
     * 
     * <p>
     * The agent's primary logic will take place here.
     */
    public void matchFrame();

    /**
     * Called only once at the end of a game.
     * 
     * @param isWinner
     *            {@code true} if the agent won; {@false} if the agent lost or if the game
     *            was a replay
     */
    public void matchEnd(final boolean isWinner);

    /**
     * Called when the state of the Broodwar game is saved to a file.
     * 
     * @param fileName
     *            name of the file the game was saved as
     */
    public void saveGame(final String fileName);

    /**
     * Called when the user hits a key in-game. This method is not invoked if user input has not
     * been {@link Broodwar#enableUserInput() enabled}.
     * 
     * <p>
     * This method enables the user to interact with the agent for various purposes (e.g. debugging,
     * controlling).
     * 
     * @param keyCode
     *            key pressed by the user
     */
    public void keyPressed(final int keyCode);

    /**
     * Called when the user attempts to sends a message in-game. This method is not invoked if user
     * input has not been {@link Broodwar#enableUserInput() enabled}.
     * 
     * <p>
     * This method enables the user to interact with the agent for various purposes (e.g. debugging,
     * controlling).
     * 
     * @param message
     *            message sent by the user
     */
    public void sendText(final String message);

    /**
     * Called when the agent receives a message from another {@code Player}. Messages sent by the
     * agent itself will never invoke this method.
     * 
     * <p>
     * This method enables other players or agent to interact with the agent for various purposes
     * (e.g. debugging, controlling).
     * 
     * @param message
     *            message sent by another player or agent
     */
    public void receiveText(final String message);

    /**
     * Called when a {@code Player} leaves the game. All of their units are automatically given to
     * the neutral player with their color and alliance parameters preserved.
     * 
     * @param playerId
     *            id of the player that has sleft the game
     */
    public void playerLeft(final int playerId);

    /**
     * Called when a {@code PLayer} has been dropped from the game. All of their units are
     * automatically given to the neutral player with their color and alliance parameters preserved.
     * 
     * @param playerId
     *            id of the player that has been dropped from the game
     */
    public void playerDropped(final int playerId);

    /**
     * Called when a nuke has been launched and the location is visible.
     * 
     * @param x
     *            x coordinate of the nuke
     * 
     * @param y
     *            y coordinate of the nuke
     */
    public void nukeDetect(final int x, final int y);

    /**
     * Called when a nuke has been launched and the location is not visible.
     */
    public void nukeDetect();

    /**
     * Called only when an accessible unit is created. This method will not be called for enemy
     * units if perfect information is disabled, Zerg units morphing, and the construction of
     * structures over a Geyser.
     * 
     * @param unitId
     *            id of the unit that has been created
     */
    public void unitCreate(final int unitId);

    /**
     * Called when an accessible {@code Unit} changes its {@code UnitType}.
     * 
     * <p>
     * Examples of morphing events:
     * <ul>
     * <li>Larva becomes an egg</li>
     * <li>Egg becomes Drone</li>
     * <li>Drone becomes a Hatchery</li>
     * <li>Geyser becomes Refinery</li>
     * <li>SiegeTank using SiegeMode</li>
     * </ul>
     * 
     * @param unitId
     *            id of the unit that has had its {@code UnitType} changed
     */
    public void unitMorph(final int unitId);

    /**
     * Called every time a previously inaccessible {@code Unit} becomes accessible.
     * 
     * @param unitId
     *            id of the unit that is now accessible
     */
    public void unitDiscover(final int unitId);

    /**
     * Called when a previously invisible {@code Unit} becomes visible.
     * 
     * @param unitId
     *            id of the unit that is becoming visible
     */
    public void unitShow(final int unitId);

    /**
     * Called when the state of a unit changes from incomplete to complete.
     * 
     * @param unitId
     *            id of the unit that just finished training or being constructed
     */
    public void unitComplete(final int unitId);

    /**
     * Called when a previously visible {@code Unit} becomes invisible.
     * 
     * @param unitId
     *            id of the unit that is becoming invisible
     */
    public void unitHide(final int unitId);

    /**
     * Called when a previously accessible {@code Unit} becomes inaccessible.
     * 
     * @param unitId
     *            id of the unit that is now inaccessible
     */
    public void unitEvade(final int unitId);

    /**
     * Called when an accessible unit is removed from the game either through death or other means
     * (e.g. mined out mineral patch, Drone becomes Extractor).
     * 
     * @param unitId
     *            id of the unit that has been destroyed
     */
    public void unitDestroy(final int unitId);

    /**
     * Called when a unit changes ownership (e.g. through Dark Archon's Mind Control, Geyser when an
     * assimilator is built on it).
     * 
     * @param unitId
     *            id of the unit that has changed ownership
     */
    public void unitRenegade(final int unitId);

    /**
     * An adaptor for the {@code BWAPIEventListener}.
     */
    public class Adaptor implements BroodwarListener {

        /**
         * {@inheritDoc}
         */
        @Override
        public void connected() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void matchStart() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void matchFrame() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void matchEnd(final boolean isWinner) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void keyPressed(final int keyCode) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void sendText(final String message) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void receiveText(final String message) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void playerLeft(final int playerId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void nukeDetect(final int x, final int y) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void nukeDetect() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitDiscover(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitEvade(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitShow(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitHide(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitCreate(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitDestroy(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitMorph(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitRenegade(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void saveGame(final String fileName) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitComplete(final int unitId) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void playerDropped(final int playerId) {
        }
    }
}
