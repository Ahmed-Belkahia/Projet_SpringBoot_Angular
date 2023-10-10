package com.example.techmasterpi.Controller;

import com.example.techmasterpi.domain.Post;
import com.example.techmasterpi.domain.Rating;
import com.example.techmasterpi.domain.Tag;
import com.example.techmasterpi.domain.User;
import com.example.techmasterpi.model.Category;
import com.example.techmasterpi.model.State;
import com.example.techmasterpi.repos.PostRepository;
import com.example.techmasterpi.repos.TagRepository;
import com.example.techmasterpi.repos.UserRepository;
import com.example.techmasterpi.service.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping(value = "/api/post")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
    @Autowired
    IPostService ip;

    //******************
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationService notificationService;

    @Autowired
    PostService postService;
//**********************

    @Autowired
    PostRepository postRepository;





	/*@PostMapping("addPost")
	@ResponseBody
	String addPost(@RequestBody Post p)
	{
		return ip.addPost(p);
	}

	*/



    /* @PostMapping("addPost/{userId}")
     @ResponseBody

     public Post addpost(@RequestBody Post p, @PathVariable("userId") int userId)throws MessagingException
     {



         //   return fileName;
         return ip.addPost(p,userId);
     }

     */
    //**********************
    @PostMapping("addPost")
    @ResponseBody

    public Post addpost(@RequestBody Post p, @NonNull HttpServletRequest request) throws MessagingException {

        p.setPostdate(LocalDateTime.now());
        //   return fileName;
        return ip.addPost(p, request);
    }
    @PostMapping("addRate")
    @ResponseBody
    public boolean ratePost(@RequestBody Rating r)
    {
        ip.ratePost(r);

        return true;
    }





    @PostMapping("/addImage/{userId}")
    public ResponseEntity<?> addRentalOffer(@RequestParam("title") String title,
                                            @RequestParam(value = "image", required = false) MultipartFile image,
                                            @RequestParam("category") Category category,
                                            @RequestParam("countlike") Integer countlike,
                                            @RequestParam("state") State state,
                                            @RequestParam("postcontent") String postcontent,


                                            @PathVariable("userId") int userId) throws Exception {

        // Vérifier si l'utilisateur existe
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        // Créer un nouveau plan
        Post post = new Post();

        post.setUserPost(user);
        post.setTitle(title);
        post.setCategory(category);
        post.setCountLike(countlike);
        post.setState(state);
        post.setPostContent(postcontent);
        post.setPostdate(LocalDateTime.now());
        // Enregistrer l'image si elle existe
        if (image != null && !image.isEmpty()) {
            String imagePath = postService.saveImage(image, post);
            post.setImage(imagePath);
        }

        // Enregistrer le plan

        if (image != null && !image.isEmpty()) {
            String imagePath = postService.saveImage(image, post);
            post.setImage(imagePath);
        }
        Post savepost = ip.savePost(post);
        return ResponseEntity.ok(savepost);
    }
//**********************




    @GetMapping("/retrieve-postbydateasc")
    @ResponseBody
    public List<Post> retrieveByDateasc() {
        List<Post> listposts = postService.retrieveByDateAsc();
        return listposts;
    }

    @GetMapping("/retrieve-postbydatedesc")
    @ResponseBody
    public List<Post> retrieveByDatedesc() {
        List<Post> listpostss = postService.retrieveByDateDesc();
        return listpostss;
    }

    @DeleteMapping("dPost/{idp}")
    void deletePost(@PathVariable("idp") int idp)
    {
        ip.deletePost(idp);
    }

    @PutMapping("updatePost")
    void updatePost(@RequestBody Post p)
    {

        ip.updatePost(p);
    }



    @GetMapping("posts")
    List<Post> getallposts()
    {
        return ip.allPost();
    }
    @PutMapping("follow/{idp}/{idu}")
    public void follow(@PathVariable("idp") int idp,@PathVariable("idu") int idu) {
        ip.follow(idp,idu);
    }

    @PutMapping("unfollow/{idp}/{idu}")
    public void unfollow(@PathVariable("idp") int idp,@PathVariable("idu") int idu) {
        ip.unfollow(idp,idu);

    }





    @GetMapping("/trends/{days}")
    @ResponseBody
    public Map<Integer, Post> greeting(@PathVariable("days") int days) {
        return ip.topPosts(days);
    }

    @GetMapping("/showMyNotifications")
    public List<String> showMyNotifications(@RequestBody User u) {

        List<String> mynotif = notificationService.showMyNotif(u);
        Collections.reverse(mynotif);

        return mynotif;

    }
    Post post = new Post();
    @GetMapping("/getid/{id}")
    public Post getbyid(@PathVariable("id") int postid){
        return    ip.getById(postid);



    }


}