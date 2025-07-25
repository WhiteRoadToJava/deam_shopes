package dev.mohammad.dreamshopes.service.CartItem;

import dev.mohammad.dreamshopes.model.CartItem;

import java.math.BigDecimal;

public interface CartItemService {
    void addItemToCart(Long cariId, Long productOd, int quantity);
    void removeItemFromCart(Long cartId, Long productOd);
    void updateItemQuantity(Long cartId, Long productOd, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
