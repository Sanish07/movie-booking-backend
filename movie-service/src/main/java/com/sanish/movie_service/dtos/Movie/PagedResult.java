package com.sanish.movie_service.dtos.Movie;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isFirst")
    boolean isFirst;

    @JsonProperty("isLast")
    boolean isLast;

    @JsonProperty("hasNext")
    boolean hasNext;

    @JsonProperty("hasPrevious")
    boolean hasPrevious;
}
