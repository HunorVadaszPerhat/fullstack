package com.example.backend.util.sorting;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortUtilsTest {

    @Test
    void singleSortParam_withDirection_shouldReturnCorrectOrder() {
        String[] input = {"city,asc"};

        List<Sort.Order> result = SortUtils.parseSorts(input);

        assertEquals(1, result.size());
        assertEquals("city", result.getFirst().getProperty());
        assertEquals(Sort.Direction.ASC, result.getFirst().getDirection());
    }

    @Test
    void multipleSortParams_shouldReturnMultipleOrders() {
        String[] input = {"city,asc", "postalCode,desc"};

        List<Sort.Order> result = SortUtils.parseSorts(input);

        assertEquals(2, result.size());
        assertEquals("city", result.get(0).getProperty());
        assertEquals(Sort.Direction.ASC, result.get(0).getDirection());
        assertEquals("postalCode", result.get(1).getProperty());
        assertEquals(Sort.Direction.DESC, result.get(1).getDirection());
    }

    @Test
    void missingDirection_shouldDefaultToAsc() {
        String[] input = {"modifiedDate"};

        List<Sort.Order> result = SortUtils.parseSorts(input);

        assertEquals(1, result.size());
        assertEquals("modifiedDate", result.getFirst().getProperty());
        assertEquals(Sort.Direction.ASC, result.getFirst().getDirection()); // default
    }

    @Test
    void invalidDirection_shouldThrowException() {
        String[] input = {"city,UP"};

        assertThrows(IllegalArgumentException.class, () -> SortUtils.parseSorts(input));
    }
}
