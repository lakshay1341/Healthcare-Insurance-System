package in.lakshay.bindigs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerAccount {
    private Integer workerId;
    private String name;
    private String email;
    private Long mobileNo;
    private String gender;
    private LocalDate dob;
    private Long ssn;
    private String designation;
    private String helpCenterName;
    private String helpCenterLocation;
}
