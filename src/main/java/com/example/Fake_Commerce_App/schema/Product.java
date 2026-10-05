package com.example.Fake_Commerce_App.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@SQLDelete(sql="UPDATE products set deleted_at=NOW() WHERE ID=?")
@SQLRestriction("deleted_at IS NULL")


public class Product extends BaseEntity{
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

     @Column(nullable = false)
    private BigDecimal rating;

}
