package com.example.Fake_Commerce_App.schema;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@SQLDelete(sql="UPDATE orders set deleted_at=NOW() WHERE ID=?")
@SQLRestriction("deleted_at IS NULL")
public class Order extends BaseEntity {
    @Enumerated(value = EnumType.ORDINAL)
    private OrderStatus status;

//    @ManyToMany
//    // only create new table with three field id and both table ids you can not add other
//    // field in through/join table
//    @JoinTable(name = "order_products",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private List<Product> products = new ArrayList<>();
}
