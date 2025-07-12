package dev.mohammad.dreamshopes.controller;


import dev.mohammad.dreamshopes.dto.ImageDto;
import dev.mohammad.dreamshopes.exception.ResourceNotFoundException;
import dev.mohammad.dreamshopes.model.Image;

import dev.mohammad.dreamshopes.response.ApiResponse;
import dev.mohammad.dreamshopes.service.image.ImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService  imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files,@RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos= imageService.saveImage(files,productId);
            return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("upload failed", e.getMessage()));
        }
    }


    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> doownloadImage( @PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
         ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filememe=\"" + image.getFilename() + "\"")
                .body(resource);
    }


    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        Image image = imageService.getImageById(imageId);
        try{
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully", null));
            }
        }catch (ResourceNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Image not found", null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Image not found", null));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId ) {
        Image image = imageService.getImageById(imageId);
        try{
            if (image != null) {
                imageService.deleteProductById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image delete successfully", null));
            }
        }catch (ResourceNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Image not found", null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete feiled!", null));
    }






}
