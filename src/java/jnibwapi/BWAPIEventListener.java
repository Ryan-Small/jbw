package jnibwapi;

/**
 * Interface for BWAPI callback methods;
 * 
 * For BWAPI specific events see: http://code.google.com/p/bwapi/wiki/AIModule
 */
public interface BWAPIEventListener {

    /**
     * Called when the agent has connected to BWAPI.
     */
    public void connected();

    /**
     * Called only once at the beginning of a game. Using the {@code JNIBWAPI} interface before this
     * function is called can produce undefined behavior and crash your agent.
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
    public void matchEnd(boolean isWinner);

    /** keyPressed from within StarCraft */
    /**
     * 
     * 
     * @param keyCode
     */
    public void keyPressed(int keyCode);

    /**
     * Called when the user attempts to sends a message in-game. This method is not invoked if
     * {@link JNIBWAPI#enableUserInput()} was not invoked prior to the message being sent.
     * 
     * <p>
     * Users can send messages to the agent for various purposes (i.e. debugging, controlling, etc).
     * 
     * @param message
     *            the message sent by the user
     */
    public void sendText(String message);

    /**
     * Called when the agent receives a message from another {@code Player}. Messages sent by the
     * agent itself will never invoke this method.
     * 
     * <p>
     * Other players or agents can send messages to the agent for various purposes (i.e. information
     * sharing).
     * 
     * @param message
     *            the message sent by another player or agent
     */
    public void receiveText(String message);

    /**
     * Called when a {@code Player} leaves the game. All of their units are automatically given to
     * the neutral player with their color and alliance parameters preserved.
     * 
     * @param playerId
     *            id of the player that left the game
     */
    public void playerLeft(int playerId);

    /**
     * Called when a nuke has been launched and the location is known.
     * 
     * @param x
     *            x coordinate of the nuke
     * 
     * @param y
     *            y coordinate of the nuke
     */
    public void nukeDetect(int x, int y);

    /**
     * Called when a nuke has been launched and the location is unknown.
     */
    public void nukeDetect();

    /**
     * Called when a {@code Unit} becomes accessible.
     * 
     * @param unitId
     *            id of the unit that is now accessible
     */
    public void unitDiscover(int unitId);

    /**
     * Called when a {@code Unit} becomes inaccessible.
     * 
     * @param unitId
     *            id of the unit that is now inaccessible
     */
    public void unitEvade(int unitId);

    /**
     * Called when a previously invisible {@code Unit} becomes visible.
     * 
     * @param unitId
     *            id of the unit that is becoming visible
     */
    public void unitShow(int unitId);

    /**
     * Called when a previously visible {@code Unit} becomes invisible.
     * 
     * @param unitId
     *            id of the unit that is becoming invisible
     */
    public void unitHide(int unitId);

    /**
     * Called when a unit is created. If the unit is not accessible at the time of creation, (i.e.
     * if the unit is invisible and perfect information wasn't enabled), then this method will not
     * be called. If the unit is visible at the time of creation, {@link #unitShow(int)} will also
     * be called.
     * 
     * <p>
     * Due to the internal workings of Broodwar, this method excludes Zerg units morphing and the
     * construction of structures over a Geyser.
     * 
     * @param unitId
     *            id of the unit that has been created
     */
    public void unitCreate(int unitId);

    /**
     * Called when a unit is removed from the game either through death or other means. If the unit
     * is not accessible at the time of destruction, (i.e. if the unit is invisible and perfect
     * information wasn't enabled), then this method will not be called. If the unit was visible at
     * the time of destruction, {@link #unitHide(int)} will also be called.
     * 
     * <p>
     * Due to the internal workings of Broodwar, when a Drone morphs into an Extractor, the Drone is
     * removed from the game and the Geyser morphs into and Extractor.
     * 
     * @param unitId
     *            id of the unit that has been destroyed
     */
    public void unitDestroy(int unitId);

    /**
     * Called when a {@code Unit} changes its {@code UnitType}. This is not called when a
     * {@code Unit} changes to or from {@code UnitTypes.Unknown}.
     * 
     * <p>
     * For example:
     * <ul>
     * <li>Drone transforms into a Hatchery</li>
     * <li>SiegeTank using SiegeMode</li>
     * <li>Geyser becomes Refinery</li>
     * </ul>
     * 
     * @param unitId
     *            id of the unit that has had its UnitType changed
     */
    public void unitMorph(int unitId);

    /**
     * Called when a unit changes ownership.
     * 
     * @param unitId
     *            id of the unit that has changed ownership
     */
    public void unitRenegade(int unitId);

    /**
     * Called when the state of the Broodwar game is saved to a file.
     * 
     * @param gameName
     *            name of the file the game was saved as
     */
    public void saveGame(String gameName);

    /**
     * Called when the state of a unit changes from incomplete to complete.
     * 
     * @param unitID
     *            id of the unit that just finished training or being constructed
     */
    public void unitComplete(int unitID);

    public void playerDropped(int playerID);

    /**
     * An adaptor for the {@code BWAPIEventListener}.
     */
    public class Adaptor implements BWAPIEventListener {

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
        public void matchEnd(final boolean winner) {
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
        public void sendText(final String text) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void receiveText(final String text) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void playerLeft(final int playerID) {
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
        public void unitDiscover(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitEvade(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitShow(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitHide(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitCreate(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitDestroy(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitMorph(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitRenegade(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void saveGame(final String gameName) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitComplete(final int unitID) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void playerDropped(final int playerID) {
        }
    }
}
