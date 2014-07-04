package com.harbinger.jbw;

import java.io.*;

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
    protected Thread connect() {
        final Thread agentThread = new Thread(() -> broodwar.connect());
        agentThread.start();
        return agentThread;
    }

    /**
     * Starts Broodwar and connects this agent.
     *
     * <p>
     * It should be noted that this implementation uses some system specific commands and setup.
     *
     * @return the Thread that this agent is running on
     *
     * @see BroodwarAgent#launchChaosLauncher()
     * @see BroodwarAgent#isBroodwarRunning()
     */
    protected Thread launchWithBroodwar() {
        final Thread agentThread = connect();

        final Process chaosLauncherProcess = launchChaosLauncher();
        while (!isBroodwarRunning()) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException ignore) {
                // just continue to wait
            }
        }
        chaosLauncherProcess.destroy();
        return agentThread;
    }

    /**
     * Launches the Broodwar game.
     *
     * <p>
     * It should be noted that this implementation launches Broodwar through Chaoslauncher and
     * depends on the a CHAOSLAUNCHER_HOME environment variable. Additionally, Chaoslauncher needs
     * to be configured to automatically launch Broodwar. This can be done by checking the box
     * "Run Starcraft on Startup" in the Settings tab of Chaoslauncher. This implementation may not
     * work on some systems and may need to be overridden.
     *
     * <p>
     *
     *
     * @return the Process used to launch Chaoslauncher
     */
    protected Process launchChaosLauncher() {
        try {
            final String chaoslauncherEnv = "CHAOSLAUNCHER_HOME";
            final String workingDirectoryPath = System.getenv(chaoslauncherEnv);
            if (workingDirectoryPath != null) {
                final File workingDirectory = new File(workingDirectoryPath);
                final String executablePath = workingDirectoryPath + "\\Chaoslauncher.exe";
                return Runtime.getRuntime().exec(executablePath, new String[0], workingDirectory);
            } else {
                final String msg = chaoslauncherEnv + " environment variable has not been set";
                throw new RuntimeException(msg);
            }
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Indicates if Broodwar is running.
     *
     * <p>
     * It should be noted that this implementation uses tasklist, a Windows application, to check if
     * "StarCraft.exe" is listed as a running process. This implementation may not work on some
     * systems and may need to be overridden.
     *
     * @return true if Broodwar is running; false otherwise
     */
    protected boolean isBroodwarRunning() {
        try {
            final String tasklist = System.getenv("windir") + "\\system32\\tasklist.exe";
            final Process tasklistProcess = Runtime.getRuntime().exec(tasklist);

            final InputStream inputStream = tasklistProcess.getInputStream();
            final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            final BufferedReader input = new BufferedReader(inputStreamReader);

            String line;
            String pidInfo = "";
            while ((line = input.readLine()) != null) {
                pidInfo += line;
            }

            input.close();
            return pidInfo.contains("StarCraft.exe");

        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Terminates the Broodwar game.
     *
     * <p>
     * It should be noted that this implementation uses TASKKILL, a Windows application, to halt
     * "StarCraft.exe". This may result in phantom icons of Chaoslauncher hanging out in the System
     * tray. This is due to Chaoslauncher being rudely killed. This implementation may not work (or
     * be desired) on some systems and may need to be overridden.
     */
    protected void terminateBroodwar() {
        try {
            Runtime.getRuntime().exec(new String[] { "TASKKILL", "/IM", "StarCraft.exe" });
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
