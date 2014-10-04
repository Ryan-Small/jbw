package com.harbinger.jbw;

import static com.harbinger.jbw.Position.Resolution.PIXEL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.harbinger.jbw.util.BroodwarAgentTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * This test is responsible for ensuring that each BaseLocation is initialized correctly.
 */
public class BaseLocationAcceptanceTest extends BroodwarAgentTest {

    private static final boolean TERMINATE_AFTER_TEST = true;

    @Test
    public void initialization() {
        new BaseLocationAcceptanceTest().launchAndWait();
    }

    @Override
    public void matchStart() {
        final String expectedMapName = "(5)Island Hop.scm";
        assertThat(broodwar.getMap().getFileName(), is(equalTo(expectedMapName)));

        final List<BaseLocation> actualBaseLocations = broodwar.getMap().getBaseLocations();
        final List<BaseLocation> expectedBaseLocations = getExpectedBaseLocations();

        assertThat(actualBaseLocations.size(), is(equalTo(expectedBaseLocations.size())));
        for (final BaseLocation expectedBaseLocation : expectedBaseLocations) {
            assertThat(actualBaseLocations.contains(expectedBaseLocation), is(true));
        }

        if (TERMINATE_AFTER_TEST) {
            terminateBroodwar();
        }
    }

    @Override
    public void matchFrame() {
        for (final BaseLocation baseLocation : broodwar.getMap().getBaseLocations()) {
            final Position pos = baseLocation.getCenter();
            broodwar.drawCircleMap(pos, 5, BWColor.CYAN, true);
            broodwar.drawCircleMap(baseLocation.getPosition(), 5, BWColor.ORANGE, true);

            final String messageMinerals = "Initial Minerals: " + baseLocation.getMinerals();
            broodwar.drawTextMap(pos.translated(8, 0), messageMinerals);

            final String messageGas = "Initial Gas: " + baseLocation.getGas();
            broodwar.drawTextMap(pos.translated(8, 10), messageGas);

            final String messageIsland = "Is Island: " + baseLocation.isIsland();
            broodwar.drawTextMap(pos.translated(8, 20), messageIsland);

            final String messageMineralsOnly = "Is Only Minerals: " + baseLocation.isMineralOnly();
            broodwar.drawTextMap(pos.translated(8, 30), messageMineralsOnly);

            final String messageStartingLocation =
                    "Is Starting Location: " + baseLocation.isStartLocation();
            broodwar.drawTextMap(pos.translated(8, 40), messageStartingLocation);
        }
    }

    /*
     * Returns the list of BaseLocations for (5)Island Hop.scm.
     */
    private List<BaseLocation> getExpectedBaseLocations() {
        final List<BaseLocation> baseLocations = new ArrayList<>();

        final Position position1 = new Position(3104, 3856, PIXEL);
        final Position center1 = new Position(3040, 3808, PIXEL);
        baseLocations.add(new BaseLocation(position1, center1, 13500, 5000, true, false, true));

        final Position position2 = new Position(2208, 3632, PIXEL);
        final Position center2 = new Position(2144, 3584, PIXEL);
        baseLocations.add(new BaseLocation(position2, center2, 9000, 5000, true, false, false));

        final Position position3 = new Position(640, 3280, PIXEL);
        final Position center3 = new Position(576, 3232, PIXEL);
        baseLocations.add(new BaseLocation(position3, center3, 13500, 5000, true, false, true));

        final Position position4 = new Position(2688, 2992, PIXEL);
        final Position center4 = new Position(2624, 2944, PIXEL);
        baseLocations.add(new BaseLocation(position4, center4, 9000, 5000, true, false, false));

        final Position position5 = new Position(1792, 2480, PIXEL);
        final Position center5 = new Position(1728, 2432, PIXEL);
        baseLocations.add(new BaseLocation(position5, center5, 12000, 0, true, true, false));

        final Position position6 = new Position(3200, 1776, PIXEL);
        final Position center6 = new Position(3136, 1728, PIXEL);
        baseLocations.add(new BaseLocation(position6, center6, 13500, 5000, true, false, true));

        final Position position7 = new Position(640, 1968, PIXEL);
        final Position center7 = new Position(576, 1920, PIXEL);
        baseLocations.add(new BaseLocation(position7, center7, 9000, 5000, true, false, false));

        final Position position8 = new Position(1792, 1808, PIXEL);
        final Position center8 = new Position(1728, 1760, PIXEL);
        baseLocations.add(new BaseLocation(position8, center8, 12000, 0, true, true, false));

        final Position position9 = new Position(2336, 560, PIXEL);
        final Position center9 = new Position(2272, 512, PIXEL);
        baseLocations.add(new BaseLocation(position9, center9, 13500, 5000, true, false, true));

        final Position position10 = new Position(3584, 720, PIXEL);
        final Position center10 = new Position(3520, 672, PIXEL);
        baseLocations.add(new BaseLocation(position10, center10, 9000, 5000, true, false, false));

        final Position position11 = new Position(544, 432, PIXEL);
        final Position center11 = new Position(480, 384, PIXEL);
        baseLocations.add(new BaseLocation(position11, center11, 13500, 5000, true, false, true));

        return baseLocations;
    }
}
