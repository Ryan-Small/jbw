package com.harbinger.jbw;

public class ColorAcceptanceTestAgent extends BroodwarAgent {

    public static void main(final String[] args) {
        new ColorAcceptanceTestAgent().start();
    }

    private BWColor getColor() {
        // Rounds the current frame to the nearest value that is divisible by ten and use that value
        // as the color's index. This results in the same color being selected for ten consecutive
        // frames before using the next color.
        final int colorIndex = ((broodwar.getFrame() + 9) / 10) * 10;
        return BWColor.values()[colorIndex % BWColor.values().length];
    }

    @Override
    public void matchFrame() {
        final BWColor color = getColor();

        broodwar.drawTextMap(new Position(10, 10), "Text", color);
        broodwar.drawTextMap(40, 10, "Text", color);
        broodwar.drawTextScreen(new Position(70, 10), "Text", color);
        broodwar.drawTextScreen(100, 10, "Text", color);

        broodwar.drawLineMap(new Position(10, 30), new Position(30, 50), color);
        broodwar.drawLineMap(40, 30, 60, 50, color);
        broodwar.drawLineScreen(new Position(70, 30), new Position(90, 50), color);
        broodwar.drawLineScreen(100, 30, 120, 50, color);

        broodwar.drawRectangleMap(new Position(10, 60), 20, 20, color, false);
        broodwar.drawRectangleMap(40, 60, 20, 20, color, true);
        broodwar.drawRectangleScreen(new Position(70, 60), 20, 20, color, false);
        broodwar.drawRectangleScreen(100, 60, 20, 20, color, true);

        broodwar.drawCircleMap(new Position(20, 100), 10, color, false);
        broodwar.drawCircleMap(50, 100, 10, color, true);
        broodwar.drawCircleScreen(new Position(80, 100), 10, color, false);
        broodwar.drawCircleScreen(110, 100, 10, color, true);

        broodwar.drawEllipseMap(new Position(20, 130), 10, 5, color, false);
        broodwar.drawEllipseMap(50, 130, 10, 5, color, true);
        broodwar.drawEllipseScreen(new Position(80, 130), 10, 5, color, false);
        broodwar.drawEllipseScreen(110, 130, 10, 5, color, true);
    }
}
