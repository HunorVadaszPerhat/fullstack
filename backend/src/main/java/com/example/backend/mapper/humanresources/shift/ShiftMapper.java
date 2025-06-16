package com.example.backend.mapper.humanresources.shift;

import com.example.backend.domain.model.humanresources.shift.Shift;
import com.example.backend.dto.humanresources.shift.ShiftDto;
import org.springframework.stereotype.Component;

@Component
public class ShiftMapper {

    public ShiftDto toDto(Shift shift) {
        return ShiftDto.builder()
                .shiftId(shift.getShiftId())
                .name(shift.getName())
                .startTime(shift.getStartTime())
                .endTime(shift.getEndTime())
                .modifiedDate(shift.getModifiedDate())
                .build();
    }

    public Shift toEntity(ShiftDto dto) {
        return Shift.builder()
                .shiftId(dto.getShiftId())
                .name(dto.getName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
    }

    public void updateEntityFromDto(ShiftDto dto, Shift shift) {
        shift.setName(dto.getName());
        shift.setStartTime(dto.getStartTime());
        shift.setEndTime(dto.getEndTime());
    }
}
