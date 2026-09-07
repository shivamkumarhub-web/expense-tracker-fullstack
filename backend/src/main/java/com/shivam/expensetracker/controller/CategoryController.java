package com.shivam.expensetracker.controller;

import com.shivam.expensetracker.dto.CategoryRequest;
import com.shivam.expensetracker.entity.Category;
import com.shivam.expensetracker.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories")
@SecurityRequirement(name = "Bearer Authentication")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(Authentication auth) {
        return ResponseEntity.ok(categoryService.getUserCategories(auth.getName()));
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request, Authentication auth) {
        return ResponseEntity.ok(categoryService.createCategory(request, auth.getName()));
    }
}