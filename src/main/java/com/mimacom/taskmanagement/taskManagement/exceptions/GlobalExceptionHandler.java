/**
 *
 */
package com.mimacom.taskmanagement.taskManagement.exceptions;

import com.mimacom.taskmanagement.taskManagement.dto.ErrorDto;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//@SuppressWarnings({"rawtypes", "unchecked"})
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ErrorDto error = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(TaskNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.toString(), "Task with id " + ex.getMessage() + " not found.");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskAnyNotFoundException.class)
    public final ResponseEntity<Object> handleAnyNotFoundException(TaskAnyNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.toString(), "There is no Task in the DB");
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskBadReqException.class)
    public final ResponseEntity<Object> handleBadRequest(TaskBadReqException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
