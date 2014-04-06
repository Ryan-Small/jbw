package com.harbinger.jbw;

import com.harbinger.jbw.Position.Positions;

/**
 * Serves as a callback interface for {@link Broodwar}, which will notify the implementing class of
 * game events.
 */
public interface BroodwarListener {

    /**
     * Invoked when a connection to the game has been made.
     */
    public void connected();

    /**
     * Invoked only once at the beginning of a match (i.e. frame 0)
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
     *            {@code true} if this agent won; {@code false} if this agent lost or if the game
     *            was a replay
     */
    public void matchEnd(final boolean isWinner);

    /**
     * Invoked when the state of a match is saved to a file.
     * 
     * @param fileName
     *            the name of the file the match was saved to
     */
    public void saveGame(final String fileName);

    /**
     * Invoked when the user hits a key in-game. This method is not invoked if user input has not
     * been {@link Broodwar#enableUserInput() enabled}.
     * 
     * <p>
     * This method enables the user to interact with the agent for various purposes (e.g. debugging,
     * controlling).
     * 
     * @param keyCode
     *            the key code of the key that was pressed by the user
     */
    public void keyPressed(final int keyCode);

    /**
     * Invoked when the user attempts to sends a message in-game. This method is not invoked if user
     * input has not been {@link Broodwar#enableUserInput() enabled}.
     * 
     * <p>
     * This method can enable the user to interact with the agent for various purposes (e.g.
     * debugging, controlling).
     * 
     * @param message
     *            the message sent by the user
     */
    public void sendText(final String message);

    /**
     * Invoked when the agent receives a message from another {@code Player}. Messages sent by the
     * user or the agent will never invoke this method.
     * 
     * <p>
     * This method enables other players or agents to interact with the agent for various purposes
     * (e.g. debugging, controlling).
     * 
     * @param message
     *            the message sent by another player or agent
     */
    public void receiveText(final String message);

    /**
     * Invoked when a {@code Player} leaves the game. All of their units are automatically given to
     * the neutral player with their color and alliance parameters preserved.
     * 
     * @param player
     *            the player that has left the game
     */
    public void playerLeft(final Player player);

    /**
     * Invoked when a {@code Player} has been dropped from the game. All of their units are
     * automatically given to the neutral player with their color and alliance parameters preserved.
     * 
     * @param player
     *            the player that has been dropped from the game
     */
    public void playerDropped(final Player player);

    /**
     * Invoked when a nuke has been launched. If the location is not visible, the provided location
     * will be {@link Positions#Unknown unknown}.
     * 
     * @param position
     *            the position the nuke is targeted for
     */
    public void nukeDetect(final Position position);

    /**
     * Invoked only when an accessible unit is created. This method will not be Invoked for enemy
     * units if perfect information is disabled, Zerg units morphing, and the construction of
     * structures over a Geyser.
     * 
     * @param unit
     *            the unit that has been created
     */
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
     *            the unit that has had its {@code UnitType} changed
     */
    public void unitMorph(final Unit unit);

    /**
     * Invoked every time a previously inaccessible {@code Unit} becomes accessible.
     * 
     * @param unitId
     *            the unit that is now accessible
     */
    public void unitDiscover(final Unit unit);

    /**
     * Invoked when a previously invisible {@code Unit} becomes visible. This method is not invoked
     * for units owned by this player.
     * 
     * @param unit
     *            the unit that is becoming visible
     */
    public void unitShow(final Unit unit);

    /**
     * Invoked when the state of a unit changes from incomplete to complete.
     * 
     * @param unit
     *            the unit that just finished training or being constructed
     */
    public void unitComplete(final Unit unit);

    /**
     * Invoked when a previously visible {@code Unit} becomes invisible. This method is not invoked
     * for units owned by this player.
     * 
     * @param unit
     *            the unit that is becoming invisible
     */
    public void unitHide(final Unit unit);

    /**
     * Invoked when a previously accessible {@code Unit} becomes inaccessible.
     * 
     * @param unit
     *            the unit that is now inaccessible
     */
    public void unitEvade(final Unit unit);

    /**
     * Invoked when an accessible unit is removed from the game either through death or other means
     * (e.g. mined out mineral patch, Drone becomes Extractor).
     * 
     * @param unit
     *            the unit that has been destroyed
     */
    public void unitDestroy(final Unit unit);

    /**
     * Invoked when a unit changes ownership (e.g. through Dark Archon's Mind Control, when an
     * assimilator is built on a Geyser).
     * 
     * @param unit
     *            the unit that has changed ownership
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
        public void nukeDetect(final Position position) {
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
