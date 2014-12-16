## JavaBroodWar

JBW is a Java library for interfacing with Starcraft: Brood War. 

JBW only reveals the visible parts of the game state and prohibits user input by default. This enables developers to write competitive non-cheating agents that must plan and operate under partial information conditions. 

This library is a fork of [JNIBWAPI](https://code.google.com/p/jnibwapi/) which uses JNI to interface with [BWAPI] (https://github.com/bwapi/bwapi) (specifically version [3.7.4](https://github.com/bwapi/bwapi/tree/c15a6ffdc0867c360fe4c59d8327b2007a96017b)). This library has received significant structural and API changes in order to reduce complexity. These changes are not compatible with JNIBWAPI. 

#### Requirements

  * Starcraft: Brood War [1.16.1 patch](http://ftp.blizzard.com/pub/broodwar/patches/PC/BW-1161.exe)
  * 32-bit Java Runtime Environment

#### Installation

  1. Download and install [Eclipse](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR1/eclipse-java-luna-SR1-win32.zip)
    * Note that you can use a 64-bit version of Eclipse, but the project will need a 32-bit version of Java to run.
  2. Install the Gradle IDE Pack plugin from the Eclipse marketplace.
  3. Import the project into Eclipse as a Gradle project.
  4. Copy the *bwapi-data* folder, from the *dependencies* directory, and paste it into the directory where *Starcraft* is installed.
  4. Create a new project in Eclipse and add the JBW project to the build path under the project's settings.
  5. Create a class that implements the [BroodwarListener](src/main/java/com/harbinger/jbw/BroodwarListener.java) interface or extend the adaptor.
  6. Provide the relevant methods with your agent's impeccable logic.
  7. Create a singple player ID.
    1. manually launch Starcraft Brood War
    2. Select *Single Player*
    3. Select the *Expansion* game type
    4. Specify an ID for the agent to use.
  
###### BWAPI.ini Notes

  * The *bwapi.ini* file copied in step 4 tells BWAPI how to configure the game.
  * The [auto-menu](https://code.google.com/p/bwapi/wiki/MenuAutomation) section of the file focuses on automating the setup of the match.
  * This allows you to specify the map, race, enemies and more. However, this file is shared between all agents using BWAPI.

###### Chaoslauncher Notes

  * JBW includes the Chaoslauncher application which is needed for injecting the BWAPI's dll files.
  * Running an agent requires that Starcraft be started through Chaoslauncher. This step can be automated by enabling the *Run Starcraft on Startup* option in the settings tag of Chaoslauncher. This option combined with the auto-menu can greatly simplify the launching of your agent.
  * Ensure that the *BWAPI Injector* and *W-Mode* plugins are enabled on the Plugins tab of Chaoslauncher.

#### Running the example SixPoolAgent

  1. Simply launch the *com.harbinger.jbw.example.SixPoolAgent*.
    * The *bwapi-data/bwapi.ini* file, copied over from step 4 in Installation, has the auto-menu enabled and will automatically configure the game as needed (i.e. Zerg race, Blood Bath map, one enemy, etc.). As mentioned under *BWAPI.ini Notes*, if the *bwapi.ini* file has been changed (e.g. race changed to Protoss) it may prevent the example agents from working properly.

#### Creating an Agent

  * Use the *com.harbinger.jbw.example.SixPoolAgent* class from the *test* directory as a concrete example.
  * At the moment it may be easier to write the code directly within the JBW project as dll and Chaoslauncher are required.
  
  1. Create a new project and add JBW to the classpath.
  2. Create a class that extends the *com.harbinger.jbw.BroodwarAgent* class.
  3. Override the *matchFrame* method and any other relevant methods from the class.
  4. Have the *main* method invoke the *launch* method of your agent.
