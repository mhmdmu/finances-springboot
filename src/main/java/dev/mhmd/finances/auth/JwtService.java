package dev.mhmd.finances.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;
    private final long expirationInSeconds;

    public JwtService(JwtEncoder jwtEncoder,
                      @Value("${auth.jwt.expiration-seconds}") long expirationInSeconds) {
        this.jwtEncoder = jwtEncoder;
        this.expirationInSeconds = expirationInSeconds;
    }

    public String generateToken(Integer id) {
        var claims = JwtClaimsSet.builder()
                .subject(Integer.toString(id))
                .expiresAt(Instant.now().plusSeconds(expirationInSeconds))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
