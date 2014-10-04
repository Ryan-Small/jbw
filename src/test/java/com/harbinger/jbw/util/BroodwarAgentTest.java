package com.harbinger.jbw.util;

import com.harbinger.jbw.*;

/**
 * Provides functionality for launching an agent as a test.
 *
 * <p>
 * Automated test can extend this class and override methods as needed. The tests will need to
 * invoke {@link #launchAndWait()} instead of {@link #launch()}. Any Errors that are then thrown
 * during a {@link BroodwarListener callback} will terminate Broodwar and be rethrown on this
 * Thread.
 */
public class BroodwarAgentTest extends BroodwarAgent {

    private Error error;

    /**
     * Constructs the agent.
     */
    public BroodwarAgentTest() {
        broodwar = new Broodwar(new BroodwarListenerFacade());
    }

    /**
     * Starts Broodwar and connects the agent. This method should be used instead of
     * {@link #launch()} when running automated tests.
     *
     * <p>
     * Any Errors that are thrown during a {@link BroodwarListener callback} will terminate Broodwar
     * and be rethrown on this Thread.
     *
     * <p>
     * This implementation uses some system specific commands and may any may not work on some
     * systems and may need to be overridden.
     *
     * @see BroodwarAgent#launchChaosLauncher()
     * @see BroodwarAgent#isBroodwarRunning()
     */
    public void launchAndWait() {
        final Thread agentThread = launch();
        while (agentThread.isAlive()) {
            try {
                agentThread.join();
            } catch (final InterruptedException ignore) {
                // the thread is not exposed and referenced only locally
                // there should be no reason for it to be interrupted
                // so just rejoin the thread
            }
        }

        if (error != null) {
            throw error;
        }
    }

    /**
     * Helper class that wraps all of the listener calls in a try-catch statement in order to catch
     * any thrown errors and preserve them. This is primarily used to help with JUnit tests
     */
    private class BroodwarListenerFacade implements BroodwarListener {

        @Override
        public void connected() {
            try {
                BroodwarAgentTest.this.connected();
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void matchStart() {
            try {
                BroodwarAgentTest.this.matchStart();
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void matchFrame() {
            try {
                BroodwarAgentTest.this.matchFrame();
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void matchEnd(final boolean isWinner) {
            try {
                BroodwarAgentTest.this.matchEnd(isWinner);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void keyPressed(final int keyCode) {
            try {
                BroodwarAgentTest.this.keyPressed(keyCode);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void sendText(final String message) {
            try {
                BroodwarAgentTest.this.sendText(message);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void receiveText(final String message) {
            try {
                BroodwarAgentTest.this.receiveText(message);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void playerLeft(final Player player) {
            try {
                BroodwarAgentTest.this.playerLeft(player);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void nukeDetect(final Position position) {
            try {
                BroodwarAgentTest.this.nukeDetect(position);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitDiscover(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitDiscover(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitEvade(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitEvade(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitShow(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitShow(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitHide(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitHide(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitCreate(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitCreate(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitDestroy(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitDestroy(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitMorph(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitMorph(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitRenegade(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitRenegade(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void saveGame(final String fileName) {
            try {
                BroodwarAgentTest.this.saveGame(fileName);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void unitComplete(final Unit unit) {
            try {
                BroodwarAgentTest.this.unitComplete(unit);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }

        @Override
        public void playerDropped(final Player player) {
            try {
                BroodwarAgentTest.this.playerDropped(player);
            } catch (final Error error) {
                BroodwarAgentTest.this.error = error;
                terminateBroodwar();
            }
        }
    }
}
