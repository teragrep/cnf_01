/*
 * Teragrep Configuration Library for Java (cnf_01)
 * Copyright (C) 2024 Suomen Kanuuna Oy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 * Additional permission under GNU Affero General Public License version 3
 * section 7
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with other code, such other code is not for that reason alone subject to any
 * of the requirements of the GNU Affero GPL version 3 as long as this Program
 * is the same Program as licensed from Suomen Kanuuna Oy without any additional
 * modifications.
 *
 * Supplemented terms under GNU Affero General Public License version 3
 * section 7
 *
 * Origin of the software must be attributed to Suomen Kanuuna Oy. Any modified
 * versions must be marked as "Modified version of" The Program.
 *
 * Names of the licensors and authors may not be used for publicity purposes.
 *
 * No rights are granted for use of trade names, trademarks, or service marks
 * which are in The Program if any.
 *
 * Licensee must indemnify licensors and authors for any liability that these
 * contractual assumptions impose on licensors and authors.
 *
 * To the extent this program is licensed as part of the Commercial versions of
 * Teragrep, the applicable Commercial License may apply to this file if you as
 * a licensee so wish it.
 */
package com.teragrep.cnf_01;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ImmutabilitySupportedMapTest {

    @Test
    public void testToImmutableMap() {
        Map<String, String> originalMap = new HashMap<>();
        originalMap.put("foo", "bar");

        ImmutabilitySupportedMap<String, String> map = new ImmutabilitySupportedMap<>(originalMap);

        ImmutableMap<String, String> resultMap = map.toImmutableMap();

        // modifying the original doesn't have effect after the method call
        originalMap.put("bar", "foo");

        Assertions.assertEquals(1, resultMap.size());
        Assertions.assertEquals("bar", resultMap.get("foo"));

        // can't be modified
        Assertions.assertThrows(UnsupportedOperationException.class, () -> resultMap.put("foo", "baz"));
    }

    @Test
    public void testEquals() {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map1.put("foo", "bar");
        map2.put("foo", "bar");

        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap1 = new ImmutabilitySupportedMap<>(map1);
        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap2 = new ImmutabilitySupportedMap<>(map2);

        // It's a Map decorator so it is mutable, but the new function toImmutableMap shouldn't mutate the object
        immutabilitySupportedMap1.toImmutableMap();

        Assertions.assertEquals(immutabilitySupportedMap1, immutabilitySupportedMap2);
    }

    @Test
    public void testNotEquals() {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> difMap = new HashMap<>();
        map1.put("foo", "bar");
        map2.put("foo", "bar");

        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap1 = new ImmutabilitySupportedMap<>(map1);
        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap2 = new ImmutabilitySupportedMap<>(map2);
        ImmutabilitySupportedMap<String, String> difImmutabilitySupportedMap = new ImmutabilitySupportedMap<>(difMap);

        Assertions.assertEquals(immutabilitySupportedMap1, immutabilitySupportedMap2);
        Assertions.assertNotEquals(immutabilitySupportedMap1, difImmutabilitySupportedMap);
    }

    @Test
    public void testHashCode() {
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> difMap = new HashMap<>();
        map1.put("foo", "bar");
        map2.put("foo", "bar");

        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap1 = new ImmutabilitySupportedMap<>(map1);
        ImmutabilitySupportedMap<String, String> immutabilitySupportedMap2 = new ImmutabilitySupportedMap<>(map2);
        ImmutabilitySupportedMap<String, String> difImmutabilitySupportedMap = new ImmutabilitySupportedMap<>(difMap);

        Assertions.assertEquals(immutabilitySupportedMap1.hashCode(), immutabilitySupportedMap2.hashCode());
        Assertions.assertNotEquals(immutabilitySupportedMap1.hashCode(), difImmutabilitySupportedMap.hashCode());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(ImmutabilitySupportedMap.class).withNonnullFields("map").verify();
    }
}
