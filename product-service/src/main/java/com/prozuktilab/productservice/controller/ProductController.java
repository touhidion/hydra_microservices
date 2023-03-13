package com.prozuktilab.productservice.controller;

import com.prozuktilab.productservice.dto.AuthRequest;
import com.prozuktilab.productservice.dto.ProductRequest;
import com.prozuktilab.productservice.dto.ProductResponse;
import com.prozuktilab.productservice.service.JwtService;
import com.prozuktilab.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@RequestParam String id) {
        return productService.getProductById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse deleteProduct(@RequestParam String id){
        return productService.deleteProductById(id);
    }

    @PostMapping("/auth")
    public String authenticatedAndGetToken(@RequestBody AuthRequest authRequest){
        return jwtService.generateToken(authRequest.getUsername());
    }
}
