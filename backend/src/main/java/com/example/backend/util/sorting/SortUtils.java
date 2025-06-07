package com.example.backend.util.sorting;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class SortUtils {
    public static List<Sort.Order> parseSorts(String[] sortParams) {
        return Arrays.stream(sortParams)
                .map(s -> {
                    String[] parts = s.split(",");
                    return new Sort.Order(
                            parts.length > 1 ? Sort.Direction.fromString(parts[1]) : Sort.Direction.ASC,
                            parts[0]
                    );
                })
                .toList();
    }
}
