package com.harbinger.jbw;

enum EventType {
    MATCH_START,
    MATCH_END,
    MATCH_FRAME,
    MENU_FRAME,
    SEND_TEXT,
    RECEIVE_TEXT,
    PLAYER_LEFT,
    NUKE_DETECT,
    UNIT_DISCOVER,
    UNIT_EVADE,
    UNIT_SHOW,
    UNIT_HIDE,
    UNIT_CREATE,
    UNIT_DESTROY,
    UNIT_MORPH,
    UNIT_RENEGADE,
    SAVE_GAME,
    UNIT_COMPLETE,
    PLAYER_DROPPED,
    NONE;

    public static EventType getEventType(final int id) {
        return EventType.values()[id];
    }
}
