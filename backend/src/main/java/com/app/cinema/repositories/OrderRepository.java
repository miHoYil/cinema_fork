package com.app.cinema.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.cinema.models.Order;
import com.app.cinema.models.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUser(User user);

    List<Order> findByOrderTimeBetween(Instant start, Instant end);

    List<Order> findByOrderTimeAfter(Instant start);

    List<Order> findByOrderTimeBefore(Instant end);

}
