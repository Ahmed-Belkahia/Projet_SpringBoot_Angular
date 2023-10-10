package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.Tag;
import com.example.techmasterpi.domain.User;

import java.util.List;

public interface ITagService {

        public Tag addTag(Tag t);

        public void updateTag(Tag t);

        public List<Tag> allTags();

        Tag retrieveTagById(Integer tagId);

        public int countTags(Tag t);

        public String deleteTag(int tag);
}
