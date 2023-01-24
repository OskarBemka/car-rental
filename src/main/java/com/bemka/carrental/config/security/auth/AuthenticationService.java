package com.bemka.carrental.config.security.auth;

import com.bemka.carrental.common.exception.BadRequestException;
import com.bemka.carrental.config.security.config.JwtService;
import com.bemka.carrental.user.User;
import com.bemka.carrental.user.UserRepository;
import com.bemka.carrental.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        validateUserEmail(request);
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        final var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    private void validateUserEmail(RegisterRequest request) {
        final var registeredUser = repository.findByEmail(request.email());
        if (registeredUser.isPresent()) {
            throw new BadRequestException(String.format("User with email %s already exist!", request.email()));
        }
    }
}
