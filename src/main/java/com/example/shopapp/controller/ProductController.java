package com.example.shopapp.controller;


import com.example.shopapp.dtos.ProductDTO;

import com.example.shopapp.dtos.ProductImageDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Product;
import com.example.shopapp.model.ProductImage;
import com.example.shopapp.response.ProductListResponse;
import com.example.shopapp.response.ProductResponse;
import com.example.shopapp.service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;



    @GetMapping("")//http://localhost:5000/api/v1/product?page=1&limit=10
    public ResponseEntity<ProductListResponse> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // tạo pageable từ thông tin trang và giới hạn
        PageRequest pageRequest = PageRequest.of(page, limit
        );
        Page<ProductResponse> productPage = productService.getAllProducts(pageRequest);
        //lấy tông số trang
        int totalPages = productPage.getTotalPages();
       List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse.builder()
                .products(products)
                .totalPages(totalPages).build());
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long productID) {
        try {
           Product existingProduct = productService.getProductById(productID);
           return ResponseEntity.ok(ProductListResponse.build(existingProduct));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // http://localhost:5000/api/v1/products
    @PostMapping
    public ResponseEntity<?> addProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult bindingResult) {
        try{
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);

            }
            Product newProduct = productService.addProduct(productDTO);
            return ResponseEntity.ok(newProduct.getId() );
        }catch ( Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // http://localhost:5000/api/v1/products
    @PostMapping(value = "upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> UploadImages(
            @PathVariable("id") Long productId,
            @RequestParam("files") List<MultipartFile> files) {
        try {
//            if(files == null || files.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
            Product existingProduct = productService.getProductById(productId);
            List<ProductImage> productImages = new ArrayList<>();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // kiểm tra kích thước file và định dạng file
                if(file.getSize() > 10 * 1024 *1024){  // kích thước > 10MB
                    throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,"file is too large! Maximum size í 10MB");
                }
                String contentType =file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("file must be an image");
                }
                String filename = storeFile(file);
                /// luư vào đb product_image
                ProductImage newProductImage= productService.addProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(filename)
                                .build());
                productImages.add(newProductImage);
            }
            return ResponseEntity.ok(productImages.toString());
        }catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    private String storeFile(MultipartFile file ) throws IOException{
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm UUID đảm tên file này là duy nhất
          String uniqueFileName = UUID.randomUUID().toString() + ""+ fileName;
          // đường dẫn dến thư mục lưu file
        java.nio.file.Path updoadDir = Paths.get("uploads");

        // kiểm tra và tạo nếu thư mục không tồn tại
        if (!Files.exists(updoadDir)){
             Files.createDirectories(updoadDir);
        }
        // đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(updoadDir.toString(),uniqueFileName);

        // sao chép file vào thư muc đích
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable long id,
             @RequestBody ProductDTO productDTO) {
       try {
            Product updateProduct = productService.updateProduct(id,productDTO);
            return ResponseEntity.ok(updateProduct);
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());

       }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            productService.deleteProduct(Long.valueOf(id));
            return ResponseEntity.ok(String.format("Product with id %s has been deleted", id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    // fake dữ liệu
    @PostMapping("/fakeProducts")
    public ResponseEntity<String> fakeProducts() throws DataNotFoundException {
        Faker fake = new Faker();
        for (int i = 0; i < 50; i++) {
            String productName = fake.name().name();
            if (productService.existsByName(productName)){
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)fake.number().numberBetween(10,90000))
                    .description(fake.lorem().sentence())
                    .thumbnail("")
                    .categoryId((long)fake.number().numberBetween(6,9))
                    .build();
            try {
                productService.addProduct(productDTO);
            }catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
        return ResponseEntity.ok("thanh công");
    }
}
