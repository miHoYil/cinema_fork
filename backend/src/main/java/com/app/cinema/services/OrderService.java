package com.app.cinema.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.cinema.controllers.requestbody.OrdersRequest;
import com.app.cinema.exceptions.MessageException;
import com.app.cinema.models.Order;
import com.app.cinema.models.Seat;
import com.app.cinema.models.Session;
import com.app.cinema.models.User;
import com.app.cinema.repositories.OrderRepository;
import com.app.cinema.security.UserDetailsImpl;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    SeatService seatService;

    public List<Order> getOrders(OrdersRequest ordersRequest) {
        if (ordersRequest != null) {
            if (ordersRequest.getStart() != null && ordersRequest.getEnd() != null) {
                return (List<Order>) orderRepository.findByOrderTimeBetween(ordersRequest.getStart(),
                        ordersRequest.getEnd());
            } else if (ordersRequest.getStart() != null) {
                return (List<Order>) orderRepository.findByOrderTimeAfter(ordersRequest.getStart());

            } else if (ordersRequest.getEnd() != null) {
                return (List<Order>) orderRepository.findByOrderTimeBefore(ordersRequest.getEnd());

            }
        }
        return (List<Order>) orderRepository.findAll();
    }

    public Order buyTicket(Long sessionId, Long seatId) throws Exception {
        String username = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            Session session = sessionService.getSession(sessionId);
            Seat seat = seatService.getSeat(seatId);
            if (session != null && seat != null) {
                if (!session.getListOfReservedSeats().contains(seat)) {
                    Order order = new Order();
                    order.setUser(user);
                    order.setSession(session);
                    order.setSeat(seat);
                    order.setOrderTime(Instant.now());
                    System.out.println(Instant.now());
                    orderRepository.save(order);
                    session.getListOfReservedSeats().add(seat);
                    sessionService.save(session);
                    return order;
                } else {
                    throw new MessageException("Место занято");
                }
            } else {
                throw new MessageException("Сеанс или место не найдено");
            }
        } else {
            throw new MessageException("Пользователь не найден");
        }

    }

    public List<Order> getOrdersByUserId(Long userId) throws Exception {
        User user = userService.getUser(userId);
        UserDetailsImpl currentUser = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin || currentUser.getId() == userId) {
            return (List<Order>) orderRepository.findByUser(user);
        } else {
            throw new MessageException("Это не твои заказы");
        }
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
