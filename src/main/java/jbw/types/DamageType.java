package jbw.types;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * http://code.google.com/p/bwapi/wiki/DamageType
 */
public class DamageType {

    private static Map<Integer, DamageType> idToDamageType = new HashMap<>();

    public static class DamageTypes {
        public static final DamageType Independent = new DamageType(0);
        public static final DamageType Explosive = new DamageType(1);
        public static final DamageType Concussive = new DamageType(2);
        public static final DamageType Normal = new DamageType(3);
        public static final DamageType Ignore_Armor = new DamageType(4);
        public static final DamageType None = new DamageType(5);
        public static final DamageType Unknown = new DamageType(6);

        public static DamageType getDamageType(final int id) {
            return idToDamageType.get(id);
        }

        public static Collection<DamageType> getAllDamageTypes() {
            return Collections.unmodifiableCollection(idToDamageType.values());
        }
    }

    public static final int NUM_ATTRIBUTES = 1;

    private String name;
    private final int id;

    private DamageType(final int id) {
        this.id = id;
        idToDamageType.put(id, this);
    }

    public void initialize(final int[] data, int index, final String name) {
        if (id != data[index++]) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
