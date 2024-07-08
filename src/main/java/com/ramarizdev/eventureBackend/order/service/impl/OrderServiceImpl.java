package com.ramarizdev.eventureBackend.order.service.impl;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.service.TicketTypeService;
import com.ramarizdev.eventureBackend.event.service.impl.EventServiceImpl;
import com.ramarizdev.eventureBackend.order.dto.OrderDto;
import com.ramarizdev.eventureBackend.order.entity.Order;
import com.ramarizdev.eventureBackend.order.entity.OrderItem;
import com.ramarizdev.eventureBackend.order.service.OrderService;
import com.ramarizdev.eventureBackend.user.entity.Attendee;
import com.ramarizdev.eventureBackend.user.service.impl.AttendeeServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final AttendeeServiceImpl attendeeService;
    private final EventServiceImpl eventService;
    private final TicketTypeService ticketTypeService;

    public OrderServiceImpl(AttendeeServiceImpl attendeeService, EventServiceImpl eventService, TicketTypeService ticketTypeService) {
        this.attendeeService = attendeeService;
        this.eventService = eventService;
        this.ticketTypeService = ticketTypeService;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();

        Event event = eventService.getEventDetails(orderDto.getEventId());
        order.setEvent(event);

        Attendee attendee = attendeeService.getAttendeeById(orderDto.getAttendeeId());
        order.setAttendee(attendee);

        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(
                orderItemDto -> {
                    OrderItem orderItem1 = new OrderItem();

                    TicketType ticketType = ticketTypeService.getTicketTypeById(orderItemDto.getTicketTypeId());
                    orderItem1.setTicketType(ticketType);

                    orderItem1.setPrice(orderItemDto.getPrice());
                    orderItem1.setQuantity(orderItemDto.getQuantity());

                    orderItem1.setOrder(order);

                    return orderItem1 ;
                }
        ).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setTotalPrice(order.getTotalPrice());


        return order.toDto();
    }
}
