package com.example.backend.service.humanresources.shift;

import com.example.backend.dto.humanresources.shift.ShiftDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShiftService {
    ShiftDto getById(Byte id);
    List<ShiftDto> getAll();
    PagedResponse<ShiftDto> getPaginated(Pageable pageable);
    ShiftDto create(ShiftDto dto);
    ShiftDto update(Byte id, ShiftDto dto);
    void delete(Byte id);
}
