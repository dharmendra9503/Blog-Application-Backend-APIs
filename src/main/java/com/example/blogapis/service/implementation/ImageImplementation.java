package com.example.blogapis.service.implementation;

import com.example.blogapis.exception.ResourceNotFoundException;
import com.example.blogapis.model.ImageData;
import com.example.blogapis.model.Post;
import com.example.blogapis.repository.ImageDataRepository;
import com.example.blogapis.repository.PostRepository;
import com.example.blogapis.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Optional;


@Service
public class ImageImplementation implements ImageService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private PostRepository postRepository;

    //This will give path where directory create.
    @Value("${project.file}")  //Property present in application.properties
    private String FOLDER_PATH;


    @Override
    public String uploadImage(MultipartFile image, Integer postId) throws IOException {

        String imageName = image.getOriginalFilename();

        //Modified image name that contains file name with postId.
        assert imageName != null;
        String imageNameModified = postId.toString().concat("_"+imageName);

        //path where file store with file name in file storage
        String imagePath= FOLDER_PATH+imageNameModified;

        //will get post with postId, If post doesn't exist then will throw Exception.
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "Post Id", postId));

        /*
            based on the postId will get post and after that based on that post
            will get image
        */
        ImageData imageData = imageDataRepository.findByPost(post);

        /*
            If image doesn't exist with given postId then above query will create new entry in database
            And if image exist then we will modify present entry of image.
        */
        if(imageData == null){
            imageData = new ImageData();
        }

        imageData.setName(imageNameModified);
        imageData.setType(image.getContentType());
        imageData.setPost(post);
        imageData.setFilePath(imagePath);
        imageData.setDate(new Date());

        //This will save image path in database.
        imageDataRepository.save(imageData);

        //If directory doesn't exist in file system then above code will create
        File dir = new File(FOLDER_PATH);
        if(!dir.exists()){
            dir.mkdir();
        }

        Path path = Path.of(imagePath);

        //In directory if file/image already exist then above line will delete that file.
        Files.deleteIfExists(path);

        //This will create new file in file system.
        Files.copy(image.getInputStream(), path);

        //Return file modified file name and that file name contains postId.
        return imageNameModified;
    }


    @Override
    public byte[] downloadImage(String imageName, Post post) throws IOException {

        String imageNameModified = post.getPostId().toString().concat("_"+imageName);
        Optional<ImageData> fileData = imageDataRepository.findByNameAndPost(imageNameModified, post);

//        String filePath;
//        if(fileData.isPresent()){
//            filePath=fileData.get().getFilePath();
//        }
//        else{
//            filePath = null;
//        }

//      This is a replacement of above commented code
        String filePath = fileData.map(ImageData::getFilePath).orElse(null);

        if(filePath != null) {
//            byte[] images = Files.readAllBytes(new File(filePath).toPath());
            return Files.readAllBytes(new File(filePath).toPath());
        }

        return null;
    }
}
