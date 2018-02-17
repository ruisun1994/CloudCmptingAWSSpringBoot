package edu.neu.csye6225.spring2018.controller;


import edu.neu.csye6225.spring2018.dao.UserRepository;
import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static edu.neu.csye6225.spring2018.WebSecurityConfig.SESSION_KEY;

@Controller
@RequestMapping("/user/*")
public class ProfileController {

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/home/tianjiao/csye6225/dev/csye6225-spring2018/webapp/CloudCmpting/src/main/resources/static/imgs/";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    User user = new User();

    @RequestMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request, HttpSession session, Map<String, Object> model) {

//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
//            return "index";
//        }
        System.out.println("upload page session key" + session.getAttribute(SESSION_KEY));

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println("path" + path);
    //        if (file.getOriginalFilename() == null)
            System.out.println("file name:" + file.getOriginalFilename());
            String imageFilePath = file.getOriginalFilename();
            String aboutMe = request.getParameter("aboutme");
            User user = new User();
            user = userService.findByEmail((String)session.getAttribute(SESSION_KEY));
            int id = user.getId();
//            System.out.println("imageFilePath: " + imageFilePath);
            userService.updateUser(id, aboutMe, imageFilePath);
            model.put("imageFilePath",imageFilePath);
            model.put("aboutMe",aboutMe);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "profile";
    }


    @RequestMapping("/deleteImgs")
    public String deleteImgs(HttpServletRequest request, HttpSession session, Map<String, Object> model) {
        user = userService.findByEmail((String)session.getAttribute(SESSION_KEY));
        int id = user.getId();
        String imageFilePath = "default.png";
        String aboutMe = request.getParameter("aboutme");
        userService.updateUser(id, aboutMe, imageFilePath);
        System.out.println(imageFilePath);
        model.put("imageFilePath",imageFilePath);
        return "profile";
    }

    String imageFilePath = "";
    String aboutMe = "";

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchProfile(HttpServletRequest request, Map<String, Object> model) {
        String email = request.getParameter("email");
        user = userService.findByEmail(email);
        if (user != null) {
            System.out.println("searched email: " + user.getEmail());
            imageFilePath = user.getImageFilePath();
            aboutMe = user.getAboutMe();
            System.out.println("home img path: " + imageFilePath);
            System.out.println("aboutme" + aboutMe);
            model.put("aboutMe", this.aboutMe);
            model.put("imageFilePath", this.imageFilePath);
            return "home";
        }else{
            return "index";
        }

    }
}
