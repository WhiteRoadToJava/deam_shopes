package dev.mohammad.dreamshopes.service.CardService;

import dev.mohammad.dreamshopes.exception.ResourceNotFoundException;
import dev.mohammad.dreamshopes.model.Cart;


import dev.mohammad.dreamshopes.model.CartItem;
import dev.mohammad.dreamshopes.repository.CartItemRepository;
import dev.mohammad.dreamshopes.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCard(Long id) {
        Cart cart   = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("cart not found"));

        BigDecimal totalAmount = cart.getTatalAmount();
        cart.setTatalAmount(totalAmount);
        return cartRepository.save(cart);

    }


    @Override
    public void clearCard(Long id) {
        Cart cart = getCard(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCard(id);
        return cart
                .getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
