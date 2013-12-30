package jnibwapi;

/**
 * Interface for BWAPI callback methods;
 * 
 * For BWAPI specific events see: http://code.google.com/p/bwapi/wiki/AIModule
 */
public interface BWAPIEventListener {

    /** connection to BWAPI established */
    public void connected();

    /** game has just started, game settings can be turned on here */
    public void matchStart();

    /** perform AI logic here */
    public void matchFrame();

    /** game has just terminated */
    public void matchEnd(boolean winner);

    /** keyPressed from within StarCraft */
    public void keyPressed(int keyCode);

    // BWAPI callbacks
    public void sendText(String text);

    public void receiveText(String text);

    public void playerLeft(int playerID);

    public void nukeDetect(int x, int y);

    public void nukeDetect();

    public void unitDiscover(int unitID);

    public void unitEvade(int unitID);

    public void unitShow(int unitID);

    public void unitHide(int unitID);

    public void unitCreate(int unitID);

    public void unitDestroy(int unitID);

    public void unitMorph(int unitID);

    public void unitRenegade(int unitID);

    public void saveGame(String gameName);

    public void unitComplete(int unitID);

    public void playerDropped(int playerID);

    /**
     *
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
