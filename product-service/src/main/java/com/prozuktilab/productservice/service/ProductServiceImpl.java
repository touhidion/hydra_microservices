package com.prozuktilab.productservice.service;

import com.prozuktilab.productservice.dto.ProductRequest;
import com.prozuktilab.productservice.dto.ProductResponse;
import com.prozuktilab.productservice.model.Product;
import com.prozuktilab.productservice.repository.ProductRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved.", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(this::dtoToProductResponse).toList();
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found!"));
        return dtoToProductResponse(product);
    }

    @Override
    public ProductResponse deleteProductById(String id) {
        ProductResponse productResponse = getProductById(id);
        if (productResponse != null) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invalid id to delete.");
        }
        return productResponse;
    }

    private ProductResponse dtoToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
