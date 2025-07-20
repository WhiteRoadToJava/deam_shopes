package dev.mohammad.dreamshopes.controller;


import dev.mohammad.dreamshopes.model.Order;
import dev.mohammad.dreamshopes.response.ApiResponse;
import dev.mohammad.dreamshopes.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/order")
    private ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Order created", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }


    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<Order> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Order found", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }


}