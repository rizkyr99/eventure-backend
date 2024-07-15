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
import com.ramarizdev.eventureBackend.user.service.impl.PointServiceImpl;
import com.ramarizdev.eventureBackend.voucher.dto.CreateVoucherDto;
import com.ramarizdev.eventureBackend.voucher.entity.Voucher;
import com.ramarizdev.eventureBackend.voucher.service.impl.VoucherServiceImpl;
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
    private final PointServiceImpl pointService;
    private final VoucherServiceImpl voucherService;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(AttendeeServiceImpl attendeeService, EventServiceImpl eventService, TicketTypeService ticketTypeService, PointServiceImpl pointService, VoucherServiceImpl voucherService, OrderRepository orderRepository) {
        this.attendeeService = attendeeService;
        this.eventService = eventService;
        this.ticketTypeService = ticketTypeService;
        this.pointService = pointService;
        this.voucherService = voucherService;
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

        BigDecimal usedPoints = BigDecimal.ZERO;

        if(orderDto.isUsePoints()) {
            BigDecimal availablePoints = new BigDecimal(calculateAvailablePoints(attendee.getPoints()));
            usedPoints = availablePoints.min(orderDto.getTotalPrice());

            orderDto.setTotalPrice(orderDto.getTotalPrice().subtract(usedPoints));
        }

        List<OrderItem> orderItems = orderDto.getOrderItems().stream().map(
                orderItemDto -> {
                    OrderItem orderItem1 = new OrderItem();

                    TicketType ticketType = ticketTypeService.getTicketTypeById(orderItemDto.getTicketTypeId());
                    if (ticketType.getQuantity() < orderItemDto.getQuantity()) {
                        throw new IllegalArgumentException("Insufficient quantity for ticket type: " + ticketType.getName());
                    }

                    ticketTypeService.reduceQuantity(ticketType, orderItemDto.getQuantity());

                    orderItem1.setTicketType(ticketType);

                    orderItem1.setPrice(orderItemDto.getPrice());
                    orderItem1.setQuantity(orderItemDto.getQuantity());

                    orderItem1.setOrder(order);

                    return orderItem1 ;
                }
        ).collect(Collectors.toList());

        List<Voucher> vouchers = orderDto.getVoucherIds().stream().map(voucherService::getVoucherById).collect(Collectors.toList());

        order.setVouchers(vouchers);
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderDto.getTotalPrice());

        if(orderDto.isUsePoints() && usedPoints.intValue() > 0) {
            pointService.usePoint(attendee, usedPoints.intValue() * -1);
        }

        orderRepository.save(order);


        return order.toDto();
    }

    private Integer calculateAvailablePoints(List<Point> points) {
        return points.stream().filter(point -> point.getExpirationDate() == null || point.getExpirationDate().isAfter(LocalDate.now()))
                .mapToInt(Point::getAmount)
                .sum();
    }
}
