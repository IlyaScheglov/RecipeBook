package com.example.RecipeBook.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "likes")
public class Likes implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "recipe_id")
    private long recipeId;

    @Column(name = "date_of_like")
    private Timestamp dateOfLike;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Recipes recipes;


}
