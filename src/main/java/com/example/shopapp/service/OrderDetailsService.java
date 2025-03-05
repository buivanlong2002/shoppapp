package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDetailsDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderDetails;
import com.example.shopapp.model.Product;
import com.example.shopapp.repositories.OrderDetailsRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService implements IOrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderDetails addOrderDetails(OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException {
        // tìm xem có order này k
        Order order = orderRepository.findById(orderDetailsDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order id not found" + orderDetailsDTO.getOrderId()));
        // tìm xem có sản phẩm này k
        Product product = productRepository.findById(orderDetailsDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Order id not found" + orderDetailsDTO.getOrderId()));
        OrderDetails orderDetails = OrderDetails.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailsDTO.getNumberOfProduct())
                .price(orderDetailsDTO.getPrice())
                .totalMoney(orderDetailsDTO.getTotalMoney())
                .color(orderDetailsDTO.getColor())
                .build();
        orderDetailsRepository.save(orderDetails);

        return orderDetails;
    }

    @Override
    public OrderDetails updateOrderDetails(Long id, OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException {
        OrderDetails existingOrderDetails = orderDetailsRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order id not found" + id));
        // tìm xem có order này k
        Order order = orderRepository.findById(orderDetailsDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Order id not found" + orderDetailsDTO.getOrderId()));
        // tìm xem có sản phẩm này k
        Product product = productRepository.findById(orderDetailsDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Order id not found" + orderDetailsDTO.getOrderId()));
        existingOrderDetails.setOrder(order);
        existingOrderDetails.setProduct(product);
        existingOrderDetails.setNumberOfProducts(orderDetailsDTO.getNumberOfProduct());
        existingOrderDetails.setPrice(orderDetailsDTO.getPrice());
        existingOrderDetails.setTotalMoney(orderDetailsDTO.getTotalMoney());
        existingOrderDetails.setColor(orderDetailsDTO.getColor());
        return orderDetailsRepository.save(existingOrderDetails);
    }

    @Override
    public OrderDetails getOrderDetailsById(long id) throws DataNotFoundException {
        return orderDetailsRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order id not found" + id));
    }

    @Override
    public void deleteOrderDetailsById(Long id) {
        orderDetailsRepository.deleteById(id);

    }

    @Override
    public List<OrderDetails> getAllOrderDetails(Long orderId) {
        return orderDetailsRepository.findByOrderId(orderId);
    }
}
