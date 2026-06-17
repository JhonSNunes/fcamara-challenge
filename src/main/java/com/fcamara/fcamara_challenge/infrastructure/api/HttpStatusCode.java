package com.fcamara.fcamara_challenge.infrastructure.api;

import org.springframework.http.HttpStatus;

public final class HttpStatusCode {

    public static final String OK = "200";
    public static final String CREATED = "201";
    public static final String NOT_FOUND = "404";
    public static final String UNPROCESSABLE_ENTITY = "422";

    static {
        assertMatches(HttpStatus.OK, OK);
        assertMatches(HttpStatus.CREATED, CREATED);
        assertMatches(HttpStatus.NOT_FOUND, NOT_FOUND);
        assertMatches(HttpStatus.UNPROCESSABLE_ENTITY, UNPROCESSABLE_ENTITY);
    }

    private HttpStatusCode() {
    }

    private static void assertMatches(HttpStatus status, String code) {
        if (!code.equals(String.valueOf(status.value()))) {
            throw new ExceptionInInitializerError(
                    "Code %s does not match %s".formatted(code, status)
            );
        }
    }
}
