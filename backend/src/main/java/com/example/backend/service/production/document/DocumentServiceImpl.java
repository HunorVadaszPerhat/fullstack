package com.example.backend.service.production.document;

import com.example.backend.domain.model.production.culture.Culture;
import com.example.backend.domain.model.production.document.Document;
import com.example.backend.dto.production.culture.CultureDto;
import com.example.backend.dto.production.document.DocumentDto;
import com.example.backend.mapper.production.document.DocumentMapper;
import com.example.backend.mapper.production.document.DocumentResolver;
import com.example.backend.repository.production.document.DocumentRepository;
import com.example.backend.util.response.PagedResponse;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.backend.constants.CacheNames.*;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository repository;
    private final DocumentMapper mapper;
    private final DocumentResolver resolver;

    @Override
    @Caching(evict = {
            @CacheEvict(value = DOCUMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DOCUMENTS_GET_BY_ID, key = "#dto.documentNode")
    })
    @Timed(value = "document.create")
    public DocumentDto create(DocumentDto dto) {
        Document entity = mapper.toEntity(dto, resolver.resolveOwner(dto.getOwnerId()));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Cacheable(value = DOCUMENTS_GET_BY_ID, key = "#id")
    @Timed(value = "document.get-by-id", description = "Time taken to get document by ID")
    public DocumentDto getById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));
    }

    @Override
    @Cacheable(value = DOCUMENTS_GET_ALL)
    @Timed(value = "document.get-all")
    public List<DocumentDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    @Cacheable(value = SEARCH_DOCUMENTS, key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
    @Timed(value = "document.get-paginated")
    public PagedResponse<DocumentDto> getPaginated(Pageable pageable) {
        Page<Document> page = repository.findAll(pageable);
        List<DocumentDto> content = page.getContent().stream()
                .map(mapper::toDto)
                .toList();
        return new PagedResponse<>(content, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages(), page.isLast());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = DOCUMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DOCUMENTS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_DOCUMENTS, allEntries = true)
    })
    @Timed(value = "document.update")
    public DocumentDto update(String id, DocumentDto dto) {
        Document entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found"));

        mapper.updateEntityFromDto(dto, entity, resolver.resolveOwner(dto.getOwnerId()));
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = DOCUMENTS_GET_ALL, allEntries = true),
            @CacheEvict(value = DOCUMENTS_GET_BY_ID, key = "#id"),
            @CacheEvict(value = SEARCH_DOCUMENTS, allEntries = true)
    })
    @Timed(value = "document.delete")
    public void delete(String id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Document not found");
        repository.deleteById(id);
    }
}
