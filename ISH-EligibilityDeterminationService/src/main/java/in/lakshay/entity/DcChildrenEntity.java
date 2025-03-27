package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="DC_CHILDREN")
@Data
public class DcChildrenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer childId;
	private Integer caseNo;
	private LocalDate childDOB;
	private Long childSSN;

}
