package dev.mohammad.dreamshopes.service.CardService;

import dev.mohammad.dreamshopes.exception.ResourceNotFoundException;
import dev.mohammad.dreamshopes.model.Cart;


import dev.mohammad.dreamshopes.model.CartItem;
import dev.mohammad.dreamshopes.repository.CartItemRepository;
import dev.mohammad.dreamshopes.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart   = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        BigDecimal totalAmount = cart.getTatalAmount();
        cart.setTatalAmount(totalAmount);
        return cartRepository.save(cart);

    }


    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart
                .getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();

    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return null;
    }
}
