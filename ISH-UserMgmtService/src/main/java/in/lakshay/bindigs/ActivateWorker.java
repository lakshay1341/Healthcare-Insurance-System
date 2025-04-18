package in.lakshay.bindigs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivateWorker {
    private String email;
    private String tempPassword;
    private String newPassword;
    private String confirmPassword;
}
