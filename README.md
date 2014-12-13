## JavaBroodWar

JBW is a Java library for interfacing with Starcraft: Brood War. 

JBW only reveals the visible parts of the game state and prohibits user input by default. This enables developers to write competitive non-cheating agents that must plan and operate under partial information conditions. 

This library is a fork of [JNIBWAPI](https://code.google.com/p/jnibwapi/) which uses JNI to interface with ([BWAPI] (https://github.com/bwapi/bwapi)). This library has received significant structural and API changes in order to reduce complexity. These changes make are not compatible with JNIBWAPI.

#### Requirements

  * Starcraft: Brood War [1.16.1 patch](http://ftp.blizzard.com/pub/broodwar/patches/PC/BW-1161.exe)
  * 32-bit Java Runtime Environment

#### Installation

  1. Download and install [Eclipse](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/luna/SR1/eclipse-java-luna-SR1-win32.zip)
    * Note that you can use a 64-bit version of Eclipse, but the project will need a 32-bit version of Java to run.
  2. Install the Gradle IDE Pack plugin from the Eclipse marketplace.
  3. Import the project into Eclipse as a Gradle project.
  4. Create a new project in Eclipse and add the JBW project to the build path under the project's settings.
  5. Create a class that implements the [BroodwarListener](src/main/java/com/harbinger/jbw/BroodwarListener.java) interface or extend the adaptor.
  6. Implement/override the relevant methods with your impeccable logic.

#### Getting Started
  - Ensure that a id has been created in single player mode.
  - Move dependencies/bwapi-data to the Starcraft installation directory.
  - Launch the SixPoolAgent
  - Ensure that the BWAPI Injector and W-Mode plugin are selected within Chaoslauncher.
    - Optionally, enable the "Run Starcraft on Startup" option under Chaoslauncher settings.

  - Select Single Player
  - Select the Broodwar expansion
  - Create a new character name
  - Select Play Custom
  - Select [Up One Level]
  - Select (4)Blood Bath.scm
  - Select the Zerg race for the agent
  - Select Ok
