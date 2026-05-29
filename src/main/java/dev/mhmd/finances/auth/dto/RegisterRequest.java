package dev.mhmd.finances.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        @NotBlank
        @Length(min = 4, max = 20, message = "Username length must be between 4 and 20 letters")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username must start with a letter and contain only letters, numbers, and underscores")
        @Pattern(regexp = "^(?!.*__).*$", message = "Consecutive underscores in username are not allowed")
        String username,

        @NotBlank
        @Length(min = 8, max = 64, message = "Password length must be between 8 and 64 letters")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain lowercase letter")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain uppercase letter")
        @Pattern(regexp = ".*\\d.*", message = "Password must contain a digit")
        String password) {
}
