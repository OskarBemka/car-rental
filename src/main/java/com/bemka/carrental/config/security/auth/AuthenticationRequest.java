package com.bemka.carrental.config.security.auth;

import jakarta.validation.constraints.Email;

public record AuthenticationRequest(@Email(message = "Email address is incorrect!")
                                    String email,
                                    String password) {
}
