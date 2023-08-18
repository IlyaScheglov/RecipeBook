package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Ingridients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IngridientsRepo extends JpaRepository<Ingridients,Long> {

    @Query("SELECT i FROM Ingridients i WHERE i.userId = :usId AND i.listOfIngridients = :desc")
    List<Ingridients> findByUserIdAndNullListOfIngs(@Param("usId") long userId, @Param("desc") String desc);

    Ingridients findById(long id);
}
