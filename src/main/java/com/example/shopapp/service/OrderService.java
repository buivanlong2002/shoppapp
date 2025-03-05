package com.example.shopapp.service;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.mapper.OrderMapper;
import com.example.shopapp.model.Order;
import com.example.shopapp.model.OrderStatus;
import com.example.shopapp.model.User;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final OrderMapper orderMapper;

    @Override
    public Order addOrder(OrderDTO orderDTO) throws Exception {
        // tìm xem user_id có tồn tại hay k
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        // convert orderDTO sang order
        Order newOrder = orderMapper.toOrder(orderDTO);
        newOrder.setUser(user);
        newOrder.setOrderDate(new Date());// ấy thời gian hiện tại
        newOrder.setStatus(OrderStatus.PENDING);


        //// kiểm tra shipping date phải > hôm nay
        LocalDate shippingDate = newOrder.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping Date is wrong");
        }
        newOrder.setActive(true);
        newOrder.setShippingDate(shippingDate);

        //// lưu orders
        orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO)
            throws DataNotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new DateTimeException("Order not found" + id));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(() ->
                new DateTimeException("User not found" + orderDTO.getUserId()));
        orderMapper.updateOrder(order, orderDTO);
        order.setUser(existingUser);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(long userId) {
        return orderRepository.findById(userId).orElseThrow();
    }

    @Override
    public void deleteOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        /// không xóa cứng ---> xóa mềmmm
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getAllOrderUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
