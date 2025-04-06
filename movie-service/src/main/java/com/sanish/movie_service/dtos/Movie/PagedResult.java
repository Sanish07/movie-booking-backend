package com.sanish.movie_service.dtos.Movie;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagedResult<T> {
    List<T> data;
    long totalElements;
    int pageNumber;
    int totalPages;
    boolean isFirst;
    boolean isLast;
    boolean hasNext;
    boolean hasPrevious;
}
