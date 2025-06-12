package com.example.backend.service.person.contacttype;

import com.example.backend.dto.person.contacttype.ContactTypeDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactTypeService {
    ContactTypeDto create(ContactTypeDto dto);
    ContactTypeDto getById(Integer id);
    List<ContactTypeDto> getAll();
    PagedResponse<ContactTypeDto> getPaginated(Pageable pageable);
    ContactTypeDto update(Integer id, ContactTypeDto dto);
    void delete(Integer id);
}

