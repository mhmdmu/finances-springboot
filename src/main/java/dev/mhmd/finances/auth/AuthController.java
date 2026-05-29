package dev.mhmd.finances.auth;

import dev.mhmd.finances.auth.dto.AuthResponse;
import dev.mhmd.finances.auth.dto.LoginRequest;
import dev.mhmd.finances.auth.dto.RegisterRequest;
import dev.mhmd.finances.auth.dto.UserDto;
import dev.mhmd.finances.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> loginHandler(@Valid @RequestBody LoginRequest request) {
        var response = this.authService.login(request);
        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> registerHandler(@Valid @RequestBody RegisterRequest request) {
        var newUser = this.authService.register(request);
        var newUserDto = new UserDto(newUser.id(), newUser.username());

        return new ResponseEntity<>(ApiResponse.success(newUserDto), HttpStatus.CREATED);
    }
}
