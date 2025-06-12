package com.example.backend.service.person.password;

import com.example.backend.dto.person.password.PasswordDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PasswordService {
    PasswordDto create(PasswordDto dto);
    PasswordDto getById(Integer personId);
    List<PasswordDto> getAll();
    PagedResponse<PasswordDto> getPaginated(Pageable pageable);
    PasswordDto update(Integer personId, PasswordDto dto);
    void delete(Integer personId);
}
