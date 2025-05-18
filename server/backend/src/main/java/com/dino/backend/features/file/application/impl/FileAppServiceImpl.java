package com.dino.backend.features.file.application.impl;

import com.dino.backend.features.file.application.IFileAppService;
import com.dino.backend.features.file.provider.IFileProvider;
import com.dino.backend.features.file.application.model.UploadRes;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileAppServiceImpl implements IFileAppService {

    IFileProvider fileProvider;

    // COMMAND //

    // upload //
    @Override
    public UploadRes upload(MultipartFile file, String folder) {
        return this.fileProvider.upload(file, folder);
    }
}
