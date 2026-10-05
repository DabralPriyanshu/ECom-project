package com.example.Fake_Commerce_App.schema;

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
@Table(name = "reviews")
@SQLDelete(sql="UPDATE reviews set deleted_at=NOW() WHERE ID=?")
@SQLRestriction("deleted_at IS NULL")
public class Review extends BaseEntity{

    private String comment;
    @Column(nullable = false)
    private BigDecimal rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;
}
