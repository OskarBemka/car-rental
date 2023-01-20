package com.bemka.carrental.config.security.auth;

import com.bemka.carrental.config.security.config.JwtService;
import com.bemka.carrental.user.User;
import com.bemka.carrental.user.UserRepository;
import com.bemka.carrental.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        final var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .userRole(UserRole.USER)
                .build();
        repository.save(user);
        final var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        final var user = repository.findByEmail(request.email())
                .orElseThrow();
        final var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
