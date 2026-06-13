package com.server.app.config;

import org.springframework.util.AntPathMatcher;

import java.util.Map;
import java.util.Set;

public class SecurityRules {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public static final Map<String, Set<String>> PUBLIC = Map.of(
            "GET", Set.of("/api/public/info"),
            "POST", Set.of("/api/auth/login","/api/auth/signup")
    );

    public static final Map<String, Set<String>> AUTH_ONLY = Map.of(
            "GET", Set.of("/api/auth/profile", "/api/catalogos", "/api/categorias"),
            "POST", Set.of("/api/auth/logout", "/api/catalogos", "/api/categorias"),
            "PUT", Set.of("/api/catalogos/{id}", "/api/categorias/{id}"),
            "DELETE", Set.of("/api/catalogos/{id}", "/api/categorias/{id}")


    );

    public static final Set<String> IGNORED = Set.of("/error");

    public static boolean isPublic(String method, String path) {
        return PUBLIC.containsKey(method) && matches(PUBLIC.get(method), path);
    }

    public static boolean isAuthOnly(String method, String path) {
        return AUTH_ONLY.containsKey(method) && matches(AUTH_ONLY.get(method), path);
    }

    public static boolean isIgnored(String path) {
        return matches(IGNORED, path);
    }

    public static boolean requiresAuth(String method, String path) {
        return !isPublic(method, path) && !isIgnored(path);
    }

    private static boolean matches(Set<String> patterns, String path) {
        return patterns.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, path));
    }
}
