package com.harbinger.jbw;

import java.io.*;

/**
 * Provides functionality for launching an agent on a separate thread.
 *
 * <p>
 * Agents can extend this class and override methods as needed.
 */
public abstract class BroodwarAgent extends BroodwarListener.Adaptor {

    /**
     * Provides functionality for interacting with the currently connected Broodwar game.
     *
     * <p>
     * Using this object before a match {@link #matchStart() starts} or after a match
     * {@link #matchEnd(boolean) ends} can produce undefined behavior and crash the agent.
     */
    protected Broodwar broodwar;

    /**
     * Constructs the agent.
     *
     * <p>
     * The agent will still need to be {@link #connect() connected} before a match begins.
     */
    public BroodwarAgent() {
        broodwar = new Broodwar(this);
    }

    /**
     * Starts Broodwar and connects the agent.
     *
     * <p>
     * This implementation uses some system specific commands and may any may not work on some
     * systems and may need to be overridden.
     *
     * @return the Thread that this agent is running on
     *
     * @see BroodwarAgent#launchChaosLauncher()
     * @see BroodwarAgent#isBroodwarRunning()
     */
    protected Thread launch() {
        final Thread agentThread = connect();

        // we need to kill chaoslauncher once broodwar is
        // running so that we can restart the game later
        final Process chaosLauncherProcess = launchChaosLauncher();
        while (!isBroodwarRunning()) {
            try {
                Thread.sleep(500);
            } catch (final InterruptedException ignore) {
                // just continue to wait
            }
        }
        chaosLauncherProcess.destroy();

        return agentThread;
    }

    /**
     * Starts the agent on a separate thread. Broodwar will need to be launched separately.
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
     * Launches the ChaosLauncher application which will then automatically launch Broodwar.
     *
     * <p>
     * Chaoslauncher needs to be configured to automatically launch Broodwar. This can be done by
     * checking the box "Run Starcraft on Startup" in the Settings tab of Chaoslauncher. This
     * implementation may not work on some systems and may need to be overridden.
     *
     * @return the Process that launched Chaoslauncher
     */
    protected Process launchChaosLauncher() {
        try {
            final File workingDirectory = new File("dependencies/Chaoslauncher");
            final String executablePath = workingDirectory.getAbsolutePath() + "/Chaoslauncher.exe";
            return Runtime.getRuntime().exec(executablePath, new String[0], workingDirectory);
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Indicates if Broodwar is running.
     *
     * <p>
     * This implementation uses tasklist, a Windows application, to check if "StarCraft.exe" is
     * listed as a running process. This implementation may not work on some systems and may need to
     * be overridden.
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
            final StringBuilder pidInfo = new StringBuilder();
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }

            input.close();
            return pidInfo.toString().contains("StarCraft.exe");

        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Terminates the Broodwar game.
     *
     * <p>
     * This implementation uses TASKKILL, a Windows application, to halt "StarCraft.exe". This may
     * result in phantom icons of Chaoslauncher hanging out in the System tray. This is due to
     * Chaoslauncher being rudely killed. This implementation may not work (or be desired) on some
     * systems and may need to be overridden.
     */
    protected void terminateBroodwar() {
        try {
            final String[] args = new String[] { "TASKKILL", "/IM", "StarCraft.exe" };
            final Process process = Runtime.getRuntime().exec(args);
            process.waitFor();
        } catch (final InterruptedException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
