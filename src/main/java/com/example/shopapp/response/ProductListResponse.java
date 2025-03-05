package com.example.shopapp.response;

import com.example.shopapp.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;
    public static ProductResponse build(Product product) {
        ProductResponse pr = ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .category(product.getCategory().getId())
                .build();
        pr.setCreateAt(product.getCreateAt());
        pr.setUpdateAt(product.getUpdateAt());
        return pr;
    }
}
