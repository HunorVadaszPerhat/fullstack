package com.example.backend.domain.model.production.document;

import com.example.backend.domain.model.humanresources.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "Document", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @Column(name = "DocumentNode", nullable = false, columnDefinition = "hierarchyid")
    private String documentNode;

    @Column(name = "Title", nullable = false, length = 50)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Owner", nullable = false)
    private Employee owner;

    @Column(name = "FolderFlag", nullable = false)
    private Boolean folderFlag = false;

    @Column(name = "FileName", nullable = false, length = 400)
    private String fileName;

    @Column(name = "FileExtension", nullable = false, length = 8)
    private String fileExtension;

    @Column(name = "Revision", nullable = false, length = 5)
    private String revision;

    @Column(name = "ChangeNumber", nullable = false)
    private Integer changeNumber = 0;

    @Column(name = "Status", nullable = false)
    private Byte status;

    @Lob
    @Column(name = "DocumentSummary")
    private String documentSummary;

    @Lob
    @Column(name = "Document")
    private byte[] document;

    @Column(name = "rowguid", nullable = false, unique = true)
    private UUID rowguid;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

