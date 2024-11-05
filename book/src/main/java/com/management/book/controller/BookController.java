package com.management.book.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {
    @GetMapping("/student")
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public String StudentProtectedRoute() {
        return "Student";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'ADMIN')")
    public String TeacherProtectedRoute() {
        return "Teacher";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String AdminProtectedRoute() {
        return "Admin";
    }
}
