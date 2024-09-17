package com.yunha.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "category_code")
    private Long categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @JoinColumn(name = "category_user_code")
    @ManyToOne
    private User categoryUser;

    @Column(name = "category_is_private")
    private boolean categoryIsPrivate;

    public Category(Long categoryCode) {
        this.categoryCode = categoryCode;
    }

}
