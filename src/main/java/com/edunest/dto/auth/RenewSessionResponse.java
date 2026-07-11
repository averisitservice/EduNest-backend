package com.edunest.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RenewSessionResponse {

    private Token token;

    @Data
    @AllArgsConstructor
    public static class Token {
        private String session;
    }
}
