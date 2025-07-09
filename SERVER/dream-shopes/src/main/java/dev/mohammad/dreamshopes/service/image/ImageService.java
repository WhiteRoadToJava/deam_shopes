package dev.mohammad.dreamshopes.service.image;


import dev.mohammad.dreamshopes.dto.ImageDto;
import dev.mohammad.dreamshopes.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
Image getImageById(long id);
void deleteProductById(long id);
List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
void updateImage(MultipartFile file, Long imageId);

}
