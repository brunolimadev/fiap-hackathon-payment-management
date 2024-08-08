package com.fiap.hackathon_payment_management.adapters.validation.exception;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class ErrorHandlerTest {

  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  private ErrorHandler errorHandler;
  private AutoCloseable openMocks;

  @BeforeEach
  void setup() {

    openMocks = MockitoAnnotations.openMocks(this);
    errorHandler = new ErrorHandler();

    when(methodArgumentNotValidException.getFieldErrors())
            .thenReturn(List.of(new FieldError(
                    "teste",
                    "teste",
                    "errro teste"
            )));

  }

  @AfterEach
  void tearDown() throws Exception {

    openMocks.close();

  }

  @Test
  void shouldHandleBadRequestWithMethodArgumentNotValidException() {

    //Arrange
    var exception = methodArgumentNotValidException;

    //Act
    var response = errorHandler.errorHandler400(exception);

    //Assert
    assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.BAD_REQUEST);

  }

  @Test
  void shouldHandleInternalServerErrorWithValidationException() {

    //Arrange
    var exception = new ValidationException("Error");

    //Act
    var response = errorHandler.businessRuleError(exception);

    //Assert
    assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

  }

  @Test
  void shouldHandlePaymentRequiredWithValidationLimitCardException() {

    //Arrange
    var exception = new ValidationLimitCardException("Error");

    //Act
    var response = errorHandler.businessRuleError(exception);

    //Assert
    assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.PAYMENT_REQUIRED);

  }

}