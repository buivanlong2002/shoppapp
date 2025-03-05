package com.example.shopapp.service;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    public Product getProductById(Long id);
    public Product addProduct(ProductDTO request) throws Exception;
    public Product updateProduct(Long id ,ProductDTO request) throws Exception;
    public void deleteProduct(Long id);
    Boolean existsByName(String name);

    ProductImage addProductImage(Long id, ProductImageDTO productImageDTO) throws Exception;

}
