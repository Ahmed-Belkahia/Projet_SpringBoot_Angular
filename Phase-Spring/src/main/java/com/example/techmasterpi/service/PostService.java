package com.example.techmasterpi.service;

import com.example.techmasterpi.domain.*;
import com.example.techmasterpi.repos.CommentRepository;
import com.example.techmasterpi.repos.PostRepository;
import com.example.techmasterpi.repos.RatingRepository;
import com.example.techmasterpi.repos.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class PostService implements IPostService{
    @Autowired
    PostRepository pr;
    @Autowired
    Session session;
    @Autowired
    UserRepository ur;
    @Autowired
    CommentRepository cr;
    @Autowired
    RatingRepository ratingRepository;

    StringSimilarity stringSimilarity;
    @Autowired
    UserService userService;





    @Override
    public Post savePost(Post post) throws Exception {
        List<Post> posts = pr.findAll();

        for(Post p : posts) {
            if (stringSimilarity.similarity(p.getTitle(), post.getTitle()) > 0.500) {//if(stringSimilarity.similarity(p.getPostContent(), post.getPostContent())>0.500 ){
                String mail;
                mail = post.getUserPost().getEmail();
                String htmlBody = "<!DOCTYPE html> \n" +
                        "<html>\n" +
                        "    <head><style> header{background-color: yellow; font-weight: bold;font-family: cursive ; text-align: center;}div{background-color: beige;}</style></head>\n" +
                        "    <body>\n" +
                        "       <header>TechMaster</header>\n" +
                        "          <div>\n" +
                        "          <h3>Hello </h3>" + post.getUserPost().getLastname() + " " + post.getUserPost().getFirstname() +
                        "           <a>This is just a warning please make sure to avoid creating post with with existing title or else your post will be reviewed later and can be automatically deleted.</a>   \n" +
                        "<footer>Cordially.</footer>    \n" +
                        "          </div>\n" +
                        "       \n" +
                        "    </body>\n" +
                        "</html>";
                sendEmail(mail, "Payment", "", htmlBody);
            }
        }
        return pr.save(post);
    }
    @Override
    public Post addPost(Post p, @NonNull HttpServletRequest request) throws MessagingException
    {
        List<Post> posts = pr.findAll();
        for(Post pi : posts) {

            if (stringSimilarity.similarity(pi.getTitle(), p.getTitle()) > 0.500) {//if(stringSimilarity.similarity(p.getPostContent(), post.getPostContent())>0.500 ){
                String mail;
                mail = "benabdallah.ahmed@esprit.tn";
                String htmlBody = "<!DOCTYPE html> \n" +
                        "<html>\n" +
                        "    <head><style> header{background-color: yellow; font-weight: bold;font-family: cursive ; text-align: center;}div{background-color: beige;}</style></head>\n" +
                        "    <body>\n" +
                        "       <header>TechMaster</header>\n" +
                        "          <div>\n" +
                        "          <h3>Hello Admin</h3>\n" +
                        "           <a>This is just a warning please make sure to verify posts with name "+ p.getTitle() +" a user created a post with existing title it should  be reviewed later.</a>   \n" +
                        "<footer>Cordially.</footer>    \n" +
                        "          </div>\n" +
                        "       \n" +
                        "    </body>\n" +
                        "</html>";
                sendEmail(mail, "PostAlert", "", htmlBody);

            }
        }
        User user = userService.getUserByToken(request);
        p.setUserPost(user);

        return pr.save(p);
    }
    @Override
    public void updatePost(Post p) {
        if (pr.findById(p.getPostId()).isPresent())
            pr.save(p);
        else
            System.out.println("doesnt exist");
    }

    @Override
    public String deletePost(int idp) {
        if (pr.findById(idp).isPresent()) {
            pr.delete(pr.findById(idp).get());
            return "The comment is deleted successfully";
        } else
            return "The comment doesn't exist";
    }


    @Override
    public List<Post> allPost() {
        return pr.findAll();
    }

    @Override
    public Post retrievePostById(Integer postId) {
        return pr.findById(postId).orElse(null);
    }

    /////////

    public void sendEmail(String to, String subject, String text, String htmlBody) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);

        // Create the plain text part of the message
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text);

        // Create the HTML part of the message
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");

        // Create a multipart message and add the text and HTML parts to it
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(htmlPart);

        // Set the content of the message to the multipart message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
    }

    public static String saveImage(MultipartFile image, Post post) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads");
        Files.createDirectories(path);
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            post.setImage(filePath.toString());
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save file " + fileName, e);
        }

    }
    @Override
    public boolean ratePost(Rating rating) {

        try {

            ratingRepository.save(rating);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }
    @Override
    public void follow(int idp, int idu) {
        Post p = pr.getById(idp);

        if (p.getFollowers() != null && !(p.getFollowers().contains(","+idu+",")))
        {
            p.setFollowers(p.getFollowers().concat(idu+","));
        }
        else
        {
            p.setFollowers(","+idu+",");
        }
        pr.save(p);
    }

    @Override
    public void unfollow(int idp, int idu) {

        Post p = pr.getById(idp);
        if (p.getFollowers() != null)
            p.setFollowers(p.getFollowers().replace(idu+",",""));
        pr.save(p);

    }

    public List<Integer> stringToList(String s) {
        int i=0;
        List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
        System.out.println("LIST==>"+myList.get(0));
        List<Integer> l=new ArrayList<Integer>();
        for(String string : myList)
        {
            if(i!=0)
                l.add(Integer.valueOf(string));
            i++;
        }

        return l;

    }
    //TOP 3 TRENDS
    public Map<Integer, Post> calculateTendance(int days) throws IOException {

        int trend3 = 0, trend2 = 0, trend1 = 0;

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dateLimit = today.minusDays(days);
        Map<Integer, Post> Trends = new HashMap<Integer, Post>();
        File f1 = new File("forum/likes.txt"); // Creation of File Descriptor for
        // input file

        String[] words = null; // Intialize the word Array
        // Creation of File Reader object
        List<Post> listePosts = allPost();

        for (Post post : listePosts) {
            System.out.println("ID=" + post.getPostId());
            FileReader fr = new FileReader(f1);
            BufferedReader br = new BufferedReader(fr); // Creation of
            // BufferedReader object
            String s;

            String idp = String.valueOf(post.getPostId());

            String comment = "addComment" + idp;

            int count = 0; // Intialize the word to zero
            while ((s = br.readLine()) != null) // Reading Content from the file
            {
                words = s.split(" "); // Split the word using space
                // for (String word : words)
                // {

                LocalDateTime dateAction = LocalDateTime.parse(words[1]);

                if (dateAction.isBefore(today) && dateAction.isAfter(dateLimit))
                    if (words[0].equals(comment)) // Search for the given word
                    {
                        count++; // If Present increase the count by one
                        // If Present increase the count by one
                    }

                // Trends.put(comment.getPost().getId(), count)
                // Input word to be searched
            }

            System.out.println("Count=" + count);

            if (count > trend1) {
                trend1 = count;
                if (Trends.get(2) != null)
                    Trends.put(3, Trends.get(2));
                else
                    Trends.put(2,Trends.get(1));
                if (Trends.get(3) != null)
                    Trends.put(2, Trends.get(1));
                Trends.put(1, post);
                System.out.println("into 1");
            } else if (count >= trend2) // Check for count not equal to zero
            {
                trend2 = count;
                if (Trends.get(2) != null)
                    Trends.put(3, Trends.get(2));
                Trends.put(2, post);
                System.out.println("into 2");
            } else if (count > trend3) // Check for count not equal to zero
            {
                trend3 = count;
                Trends.put(3, post);
                System.out.println("into 3");
            }
            fr.close();
        }

        return Trends;
    }

    // 0 0 10 ? * 2#1
    @Override
    public Map<Integer, Post> topPosts(int days) {
        try {
            Map<Integer, Post> trends;

            trends = calculateTendance(days);

            System.out.println("TREND1:" + trends.get(1).getTitle());
            if (trends.get(2) != null)
                System.out.println("TREND2:" + trends.get(2).getTitle());
            if (trends.get(3) != null)
                System.out.println("TREND3:" + trends.get(3).getTitle());
            return trends;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return null;
    }

    // @Scheduled(cron = "0 0/59 * * * *")
    public Map<Integer, Post> currentTopPosts() {
        try {
            Map<Integer, Post> trends;

            trends = calculateTendance(1);

            System.out.println("TREND1:" + trends.get(1).getTitle());
            if (trends.get(2) != null)
                System.out.println("TREND2:" + trends.get(2).getTitle());
            if (trends.get(3) != null)
                System.out.println("TREND3:" + trends.get(3).getTitle());
            return trends;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        return null;
    }


    @Override
    public List<Post> findTagPosts(Tag t) {
        List<Post> result = new ArrayList<Post>();
        for (Post p : pr.findAll()) {
            if (p.getTags().contains(t))
                result.add(p);
        }

        return result;
    }

    @Override
    public List<Post> retrieveByDateAsc() {
        // TODO Auto-generated method stub
        return pr.findByOrderByPostdateAsc() ;
    }

    @Override
    public List<Post> retrieveByDateDesc() {
        // TODO Auto-generated method stub
        return pr.findByOrderByPostdateDesc();
    }
    Post post = new Post();

    @Override
    public Post getById(final Integer offreid) {

        post =  pr.findById(offreid).get();
        return post;
    }

}