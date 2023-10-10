package com.example.techmasterpi.service;
import java.io.IOException;

import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.model.TypeReact;
import com.example.techmasterpi.repos.CommentReactRepository;
import com.example.techmasterpi.repos.CommentRepository;
import com.example.techmasterpi.repos.PostRepository;
import com.example.techmasterpi.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.TreeSet;

@Service
public class CommentService implements ICommentService {
    public static int idstatic = 0;
    @Autowired
    CommentRepository cr;
    @Autowired
    PostRepository pr;

    @Autowired
    CommentReactRepository commentReactRepository;


    @Autowired
    UserRepository userRepository;
    @Override
    public void addComment(Comment c) throws IOException{
        // File file = new File("forum/spam"+ c.getCommentDate()+".txt");
        // if (file.createNewFile()) {
        //    System.out.println("New Text File is created!");
        //  } else {
        //     System.out.println("File already exists.");
        // }



        //if (this.AntiSpam(c)) {

        //  System.out.println(c.getCommentContent());
        //  System.out.println(c.getPostComment());

        //  if (c.getPostComment() != null)
        //    idstatic = c.getPostComment().getPostId();
        // System.out.print("ITS NOT A SPAM");

        cr.save(c);

        // }

        // else {



//
        //  FileWriter fileWriter = new FileWriter("forum/spam"+c.getPostComment().getPostId()+".txt", true);
        //    PrintWriter printWriter = new PrintWriter(fileWriter);
        //   printWriter.println(c.getUser().getUserid() + " " + c.getCommentContent() + " " + c.getCommentDate()); // New
        // line
        //  printWriter.close();
        //  System.out.println("FILE DONE!");
        // System.out.println("ID="+c.getCommentId());
        //this.deleteComment(c.getId());


    }










    @Override
    public void updateComment(Comment c) {
        if (cr.findById(c.getCommentId()).isPresent())
            cr.save(c);
        else
            System.out.println("Comment doesnt exist");

    }



    @Override
    public String deleteComment(int idc) {
        if (cr.findById(idc).isPresent()) {
            cr.delete(cr.findById(idc).get());
            return "The comment is deleted successfully";
        } else
            return "The comment doesn't exist";



    }

    // USER !!!!

    @Override
    public List<Comment> comments() {
        return cr.findAll();

    }

    @Override
    public Comment retrieveCommentById(Integer commentId) {
        return cr.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> comments(Post p) {
        return cr.getAllComments(p);

    }
    public List<Comment> commentsUser(long idu) {

        List<Comment> comments = new ArrayList<Comment>();
	/*	if (cr != null)
			for (Comment c : cr.findAll()) {
				System.out.println("22222222222");
				if (c.getUser().getId() == idu)
					comments.add(c);
			}
		System.out.println("33333333333");*/
        return comments;
    }


    public boolean AntiSpam(Comment c) throws IOException {
        int p = 0;
        int links = 0;
        int userComments = 0;
        // Links
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(c.getCommentContent());

        while (urlMatcher.find()) {
            links++;
        }
        if (links <= 1)
            p += 2;
        else
            p--;

        System.out.println("P1=" + p);
        // Content length
        if (c.getCommentContent().length() < 20 && links < 1)
            p ++;
        else
            p--;

        System.out.println("P2=" + p);
        // Nb of comments
        if (c.getUser() != null)
            for (Comment comment : commentsUser(c.getUser().getUserid())) {

                if (comment.getPostComment().getPostId() == c.getPostComment().getPostId()) {
                    p++;
                }

            }

        System.out.println("P3=" + p);
        // parcours fichier
        if (c.getPostComment() != null) {
            File spamF = new File("forum/spam" + c.getPostComment().getPostId() + ".txt");
            String[] wordss = null;

            FileReader frr = new FileReader(spamF);
            BufferedReader brr = new BufferedReader(frr);
            String strr;

            while ((strr = brr.readLine()) != null) {

                wordss = strr.split(" ");
                System.out.println("words[0=>"+wordss[0]+"\n");
                System.out.println("c.getUser().getId())="+c.getUser().getUserid());
                // Search for the word
                if (wordss[0].equals(Long.toString(c.getUser().getUserid()))) {
                    System.out.print("Spam exists");
                    p--;
                }


            }

        }
        System.out.println("P4=" + p);
        // Keywords
        File file = new File("forum/badWords.txt");
        String[] words = null;
        String[] bads = null;
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String str;

        while ((str = br.readLine()) != null) {

            words = str.split("\n");
            bads = c.getCommentContent().split(" ");
            for (String bad : bads) {
                for (String word : words) {
                    // Search for the word
                    if (word.equals(bad)) {
                        System.out.print("BAD=" + bad);
                        p=-2;
                    }
                }
            }
        }
        System.out.println("P5=" + p);
        System.out.println("c.getPost(🙁" + c.getPostComment().getPostId());
        // BodyUsed

        Post post=pr.findById(c.getPostComment().getPostId()).orElse(null);
        for (Comment comment : post.getComments()) {

            if (comment.getCommentContent().equals(c.getCommentContent()) && comment.getCommentId() != c.getCommentId())
                p--;
        }
        System.out.println("P6=" + p);
        // random words
        String cons = "bcdfghjklmnpqrstvwxz";
        char[] consonants = cons.toCharArray();
        int z = 0;
        char[] ch = c.getCommentContent().toLowerCase().toCharArray();
        for (int i = 0; i < c.getCommentContent().length(); i++) {
            boolean isConsonant = false;
            for (int j = 0; j < 20; j++) {
                if (ch[i] == consonants[j]) {
                    isConsonant = true;
                    break;
                }
            }
            if (isConsonant) {
                z++;
                if (z == 5)
                    break;
            } else {
                z = 0;
            }
        }
        if (z == 5)
            p--;
        else if (z == 0)
            p++;

        System.out.println("P7=" + p);

        if (p >= 1)
            return true;
        else

            return false;

    }
/*
    public Set<Comment> commentsPost(int idp) {
        Post p=pr.findById(idp).orElse(null);
        return p.getComments();
    }

    public void commentUp(int commentId) {
        Comment c = cr.findById(commentId).get();
        idstatic = c.getPostComment().getPostId();
        c.setCountLike(c.getCountLike()+ 1);
        cr.save(c);

    }
*/

  /*  @Override

    public List<Comment> getFilteredComments(String filterType, int postId) {
        List<Comment> comments =  cr.findAllByCommentPost(pr.findById(postId).get());

        if(filterType.equals("popular")) {
            for(Comment c: comments) {
                c.setListReacts(commentReactRepository.findAllByReactComment(c));
                c.setReactCount(c.getListReacts().size());
            }
            comments.sort(Comparator.comparing(Comment::getReactCount));
            Collections.reverse(comments);
        }

        return comments;
    }

*/





/*
    @Override
    public Set<Comment> commentsPostbyDate(int idp) {

        Set<Comment> sorted = new TreeSet<Comment>(new Comparator<Comment>() {
            public int compare(Comment c1, Comment c2) {
                return c1.getCommentDate().compareTo(c2.getCommentDate());
            }});

        Set<Comment> comments = commentsPostbyDate(idp);
        sorted=comments;


        return sorted;

    }

*/

    public List<Comment> getCommentsByPostId(int postId) {
        Post post = pr.findById(postId).orElse(null);

        return cr.getAllComments(post);

    }
}
