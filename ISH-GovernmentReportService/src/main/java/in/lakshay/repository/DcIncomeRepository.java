package in.lakshay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.lakshay.entity.DcIncomeEntity;

/**
 * Repository for data collection income operations
 */
@Repository
public interface DcIncomeRepository extends JpaRepository<DcIncomeEntity, Integer> {
    
    /**
     * Find income details by case number
     */
    DcIncomeEntity findByCaseNo(Long caseNo);
    
    /**
     * Get average employment income
     */
    @Query("SELECT AVG(i.empIncome) FROM DcIncomeEntity i")
    Double getAverageEmploymentIncome();
    
    /**
     * Get average property income
     */
    @Query("SELECT AVG(i.propertyIncome) FROM DcIncomeEntity i")
    Double getAveragePropertyIncome();
    
    /**
     * Get income distribution by range
     */
    @Query("SELECT " +
           "CASE " +
           "WHEN (i.empIncome + i.propertyIncome) < 15000 THEN '$0-$15,000' " +
           "WHEN (i.empIncome + i.propertyIncome) BETWEEN 15000 AND 30000 THEN '$15,001-$30,000' " +
           "WHEN (i.empIncome + i.propertyIncome) BETWEEN 30001 AND 50000 THEN '$30,001-$50,000' " +
           "WHEN (i.empIncome + i.propertyIncome) BETWEEN 50001 AND 75000 THEN '$75,001-$75,000' " +
           "ELSE '$75,001+' END as incomeRange, " +
           "COUNT(i) FROM DcIncomeEntity i GROUP BY incomeRange")
    List<Object[]> getIncomeDistributionByRange();
}
