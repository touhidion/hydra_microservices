package com.prozuktilab.productservice.service;

import com.prozuktilab.productservice.dto.ProductRequest;
import com.prozuktilab.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(String id);

    ProductResponse deleteProductById(String id);
}
