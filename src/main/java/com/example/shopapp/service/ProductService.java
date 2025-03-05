package com.example.shopapp.service;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.mapper.ProductMapper;
import com.example.shopapp.model.Category;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.repositories.CategoryRepository;
import com.example.shopapp.repositories.ProductImageRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.response.ProductListResponse;
import com.example.shopapp.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImageRepository productImageRepository;




    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        /// lấy danh sách theo trang và giới hạn
        return productRepository.findAll(pageRequest).map(product -> ProductListResponse.build(product)
        );

    };


    @Override
    public Product getProductById(Long id)  {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product addProduct(ProductDTO request) throws DataNotFoundException {
        try {
            /// check xem co categoryId hay k
           Category existingCategory= categoryRepository.findById(request.getCategoryId()).
                    orElseThrow(()-> new DataNotFoundException("Category not found" + request.getCategoryId()));
            Product newProduct = Product.builder()
                    .name(request.getName())
                    .price(request.getPrice())
                    .thumbnail(request.getThumbnail())
                    .description(request.getDescription())
                    .category(existingCategory).build();
            return productRepository.save(newProduct);
        }catch (DataNotFoundException e){
            throw new DataNotFoundException("Product not found");
        }

    }

    @Override
    public Product updateProduct(Long id ,ProductDTO request) throws Exception{
        Product product = getProductById(id);
        productMapper.updateProduct(product,request);
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
      Optional<Product> product = productRepository.findById(id);
      if (product.isPresent()) {
          productRepository.delete(product.get());
      }
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage addProductImage(
            Long productId,
            ProductImageDTO productImageDTO) throws DataNotFoundException {
        Product exstingProduct = productRepository.findById(productId).orElse(null);
        ProductImage newProductImage = ProductImage.builder().product(exstingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        /// không cho tạo quá 5 ảnh trong 1 sản phẩm
       int size = productImageRepository.findByProductId(productId).size();
        if (size >= 5) {
            throw new DataNotFoundException("number of images must be <= 5");
        }
        return productImageRepository.save(newProductImage);
    }
}
