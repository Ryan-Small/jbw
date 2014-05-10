package com.harbinger.jbw;

/**
 * Serves as the base class for an agent by providing the functionality for launching the agent on a
 * separate thread.
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
     * Constructs a new agent.
     *
     * <p>
     * The agent will still need to be {@link #start() started} in order for it to be notified of
     * game {@link BroodwarListener events}.
     */
    public BroodwarAgent() {
        broodwar = new Broodwar(this);
    }

    /**
     * Connects the agent to Broodwar on a separate thread.
     *
     * <p>
     * This method needs to be invoked prior to starting a Broodwar match.
     *
     * @return the thread that the agent is running on
     *
     * @throws IllegalThreadStateException
     *             if the thread was already started
     */
    protected Thread start() {
        final Runnable agentRunnable = new AgentRunnable();
        final Thread agentThread = new Thread(agentRunnable);
        agentThread.start();
        return agentThread;
    }

    private class AgentRunnable implements Runnable {

        @Override
        public void run() {
            broodwar.start();
        }
    }
}
