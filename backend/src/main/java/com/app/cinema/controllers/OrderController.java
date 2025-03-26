package com.app.cinema.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.cinema.controllers.requestbody.OrdersRequest;
import com.app.cinema.exceptions.MessageException;
import com.app.cinema.models.Order;
import com.app.cinema.services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/admin")
    public ResponseEntity<?> getOrders(@RequestBody(required = false) OrdersRequest ordersRequest) {
        try {
            List<Order> orders = orderService.getOrders(ordersRequest);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable(name = "userId", required = true) Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok().body(orders);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/buy-ticket")
    public ResponseEntity<?> byTicket(@RequestParam(name = "sessionId", required = true) Long sessionId,
            @RequestParam(name = "seatId", required = true) Long seatId) {
        try {
            Order order = orderService.buyTicket(sessionId, seatId);
            return ResponseEntity.ok().body(order);
        } catch (MessageException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable(name = "id", required = true) Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().body("Удалено");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
