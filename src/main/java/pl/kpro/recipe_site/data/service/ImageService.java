package pl.kpro.recipe_site.data.service;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kpro.recipe_site.data.entity.Image;
import pl.kpro.recipe_site.data.repository.ImageRepository;
import pl.kpro.recipe_site.web.exception.NotFoundException;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Service
public class ImageService
{
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository)
    {
        this.imageRepository = imageRepository;
    }

    public String addImage(MultipartFile file)
    {
        try
        {
            Binary img = new Binary(BsonBinarySubType.BINARY, file.getBytes());
            UUID name = UUID.randomUUID();
            Image image = new Image(img, name);
            image = imageRepository.save(image);
            return image.getName().toString();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public Image getImage(UUID name)
    {
        var img = imageRepository.findAllByName(name);
        if(img.isPresent())
            return img.get();
        else
            throw new NotFoundException("Image not found");
    }
}
