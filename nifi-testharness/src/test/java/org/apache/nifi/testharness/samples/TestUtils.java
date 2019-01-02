/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package org.apache.nifi.testharness.samples;

import org.apache.nifi.testharness.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

final class TestUtils {

    private TestUtils() {
        // no instances allowed
    }

    static File getBinaryDistributionZipFile(File binaryDistributionZipDir) {

        if (!binaryDistributionZipDir.exists()) {
            throw new IllegalStateException("NiFi distribution ZIP file not found at the expected location: "
                    + binaryDistributionZipDir.getAbsolutePath());
        }

        File[] files = binaryDistributionZipDir.listFiles((dir, name) ->
                name.startsWith("nifi-") && name.endsWith("-bin.zip"));

        if (files == null) {
            throw new IllegalStateException(
                    "Not a directory or I/O error reading: " + binaryDistributionZipDir.getAbsolutePath());
        }

        if (files.length == 0) {

            String filesFoundString;

            try {
                filesFoundString = FileUtils.listDirRecursive(binaryDistributionZipDir.toPath()).stream()
                        .map(File::getAbsolutePath)
                        .collect(Collectors.joining(","));
            } catch (IOException e) {
                filesFoundString = "";
            }

            throw new IllegalStateException("No NiFi distribution ZIP file is found in: "
                            + binaryDistributionZipDir.getAbsolutePath()
                            + "; Files found are: " + filesFoundString);
        }

        if (files.length > 1) {
            throw new IllegalStateException(
                    "Multiple NiFi distribution ZIP files are found in: " + binaryDistributionZipDir.getAbsolutePath());
        }

        return files[0];
    }
}
