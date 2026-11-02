package com.github.dr3xcode.provacorrida.exceptions;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        //Exibida apenas se for diferente de Null
        String message,
        //Exibida apenas se for diferente de Null
        List<ValidationError> validationErrors
) {
}
