package in.lakshay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.lakshay.entity.CitizenAppRegistrationEntity;

/**
 * Repository for citizen application operations
 */
@Repository
public interface CitizenAppRegistrationRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer> {

    /**
     * Find applications by state name
     */
    List<CitizenAppRegistrationEntity> findByStateName(String stateName);

    /**
     * Find applications by creation date between
     */
    List<CitizenAppRegistrationEntity> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Count applications by state name
     */
    Long countByStateName(String stateName);

    /**
     * Count applications by gender
     */
    Long countByGender(String gender);

    /**
     * Count applications created between dates
     */
    Long countByCreationDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Get applications count by state
     */
    @Query("SELECT c.stateName, COUNT(c) FROM CitizenAppRegistrationEntity c GROUP BY c.stateName")
    List<Object[]> getApplicationCountByState();

    /**
     * Get applications count by month
     */
    @Query("SELECT FUNCTION('MONTH', c.creationDate) as month, COUNT(c) FROM CitizenAppRegistrationEntity c WHERE c.creationDate BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', c.creationDate)")
    List<Object[]> getApplicationCountByMonth(LocalDate startDate, LocalDate endDate);

    /**
     * Get applications count by age group
     */
    @Query("SELECT "+
           "CASE "+
           "WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE) < 18 THEN '0-17' "+
           "WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE) BETWEEN 18 AND 25 THEN '18-25' "+
           "WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE) BETWEEN 26 AND 40 THEN '26-40' "+
           "WHEN TIMESTAMPDIFF(YEAR, c.dob, CURRENT_DATE) BETWEEN 41 AND 65 THEN '41-65' "+
           "ELSE '65+' END as ageGroup, "+
           "COUNT(c) FROM CitizenAppRegistrationEntity c GROUP BY ageGroup")
    List<Object[]> getApplicationCountByAgeGroup();
}
