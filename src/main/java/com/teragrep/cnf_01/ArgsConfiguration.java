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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ArgsConfiguration implements Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgsConfiguration.class);

    private final String[] args;

    public ArgsConfiguration(final String[] args) {
        this.args = Arrays.copyOf(args, args.length);
    }

    /**
     * Produces a Map of configurations from the args.
     *
     * @return immutable map of the args
     * @throws ConfigurationException If the args are not given in the format of this regex: ([a-z]+)(=.+)
     */
    @Override
    public Map<String, String> asMap() throws ConfigurationException {
        final Map<String, String> map = new HashMap<>();

        if (args.length != 0) {
            final Pattern ptn = Pattern.compile("([a-z]+)(=.+)");
            for (final String arg : args) {
                final Matcher matcher = ptn.matcher(arg);
                if (!matcher.matches()) {
                    throw new ConfigurationException(
                            String
                                    .format(
                                            "Can't parse argument '%s'. Args have to be given in \"key=value\" format.",
                                            arg
                                    )
                    );
                }
                map.put(matcher.group(1), matcher.group(2).substring(1));
            }
        }
        LOGGER.debug("Returning configuration map generated from command-line arguments.");
        LOGGER.trace("Returning configuration map <[{}]>", map);

        return Collections.unmodifiableMap(map);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ArgsConfiguration config = (ArgsConfiguration) o;
        return Arrays.equals(args, config.args);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(args);
    }
}
