package com.github.dr3xcode.provacorrida.exceptions;

public record ValidationError(
    String field,
    String message
) {
}
