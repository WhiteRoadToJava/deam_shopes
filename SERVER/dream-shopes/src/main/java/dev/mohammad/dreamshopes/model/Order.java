package dev.mohammad.dreamshopes.model;

import dev.mohammad.dreamshopes.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;




@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate  orderDate;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
