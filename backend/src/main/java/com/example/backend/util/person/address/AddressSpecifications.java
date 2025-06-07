package com.example.backend.util.person.address;

import com.example.backend.domain.model.person.address.Address;
import org.springframework.data.jpa.domain.Specification;

/**
 * This class defines reusable dynamic filter conditions (Specifications) for the Address entity.
 * Each method returns a Specification that can be combined using .and() / .or().
 */
public class AddressSpecifications {

    /**
     * Filters by city name using case-insensitive partial match.
     */
    public static Specification<Address> hasCity(String city) {
        return (root, query, cb) ->
                city == null ? null :
                        cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    /**
     * Filters by exact postal code (case-sensitive, exact match).
     */
    public static Specification<Address> hasPostalCode(String postalCode) {
        return (root, query, cb) ->
                postalCode == null ? null :
                        cb.equal(root.get("postalCode"), postalCode);
    }

    /**
     * Filters by associated StateProvince ID.
     * Requires a join from Address to StateProvince.
     */
    public static Specification<Address> hasStateProvinceId(Integer stateProvinceId) {
        return (root, query, cb) ->
                stateProvinceId == null ? null :
                        cb.equal(root.get("stateProvince").get("id"), stateProvinceId);
    }
}

