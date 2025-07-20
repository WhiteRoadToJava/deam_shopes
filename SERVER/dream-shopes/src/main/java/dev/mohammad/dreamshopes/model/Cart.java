package dev.mohammad.dreamshopes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cart {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal tatalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
        updateTotalAmount();
    }
    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        this.tatalAmount = items.stream().map(
                item ->{
                    BigDecimal unitPrice = item.getUnitPrice();
                    if(unitPrice == null){
                        return BigDecimal.ZERO;
                    }
                    return unitPrice.multiply(new BigDecimal(item.getQuantity()));
                }
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
