package org.sid.banquetechcodec.web;

import org.apache.tomcat.util.codec.binary.Base64;
import org.sid.banquetechcodec.dao.UserRepository;
import org.sid.banquetechcodec.data.ImageMobille;
import org.sid.banquetechcodec.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin("*")
public class PhotoController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/userImage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable Long id) throws IOException {

        User user = userRepository.findById(id).orElse(null);

        //user.setPhoto(String.valueOf(id)+".png");

        userRepository.save(user);

        String photoName = user.getPhoto();

        File file = new File(System.getProperty("user.home")+"/cinema/images/"+photoName);

        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);

    }

    @PostMapping(path = "/UploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file,@PathVariable Long id) throws IOException {

        User user = userRepository.findById(id).orElse(null);

        user.setPhoto(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home")+"/cinema/images/"+user.getPhoto()),file.getBytes());
        userRepository.save(user);
    }

    @PostMapping(path ="/Uploadphotomobille/{id}")
    public String uploadphotomobile(@RequestBody ImageMobille imagebase64, @PathVariable Long id){

        try
        {
            //This will decode the String which is encoded by using Base64 class
            byte[] imageByte= Base64.decodeBase64(imagebase64.getImageBase64());

            User user = userRepository.findById(id).orElse(null);

            user.setPhoto("image"+id);
            Files.write(Paths.get(System.getProperty("user.home")+"/cinema/images/"+user.getPhoto()),imageByte);
            userRepository.save(user);

            return "success ";
        }
        catch(Exception e)
        {
            return "error = "+e;
        }
    }
}
