package com.wapp.core.utils;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {

    /**
     * Returns the date and time from UTC-3 as a string.
     *
     * @return A string representation of the current date and time.
     */
    public static String getCurrentDateTimeAsString() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return now.format(formatter);
    }

    public static Timestamp getCurrentTimestamp() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.of("-03:00"));
        return Timestamp.valueOf(offsetDateTime.toLocalDateTime());
    }

}
