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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

public final class PathConfiguration implements Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathConfiguration.class);

    private final File file;

    public PathConfiguration(final String fileName) {
        this(new File(fileName));
    }

    public PathConfiguration(final File file) {
        this.file = file;
    }

    @Override
    public Map<String, String> asMap() throws ConfigurationException {
        final Properties properties = new Properties();
        try (final InputStream in = Files.newInputStream(file.toPath())) {
            properties.load(in);
        }
        catch (IOException e) {
            throw new ConfigurationException("Can't find the properties file at " + file.getPath(), e);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Returning configuration map generated from file <[{}]>.", file.getPath());
        }

        final Map<String, String> configuration = Collections
                .unmodifiableMap(
                        properties
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(k -> k.getKey().toString(), k -> k.getValue().toString()))
                );

        LOGGER.trace("Returning configuration map <[{}]>", configuration);

        return configuration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PathConfiguration config = (PathConfiguration) o;
        return file.equals(config.file);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(file);
    }
}
