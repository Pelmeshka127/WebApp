package com.pelmeshka.Cakes.controllers;

import com.pelmeshka.Cakes.models.Image;
import com.pelmeshka.Cakes.models.Product;
import com.pelmeshka.Cakes.repositories.ImageRepository;
import com.pelmeshka.Cakes.services.ProductService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ImageRepository imageRepository;

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "products";
    }

    @GetMapping("/product/{id}")
    public String getProductInfo(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam(name = "imageFile") MultipartFile file, Product product)
    throws IOException {
        productService.createProduct(product, file);
        return "redirect:/";
    }

    @PostMapping("/product/addimage/{id}")
    public String addImageToProduct(@RequestParam(name = "imageFile") MultipartFile file, @PathVariable Long id)
    throws IOException {
        productService.addImageToProduct(productService.findProductById(id), file);
        return "redirect:/product/{id}";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/product/deleteImage/{productId}/{imageId}")
    public String deleteImage(@PathVariable Long productId, @PathVariable Long imageId) {
        System.out.println(productId + " " + imageId);
        System.out.println(imageRepository.getById(imageId).getId());
        productService.deleteImage(productService.findProductById(productId), imageId);
        imageRepository.deleteById(imageId);
        return "redirect:/product/{productId}";
    }
}