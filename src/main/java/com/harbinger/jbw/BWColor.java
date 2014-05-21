package com.harbinger.jbw;

public enum BWColor {

    /** the default color for Player 1 */
    RED(111, "\u0008"),

    /** the default color for Player 2 */
    BLUE(165, "\u000E"),

    /** the default color for Player 3 */
    TEAL(159, "\u000F"),

    /** the default color for Player 4 */
    PURPLE(164, "\u0010"),

    /** the default color for Player 5 */
    ORANGE(179, "\u0011"),

    /** the default color for Player 6 */
    BROWN(19, "\u0015"),

    /** the default color for Player 7 */
    WHITE(255, "\u0004"),

    /** the default color for Player 8 */
    YELLOW(135, "\u0017"),

    /** the alternate color for Player 7 on Ice tilesets */
    GREEN(117, "\u0007"),

    /** the default color for Player 12 (neutral) */
    CYAN(128, "\u001F"),

    BLACK(0, "\u0005"),
    GREY(74, "\u0016");

    private int id;
    private String controlCharacters;

    private BWColor(final int id, final String escape) {
        this.id = id;
        controlCharacters = escape;
    }

    int getId() {
        return id;
    }

    String getControlCharacters() {
        return controlCharacters;
    }
}
