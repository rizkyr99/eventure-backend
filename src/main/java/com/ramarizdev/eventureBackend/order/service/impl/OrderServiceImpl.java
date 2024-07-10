package com.ramarizdev.eventureBackend.order.service.impl;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.service.TicketTypeService;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.order.dto.OrderDto;
import com.ramarizdev.eventureBackend.order.entity.Order;
import com.ramarizdev.eventureBackend.order.entity.OrderItem;
import com.ramarizdev.eventureBackend.order.repository.OrderRepository;
import com.ramarizdev.eventureBackend.order.service.OrderService;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.entity.Point;
import com.ramarizdev.eventureBackend.user.service.impl.AttendeeServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final AttendeeServiceImpl attendeeService;
    private final EventServiceImpl eventService;
    private final TicketTypeService ticketTypeService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(AttendeeServiceImpl attendeeService, EventServiceImpl eventService, TicketTypeService ticketTypeService, OrderRepository orderRepository) {
        this.attendeeService = attendeeService;
        this.eventService = eventService;
        this.ticketTypeService = ticketTypeService;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();

        Event event = eventService.getEventDetails(orderDto.getEventId());
        order.setEvent(event);

        Attendee attendee = attendeeService.getAttendeeById(orderDto.getAttendeeId());
        order.setAttendee(attendee);

        if(orderDto.isUsePoints()) {
            BigDecimal availablePoints = new BigDecimal(calculateAvailablePoints(attendee.getPoints()));
            BigDecimal usedPoints = availablePoints.min(orderDto.getTotalPrice());

            orderDto.setTotalPrice(orderDto.getTotalPrice().subtract(usedPoints));
        }

        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(
                orderItemDto -> {
                    OrderItem orderItem1 = new OrderItem();

                    TicketType ticketType = ticketTypeService.getTicketTypeById(orderItemDto.getTicketTypeId());

                    ticketTypeService.reduceQuantity(ticketType);
                    orderItem1.setTicketType(ticketType);

                    orderItem1.setPrice(orderItemDto.getPrice());
                    orderItem1.setQuantity(orderItemDto.getQuantity());

                    orderItem1.setOrder(order);

                    return orderItem1 ;
                }
        ).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalPrice(orderDto.getTotalPrice());

        orderRepository.save(order);

        return order.toDto();
    }

    private Integer calculateAvailablePoints(List<Point> points) {
        return points.stream().filter(point -> point.getExpirationDate().isAfter(LocalDate.now()))
                .mapToInt(Point::getAmount)
                .sum();
    }
}
