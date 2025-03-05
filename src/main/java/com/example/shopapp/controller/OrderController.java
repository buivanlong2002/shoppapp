package com.example.shopapp.controller;

import com.example.shopapp.dtos.*;
import com.example.shopapp.model.Order;
import com.example.shopapp.response.OrderResponse;
import com.example.shopapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<?> addOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult bindingResult
    ) {
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errors);
            }
            Order orderResponse = orderService.addOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    //GET http://localhost:5000/api/v1/ordes/4
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long user_id) {
        try {
            List<Order> listOrderUserId = orderService.getAllOrderUserId(user_id);
            return ResponseEntity.ok(listOrderUserId);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    //GET http://localhost:5000/api/v1/ordes/4
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long id) {
        try {
            Order existingOrder = orderService.getOrderById(id);
            return ResponseEntity.ok(existingOrder);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    //PUT http://localhost:5000/api/v1/ordes/4
    // công việc admin
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable Long id,
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        try {
            Order existingOrder = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(existingOrder);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrders(@Valid @PathVariable Long id) {
        // xóa mềm => cập nhập active = false
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("xóa");
    }
}
