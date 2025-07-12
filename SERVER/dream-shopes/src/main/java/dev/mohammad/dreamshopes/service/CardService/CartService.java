package dev.mohammad.dreamshopes.service.CardService;


import dev.mohammad.dreamshopes.model.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCard(Long id);
    void clearCard(Long id);
    BigDecimal getTotalPrice(Long id);
}
