package in.lakshay.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for search results
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResults {
    private Integer id;
    private String name;
    private String category;
    private String format;
    private LocalDateTime createdDate;
    private String status;
    private String description;
}
