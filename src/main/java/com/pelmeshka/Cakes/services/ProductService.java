package com.pelmeshka.Cakes.services;

import com.pelmeshka.Cakes.models.Image;
import com.pelmeshka.Cakes.models.Product;
import com.pelmeshka.Cakes.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void createProduct(Product product, MultipartFile file)
    throws IOException {
        if (file.getSize() != 0) {
            Image image = getImageFromFile(file);
            image.setPreviewImage(true);
            product.addImageToProduct(image);
        }

        log.info("Saving new product. Title: {}; Description: {}", product.getTitle(), product.getDescription());

        Product productFromDb = productRepository.save(product);
        if (productFromDb.getImages().size() != 0) {
            productFromDb.setImagePreviewId(productFromDb.getImages().get(0).getId());
            productRepository.save(productFromDb);
        }
    }

    public void addImageToProduct(Product product, MultipartFile file)
    throws IOException {
        if (file.getSize() != 0) {
            Image image = getImageFromFile(file);
            product.addImageToProduct(image);
        }

        Product productFromDb = productRepository.save(product);
        if (productFromDb.getImages().size() != 0) {
            productFromDb.setImagePreviewId(productFromDb.getImages().get(0).getId());
            productRepository.save(productFromDb);
        }
    }

    private Image getImageFromFile(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteImage(Product product, Long imageId) {
        for (Image image: product.getImages()) {
            if (imageId == image.getId()) {
                product.getImages().remove(image);
                return;
            }
        }
    }
}