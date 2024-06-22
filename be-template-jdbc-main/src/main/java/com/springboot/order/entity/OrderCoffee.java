package com.springboot.order.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table("ORDER_COFFEE")
public class OrderCoffee {
    @Id
    private long OrderCoffeeId;
    private long CoffeeId;
    private int quantity;
}
