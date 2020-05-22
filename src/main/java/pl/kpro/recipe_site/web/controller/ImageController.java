package pl.kpro.recipe_site.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kpro.recipe_site.data.entity.Image;
import pl.kpro.recipe_site.data.service.ImageService;

import java.util.Base64;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Controller
@RequestMapping("/api/image")
public class ImageController
{
    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService)
    {
        this.imageService = imageService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable UUID filename, Model model)
    {
        Image image = imageService.getImage(filename);
        model.addAttribute("title", image);
        model.addAttribute("image",
                           Base64.getEncoder().encodeToString(image.getImage().getData()));
        return new ResponseEntity<byte[]>(image.getImage().getData(), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPhoto(@RequestParam("image") MultipartFile image, Model model)
    {
        String name = imageService.addImage(image);
        return new ResponseEntity<>(name, (name == null) ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED);
    }
}
