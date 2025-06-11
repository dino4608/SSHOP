package com.dino.backend.features.file.application.service;

import com.dino.backend.features.file.application.model.UploadRes;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    // COMMAND //

    UploadRes upload(MultipartFile file, String folder);

}
