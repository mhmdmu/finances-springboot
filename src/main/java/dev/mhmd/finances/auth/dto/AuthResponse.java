package dev.mhmd.finances.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(@JsonProperty("access_token") String token) {
    @JsonProperty("token_type")
    public String tokenType() {
        return "bearer";
    }
}
