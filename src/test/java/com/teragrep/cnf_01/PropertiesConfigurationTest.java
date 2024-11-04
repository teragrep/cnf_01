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
        Map<String, String> result = config.asMap();

        System.clearProperty("foo");
        System.clearProperty("bar");

        Assertions.assertEquals("bar", result.get("foo"));
        Assertions.assertEquals("foo", result.get("bar"));
    }

    @Test
    public void testProperties() {
        Properties properties = new Properties();
        properties.put("foo", "bar");
        properties.put("bar", "foo");

        PropertiesConfiguration config = new PropertiesConfiguration(properties);
        Map<String, String> result = config.asMap();

        // modifying the original properties doesn't modify the result map
        properties.put("biz", "buz");

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
    public void testEqualsWithSystemProperties() {
        PropertiesConfiguration config1 = new PropertiesConfiguration();
        PropertiesConfiguration config2 = new PropertiesConfiguration();

        config1.asMap();
        Assertions.assertEquals(config1, config2);
    }

    @Test
    public void testEqualsWithProperties() {
        Properties properties1 = new Properties();
        properties1.put("foo", "bar");

        Properties properties2 = new Properties();
        properties2.put("foo", "bar");

        PropertiesConfiguration config1 = new PropertiesConfiguration(properties1);
        PropertiesConfiguration config2 = new PropertiesConfiguration(properties2);

        config1.asMap();
        Assertions.assertEquals(config1, config2);
    }

    @Test
    public void testNotEquals() {
        Properties properties = new Properties();
        properties.put("foo", "bar");

        PropertiesConfiguration config1 = new PropertiesConfiguration();
        PropertiesConfiguration config2 = new PropertiesConfiguration(properties);

        Assertions.assertNotEquals(config1, config2);
    }

    @Test
    public void testHashCode() {
        Properties properties1 = new Properties();
        properties1.put("foo", "bar");

        Properties properties2 = new Properties();
        properties2.put("foo", "bar");

        PropertiesConfiguration config1 = new PropertiesConfiguration(properties1);
        PropertiesConfiguration config2 = new PropertiesConfiguration(properties2);
        PropertiesConfiguration difConfig = new PropertiesConfiguration();

        Assertions.assertEquals(config1.hashCode(), config2.hashCode());
        Assertions.assertNotEquals(config1.hashCode(), difConfig.hashCode());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(PropertiesConfiguration.class).withNonnullFields("properties").verify();
    }
}
