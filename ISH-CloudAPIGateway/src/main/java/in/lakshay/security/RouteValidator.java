package in.lakshay.security;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * Route Validator for API Gateway
 * This component determines which routes require authentication
 */
@Component
public class RouteValidator {

    /**
     * List of public endpoints that don't require authentication
     */
    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/login",
            "/user-api/save",
            "/user-api/activate",
            "/user-api/login",
            "/worker-api/save",
            "/worker-api/activate",
            "/worker-api/login",
            "/admin-api/create",
            "/eureka",
            "/actuator"
    );

    /**
     * Predicate to check if a route is secured
     */
    public Predicate<ServerHttpRequest> isSecured =
            request -> {
                String path = request.getURI().getPath();
                System.out.println("Checking security for path: " + path);

                // Always allow these specific endpoints
                if (path.equals("/worker-api/login") ||
                    path.equals("/user-api/login") ||
                    path.equals("/api/auth/login")) {
                    System.out.println("Login endpoint detected - allowing access");
                    return false; // Not secured
                }

                // Check other open endpoints
                boolean secured = openApiEndpoints
                        .stream()
                        .noneMatch(uri -> path.contains(uri));
                System.out.println("Path: " + path + ", Secured: " + secured);
                return secured;
            };
}
