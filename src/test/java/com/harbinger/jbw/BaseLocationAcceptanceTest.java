/*
 * Copyright 2014 IDEXX Laboratories, Inc. All rights reserved. IDEXX PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.harbinger.jbw;

import static org.junit.Assert.fail;

import com.harbinger.jbw.util.BroodwarAgentTest;

import org.junit.Test;

public class BaseLocationAcceptanceTest extends BroodwarAgentTest {

    @Test
    public void initialization() {
        new BaseLocationAcceptanceTest().launchAndWait();
    }

    @Override
    public void matchStart() {
        if (broodwar.getMap().getFileName().equals("(4)Blood Bath.scm")) {
            fail(getClass().getSimpleName() + " requires the Blood Bath map");
        }

        broodwar.enableUserInput();
    }

    @Override
    public void matchFrame() {
        // for (final BaseLocation baseLocation : broodwar.getMap().getBaseLocations()) {
        //
        // final Position pos = baseLocation.getCenter();
        // broodwar.drawCircleMap(pos, 5, BWColor.CYAN, true);
        // broodwar.drawCircleMap(baseLocation.getPosition(), 5, BWColor.ORANGE, true);
        //
        // broodwar.drawTextMap(pos.translated(8, 0), "Minerals: " + baseLocation.getMinerals());
        // broodwar.drawTextMap(pos.translated(8, 10), "Gas: " + baseLocation.getGas());
        // broodwar.drawTextMap(pos.translated(8, 20), "Is Island: " + baseLocation.isIsland());
        // broodwar.drawTextMap(pos.translated(8, 30),
        // "Minerals Only: " + baseLocation.isMineralOnly());
        // broodwar.drawTextMap(pos.translated(8, 40),
        // "Is Starting Location: " + baseLocation.isStartLocation());
        //
        // }
    }
}
