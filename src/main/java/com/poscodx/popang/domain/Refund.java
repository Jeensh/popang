package com.poscodx.popang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reason;
    private Long status;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
