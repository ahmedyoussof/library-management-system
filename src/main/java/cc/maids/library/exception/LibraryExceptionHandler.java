package cc.maids.library.exception;



import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class LibraryExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleGenericException(Exception ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder()
        .timestamp(LocalDateTime.now())
        .message(ex.getMessage())
        .details(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorDetails> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
    ErrorDetails errorDetails = ErrorDetails.builder()
        .timestamp(LocalDateTime.now())
        .message(ex.getCause().getCause().getMessage())
        .details(request.getDescription(false))
        .build();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    String errorMessage = "";
    ErrorDetails errorDetails;

    if(!ex.getBindingResult().getFieldErrors().isEmpty()) {

      errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();


    } else if (!ex.getBindingResult().getAllErrors().isEmpty()) {
      errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }

    errorDetails = ErrorDetails.builder()
        .timestamp(LocalDateTime.now())
        .message(errorMessage)
        .details(request.getDescription(false))
        .build();
    return new ResponseEntity<>(errorDetails, status);
  }


}
