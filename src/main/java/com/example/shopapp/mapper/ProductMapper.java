package com.example.shopapp.mapper;


import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.Product;
import com.example.shopapp.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductDTO categoryDTO);
    void updateProduct(@MappingTarget Product product, ProductDTO productDTO);
}

