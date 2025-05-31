package com.dino.backend.features.file.application.provider;

import com.dino.backend.features.file.application.model.UploadRes;
import org.springframework.web.multipart.MultipartFile;

public interface IFileProvider {

    // COMMAND //

    UploadRes upload(MultipartFile file, String folder);
}
