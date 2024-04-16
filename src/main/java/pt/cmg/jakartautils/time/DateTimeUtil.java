/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.time;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;
import pt.cmg.jakartautils.text.TextFormatter;

public class DateTimeUtil {

    private static final Logger LOGGER = Logger.getLogger(DateTimeUtil.class.getName());

    /**
     * Converts a Date text, in ISO format (e.g. "2021-03-05T10:12:52.17854Z" or "2021-03-05T10:12:52.1+01:00")
     * to a Date object.
     */
    public static Date convertStringToDate(String date) {

        var instant = getInstantFromDateString(date);

        if (instant != null) {
            return Date.from(instant);
        }

        return null;
    }

    /**
     * Converts a Date text, in ISO format (e.g. "2021-03-05T10:12:52.17854Z or "2021-03-05T10:12:52.1+01:00")
     * to a Local Date Time at System Default Zone object.
     */
    public static LocalDateTime convertStringToLocalDateTime(String date) {

        Instant instant = getInstantFromDateString(date);

        if (instant != null) {
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

        return null;
    }

    private static Instant getInstantFromDateString(String date) {

        if (date == null) {
            return null;
        }

        // OffsetDateTime used for dates that might not be in UTC, God forbid, but who knows
        OffsetDateTime offsetDateTime = null;
        try {
            offsetDateTime = OffsetDateTime.parse(date);
        } catch (DateTimeException e) {

            String[] dateSplit = date.split("T");
            if (dateSplit.length < 2 || dateSplit[1].contains("+") || dateSplit[1].contains("-") || dateSplit[1].contains("Z")) {
                LOGGER.severe(TextFormatter.formatMessage("Invalid input date {0} couldn't be parsed into a valid Instant", date));
                return null;
            }

            // Farfetch api has an issue in some dates that are missing the timezone, so we add it
            try {
                date = date + "Z";
                offsetDateTime = OffsetDateTime.parse(date);
            } catch (DateTimeException e2) {
                LOGGER.severe(TextFormatter.formatMessage("Invalid input date {0} couldn't be parsed into a valid Instant", date));
                return null;
            }
        }

        return offsetDateTime.toInstant();

    }

    /**
     * Converts a {@link LocalDateTime} to a {@link Date} object using the System default {@link ZoneId}.
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return toDate(localDateTime, ZoneId.systemDefault());
    }

    /**
     * Converts a {@link LocalDateTime} to a {@link Date} object using the given {@link ZoneId}
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    /**
     * Converts a {@link Date} to a {@link LocalDateTime} object using the System default {@link ZoneId}.
     */
    public static LocalDateTime toLocalDateTime(Date dateToConvert) {
        if (dateToConvert == null) {
            return null;
        }
        return toLocalDateTime(dateToConvert, ZoneId.systemDefault());
    }

    /**
     * Converts a {@link Date} to a {@link LocalDateTime} object using the given {@link ZoneId}.
     */
    public static LocalDateTime toLocalDateTime(Date dateToConvert, ZoneId zoneId) {
        if (dateToConvert == null) {
            return null;
        }
        return Instant.ofEpochMilli(dateToConvert.getTime())
            .atZone(zoneId)
            .toLocalDateTime();
    }

    /**
     * Converts Epoch milliseconds to a {@link LocalDateTime} object at the default timezone.
     */
    public static LocalDateTime toLocalDateTime(Long epochMillisseconds) {

        if (epochMillisseconds == null) {
            return null;
        }
        return toLocalDateTime(epochMillisseconds, ZoneId.systemDefault());
    }

    /**
     * Converts Epoch milliseconds to a {@link LocalDateTime} object using the given {@link ZoneId}.
     */
    public static LocalDateTime toLocalDateTime(Long epochMillisseconds, ZoneId zoneId) {

        if (epochMillisseconds == null) {
            return null;
        }

        return Instant.ofEpochMilli(epochMillisseconds)
            .atZone(zoneId)
            .toLocalDateTime();
    }

    /**
     * Returns the number of milliseconds that have passed since the beginning of the Epoch,
     * for this {@link LocalDateTime} using the System default {@link ZoneId}
     */
    public static long toMilliseconds(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return -1L;
        }

        return toDate(localDateTime, ZoneId.systemDefault()).getTime();
    }

    /**
     * Returns the number of milliseconds that have passed since the beginning of the Epoch,
     * for this {@link LocalDateTime} using the given {@link ZoneId}
     */
    public static long toMilliseconds(LocalDateTime localDateTime, ZoneId zoneId) {
        if (localDateTime == null) {
            return -1L;
        }

        return toDate(localDateTime, zoneId).getTime();
    }

    /**
     * Returns the difference between two LocalDatTimes in milliseconds.<br>
     * It will convert both dates to milliseconds and calculate its difference using the System default {@link ZoneId}
     */
    public static long dateDiffInMilliseconds(LocalDateTime endTime, LocalDateTime startTime) {
        return toMilliseconds(endTime) - toMilliseconds(startTime);
    }

    /**
     * Returns the difference between two LocalDatTimes in milliseconds.<br>
     * It will convert both dates to milliseconds and calculate its difference using the given {@link ZoneId}.<br>
     * This implies that both dates are using the same ZoneId.
     */
    public static long dateDiffInMilliseconds(LocalDateTime endTime, LocalDateTime startTime, ZoneId zoneId) {
        return toMilliseconds(endTime, zoneId) - toMilliseconds(startTime, zoneId);
    }

    /**
     * Checks if two LocalDateTime are equals considering only the UTC locale.
     * <br>
     * Both dates are converted to milliseconds and then compared to each other.
     * <br>
     * <br>
     * This function one considers nulls, unlike the standard function.
     *
     */
    public static boolean areEqual(LocalDateTime date, LocalDateTime anotherDate) {

        if (date == null && anotherDate == null) {
            return true;
        }

        if (date == null) {
            return false;
        }

        if (anotherDate == null) {
            return false;
        }

        return DateTimeUtil.toMilliseconds(date, ZoneId.of("UTC")) == (DateTimeUtil.toMilliseconds(anotherDate, ZoneId.of("UTC")));
    }

    /**
     * Checks if two LocalDateTime are different considering only the local time line.
     * <br>
     * <br>
     * Pretty much works on top of {@link java.time.LocalDateTime#isEqual(java.time.chrono.ChronoLocalDateTime)},
     * except that this one considers nulls, unlike the standard function.
     *
     */
    public static boolean areDifferent(LocalDateTime date, LocalDateTime anotherDate) {
        return !areEqual(date, anotherDate);
    }

    /**
     * Converts a LocalDateTime to a String in ISO format (e.g. "2021-03-05T10:12:52.17854Z")
     */
    public static String convertDateToISOString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return DateTimeFormatter.ISO_INSTANT.format(localDateTime.toInstant(ZoneOffset.UTC));
    }
}
