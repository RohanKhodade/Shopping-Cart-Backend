package com.example.dreamShops.service.image;

import com.example.dreamShops.dto.ImageDto;
import com.example.dreamShops.exceptions.ResourceNotFoundException;
import com.example.dreamShops.models.Image;
import com.example.dreamShops.models.Product;
import com.example.dreamShops.repository.ImageRepository;
import com.example.dreamShops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;
    private final ProductService productService;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Image with id "+id+ " not found ")
        );
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository::delete,
                ()->{
                    throw new ResourceNotFoundException("image not found !");
                }
        );
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product=productService.getProductByID(productId);
        List<ImageDto> savedImageDTOs=new ArrayList<>();
        for (MultipartFile file: files){
            try{
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImageBlob(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String BuildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl=BuildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage=imageRepository.save(image);
                savedImage.setDownloadUrl(BuildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto=new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImageDTOs.add(imageDto);

            }catch(IOException  | SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDTOs;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image=getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImageBlob(new SerialBlob(file.getBytes()));
        }catch(IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
