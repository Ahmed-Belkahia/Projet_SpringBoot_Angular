package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Comment;
import com.example.techmasterpi.domain.Tag;
import com.example.techmasterpi.repos.TagRepository;
import com.example.techmasterpi.service.IPostService;
import com.example.techmasterpi.service.ITagService;
import com.example.techmasterpi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tag")
public class TagController {

    @Autowired
    ITagService it;
    @Autowired
    IPostService ip;




    @PostMapping("addTag")
    void addComment(@RequestBody Tag t)
    {
        it.addTag(t);

        //CommentImpl.idstatic=c.getPost().getId();
    }


    @GetMapping("tags")
    List<Tag> getallTag()
    {
        return it.allTags();
    }
    @DeleteMapping("dTag/{idc}")
    void deleteTag(@PathVariable("idc") int idc)
    {

        it.deleteTag(idc);
    }

    @PutMapping("updateTag")
    void updateComment(@RequestBody Tag c)
    {

        it.updateTag(c);
    }

}