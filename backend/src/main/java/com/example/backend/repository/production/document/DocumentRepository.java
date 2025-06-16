package com.example.backend.repository.production.document;

import com.example.backend.domain.model.production.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, String> {
}

