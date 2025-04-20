package in.lakshay.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="WORKER_MASTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workerId;

    @Column(length = 50)
    private String name;

    @Column(length = 20)
    private String password;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    private Long mobileNo;

    private Long ssn;

    @Column(length = 10)
    private String gender;

    private LocalDate dob;

    @Column(length = 50)
    private String designation;

    @Column(length = 100)
    private String helpCenterName;

    @Column(length = 50)
    private String helpCenterLocation;

    @Column(length = 10)
    private String activeSw;

    // MetaData
    @CreationTimestamp
    @Column(updatable = false, insertable = true)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(insertable = false, updatable = true)
    private LocalDateTime updatedOn;

    @Column(length = 50)
    private String createdBy;

    @Column(length = 50)
    private String updatedBy;
}
