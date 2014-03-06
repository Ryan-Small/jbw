package jnibwapi.types;

/**
 * http://code.google.com/p/bwapi/wiki/Event
 */
public enum EventType {
    MatchStart,
    MatchEnd,
    MatchFrame,
    MenuFrame,
    SendText,
    ReceiveText,
    PlayerLeft,
    NukeDetect,
    UnitDiscover,
    UnitEvade,
    UnitShow,
    UnitHide,
    UnitCreate,
    UnitDestroy,
    UnitMorph,
    UnitRenegade,
    SaveGame,
    UnitComplete,
    PlayerDropped, // Will be removed in later versions of BWAPI
    None;

    public int getId() {
        return ordinal();
    }
}
