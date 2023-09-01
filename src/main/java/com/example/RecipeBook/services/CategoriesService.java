package com.example.RecipeBook.services;

import com.example.RecipeBook.repos.CategoriesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriesService {

    private final CategoriesRepo categoriesRepo;

    public String getCategoryById(long categoryId){
        return categoriesRepo.findById(categoryId).getTitle();
    }
}
