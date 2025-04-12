package in.lakshay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for search inputs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInputs {
    private String category;
    private String format;
    private String startDate;
    private String endDate;
    private String status;
}
