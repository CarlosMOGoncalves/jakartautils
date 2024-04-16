/**
 * Copyright (c) 2024 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class FormatObject {

    /**
     * Formats objects preparing them for an 'IN...' query, by outputting their values between commas and surrounded by apostrophes.
     */
    public static <E> String toSQLInClause(Set<E> objects) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }

        return objects.stream()
            .filter(Objects::nonNull)
            .map(object -> "'" + object.toString() + "'")
            .collect(Collectors.joining(","));
    }

    /**
     * Formats objects preparing them for an 'IN...' query, by outputting their values between commas and surrounded by apostrophes.
     */
    public static <E> String toSQLInClause(List<E> objects) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }

        return objects.stream()
            .filter(Objects::nonNull)
            .map(object -> "'" + object.toString() + "'")
            .collect(Collectors.joining(","));
    }

    /**
     * Formats objects preparing them for an 'IN...' query, by creating the necessary placeholders.
     */
    public static <E> String toSQLInClausePlaceholder(Collection<E> objects) {
        if (objects == null || objects.isEmpty()) {
            return null;
        }

        return objects.stream()
            .filter(Objects::nonNull)
            .map(object -> "?")
            .collect(Collectors.joining(","));
    }

    public static String createPlaceholders(int initialIdx, int numberOfItems) {

        int idx = initialIdx;
        StringBuilder sb = new StringBuilder("(?").append(idx++);

        for (var i = 1; i < numberOfItems; i++) {
            sb.append(",?").append(idx++);
        }
        sb.append(")");
        return sb.toString();

    }

}
