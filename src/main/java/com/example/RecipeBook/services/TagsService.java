package com.example.RecipeBook.services;

import com.example.RecipeBook.entities.Tags;
import com.example.RecipeBook.repos.TagsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagsService {

    private final TagsRepo tagsRepo;


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

    public long getTagIdByTitle(String title){
        Tags tags = tagsRepo.findByTitle(title);
        if(tags != null){
            return tags.getId();
        }
        else{
            return 0;
        }
    }

    public void addNewTag(String title){
        Tags tags = new Tags();
        tags.setTitle(title);
        tagsRepo.save(tags);
    }
}
