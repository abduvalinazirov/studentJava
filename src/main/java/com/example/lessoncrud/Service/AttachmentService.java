package com.example.lessoncrud.Service;

import com.example.lessoncrud.Entity.Attachment;
import com.example.lessoncrud.Entity.AttachmentStatus;
import com.example.lessoncrud.Repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.UUID;

@Service
public class AttachmentService {
  @Value("${upload.folder}")
  private String uploadFolder;
  private final AttachmentRepository attachmentRepository;

  public AttachmentService(AttachmentRepository attachmentRepository) {
    this.attachmentRepository = attachmentRepository;
  }

  public void saveFile(MultipartFile multipartFile) {
    Attachment attachment = new Attachment();
    attachment.setAttchmentStatus(AttachmentStatus.Draft);
    attachment.setName(multipartFile.getOriginalFilename());
    attachment.setContentType(multipartFile.getContentType());
    attachment.setFileSize(multipartFile.getSize());
    attachment.setExtension(getExtention(multipartFile.getOriginalFilename()));
    attachment.setHashId(UUID.randomUUID().toString());
    attachmentRepository.save(attachment);

    Date now = new Date();
    File file = new File(String.format("%s/upload_Folder/%d/%d/%d",
        this.uploadFolder,
        now.getYear() + 1900,
        now.getMonth() + 1,
        now.getDay()
    ));
    if (!file.exists() && file.mkdirs()) {
      System.out.println("Papka yaratildi");
    }
    attachment.setUploadPath(String.format("upload_folder/%d/%d/%d/%s.%s",
        now.getYear() + 1900,
        now.getMonth() + 1,
        now.getDay(),
        attachment.getHashId(),
        attachment.getExtension()
    ));
    attachmentRepository.save(attachment);
    file = file.getAbsoluteFile();
    File file1 = new File(file, String.format("%s.%s", attachment.getHashId(), attachment.getExtension()));
    try {
      multipartFile.transferTo(file1);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  private String getExtention(String fileName) {
    String name = null;
    if (!fileName.isEmpty()) {
      int index = fileName.lastIndexOf('.');
      name = fileName.substring(index + 1);
    }
    return name;
  }

  public void deleteFile(String hashId) {
    Attachment attachment = attachmentRepository.findByHashId(hashId);
    File file = new File(String.format("%s/%s", this.uploadFolder, attachment.getUploadPath()));
    if (file.delete()) {
      attachmentRepository.delete(attachment);
    }
  }

  @Scheduled(cron = "0 0 0 * * *")
  public void deleteAttachmentByStatus() {
    List<Attachment> attachments = attachmentRepository.findAllByAttchmentStatus(AttachmentStatus.Draft);
    attachments.forEach(attachment -> {
      deleteFile(attachment.getHashId());
    });
  }

}
