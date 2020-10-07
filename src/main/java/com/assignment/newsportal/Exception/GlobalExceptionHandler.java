package com.assignment.newsportal.Exception;


import com.assignment.newsportal.entity.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

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
    public ResponseEntity<?> duplicateDataException(DuplicateDataException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                "Data Already Exists in Database", exception.getMessage(),
                request.getContextPath());
            return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
        }


    @ExceptionHandler(MissingDetailException.class)
    public ResponseEntity<?> missingDetailException(MissingDetailException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                "Details incomplete", exception.getMessage(),
                request.getContextPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<?> mismatchException(MismatchException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                "Data mismatch", exception.getMessage(),
                request.getContextPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> invalidException(InvalidRequestException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.BAD_REQUEST.toString(),
                "Invalid Request", exception.getMessage(),
                request.getContextPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<?> notFoundException(NotFoundException exception, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.NOT_FOUND.toString(),
                    "Not found in database", exception.getMessage(),
                    request.getContextPath());
            return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }


        @ExceptionHandler(UnauthorisedException.class)
        public ResponseEntity<?> unauthorisedException(UnauthorisedException exception, WebRequest request) {
            ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.FORBIDDEN.toString(),
                    "User not authorised", exception.getMessage(),
                    request.getContextPath());
            return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
        }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> deniedException(AccessDeniedException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpStatus.UNAUTHORIZED.toString(),
                "Access Denied", exception.getMessage(),
                request.getContextPath());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    }
//}
