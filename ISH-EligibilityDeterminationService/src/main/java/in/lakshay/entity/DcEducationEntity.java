package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="DC_EDUCATION")
@Data
public class DcEducationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer educationId;
	private Long caseNo;
	@Column(length = 40)
	private String highestQlfy;
	private Integer passOutYear;

}
