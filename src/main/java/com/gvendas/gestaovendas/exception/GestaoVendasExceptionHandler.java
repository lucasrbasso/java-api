package com.gvendas.gestaovendas.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GestaoVendasExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String CONSTANT_VALIDATION_NOT_BLANK = "NotBlank";
  private static final String CONSTANT_VALIDATION_MIN_LENGTH = "Length";
  private static final String CONSTANT_VALIDATION_NOT_NULL = "NotNull";
  private static final String CONSTANT_VALIDATION_NOT_PATTERN = "Pattern";
  private static final String CONSTANT_VALIDATION_MIN = "Min";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<Error> errors = gerarListaDeErros(ex.getBindingResult());
    return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
  }

  private List<Error> gerarListaDeErros(BindingResult bindingResult) {
    List<Error> erros = new ArrayList<Error>();

    bindingResult.getFieldErrors().forEach(fieldError -> {
      String msgUsuario = tratarMensagemDeErroParaUsuario(fieldError);
      String msgDesenvolvedor = fieldError.toString();
      erros.add(new Error(msgUsuario, msgDesenvolvedor));
    });

    return erros;
  }

  private String tratarMensagemDeErroParaUsuario(FieldError fieldError) {
    if (Objects.equals(fieldError.getCode(), CONSTANT_VALIDATION_NOT_BLANK)) {
      return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" é obrigatório.");
    }
    if (Objects.equals(fieldError.getCode(), CONSTANT_VALIDATION_NOT_NULL)) {
      return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" é obrigatório.");
    }
    if (Objects.equals(fieldError.getCode(), CONSTANT_VALIDATION_MIN_LENGTH)) {
      return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(
        String.format(" deve ter entre %s e %s caracteres.",
          Objects.requireNonNull(fieldError.getArguments())[2],
          fieldError.getArguments()[1])
      );
    }
    if (Objects.equals(fieldError.getCode(), CONSTANT_VALIDATION_NOT_PATTERN)) {
      return Objects.requireNonNull(fieldError.getDefaultMessage()).concat(" formato inválido.");
    }
    if (Objects.equals(fieldError.getCode(), CONSTANT_VALIDATION_MIN)) {
      return Objects.requireNonNull(fieldError.getDefaultMessage())
              .concat(String.format(" deve ser maior ou igual a %s", Objects.requireNonNull(fieldError.getArguments())[1]));
    }
    return fieldError.toString();
  }

  @ExceptionHandler(EmptyResultDataAccessException.class)
  public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
    String msgUsuario = "Recurso não encontrado.";
    String msgDesenvolvedor = ex.toString();

    List<Error> errors = List.of(new Error(msgUsuario, msgDesenvolvedor));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
    String msgUsuario = "Recurso não encontrado.";
    String msgDesenvolvedor = ex.toString();

    List<Error> errors = List.of(new Error(msgUsuario, msgDesenvolvedor));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(ServiceValidationException.class)
  public ResponseEntity<Object> handleCategoryException(ServiceValidationException ex, WebRequest request) {
    String msgUsuario = ex.getMessage();
    String msgDesenvolvedor = ex.getMessage();

    List<Error> errors = List.of(new Error(msgUsuario, msgDesenvolvedor));
    return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
