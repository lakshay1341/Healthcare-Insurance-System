package in.lakshay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * JWT Authentication Filter for API Gateway
 * This filter validates JWT tokens for protected routes
 */
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        System.out.println("Processing request: " + path);

        // Explicitly bypass authentication for login endpoints
        if (path.equals("/worker-api/login") ||
            path.equals("/user-api/login") ||
            path.equals("/api/auth/login")) {
            System.out.println("Bypassing authentication for: " + path);
            return chain.filter(exchange);
        }

        // Check if this is a public endpoint
        boolean isSecured = routeValidator.isSecured.test(request);
        System.out.println("Is secured endpoint: " + isSecured);

        // Print all open API endpoints for debugging
        System.out.println("Open API endpoints: " + routeValidator.openApiEndpoints);

        // Skip validation for non-secured routes
        if (isSecured) {
            System.out.println("Secured endpoint - checking for Authorization header");
            // Check if Authorization header exists
            if (!request.getHeaders().containsKey("Authorization")) {
                System.out.println("Authorization header is missing");
                return onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
            }

            // Get token from Authorization header
            String authHeader = request.getHeaders().getFirst("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("Invalid Authorization header format: " + authHeader);
                return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
            }

            // Extract token
            String token = authHeader.substring(7);
            System.out.println("Token extracted, validating...");

            try {
                // Validate token
                if (!jwtUtil.validateToken(token)) {
                    System.out.println("Token validation failed");
                    return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
                }

                // Extract user details from token
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                System.out.println("Token validated for user: " + username + " with role: " + role);

                // Add user details to request headers
                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-Auth-User", username)
                        .header("X-Auth-Role", role)
                        .build();

                // Continue with modified request
                System.out.println("Proceeding with authenticated request");
                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (Exception e) {
                System.out.println("Token validation error: " + e.getMessage());
                e.printStackTrace();
                return onError(exchange, "Invalid token: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
        }

        // Continue with original request for non-secured routes
        System.out.println("Public endpoint - proceeding without authentication");
        return chain.filter(exchange);
    }

    /**
     * Handle authentication errors
     *
     * @param exchange ServerWebExchange
     * @param message  Error message
     * @param status   HTTP status
     * @return Mono<Void>
     */
    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // High priority
    }
}
