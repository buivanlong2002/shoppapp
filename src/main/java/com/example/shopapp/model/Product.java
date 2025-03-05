package com.example.shopapp.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name="products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, length = 350)
    private String name;

    private float price;

    @Column(name="thumbnail", nullable = true, length = 300)
    private String thumbnail;

    @Column(name="description")
    private String description;


    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
