/*
 * Copyright 2014 IDEXX Laboratories, Inc. All rights reserved.
 * IDEXX PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package jbw;

import jbw.model.Player;
import jbw.model.Unit;

public interface IdLookup {
    public Player getPlayer(int playerID);

    public Unit getUnit(int unitID);
}
