package com.example.api_gateway.security;

import com.example.api_gateway.util.AppConstant;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtGatewayFilter implements GatewayFilter, Ordered {

    private final JwtUtil jwtUtil;

    public JwtGatewayFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    /*
    This filter will check the authorization of the request on api gateway itself,
    if request is not authorized to access to resource, it won't redirect it any service
    In this filter we are not using HttpRequest & HttpResponse because here we are working with reactive web,
     which is non-blocking async that's why instead of HttpRequest, Response we use ServerWebExchange
    1. Fetch the path and method from the request
    2. Check if, the requested path is in the PUBLIC_URL(like /login, /register) or not.
    3. Fetch the authorization header and check if it starts with "Bearer " and if yes then extract token with substring
    4. Then check for role, path, method using isAuthorized method.
    5. If authorized then edit the request, set role in Header and pass for next filter.
    */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        if(AppConstant.PUBLIC_URLS.stream().anyMatch((path::contains))){
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return unauthorized(exchange);
        }
        String token = authHeader.substring(7);
        if(!jwtUtil.validateToken(token)){
            return unauthorized(exchange);
        }
        String role = jwtUtil.getRole(token);
        if(!isAuthorized(role, path, method)){
            return forbidden(exchange);
        }
        String userId = jwtUtil.getUserId(token);
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User-Role", role).build();
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

     /* It will check first whether user have authorized role or not
     After that it will check whether user is authorized to use this method
     At last it will check whether user is authorized to access this path/url/api */

    private boolean isAuthorized(String role, String path, String method) {
        Map<String, List<String>> permissions = AppConstant.ROLE_API_PERMISSIONS.get(role);
        if(permissions == null) return false;
        List<String> allowedPaths = permissions.get(method);
        if(allowedPaths == null) return false;
        return allowedPaths.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange){
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
