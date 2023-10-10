package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.repos.PlanRepository;
import com.example.techmasterpi.repos.PostRepository;
import com.example.techmasterpi.repos.TagRepository;
import com.example.techmasterpi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TagService implements ITagService{

    @Autowired

    private TagRepository tr;
    private PostRepository postR;

    @Override
    public Tag addTag(Tag t) {

        return tr.save(t);

    }






    @Override
    public void updateTag(Tag t) {
        if (tr.findById(t.getTagId()).isPresent())
            tr.save(t);
        else
            System.out.println("Comment doesnt exist");

    }

    @Override
    public List<Tag> allTags() {
        return tr.findAll();
    }

    @Override
    public Tag retrieveTagById(Integer tagId) {
        return tr.findById(tagId).orElse(null);
    }

    @Override
    public String deleteTag(int idt) {
        if (tr.findById(idt).isPresent()) {
            tr.delete(tr.findById(idt).get());
            return "The comment is deleted successfully";
        } else
            return "The comment doesn't exist";

    }

    @Override
    public int countTags(Tag t) {

        int count=0;
        System.out.println("TAG="+t.getTagId());
        for(Post p:postR.findAll())
        {
            if(p.getTags().contains(t))
                count++;
        }
        System.out.println("COUNT"+count);
        return count;
    }






}