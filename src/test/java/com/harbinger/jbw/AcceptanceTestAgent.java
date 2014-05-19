package com.harbinger.jbw;

import java.math.BigInteger;

public class AcceptanceTestAgent extends BroodwarAgent {

    public static void main(final String[] args) {
        new AcceptanceTestAgent().start();
    }

    private static int round(final int value) {
        return ((value + 4) / 5) * 5;
    }

    public String toHex(final String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/* YOUR_CHARSET? */)));
    }

    @Override
    public void matchFrame() {
        final int frame = round(broodwar.getFrame());
        final BWColor color = BWColor.values()[frame % BWColor.values().length];

        broodwar.drawTextScreen(300, 100, "Red", BWColor.Red);
        broodwar.drawTextScreen(300, 110, "Blue", BWColor.Blue);
        broodwar.drawTextScreen(300, 120, "Teal", BWColor.Teal);
        broodwar.drawTextScreen(300, 130, "Purple", BWColor.Purple);
        broodwar.drawTextScreen(300, 140, "Orange", BWColor.Orange);
        broodwar.drawTextScreen(300, 150, "Brown", BWColor.Brown);
        broodwar.drawTextScreen(300, 160, "White", BWColor.White);
        broodwar.drawTextScreen(300, 170, "Yellow", BWColor.Yellow);
        broodwar.drawTextScreen(300, 180, "Green", BWColor.Green);
        broodwar.drawTextScreen(300, 190, "Cyan", BWColor.Cyan);
        broodwar.drawTextScreen(300, 200, "Black", BWColor.Black);
        broodwar.drawTextScreen(300, 210, "Grey", BWColor.Grey);

        broodwar.drawTextMap(new Position(10, 10), "Text");
        broodwar.drawTextMap(40, 10, "Text");
        broodwar.drawTextScreen(new Position(70, 10), "Text");
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
