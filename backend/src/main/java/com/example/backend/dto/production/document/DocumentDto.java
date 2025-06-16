package com.example.backend.dto.production.document;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDto {
    private String documentNode;
    private String title;
    private Integer ownerId;
    private Boolean folderFlag;
    private String fileName;
    private String fileExtension;
    private String revision;
    private Integer changeNumber;
    private Byte status;
    private String documentSummary;
    private byte[] document;
    private UUID rowguid;
    private LocalDateTime modifiedDate;
}
