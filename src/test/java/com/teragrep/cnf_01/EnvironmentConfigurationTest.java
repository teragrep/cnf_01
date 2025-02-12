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

public class EnvironmentConfigurationTest {

    @Test
    public void testEnvironment() {
        EnvironmentConfiguration env = new EnvironmentConfiguration();
        Map<String, String> map = env.asMap();

        // Can't test environment variables reliably, just testing if the map has values.
        Assertions.assertFalse(map.isEmpty());
    }

    @Test
    public void testMap() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "bar");

        EnvironmentConfiguration env = new EnvironmentConfiguration(map);
        // modifying the original doesn't have effect on the result
        map.put("bar", "foo");

        Map<String, String> result = env.asMap();

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("foo"));
        Assertions.assertEquals("bar", result.get("foo"));
    }

    @Test
    public void testEmptyMap() {
        Map<String, String> map = new HashMap<>();
        EnvironmentConfiguration env = new EnvironmentConfiguration(map);
        // modifying the original doesn't have effect on the result
        map.put("foo", "bar");

        Map<String, String> result = env.asMap();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(EnvironmentConfiguration.class).withNonnullFields("environment").verify();
    }
}
