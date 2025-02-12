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

import java.util.Map;
import java.util.Properties;

public class PropertiesConfigurationTest {

    @Test
    public void testSystemProperties() {
        System.setProperty("foo", "bar");
        System.setProperty("bar", "foo");

        PropertiesConfiguration config = new PropertiesConfiguration(); // uses System.getProperties()
        // clearing properties doesn't affect PropertiesConfiguration
        System.clearProperty("foo");
        System.clearProperty("bar");

        Map<String, String> result = config.asMap();

        Assertions.assertEquals("bar", result.get("foo"));
        Assertions.assertEquals("foo", result.get("bar"));
    }

    @Test
    public void testProperties() {
        Properties properties = new Properties();
        properties.put("foo", "bar");
        properties.put("bar", "foo");

        PropertiesConfiguration config = new PropertiesConfiguration(properties);
        // modifying the original properties doesn't modify the result map
        properties.put("biz", "buz");

        Map<String, String> result = config.asMap();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("bar", result.get("foo"));
        Assertions.assertEquals("foo", result.get("bar"));
    }

    @Test
    public void testEmptyProperties() {
        Properties properties = new Properties();

        PropertiesConfiguration config = new PropertiesConfiguration(properties);
        Map<String, String> result = config.asMap();

        Assertions.assertEquals(0, result.size());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(PropertiesConfiguration.class).withNonnullFields("configuration").verify();
    }
}
