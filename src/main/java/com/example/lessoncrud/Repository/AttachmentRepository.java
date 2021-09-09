  package com.example.lessoncrud.Repository;

  import com.example.lessoncrud.Entity.Attachment;
  import com.example.lessoncrud.Entity.AttachmentStatus;
  import org.springframework.data.jpa.repository.JpaRepository;
  import org.springframework.stereotype.Repository;

  import java.util.List;


  @Repository
  public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Attachment findByHashId(String hashId);

    List<Attachment> findAllByAttchmentStatus(AttachmentStatus attachmentStatus);
  }