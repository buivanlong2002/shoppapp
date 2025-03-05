package com.example.shopapp.controller;

import com.example.shopapp.dtos.*;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.OrderDetails;
import com.example.shopapp.response.OrderDetailsResponse;
import com.example.shopapp.service.OrderDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;


    @PostMapping
    public ResponseEntity<OrderDetailsResponse> addOrder(
            @Valid @RequestBody OrderDetailsDTO orderDetailsDTO
    ) throws DataNotFoundException {
        try {
            OrderDetails orderDetails = orderDetailsService.addOrderDetails(orderDetailsDTO);
            return ResponseEntity.ok().body(OrderDetailsResponse.fromOrderDetailsResponse(orderDetails));
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetail(
            @Valid @PathVariable("id") Long id)
            throws DataNotFoundException {

            OrderDetails orderDetails = orderDetailsService.getOrderDetailsById(id);
            return ResponseEntity.ok().body(OrderDetailsResponse.fromOrderDetailsResponse(orderDetails));

    }

    /// lấy ra dánh sách các orderDetail của 1 order nào đó
    @GetMapping("/order/{order_id}")
    public ResponseEntity<?> getOrderDetails(
            @Valid @PathVariable("order_id") Long order_id) {
        List<OrderDetails> orderDetailsList = orderDetailsService.getAllOrderDetails(order_id);
        List<OrderDetailsResponse> orderDetailsResponseList = orderDetailsList
                .stream()
                .map(orderDetails -> OrderDetailsResponse.fromOrderDetailsResponse(orderDetails))
                        .toList();
        return ResponseEntity.ok(orderDetailsResponseList);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateOrderDetails(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailsDTO newOrderDetailData
    ) throws  DataNotFoundException{
        try {
           OrderDetails orderDetails = orderDetailsService.updateOrderDetails(id, newOrderDetailData);
            return ResponseEntity.ok().body(orderDetails);
        }catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetail(@PathVariable long id) {
           orderDetailsService.deleteOrderDetailsById(id);
        return ResponseEntity.ok("xóa");
    }
}
