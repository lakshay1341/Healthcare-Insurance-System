package in.lakshay.binding;

import lombok.Data;

@Data
public class COSummary {
    private Integer totalTriggers;
    private Integer successTriggers;
    private Integer pendingTriggers;

}
