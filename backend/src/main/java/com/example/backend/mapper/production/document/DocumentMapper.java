package com.example.backend.mapper.production.document;

import com.example.backend.domain.model.humanresources.employee.Employee;
import com.example.backend.domain.model.production.document.Document;
import com.example.backend.dto.production.document.DocumentDto;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentDto toDto(Document entity) {
        if (entity == null) return null;

        return DocumentDto.builder()
                .documentNode(entity.getDocumentNode())
                .title(entity.getTitle())
                .ownerId(entity.getOwner() != null ? entity.getOwner().getBusinessEntityId() : null)
                .folderFlag(entity.getFolderFlag())
                .fileName(entity.getFileName())
                .fileExtension(entity.getFileExtension())
                .revision(entity.getRevision())
                .changeNumber(entity.getChangeNumber())
                .status(entity.getStatus())
                .documentSummary(entity.getDocumentSummary())
                .document(entity.getDocument())
                .rowguid(entity.getRowguid())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public Document toEntity(DocumentDto dto, Employee owner) {
        if (dto == null) return null;

        return Document.builder()
                .documentNode(dto.getDocumentNode())
                .title(dto.getTitle())
                .owner(owner)
                .folderFlag(dto.getFolderFlag())
                .fileName(dto.getFileName())
                .fileExtension(dto.getFileExtension())
                .revision(dto.getRevision())
                .changeNumber(dto.getChangeNumber())
                .status(dto.getStatus())
                .documentSummary(dto.getDocumentSummary())
                .document(dto.getDocument())
                .rowguid(dto.getRowguid())
                .modifiedDate(dto.getModifiedDate())
                .build();
    }

    public void updateEntityFromDto(DocumentDto dto, Document entity, Employee owner) {
        entity.setTitle(dto.getTitle());
        entity.setOwner(owner);
        entity.setFolderFlag(dto.getFolderFlag());
        entity.setFileName(dto.getFileName());
        entity.setFileExtension(dto.getFileExtension());
        entity.setRevision(dto.getRevision());
        entity.setChangeNumber(dto.getChangeNumber());
        entity.setStatus(dto.getStatus());
        entity.setDocumentSummary(dto.getDocumentSummary());
        entity.setDocument(dto.getDocument());
    }
}

