package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Recipes;
import com.example.RecipeBook.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepo extends JpaRepository<Users, Long> {

    Users findByLogin(String login);
}