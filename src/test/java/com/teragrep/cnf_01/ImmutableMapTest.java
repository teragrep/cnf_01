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

public class ImmutableMapTest {

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");

        ImmutabilitySupportedMap<String, String> mapDecorator = new ImmutabilitySupportedMap<>(map);
        Map<String, String> immutableMap = mapDecorator.toImmutableMap();

        // modifying the original should have no effect after the map has been created with ImmutabilitySupportedMap
        map.put("bar", "foo");

        Assertions.assertEquals(1, immutableMap.size());
        Assertions.assertEquals("bar", immutableMap.get("foo"));

        // can't be modified
        Assertions.assertThrows(UnsupportedOperationException.class, () -> immutableMap.put("foo", "baz"));
    }

    @Test
    public void testEquals() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("foo", "bar");
        Map<String, String> map2 = new HashMap<>();
        map2.put("foo", "bar");

        ImmutableMap<String, String> immutableMap1 = new ImmutableMap<>(map1);
        ImmutableMap<String, String> immutableMap2 = new ImmutableMap<>(map2);

        immutableMap1.map();

        Assertions.assertEquals(immutableMap1, immutableMap2);
    }

    @Test
    public void testNotEquals() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("foo", "bar");
        Map<String, String> map2 = new HashMap<>();

        ImmutableMap<String, String> immutableMap1 = new ImmutableMap<>(map1);
        ImmutableMap<String, String> immutableMap2 = new ImmutableMap<>(map2);

        Assertions.assertNotEquals(immutableMap1, immutableMap2);
    }

    @Test
    public void testHashCode() {
        Map<String, String> map1 = new HashMap<>();
        map1.put("foo", "bar");
        Map<String, String> map2 = new HashMap<>();
        map2.put("foo", "bar");
        Map<String, String> map3 = new HashMap<>();

        ImmutableMap<String, String> immutableMap1 = new ImmutableMap<>(map1);
        ImmutableMap<String, String> immutableMap2 = new ImmutableMap<>(map2);
        ImmutableMap<String, String> difImmutableMap = new ImmutableMap<>(map3);

        Assertions.assertEquals(immutableMap1.hashCode(), immutableMap2.hashCode());
        Assertions.assertNotEquals(immutableMap1.hashCode(), difImmutableMap.hashCode());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(ImmutableMap.class).withNonnullFields("map").verify();
    }
}
