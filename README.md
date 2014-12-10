## JavaBroodWar

JBW is a Java library for interfacing with Starcraft: Brood War. 

JBW only reveals the visible parts of the game state and prohibits user input by default. This enables developers to write competitive non-cheating agents that must plan and operate under partial information conditions. 

This library is a fork of [JNIBWAPI](https://code.google.com/p/jnibwapi/) which uses JNI to interface with ([BWAPI] (https://github.com/bwapi/bwapi)). This library has received significant structural and API changes in order to reduce complexity. These changes make are not compatible with JNIBWAPI.

#### Requirements

  * Starcraft: Brood War [1.16.1 patch](http://ftp.blizzard.com/pub/broodwar/patches/PC/BW-1161.exe)
  * 32-bit Java Runtime Environment

#### Installation


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
