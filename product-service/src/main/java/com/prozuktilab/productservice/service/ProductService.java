package com.prozuktilab.productservice.service;

import com.prozuktilab.productservice.dto.ProductRequest;
import com.prozuktilab.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    public void createProduct(ProductRequest productRequest);
    public List<ProductResponse> getAllProducts();
}
