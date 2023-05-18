package com.bemka.carrental.config.security.auth;

import com.bemka.carrental.config.security.config.JwtService;
import com.bemka.carrental.user.User;
import com.bemka.carrental.user.UserRepository;
import com.bemka.carrental.user.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void shouldRegister() {
        //arrange
        final var token = "token";
        final var registerRequest = new RegisterRequest("admin@admin.com", "admin");
        when(passwordEncoder.encode(registerRequest.password())).thenReturn("admin");
        final var user = User.builder()
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .userRole(UserRole.USER)
                .build();
        when(jwtService.generateToken(user)).thenReturn(token);

        //act
        final var result = authenticationService.register(registerRequest);

        //assert
        assertThat(result.token()).isEqualTo(token);
    }

    @Test
    public void shouldAuthenticate() {
        //arrange
        final var token = "token";
        final var user = User.builder()
                .email("admin@admin.com")
                .password("admin")
                .userRole(UserRole.USER)
                .build();
        final var authenticationRequest = new AuthenticationRequest("admin@admin.com", "admin");
        when(repository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(token);

        //act
        final var result = authenticationService.authenticate(authenticationRequest);

        //assert
        assertThat(result.token()).isEqualTo(token);
    }

    @Test
    public void shouldThrowException_when_canNotAuthenticate() {
        //arrange
        final var authenticationRequest = new AuthenticationRequest("admin@admin.com", "admin");
        when(repository.findByEmail("admin@admin.com")).thenReturn(Optional.empty());

        //act
        //assert
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> authenticationService.authenticate(authenticationRequest))
                .withMessage("User not found!");
    }
}
