package com.harbinger.jbw;

import static com.harbinger.jbw.Position.Resolution.PIXEL;

import com.harbinger.jbw.util.BroodwarAgentTest;

/**
 * Verifies that text and shapes can be drawn. This test is not automated; it launches Broodwar and
 * the an agent that will invoke the drawing methods. A user will need to verify that they are in
 * face drawing properly.
 *
 * <p>
 * Some of the drawing methods will drawn directly on the screen, others on the map in the top-left
 * corner. The text and shapes will cycle through all of the available colors.
 */
public class ColorAcceptanceTestAgent extends BroodwarAgentTest {

    public static void main(final String[] args) {
        new ColorAcceptanceTestAgent().launchAndWait();
    }

    private BWColor getColor() {
        // Rounds the current frame to the nearest value that is divisible by ten and use that value
        // as the color's index. This results in the same color being selected for ten consecutive
        // frames before using the next color.
        final int colorIndex = ((broodwar.getFrame() + 10) / 10) * 10;
        return BWColor.values()[colorIndex % BWColor.values().length];
    }

    @Override
    public void matchFrame() {

        if (broodwar.getFrame() > 60) {
            terminateBroodwar();
        }

        final BWColor color = getColor();

        broodwar.drawTextMap(new Position(10, 10, PIXEL), "Text", color);
        broodwar.drawTextMap(40, 10, "Text", color);
        broodwar.drawTextScreen(new Position(70, 10, PIXEL), "Text", color);
        broodwar.drawTextScreen(100, 10, "Text", color);

        broodwar.drawLineMap(new Position(10, 30, PIXEL), new Position(30, 50, PIXEL), color);
        broodwar.drawLineMap(40, 30, 60, 50, color);
        broodwar.drawLineScreen(new Position(70, 30, PIXEL), new Position(90, 50, PIXEL), color);
        broodwar.drawLineScreen(100, 30, 120, 50, color);

        broodwar.drawRectangleMap(new Position(10, 60, PIXEL), 20, 20, color, false);
        broodwar.drawRectangleMap(40, 60, 20, 20, color, true);
        broodwar.drawRectangleScreen(new Position(70, 60, PIXEL), 20, 20, color, false);
        broodwar.drawRectangleScreen(100, 60, 20, 20, color, true);

        broodwar.drawCircleMap(new Position(20, 100, PIXEL), 10, color, false);
        broodwar.drawCircleMap(50, 100, 10, color, true);
        broodwar.drawCircleScreen(new Position(80, 100, PIXEL), 10, color, false);
        broodwar.drawCircleScreen(110, 100, 10, color, true);

        broodwar.drawEllipseMap(new Position(20, 130, PIXEL), 10, 5, color, false);
        broodwar.drawEllipseMap(50, 130, 10, 5, color, true);
        broodwar.drawEllipseScreen(new Position(80, 130, PIXEL), 10, 5, color, false);
        broodwar.drawEllipseScreen(110, 130, 10, 5, color, true);

    }
}
