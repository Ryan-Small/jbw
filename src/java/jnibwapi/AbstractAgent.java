/*
 * Copyright 2013 IDEXX Laboratories, Inc. All rights reserved.
 * IDEXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package jnibwapi;

/**
 * An abstract agent that provides functionality for starting an agent.
 */
public abstract class AbstractAgent extends BWAPIEventListener.Adaptor {

    /**
     * Allows the agent to interact with the game.
     * 
     * <p>
     * Using this object before {@link #matchStart()} is called can produce undefined behavior and
     * crash the agent.
     */
    protected final JNIBWAPI game;

    /**
     * Constructs the agent.
     * 
     * @param enableBwta
     *            {@code true} if BWTA should be enabled (may cause the game to freeze for a small
     *            duration); {@code false} otherwise
     */
    public AbstractAgent(final boolean enableBwta) {
        game = new JNIBWAPI(this, enableBwta);
    }

    /**
     * Starts the agent by connecting it to BWAPI.
     */
    protected void start() {
        final Runnable bwapiRunnable = new Runnable() {

            @Override
            public void run() {
                game.start();
            }
        };
        final Thread bwapiThread = new Thread(bwapiRunnable);
        bwapiThread.start();
    }
}
