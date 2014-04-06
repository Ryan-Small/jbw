package com.harbinger.jbw;

public enum BWColor {
    Red(111),
    Blue(165),
    Teal(159),
    Purple(164),
    Orange(179),
    Brown(19),
    White(255),
    Yellow(135),
    Green(117),
    Cyan(128),
    Black(0),
    Grey(74);

    private int id;

    private BWColor(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
