package in.lakshay.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.lakshay.entity.CourseDetails;

public interface ICourseDetailsRepository extends JpaRepository<CourseDetails, Integer> {
      
	@Query("select  distinct(courseCategory)  from  CourseDetails")
	 public  Set<String>    getUniqueCourseCategories();
	
	@Query("select  distinct(facultyName)  from  CourseDetails")
	 public  Set<String>    getUniqueFacultyNames();
	
	@Query("select  distinct(trainingMode)  from  CourseDetails")
	 public  Set<String>    getUniqueTraniningModes();
	
	
	 
}
