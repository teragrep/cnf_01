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

public class DefaultConfigurationTest {

    @Test
    public void testValidConfiguration() {
        Map<String, String> map = new HashMap<>();
        ImmutableMap<String, String> defaults = new ImmutabilitySupportedMap<>(map).toImmutableMap();

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration(
                new PathConfiguration("src/test/resources/configuration.properties"),
                defaults
        );
        Map<String, String> result = defaultConfiguration.asMap();

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("foo"));
        Assertions.assertEquals("bar", result.get("foo"));
    }

    @Test
    public void testInvalidConfiguration() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "test");
        ImmutableMap<String, String> defaults = new ImmutabilitySupportedMap<>(map).toImmutableMap();

        DefaultConfiguration defaultConfiguration = new DefaultConfiguration(
                new PathConfiguration("invalid.path"),
                defaults
        );

        // Defaults are unmodifiable between constructor and method call
        // so this should not affect the default config anymore
        map.put("foo", "bar");

        Map<String, String> result = defaultConfiguration.asMap();

        Assertions.assertEquals(1, result.size());
        Assertions.assertTrue(result.containsKey("test"));
        Assertions.assertEquals("test", result.get("test"));
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier
                .forClass(DefaultConfiguration.class)
                .withNonnullFields("config")
                .withNonnullFields("defaults")
                .verify();
    }
}
