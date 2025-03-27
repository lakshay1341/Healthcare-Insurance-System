package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="DC_CASES")
@Data
public class DcCaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer caseNo;
	private Integer appId;
	private Integer planId;

}
