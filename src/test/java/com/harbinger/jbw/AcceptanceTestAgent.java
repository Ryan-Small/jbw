package com.harbinger.jbw;

public class AcceptanceTestAgent extends BroodwarAgent {

    public static void main(final String[] args) {
        new AcceptanceTestAgent().start();
    }

    private static int round(final int value) {
        return ((value + 4) / 5) * 5;
    }

    @Override
    public void matchFrame() {
        final int frame = round(broodwar.getFrame());
        final BWColor color = BWColor.values()[frame % BWColor.values().length];

        broodwar.drawTextMap(new Position(10, 10), "Text");
        broodwar.drawTextMap(40, 10, "Text");
        broodwar.drawTextScreen(new Position(70, 10), "Text");
        broodwar.drawTextScreen(100, 10, "Text");

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
