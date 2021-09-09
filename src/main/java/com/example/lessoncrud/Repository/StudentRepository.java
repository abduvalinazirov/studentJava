package com.example.lessoncrud.Repository;

import com.example.lessoncrud.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Student findByName(String name);

  @Query("select e from Student e where e.name =:name")
  Student findByNameQuery(String name);

  List<Student> findByUsername(String username);

//  @Query("select e from Student e where e.name like concat('%',:name,'%')")
  List<Student> findAllByNameLike(@Param("name") String name);

  List<Student> findAllByLastnameStartingWith(String lastname);
}
