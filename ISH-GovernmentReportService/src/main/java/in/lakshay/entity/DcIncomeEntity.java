package in.lakshay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="DC_INCOME")
@Data
public class DcIncomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer incomeId;
    private Long caseNo;
    private Double empIncome;
    private Double propertyIncome;
}
