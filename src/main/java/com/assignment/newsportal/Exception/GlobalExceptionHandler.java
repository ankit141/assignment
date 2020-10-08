package com.assignment.newsportal.Exception;


import com.assignment.newsportal.entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
    public class GlobalExceptionHandler {

//        @ExceptionHandler(AuthenticationException.class)
//        public ResponseEntity<?> authenticationException(AuthenticationException exception, WebRequest request) {
//            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.toString(),
//                    "User not authenticated", exception.getMessage(),
//                    request.getContextPath());
//            return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
//        }

    @ExceptionHandler(DuplicateDataException.class)
    public ResponseEntity<?> duplicateDataException(DuplicateDataException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                "Data Already Exists in Database", exception.getMessage());
            return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }


//    @ExceptionHandler(MissingDetailException.class)
//    public ResponseEntity<?> missingDetailException(MissingDetailException exception, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
//                "Details incomplete", exception.getMessage(),
//                request.getContextPath());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<?> mismatchException(MismatchException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                "Data mismatch", exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> invalidException(InvalidRequestException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                "Invalid Request", exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<?> notFoundException(NotFoundException exception) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),
                    "Not found in database", exception.getMessage());
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }


        @ExceptionHandler(UnauthorisedException.class)
        public ResponseEntity<?> unauthorisedException(UnauthorisedException exception) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.toString(),
                    "User not authorised", exception.getMessage());
            return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
        }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> deniedException(AccessDeniedException exception) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.toString(),
                "Access Denied", exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions( MethodArgumentNotValidException exception) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        System.out.println("yo man");
//        return errors;
        BindingResult bindingResult= exception.getBindingResult();
        String errMsg = "";
        if (bindingResult.hasErrors()) {

            for (FieldError err : bindingResult.getFieldErrors()) {
                errMsg += " " + err.getDefaultMessage();
            }
        }

            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                    "Invalid Request", errMsg);
            return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }



//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> deniedException(MethodArgumentNotValidException exception, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
//                "Invalid Request", exception.getMessage(),
//                request.getContextPath());
//        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//    }

}
