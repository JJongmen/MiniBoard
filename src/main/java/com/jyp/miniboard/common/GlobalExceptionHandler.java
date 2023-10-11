package com.jyp.miniboard.common;

import com.jyp.miniboard.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request) {

        final List<String> errorList = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        log.warn("Invalid DTO parameters errors : {}", errorList);
        return this.makeErrorResponseEntity(errorList.toString());
    }

    private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), errorDescription));
    }
    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ErrorResponse> handleRestApiException(final BaseException exception) {
        log.warn("{} occur: ", exception.getClass().getSimpleName(), exception);
        return this.makeErrorResponseEntity(exception.getErrorResult());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(final AccessDeniedException exception) {
        log.warn("AccessDeniedException occur: ", exception);
        return this.makeErrorResponseEntity(MemberErrorResult.ACCESS_DENIED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.warn("Exception occur: ", exception);
        return this.makeErrorResponseEntity(MemberErrorResult.UNKNOWN_EXCEPTION);
    }

    private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final BaseErrorResult errorResult) {
        return ResponseEntity.status(errorResult.getHttpStatus())
                .body(new ErrorResponse(errorResult.name(), errorResult.getMessage()));
    }

    record ErrorResponse(
            String code,
            String message) {
    }
}
