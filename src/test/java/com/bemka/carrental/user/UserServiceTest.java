package com.bemka.carrental.user;

import com.bemka.carrental.common.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    private final String userName = "user@user.com";

    @Test
    public void shouldLoadUserByUsername() {
        //arrange
        final var user = new User();
        when(userRepository.findByEmail(userName)).thenReturn(Optional.of(user));

        //act
        final var result = userService.loadUserByUsername(userName);

        //assert
        assertThat(result).isEqualTo(user);
    }

    @Test
    public void shouldThrowException_when_canNotLoadUserByUsername() {
        //arrange
        when(userRepository.findByEmail(userName)).thenReturn(Optional.empty());

        //act
        //assert
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> userService.loadUserByUsername(userName))
                .withMessage(String.format("User with email %s not found", userName));
    }
}
