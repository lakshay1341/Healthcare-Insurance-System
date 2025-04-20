package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name="CITIZEN_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {

	@Id
	@SequenceGenerator(name="gen1_seq",sequenceName="app_id_seq",initialValue=1000,allocationSize=1)
	@GeneratedValue(generator="gen1_seq",strategy=GenerationType.SEQUENCE)
	private Integer appId;

	@Column(length=30)
	private String fullName;
	@Column(length=30)
	private String email;
	@Column(length=1)
	private String gender;
	private Long phoneNo;
	private Long ssn;
	@Column(length=255)
	private String stateName;
	private LocalDate dob;
	@Column(length=30)
	private String createdBy;
	@Column(length=30)
	private String updatedBy;
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate creationDate;
	@UpdateTimestamp
	@Column(updatable = false)
	private LocalDate updationDate;

}
