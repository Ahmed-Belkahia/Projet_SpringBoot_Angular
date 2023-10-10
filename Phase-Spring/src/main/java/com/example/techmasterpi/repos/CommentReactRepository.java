package com.example.techmasterpi.repos;




import com.example.techmasterpi.domain.Comment;
import com.example.techmasterpi.domain.CommentReact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CommentReactRepository extends JpaRepository<CommentReact, Integer> {
    List<CommentReact> findAllByReactComment(Comment reactComment);

    Optional<CommentReact> findByReactCommentCommentIdAndReactOwnerUserid(Integer commentId, Integer userId);

    //@Query("delete from CommentReact cr where cr.reactOwner.userid =: userId AND cr.reactComment.commentId =: commentId")
    //void removeReactFromPost(@Param("commentId") Integer commentId,@Param("userId") Integer userId);
}