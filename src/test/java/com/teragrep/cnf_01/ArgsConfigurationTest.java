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

public class ArgsConfigurationTest {

    @Test
    public void testValidArgs() {
        String param = "bar=foo";
        String[] args = {
                "foo=bar", param
        };

        ArgsConfiguration config = new ArgsConfiguration(args);
        // modifying the original doesn't have effect on the result
        args[1] = "biz=buz";

        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);

        Assertions.assertEquals(2, map.size());
        Assertions.assertTrue(map.containsKey("foo"));
        Assertions.assertTrue(map.containsKey("bar"));
        Assertions.assertEquals("bar", map.get("foo"));
        Assertions.assertEquals("foo", map.get("bar"));
    }

    @Test
    public void testCamelCaseArgs() {
        String[] args = {
                "exampleOption=value"
        };
        ArgsConfiguration config = new ArgsConfiguration(args);
        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);

        Assertions.assertEquals(1, map.size());
        Assertions.assertTrue(map.containsKey("exampleOption"));
        Assertions.assertEquals("value", map.get("exampleOption"));
    }

    @Test
    public void testDotArgs() {
        String[] args = {
                "example.option=value"
        };
        ArgsConfiguration config = new ArgsConfiguration(args);
        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);

        Assertions.assertEquals(1, map.size());
        Assertions.assertTrue(map.containsKey("example.option"));
        Assertions.assertEquals("value", map.get("example.option"));
    }

    @Test
    public void testUnderscoreArgs() {
        String[] args = {
                "example_option=value"
        };
        ArgsConfiguration config = new ArgsConfiguration(args);
        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);

        Assertions.assertEquals(1, map.size());
        Assertions.assertTrue(map.containsKey("example_option"));
        Assertions.assertEquals("value", map.get("example_option"));
    }

    @Test
    public void testHyphenArgs() {
        String[] args = {
                "example-option=value"
        };
        ArgsConfiguration config = new ArgsConfiguration(args);
        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);

        Assertions.assertEquals(1, map.size());
        Assertions.assertTrue(map.containsKey("example-option"));
        Assertions.assertEquals("value", map.get("example-option"));
    }

    @Test
    public void testInvalidArgs() {
        String[] args = {
                "foo", "bar"
        };

        ArgsConfiguration config = new ArgsConfiguration(args);

        ConfigurationException exception = Assertions.assertThrows(ConfigurationException.class, config::asMap);

        Assertions
                .assertEquals(
                        "Can't parse argument 'foo'. It might contain an unsupported character or is not given in \"key=value\" format.",
                        exception.getMessage()
                );
    }

    @Test
    public void testEmptyArgs() {
        String[] args = new String[0];
        ArgsConfiguration config = new ArgsConfiguration(args);

        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);
        Assertions.assertTrue(map.isEmpty());
    }

    @Test
    public void testImmutability() {
        String[] args = new String[0];
        ArgsConfiguration config = new ArgsConfiguration(args);

        Map<String, String> map = Assertions.assertDoesNotThrow(config::asMap);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> map.put("foo", "bar")); // The map is immutable
    }

    @Test
    public void testEqualsVerifier() {
        EqualsVerifier.forClass(ArgsConfiguration.class).withNonnullFields("args").verify();
    }
}
