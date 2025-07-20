package dev.mohammad.dreamshopes.service.order;

import dev.mohammad.dreamshopes.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

}
