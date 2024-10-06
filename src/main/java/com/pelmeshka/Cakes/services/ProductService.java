package com.pelmeshka.Cakes.services;

import com.pelmeshka.Cakes.models.Image;
import com.pelmeshka.Cakes.models.Product;
import com.pelmeshka.Cakes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts(String title) {
        if (title != null) {
            return productRepository.findByTitle(title);
        } return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void createProduct(Product product, MultipartFile file1) throws IOException{
        if (file1.getSize() != 0) {
            Image image1 = getImageFromFile(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        log.info("Saving new Product. Title: {}; Description: {}", product.getTitle(), product.getDescription());
        Product productFromDb = productRepository.save(product);
        productFromDb.setImagePreviewId(product.getImages().get(0).getId());
        productRepository.save(productFromDb);
    }

    public void addImage(Product product, MultipartFile file) throws IOException {
        if (file.getSize() != 0) {
            Image image = getImageFromFile(file);
            if (product.getImages().size() == 0) {
                image.setPreviewImage(true);
            }
            product.addImageToProduct(image);
        }
        Product productFromDb = productRepository.save(product);
        productFromDb.setImagePreviewId(product.getImages().get(0).getId());
        productRepository.save(productFromDb);
    }

    private Image getImageFromFile(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setSize(file.getSize());
        image.setContentType(file.getContentType());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
