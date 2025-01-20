/*
 * Copyright (c) 2025 Carlos Gonçalves (https://www.linkedin.com/in/carlosmogoncalves/)
 * Likely open-source, so copy at will, bugs will be yours as well.
 */
package pt.cmg.jakartautils.pagination;

import java.util.List;

/**
 * @author Carlos Gonçalves
 */
public record PaginatedDTO<T>(int total, List<T> items) {
}