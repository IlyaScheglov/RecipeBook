package com.example.RecipeBook.repos;

import com.example.RecipeBook.entities.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StepsRepo extends JpaRepository<Steps, Long> {

    @Query("SELECT s FROM Steps s WHERE s.userId = :usId AND s.description = :desc")
    List<Steps> findStepsByUserIdAndNullDescription(@Param("usId") long userId, @Param("desc") String desc);

    Steps findById(long id);
}
