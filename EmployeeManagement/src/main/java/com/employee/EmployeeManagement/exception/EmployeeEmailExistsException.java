package com.employee.EmployeeManagement.exception;

public class EmployeeEmailExistsException extends RuntimeException {
    public EmployeeEmailExistsException() {
    }

    public EmployeeEmailExistsException(String message) {
        super(message);
    }

    public EmployeeEmailExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
