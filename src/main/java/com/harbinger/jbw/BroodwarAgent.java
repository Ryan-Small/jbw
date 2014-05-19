package com.harbinger.jbw;

/**
 * Provides functionality for launching an agent on a separate thread.
 *
 * <p>
 * Most agents can extend this class and override methods as needed.
 */
public abstract class BroodwarAgent extends BroodwarListener.Adaptor {

    /**
     * Provides functionality for interacting with the currently connected Broodwar game.
     *
     * <p>
     * Using this object before a match {@link #matchStart() starts} or after a match
     * {@link #matchEnd(boolean) ends} can produce undefined behavior and crash the agent.
     */
    protected final Broodwar broodwar;

    /**
     * Constructs the agent.
     *
     * <p>
     * The agent will still need to be {@link #start() started} before a match.
     */
    public BroodwarAgent() {
        broodwar = new Broodwar(this);
    }

    /**
     * Starts the agent on a separate thread.
     *
     * <p>
     * This method needs to be invoked prior to starting a match.
     *
     * <p>
     * Each invocation will spawn a new Thread.
     *
     * @return the thread that the agent is running on
     */
    protected Thread start() {
        final Thread agentThread = new Thread(new Runnable() {

            @Override
            public void run() {
                broodwar.start();
            }
        });
        agentThread.start();
        return agentThread;
    }
}
