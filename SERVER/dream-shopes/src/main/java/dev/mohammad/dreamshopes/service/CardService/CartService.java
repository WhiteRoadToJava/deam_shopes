package dev.mohammad.dreamshopes.service.CardService;


import dev.mohammad.dreamshopes.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
