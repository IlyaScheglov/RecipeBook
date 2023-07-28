package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Tags;
import com.example.RecipeBook.repos.TagsRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagsService {

    private final TagsRepo tagsRepo;

    public TagsService(TagsRepo tagsRepo) {
        this.tagsRepo = tagsRepo;
    }

    public String getTagTitle(long tagId){
        String titleToReturn = "";
        Optional<Tags> tags = tagsRepo.findById(tagId);
        List<Tags> tagsList = new ArrayList<>();
        tags.ifPresent(tagsList::add);
        for (var el : tagsList){
            titleToReturn = el.getTitle();
        }
        return titleToReturn;
    }
}
