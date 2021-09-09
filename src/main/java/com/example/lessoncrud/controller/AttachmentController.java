package com.example.lessoncrud.controller;

import com.example.lessoncrud.Entity.Attachment;
import com.example.lessoncrud.Repository.AttachmentRepository;
import com.example.lessoncrud.Service.AttachmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class AttachmentController {
  @Value("${upload.folder}")
  private String uploadFolder;
  private final AttachmentService attachmentService;
  private final AttachmentRepository attachmentRepository;

  public AttachmentController(AttachmentService attachmentService, AttachmentRepository attachmentRepository) {
    this.attachmentService = attachmentService;
    this.attachmentRepository = attachmentRepository;
  }

  @PostMapping("/uploadFile")
  private ResponseEntity uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {
    attachmentService.saveFile(multipartFile);
    return ResponseEntity.ok(multipartFile.getName() + " File saqlandi");
  }

  @DeleteMapping("/deleteFile/{hashId}")
  private ResponseEntity deleteFile(@PathVariable String hashId) {
    System.out.println("sal");
    attachmentService.deleteFile(hashId);
    return ResponseEntity.ok("File o'chirildi");
  }

  @GetMapping("/privew/{hashId}")
  private ResponseEntity previewFile(@PathVariable String hashId) throws MalformedURLException {
    Attachment attachment = attachmentRepository.findByHashId(hashId);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(attachment.getName()))
        .contentType(MediaType.parseMediaType(attachment.getContentType()))
        .body(new FileUrlResource(String.format("%s/%s",
            uploadFolder,
            attachment.getUploadPath())));
  }

  @GetMapping("/download/{hashId}")
  private ResponseEntity downloadFile(@PathVariable String hashId) throws MalformedURLException {
    Attachment attachment = attachmentRepository.findByHashId(hashId);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(attachment.getName()))
        .contentType(MediaType.parseMediaType(attachment.getContentType()))
        .body(new FileUrlResource(String.format("%s/%s",
            uploadFolder,
            attachment.getUploadPath())));
  }

}
