package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Comment;

import com.example.techmasterpi.service.CommentService;

import com.example.techmasterpi.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/comment")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    ICommentService ic;

    @Autowired
    private CommentService commentService;

    @GetMapping("comments/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable int postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("addComment")
    void addComment(@RequestBody Comment c)throws IOException
    {


        c.setCommentDate(LocalDateTime.now());
        ic.addComment(c);


        //CommentImpl.idstatic=c.getPost().getId();
    }


    @DeleteMapping("dComment/{idc}")
    void deleteComment(@PathVariable("idc") int idc)
    {

        ic.deleteComment(idc);
    }

    @PutMapping("updateComment")
    void updateComment(@RequestBody Comment c)
    {

        ic.updateComment(c);
    }



    @GetMapping("comments")
    List<Comment> getallcomments()
    {

        return ic.comments();
    }





    /*@GetMapping("/getFilteredComments/{filterType}/{postId}")
    @ResponseBody
    List<Comment> getFilteredPosts(@PathVariable String filterType, @PathVariable int postId	) {
        return ic.getFilteredComments(filterType, postId);
    }
*/
}