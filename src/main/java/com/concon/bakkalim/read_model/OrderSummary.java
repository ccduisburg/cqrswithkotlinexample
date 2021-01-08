package com.concon.bakkalim.read_model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class OrderSummary {
    @Id
    private UUID id;
    private Double price;
    private Integer number;
    private String productid;
    @ManyToOne
    @JoinColumn(name="productid", insertable = false, updatable = false)
    private ProductSummary summary;

    public OrderSummary(UUID id, Double price, Integer number, String productid, ProductSummary summary) {
        this.id = id;
        this.price = price;
        this.number = number;
        this.productid = productid;
        this.summary = summary;
    }


    public OrderSummary(UUID orderId, double price, int number, String productId) {
        this.id = UUID.randomUUID();
        this.price = price;
        this.number = number;
        this.productid = productid;
    }
}
