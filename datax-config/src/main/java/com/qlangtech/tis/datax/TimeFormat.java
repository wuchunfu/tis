package com.qlangtech.tis.datax;

import org.apache.commons.lang3.StringUtils;

import java.sql.Time;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author: 百岁（baisui@qlangtech.com）
 * @create: 2023-02-23 13:31
 **/
public enum TimeFormat implements ITimeFormat {

    yyyyMMddHHmmss(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) //
    , yyyyMMdd(DateTimeFormatter.ofPattern("yyyyMMdd")) //
    , yyyy_MM_dd(DateTimeFormatter.ofPattern(DATA_FORMAT));
   // public static final String DATA_FORMAT = yyyy_MM_dd.timeFormatter.p;
    public static final ZoneId sysZoneId = ZoneOffset.systemDefault();
    public final DateTimeFormatter timeFormatter;
    public static Long timestampForTest;

    TimeFormat(DateTimeFormatter timeFormatter) {
        this.timeFormatter = timeFormatter;
    }

    public static long getCurrentTimeStamp() {
        if (timestampForTest != null) {
            return timestampForTest;
        }
        //  Clock.systemDefaultZone().
        return Clock.system(sysZoneId).millis();
        //  return LocalDateTime.now().toInstant()
    }

    public String format(long epochMilli) {
        return this.ofInstant(epochMilli).format(this.timeFormatter);
    }

    public LocalDateTime ofInstant(long epochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), sysZoneId);
    }

    public static TimeFormat parse(String token) {

        for (TimeFormat f : TimeFormat.values()) {
            if (StringUtils.endsWithIgnoreCase(f.name(), token)) {
                return f;
            }
        }
        throw new IllegalStateException("illegal token:" + token);
    }

    public static void main(String[] args) {
        System.out.println(TimeFormat.yyyyMMddHHmmss.format(1709001500699l));
    }
}
