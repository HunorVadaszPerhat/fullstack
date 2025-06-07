package com.example.backend.util.person.address;

import com.example.backend.domain.model.person.address.Address;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.backend.util.person.address.AddressSpecifications.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class AddressSpecificationsTest {

    private CriteriaBuilder cb;
    private CriteriaQuery<?> query;
    private Root root;

    @BeforeEach
    void setUp() {
        cb = mock(CriteriaBuilder.class);
        query = mock(CriteriaQuery.class);
        root = mock(Root.class);
    }

    @Test
    void hasCity_shouldReturnLikePredicate() {
        // Mocks for city path
        @SuppressWarnings("unchecked")
        Path<String> cityPath = (Path<String>) mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("city")).thenReturn(cityPath);
        when(cb.lower(cityPath)).thenReturn(cityPath);
        when(cb.like(eq(cityPath), eq("%berlin%"))).thenReturn(predicate);

        Predicate result = hasCity("Berlin").toPredicate(root, query, cb);

        assertNotNull(result);
    }

    @Test
    void hasCity_null_shouldReturnNull() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Address> root = mock(Root.class);

        // When no filter is passed, the Specification should produce a null predicate
        assertNull(hasCity(null).toPredicate(root, query, cb));
    }

    @Test
    void hasPostalCode_shouldReturnEqualsPredicate() {
        @SuppressWarnings("unchecked")
        Path<String> zipPath = (Path<String>) mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("postalCode")).thenReturn(zipPath);
        when(cb.equal(zipPath, "10115")).thenReturn(predicate);

        Predicate result = hasPostalCode("10115").toPredicate(root, query, cb);

        assertNotNull(result);
    }

    @Test
    void hasPostalCode_null_shouldReturnNull() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Address> root = mock(Root.class);

        // When no filter is passed, the Specification should produce a null predicate
        assertNull(hasPostalCode(null).toPredicate(root, query, cb));
    }

    @Test
    void hasStateProvinceId_shouldReturnEqualsPredicate() {
        @SuppressWarnings("unchecked")
        Path<Object> spPath = (Path<Object>) mock(Path.class);
        Path<Object> idPath = (Path<Object>) mock(Path.class);
        Predicate predicate = mock(Predicate.class);

        when(root.get("stateProvince")).thenReturn(spPath);
        when(spPath.get("id")).thenReturn(idPath);
        when(cb.equal(idPath, 7)).thenReturn(predicate);

        Predicate result = hasStateProvinceId(7).toPredicate(root, query, cb);

        assertNotNull(result);
    }

    @Test
    void hasStateProvinceId_null_shouldReturnNullPredicate() {
        CriteriaBuilder cb = mock(CriteriaBuilder.class);
        CriteriaQuery<?> query = mock(CriteriaQuery.class);
        Root<Address> root = mock(Root.class);

        // When no filter is passed, the Specification should produce a null predicate
        assertNull(hasStateProvinceId(null).toPredicate(root, query, cb));
    }

}

