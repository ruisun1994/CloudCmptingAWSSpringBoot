package edu.neu.csye6225.spring2018.controller;

import edu.neu.csye6225.spring2018.entity.User;
import edu.neu.csye6225.spring2018.service.AmazonClient;
import edu.neu.csye6225.spring2018.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static edu.neu.csye6225.spring2018.WebSecurityConfig.SESSION_KEY;

@Controller
@RequestMapping("/user/*")
public class ProfileController {

    private AmazonClient amazonClient;

    @Autowired
    ProfileController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @Autowired
    private UserService userService;
    @Autowired
    private Environment env;

    User user = new User();

    String UPLOADED_FOLDER = "";

    public boolean isAWS() {
        String result = env.getProperty("AWS.status");
        if (result.equalsIgnoreCase("yyy")) {
            System.out.println("Using AWS right now!");
            return true;
        } else {
            System.out.println("Not Using AWS right now!");
            return false;
        }
    }

    public byte[] imageToByteArray(String imageName) throws IOException {
        File imgPath = new File(imageName);
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }


    @RequestMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request, HttpSession session, Map<String, Object> model) {
        Path path;
        user = userService.findByEmail((String) session.getAttribute(SESSION_KEY));
        //String imageFilePath = "default.png";
        String aboutMe = request.getParameter("aboutme");
        int id = user.getId();

        if (!isAWS()) {

            if (file.isEmpty()) {
                System.out.println("No photo changed!");
                userService.updateaboutMe(id, aboutMe);
                model.put("imageFilePath", user.getImageFilePath());
                model.put("aboutMe", aboutMe);
            } else {
                try {
                    // Get the file and save it somewhere
                    byte[] bytes = file.getBytes();
                    String currentDir = System.getProperty("user.dir");
                    UPLOADED_FOLDER = currentDir + "/src/main/resources/static/imgs/";
                    path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                    Files.write(path, bytes);
                    System.out.println("file name:" + file.getOriginalFilename());
                    String imageFileName = file.getOriginalFilename();
                    userService.updateUser(id, aboutMe, imageFileName);
                    model.put("imageFilePath", imageFileName);
                    //System.out.println("upload page session key" + imageFilePath);
                    model.put("aboutMe", aboutMe);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "profile";
        } else {
            if (file.isEmpty()) {
                userService.updateaboutMe(id, aboutMe);
                String imageFileName = user.getImageFilePath();
                try {
                    String result = amazonClient.downloadImageFromS3(imageFileName);
                    model.put("imageFilePath", result);
                    System.out.println("Though no photo change on the AWS!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                model.put("aboutMe", aboutMe);
            } else {
                amazonClient.uploadFile(file);
                String imageFileName = file.getOriginalFilename();
                userService.updateUser(id, aboutMe, imageFileName);
                try {
                    String result = amazonClient.downloadImageFromS3(imageFileName);
                    model.put("imageFilePath", result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                model.put("aboutMe", aboutMe);
            }

            return "profile_aws";
        }
    }


    @RequestMapping("/deleteImgs")
    public String deleteImgs(HttpServletRequest request, HttpSession session, Map<String, Object> model) {
        user = userService.findByEmail((String) session.getAttribute(SESSION_KEY));
        int id = user.getId();
        String defaultFilePath = "default.png";
        userService.updateUserImage(id, defaultFilePath);
        model.put("aboutMe", user.getAboutMe());
//        System.out.println(imageFilePath);
        if (!isAWS()) {
            model.put("aboutMe", user.getAboutMe());
            model.put("imageFilePath", defaultFilePath);
            return "profile";
        } else {
            try {
                String result = amazonClient.downloadImageFromS3(defaultFilePath);
                model.put("imageFilePath", result);
                model.put("aboutMe", user.getAboutMe());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "profile_aws";

        }
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchProfile(HttpServletRequest request, Map<String, Object> model) {
        String email = request.getParameter("email");
        user = userService.findByEmail(email);
        if (user != null) {
            System.out.println("searched email: " + user.getEmail());
            String imageFilePath = user.getImageFilePath();
            String aboutMe = user.getAboutMe();
            System.out.println("home img path: " + imageFilePath);
            System.out.println("aboutme" + aboutMe);
            model.put("aboutMe", aboutMe);
            model.put("imageFilePath", imageFilePath);
            return "home";
        } else {
            return "index";
        }

    }
}
