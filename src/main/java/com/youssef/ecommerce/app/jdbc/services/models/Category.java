package com.youssef.ecommerce.app.jdbc.services.models;

import lombok.*;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Category {

    private Integer id;

    private String name;

    private Set<Product> products;

}
