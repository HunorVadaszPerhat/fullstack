package com.example.backend.domain.model.person.stateprovince;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "StateProvince", schema = "Person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateProvince {
    @Id
    @Column(name="StateProvinceID")
    @NotNull(message="State Province ID is required")
    private Integer stateProvinceID;
}

