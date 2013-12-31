/*
 * Copyright 2013 IDEXX Laboratories, Inc. All rights reserved.
 * IDEXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package jnibwapi.example;

import jnibwapi.BWAPIEventListener;
import jnibwapi.JNIBWAPI;
import jnibwapi.model.Unit;
import jnibwapi.types.UnitType;

/**
 *
 */
public class Minimal extends BWAPIEventListener.Adaptor {

    private final JNIBWAPI bwapi = new JNIBWAPI(this, true);

    public static void main(final String[] args) {
        new Minimal().launchBwapi();
    }

    private void launchBwapi() {
        /* Start BWAPI on a separate thread to prevent blocking. */
        final Runnable bwapiRunnable = new Runnable() {

            @Override
            public void run() {
                bwapi.start();
            }
        };
        final Thread bwapiThread = new Thread(bwapiRunnable);
        bwapiThread.start();
    }

    @Override
    public void matchStart() {
        // bwapi.setGameSpeed(10);
        bwapi.enableUserInput();
        // bwapi.enablePerfectInformation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitDiscover(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitDiscover: " + unitId + " - " + unitTypeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitEvade(final int unitId) {
        System.out.println("UnitEvade: " + unitId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitShow(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitShow: " + unitId + " - " + unitTypeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitHide(final int unitId) {
        System.out.println("UnitHide: " + unitId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitCreate(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitCreate: " + unitId + " - " + unitTypeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitDestroy(final int unitId) {
        System.out.println("UnitDestroy: " + unitId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitMorph(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitMorph: " + unitId + " - " + unitTypeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitRenegade(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitRenegade: " + unitId + " - " + unitTypeName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unitComplete(final int unitId) {
        final Unit unit = bwapi.getUnit(unitId);
        final UnitType unitType = bwapi.getUnitType(unit.getTypeID());
        final String unitTypeName = unitType == null ? "null" : unitType.getName();
        System.out.println("UnitComplete: " + unitId + " - " + unitTypeName);
    }
}
