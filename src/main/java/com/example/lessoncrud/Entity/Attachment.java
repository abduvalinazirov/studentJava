package com.example.lessoncrud.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Attachment implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String extension;
  private String hashId;
  private String uploadPath;
  private String contentType;
  private Long fileSize;

  @Enumerated(EnumType.STRING)
  private AttachmentStatus attchmentStatus;

}
