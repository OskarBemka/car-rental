package com.bemka.carrental.common.exception;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ExceptionResponse {

    int status;
    String error;
    List<String> messages;
}
