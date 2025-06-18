package com.example.backend.domain.model.production.productphoto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ProductPhoto", schema = "Production")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductPhotoID", nullable = false)
    private Integer productPhotoId;

    @Lob
    @Column(name = "ThumbNailPhoto")
    private byte[] thumbNailPhoto;

    @Column(name = "ThumbnailPhotoFileName", length = 50)
    private String thumbnailPhotoFileName;

    @Lob
    @Column(name = "LargePhoto")
    private byte[] largePhoto;

    @Column(name = "LargePhotoFileName", length = 50)
    private String largePhotoFileName;

    @UpdateTimestamp
    @Column(name = "ModifiedDate", nullable = false)
    private LocalDateTime modifiedDate;
}

