/*
 * Copyright 2014 IDEXX Laboratories, Inc. All rights reserved. IDEXX PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.harbinger.jbw;

public class BaseLocationAcceptanceTest extends BroodwarAgent {

    public static void main(final String[] args) {
        new BaseLocationAcceptanceTest().launchWithBroodwar();
    }

    @Override
    public void matchStart() {
        broodwar.enableUserInput();
    }

    @Override
    public void matchFrame() {
        for (final BaseLocation baseLocation : broodwar.getMap().getBaseLocations()) {

            final Position pos = baseLocation.getCenter();
            broodwar.drawCircleMap(pos, 5, BWColor.CYAN, true);
            broodwar.drawCircleMap(baseLocation.getPosition(), 5, BWColor.ORANGE, true);

            broodwar.drawTextMap(pos.translated(8, 0), "Minerals: " + baseLocation.getMinerals());
            broodwar.drawTextMap(pos.translated(8, 10), "Gas: " + baseLocation.getGas());
            broodwar.drawTextMap(pos.translated(8, 20), "Is Island: " + baseLocation.isIsland());
            broodwar.drawTextMap(pos.translated(8, 30),
                    "Minerals Only: " + baseLocation.isMineralOnly());
            broodwar.drawTextMap(pos.translated(8, 40),
                    "Is Starting Location: " + baseLocation.isStartLocation());

        }
    }
}
