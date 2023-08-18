package com.example.RecipeBook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "steps")
public class Steps implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "number")
    private int number;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private long userId;

    @ManyToOne
    private Recipes recipes;

    @ManyToOne
    private Users users;
}
