package com.example.controllers;

import com.example.models.SearchModel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("searchModel")
    public SearchModel searchModel() {
        return new SearchModel();
    }
}