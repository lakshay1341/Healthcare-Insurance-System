package in.lakshay.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Error Handler for API Gateway
 * This handler provides JSON responses for all errors
 */
@Component
@Order(-2) // High precedence
public class GlobalErrorHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        // Set content type to JSON
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // Log the error for debugging
        System.out.println("Error handling request: " + exchange.getRequest().getPath().value());
        System.out.println("Error message: " + ex.getMessage());
        ex.printStackTrace();

        // Determine HTTP status
        HttpStatus httpStatus;
        if (ex instanceof ResponseStatusException) {
            httpStatus = (HttpStatus) ((ResponseStatusException) ex).getStatusCode();
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Set status code
        response.setStatusCode(httpStatus);

        // Create error response
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", httpStatus.value());
        errorDetails.put("error", httpStatus.getReasonPhrase());
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", exchange.getRequest().getPath().value());
        errorDetails.put("timestamp", new java.util.Date().toString());

        try {
            // Convert error details to JSON
            String errorJson = objectMapper.writeValueAsString(errorDetails);
            DataBuffer buffer = response.bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            // Fallback if JSON processing fails
            DataBuffer buffer = response.bufferFactory().wrap("Internal Server Error".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
    }
}
