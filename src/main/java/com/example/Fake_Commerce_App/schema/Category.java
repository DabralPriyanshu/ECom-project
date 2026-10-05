package com.example.Fake_Commerce_App.schema;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
@SQLDelete(sql="UPDATE categories set deleted_at=NOW() WHERE ID=?")
@SQLRestriction("deleted_at IS NULL")
public class Category extends BaseEntity{
    @Column(nullable = false,unique = true)
    private String name;


}
