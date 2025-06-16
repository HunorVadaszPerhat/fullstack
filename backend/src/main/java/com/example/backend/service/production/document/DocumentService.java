package com.example.backend.service.production.document;

import com.example.backend.dto.production.document.DocumentDto;
import com.example.backend.util.response.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentService {
    DocumentDto create(DocumentDto dto);
    DocumentDto getById(String id);
    List<DocumentDto> getAll();
    PagedResponse<DocumentDto> getPaginated(Pageable pageable);
    DocumentDto update(String id, DocumentDto dto);
    void delete(String id);
}
