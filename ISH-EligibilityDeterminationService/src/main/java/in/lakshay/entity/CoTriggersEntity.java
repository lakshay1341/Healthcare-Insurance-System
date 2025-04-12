package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="CO_TRIGGERS")
@Data
@Entity
public class CoTriggersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer triggerId;
    private Long caseNo;
    @Lob
    private Byte[] coNoticePdf;
    @Column(length = 30)
    private String triggerStatus="pending";

}
