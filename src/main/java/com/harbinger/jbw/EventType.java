package com.harbinger.jbw;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
    // TriggerAction,
    PlayerDropped, // Will be removed in later versions of BWAPI
    None;

    public int getId() {
        return ordinal();
    }

    public static EventType getEventType(final int id) {
        return EventType.values()[id];
    }

    public static Collection<EventType> getAllEventTypes() {
        return Collections.unmodifiableCollection(Arrays.asList(EventType.values()));
    }
}
