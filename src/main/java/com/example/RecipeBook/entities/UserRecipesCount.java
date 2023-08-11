package com.example.RecipeBook.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRecipesCount implements Serializable{

    private int ownCount;

    private int likedCount;

    private int favouriteCount;
}
