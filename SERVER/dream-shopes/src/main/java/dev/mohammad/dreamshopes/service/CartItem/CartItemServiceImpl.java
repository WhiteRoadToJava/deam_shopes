package dev.mohammad.dreamshopes.service.CartItem;

import dev.mohammad.dreamshopes.model.Cart;
import dev.mohammad.dreamshopes.model.CartItem;
import dev.mohammad.dreamshopes.model.Product;
import dev.mohammad.dreamshopes.repository.CartItemRepository;
import dev.mohammad.dreamshopes.repository.CartRepository;
import dev.mohammad.dreamshopes.service.CardService.CartService;
import dev.mohammad.dreamshopes.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1 Get the cart
        //2 get the product
        //3 check if the product already in the cart
        //4 if yes, then increase the quantity with  the requested quaantity.
        //5  if no, the initiate a new CartItem entry.

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
        
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productOd) {

    }

    @Override
    public void updateItemQuantity(Long cartId, Long productOd, int quantity) {

    }
}
