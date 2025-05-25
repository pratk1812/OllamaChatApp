package com.ragnarson.StudentMVC.persistence.repo;

import com.ragnarson.StudentMVC.persistence.entity.StudentEntity;
import com.ragnarson.StudentMVC.enums.Major;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<StudentEntity, Long> {
	
	@Query("SELECT s FROM StudentEntity s WHERE "
			+ "(:fromAge IS NULL OR s.age >= :fromAge) AND "
			+ "(:toAge IS NULL OR s.age <= :toAge) AND "
			+ "(:fromGPA IS NULL OR s.gpa >= :fromGPA) AND "
			+ "(:toGPA IS NULL OR s.gpa <= :toGPA) AND "
			+ "(:fromDate IS NULL OR s.enrollmentDate >= :fromDate) AND"
			+ "(:toDate IS NULL OR s.enrollmentDate <= :toDate) AND "
			+ "(:major IS NULL OR s.major= :major)")
	List<StudentEntity> findByParams(
			@Param("fromAge") int fromAge, 
			@Param("toAge") int toAge, 
			@Param("fromGPA") double fromGPA, 
			@Param("toGPA") double toGPA,
			@Param("fromDate") Date fromDate,
			@Param("toDate") Date toDate,
			@Param("major") Major major);
	
	
}
