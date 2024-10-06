package com.pelmeshka.Cakes.controllers;

import com.pelmeshka.Cakes.models.Product;
import com.pelmeshka.Cakes.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name = "title", required = false) String title, Model model) {
        model.addAttribute("products", productService.getProducts(title));
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam(name = "file1") MultipartFile file, Product product) throws IOException {
        productService.createProduct(product, file);
        return "redirect:/";
    }

    @PostMapping("/product/addimage/{id}")
    public String addImage(@RequestParam(name = "file1")MultipartFile file, @PathVariable Long id) throws IOException {
        productService.addImage(productService.findProductById(id), file);
        return "redirect:/product/{id}";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
