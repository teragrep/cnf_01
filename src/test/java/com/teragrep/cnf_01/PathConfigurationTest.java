/*
 * Teragrep Java Configuration Library (cnf_01)
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

public class PathConfigurationTest {

    @Test
    public void testValidPath() {
        PathConfiguration pathConfiguration = new PathConfiguration("src/test/resources/configuration.properties");
        Map<String, String> map = Assertions.assertDoesNotThrow(pathConfiguration::asMap);

        Assertions.assertEquals(1, map.size());
        Assertions.assertTrue(map.containsKey("foo"));
        Assertions.assertEquals("bar", map.get("foo"));
    }

    @Test
    public void testInvalidPath() {
        final String path = "invalid.path";
        PathConfiguration pathConfiguration = new PathConfiguration(path);
        ConfigurationException exception = Assertions
                .assertThrows(ConfigurationException.class, pathConfiguration::asMap);
        Assertions.assertEquals("Can't find the properties file at " + path, exception.getMessage());
    }

    @Test
    public void testEquals() {
        PathConfiguration config1 = new PathConfiguration("src/test/resources/configuration.properties");
        PathConfiguration config2 = new PathConfiguration("src/test/resources/configuration.properties");

        Assertions.assertDoesNotThrow(config1::asMap);
        Assertions.assertEquals(config1, config2);
    }

    @Test
    public void testNotEquals() {
        PathConfiguration config1 = new PathConfiguration("src/test/resources/configuration.properties");
        PathConfiguration config2 = new PathConfiguration("invalid.path");

        Assertions.assertNotEquals(config1, config2);
    }

    @Test
    public void testHashCode() {
        PathConfiguration config1 = new PathConfiguration("src/test/resources/configuration.properties");
        PathConfiguration config2 = new PathConfiguration("src/test/resources/configuration.properties");
        PathConfiguration difConfig = new PathConfiguration("invalid.path");

        Assertions.assertEquals(config1.hashCode(), config2.hashCode());
        Assertions.assertNotEquals(config1.hashCode(), difConfig.hashCode());
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(PathConfiguration.class).withNonnullFields("file").verify();
    }
}
