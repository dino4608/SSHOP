package com.dino.backend.features.file.application.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadRes {
    Boolean isUploaded;
    String fileName;
    Instant uploadedAt;
}
