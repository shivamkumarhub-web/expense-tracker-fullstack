package com.shivam.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank private String name;
    private String icon;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
}