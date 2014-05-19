package com.harbinger.jbw;

public enum BWColor {
    Red(111, "\u0008"),

    Blue(165, "\u000E"),
    Teal(159, "\u000F"),
    Purple(164, "\u0010"),
    Orange(179, "\u0011"),
    Brown(19, "\u0015"),
    White(255, "\u0004"),
    Yellow(135, "\u0017"),
    Green(117, "\u0007"),
    Cyan(128, "\u001F"),
    Black(0, "\u0005"),
    Grey(74, "\u0016");

    private int id;
    private String controlCharacters;

    private BWColor(final int id, final String escape) {
        this.id = id;
        this.controlCharacters = escape;
    }

    public int getId() {
        return id;
    }

    public String getControlCharacters() {
        return controlCharacters;
    }
}
