package com.dino.backend.features.file.application;

import com.dino.backend.features.file.application.model.UploadRes;
import org.springframework.web.multipart.MultipartFile;

public interface IFileAppService {

    // COMMAND //

    UploadRes upload(MultipartFile file, String folder);

}
