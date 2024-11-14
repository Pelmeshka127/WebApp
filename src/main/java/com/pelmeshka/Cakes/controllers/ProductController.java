package com.pelmeshka.Cakes.controllers;

import com.pelmeshka.Cakes.models.Image;
import com.pelmeshka.Cakes.models.Product;
import com.pelmeshka.Cakes.models.User;
import com.pelmeshka.Cakes.repositories.ImageRepository;
import com.pelmeshka.Cakes.services.ProductService;
import com.pelmeshka.Cakes.services.UserService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ImageRepository imageRepository;

    @GetMapping("/")
    public String products(Principal principal, Model model) {
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String getProductInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("user", productService.getUserByPrincipal(principal)); //
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam(name = "imageFile") MultipartFile file, Product product, Principal principal)
            throws IOException {
        productService.createProduct(principal, product, file);
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
        Product product = productService.findProductById(id);
        productService.deleteProductFromUser(product.getUser(), id);
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @PostMapping("/product/deleteImage/{productId}/{imageId}")
    public String deleteImage(@PathVariable Long productId, @PathVariable Long imageId) {
        Product product = productService.findProductById(productId);
        productService.deleteImage(product, imageId);
        imageRepository.deleteById(imageId);
        return "redirect:/product/{productId}";
    }
}