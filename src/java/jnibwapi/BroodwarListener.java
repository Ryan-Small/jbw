package jnibwapi;

import jnibwapi.model.Location;
import jnibwapi.model.Player;
import jnibwapi.model.Unit;

/**
 * Serves as a callback interface for {@link Broodwar}, which will notify the
 * implementing class of game events.
 */
public interface BroodwarListener {

    /**
     * Invoked when a connection to the game has been made.
     */
    public void connected();

    /**
     * Invoked only once at the beginning of a match (i.e. frame 0).
     * 
     * <p>
     * The agent can do any initializations here.
     */
    public void matchStart();

    /**
     * Invoked once for every logical frame in the match.
     */
    public void matchFrame();

    /**
     * Invoked only once at the end of a match.
     * 
     * @param isWinner
     *            {@code true} if this agent won; {@code false} if this agent
     *            lost or if the game was a replay
     */
    public void matchEnd(final boolean isWinner);

    /**
     * Invoked when the state of a match is saved to a file.
     * 
     * @param fileName
     *            name of the file the match was saved to
     */
    public void saveGame(final String fileName);

    /**
     * Invoked when the user hits a key in-game. This method is not invoked if
     * user input has not been {@link Broodwar#enableUserInput() enabled}.
     * 
     * <p>
     * This method enables the user to interact with the agent for various
     * purposes (e.g. debugging, controlling).
     * 
     * @param keyCode
     *            key pressed by the user
     */
    public void keyPressed(final int keyCode);

    /**
     * Invoked when the user attempts to sends a message in-game. This method is
     * not invoked if user input has not been {@link Broodwar#enableUserInput()
     * enabled}.
     * 
     * <p>
     * This method can enable the user to interact with the agent for various
     * purposes (e.g. debugging, controlling).
     * 
     * @param message
     *            message sent by the user
     */
    public void sendText(final String message);

    /**
     * Invoked when the agent receives a message from another {@code Player}.
     * Messages sent by the user or the agent will never invoke this method.
     * 
     * <p>
     * This method enables other players or agents to interact with the agent
     * for various purposes (e.g. debugging, controlling).
     * 
     * @param message
     *            message sent by another player or agent
     */
    public void receiveText(final String message);

    /**
     * Invoked when a {@code Player} leaves the game. All of their units are
     * automatically given to the neutral player with their color and alliance
     * parameters preserved.
     * 
     * @param player
     *            player that has left the game
     */
    public void playerLeft(final Player player);

    /**
     * Invoked when a {@code Player} has been dropped from the game. All of
     * their units are automatically given to the neutral player with their
     * color and alliance parameters preserved.
     * 
     * @param player
     *            player that has been dropped from the game
     */
    public void playerDropped(final Player player);

    /**
     * Invoked when a nuke has been launched and the location is visible.
     * 
     * @param location
     *            location the nuke is targeted for
     */
    public void nukeDetect(final Location location);

    /**
     * Invoked when a nuke has been launched and the location is not visible.
     */
    public void nukeDetect();

    /**
     * Invoked only when an accessible unit is created. This method will not be
     * Invoked for enemy units if perfect information is disabled, Zerg units
     * morphing, and the construction of structures over a Geyser.
     * 
     * @param unit
     *            unit that has been created
     */
    // TODO: What if the enemy unit is visible?
    public void unitCreate(final Unit unit);

    /**
     * Invoked when an accessible {@code Unit} changes its {@code UnitType}.
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
     * @param unit
     *            unit that has had its {@code UnitType} changed
     */
    public void unitMorph(final Unit unit);

    /**
     * Invoked every time a previously inaccessible {@code Unit} becomes
     * accessible.
     * 
     * @param unitId
     *            unit that is now accessible
     */
    public void unitDiscover(final Unit unit);

    /**
     * Invoked when a previously invisible {@code Unit} becomes visible.
     * 
     * @param unit
     *            unit that is becoming visible
     */
    // TODO: What about units that are owned by the agent?
    public void unitShow(final Unit unit);

    /**
     * Invoked when the state of a unit changes from incomplete to complete.
     * 
     * @param unit
     *            unit that just finished training or being constructed
     */
    public void unitComplete(final Unit unit);

    /**
     * Invoked when a previously visible {@code Unit} becomes invisible.
     * 
     * @param unit
     *            unit that is becoming invisible
     */
    // TODO: What about units that are owned by the agent?
    public void unitHide(final Unit unit);

    /**
     * Invoked when a previously accessible {@code Unit} becomes inaccessible.
     * 
     * @param unit
     *            unit that is now inaccessible
     */
    public void unitEvade(final Unit unit);

    /**
     * Invoked when an accessible unit is removed from the game either through
     * death or other means (e.g. mined out mineral patch, Drone becomes
     * Extractor).
     * 
     * @param unit
     *            unit that has been destroyed
     */
    public void unitDestroy(final Unit unit);

    /**
     * Invoked when a unit changes ownership (e.g. through Dark Archon's Mind
     * Control, Geyser when an assimilator is built on it).
     * 
     * @param unit
     *            unit that has changed ownership
     */
    public void unitRenegade(final Unit unit);

    /**
     * An adaptor for {@code BroodwarListener}.
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
        public void playerLeft(final Player player) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void nukeDetect(final Location location) {
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
        public void unitDiscover(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitEvade(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitShow(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitHide(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitCreate(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitDestroy(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitMorph(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unitRenegade(final Unit unit) {
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
        public void unitComplete(final Unit unit) {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void playerDropped(final Player player) {
        }
    }
}
