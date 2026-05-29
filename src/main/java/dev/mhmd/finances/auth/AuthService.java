package dev.mhmd.finances.auth;

import dev.mhmd.finances.auth.dto.AuthResponse;
import dev.mhmd.finances.auth.dto.LoginRequest;
import dev.mhmd.finances.auth.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository,
                       JwtService jwtService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public AuthResponse login(LoginRequest request) {
        var principal = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password())).getPrincipal();

        if (principal instanceof User user)
            return new AuthResponse(this.jwtService.generateToken(user.id()));

        throw new BadCredentialsException("Bad principle type");
    }

    public User register(RegisterRequest request) {
        return this.repository.save(request.username().toLowerCase(), this.passwordEncoder.encode(request.password()));
    }
}
