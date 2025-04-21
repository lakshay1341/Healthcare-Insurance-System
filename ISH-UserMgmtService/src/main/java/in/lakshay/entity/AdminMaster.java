package in.lakshay.entity;

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
@Table(name="ADMIN_MASTER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @Column(length = 50)
    private String name;

    @Column(length = 100)
    private String password;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 20)
    private String role = "ROLE_ADMIN";

    @Column(length = 10)
    private String activeSw = "Active";

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
