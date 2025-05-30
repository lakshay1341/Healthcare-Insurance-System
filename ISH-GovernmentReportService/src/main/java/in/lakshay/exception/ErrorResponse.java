package in.lakshay.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response model
 */
@Data
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    /**
     * Constructor with all fields
     */
    public ErrorResponse(int status, String message, long timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
