package io.shengfq.aop.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

@Service
public class UploadServiceImpl implements UploadService {

  @Autowired
  public FastFileStorageClient fastFileStorageClient;

  @Override
  public String uploadFastDfs(MultipartFile file, String fileExtName) throws IOException {
    StorePath storePath =
        fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileExtName, null);
    return storePath.getFullPath();
  }
}
