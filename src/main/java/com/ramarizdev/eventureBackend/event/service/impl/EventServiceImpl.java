package com.ramarizdev.eventureBackend.event.service.impl;

import com.cloudinary.Cloudinary;
import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.category.repository.CategoryRepository;
import com.ramarizdev.eventureBackend.event.dto.*;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.Review;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.repository.EventRepository;
import com.ramarizdev.eventureBackend.event.service.EventService;
import com.ramarizdev.eventureBackend.event.specification.EventSpecification;
import com.ramarizdev.eventureBackend.user.entity.Organizer;
import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.repository.OrganizerRepository;
import com.ramarizdev.eventureBackend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerRepository organizerRepository;
    private final UserRepository userRepository;
    private final Cloudinary cloudinary;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, OrganizerRepository organizerRepository, UserRepository userRepository, Cloudinary cloudinary) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.organizerRepository = organizerRepository;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public Page<EventSummaryDto> getAllEvents(String categorySlug, String location, boolean isFree, String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Event> spec = Specification.where(null);

        if(categorySlug != null && !categorySlug.isEmpty()) {
            spec = spec.and(EventSpecification.hasCategory(categorySlug));
        }
        if (location != null && !location.isEmpty()) {
            spec = spec.and(EventSpecification.hasLocation(location));
        }
        spec = spec.and(EventSpecification.isFree(isFree));

        if (search != null && !search.isEmpty()) {
            spec = spec.and(EventSpecification.containsKeyword(search));
        }

        Page<Event> eventPage = eventRepository.findAll(spec,pageable);

        List<EventSummaryDto> eventResponseDtos = eventPage.getContent().stream().map(
                event -> {
                    EventSummaryDto summaryDto = event.toSummaryDto();

                    Optional<Double> lowestPrice = eventRepository.findLowestTicketPrice(event.getId());
                    lowestPrice.ifPresent(summaryDto::setLowestPrice);

                    String imageUrl = cloudinary.url().format("png").secure(true).generate(event.getImage());
                    summaryDto.setImage(imageUrl);

                    return summaryDto;
                }
        ).collect(Collectors.toList());

        return new PageImpl<>(eventResponseDtos, pageable, eventPage.getTotalElements());
    }

    @Override
    public Event getEventDetails(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        return event;
    }

    @Override
    public Event getEventBySlug(String slug) {
        Event event = eventRepository.findBySlug(slug).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        return event;
    }

    @Override
    @Transactional
    public EventSummaryDto createEvent(EventRequestDto requestDto, Long organizerId) {
        Event event = requestDto.toEntity();

        Category category = categoryRepository.findById(requestDto.getCategory()).orElseThrow(
                () -> new IllegalArgumentException("Invalid category ID")
        );

        event.setCategory(category);

        String imageUrl = uploadFile(requestDto.getImage(), "events");

        event.setImage(imageUrl);

        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> new IllegalArgumentException("Organizer not found"));
        event.setOrganizer(organizer);

        if(!requestDto.getIsFree()) {
            if(requestDto.getTicketTypes() != null) {
                List<TicketType> ticketTypes = requestDto.getTicketTypes().stream().map(
                        ticketType -> {
                            TicketType ticketType1 = new TicketType();
                            ticketType1.setName(ticketType.getName());
                            ticketType1.setPrice(ticketType.getPrice());
                            ticketType1.setQuantity(ticketType.getQuantity());
                            ticketType1.setEvent(event);
                            return ticketType1;
                        }).collect(Collectors.toList());

                event.setTicketTypes(ticketTypes);
            }

        }


        Event newEvent = eventRepository.save(event);

        EventSummaryDto responseDto = newEvent.toSummaryDto();

        return responseDto;
    }

    @Override
    public EventSummaryDto updateEvent(Long eventId, EventRequestDto requestDto, String email) throws AccessDeniedException {
        Event existEvent = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        if(!user.getOrganizer().getId().equals(existEvent.getOrganizer().getId())) {
            throw new AccessDeniedException("You are not authorized to edit this event.");
        }

        // Update the event details based on the request DTO
        existEvent.setName(requestDto.getName());
        existEvent.setDescription(requestDto.getDescription());
        existEvent.setStartDate(requestDto.getStartDate());
        existEvent.setEndDate(requestDto.getEndDate());
        existEvent.setLocation(requestDto.getLocation());

        Category category = categoryRepository.findById(requestDto.getCategory())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        existEvent.setCategory(category);

        if (requestDto.getImage() != null) {
            String imageUrl = uploadFile(requestDto.getImage(), "events");
            existEvent.setImage(imageUrl);
        }

        List<TicketType> updatedTicketTypes = requestDto.getTicketTypes().stream().map(ticketType -> {
            // Check if ticket type exists and update or create new ones
            TicketType existingTicketType = existEvent.getTicketTypes().stream()
                    .filter(tt -> tt.getId().equals(ticketType.getId()))
                    .findFirst()
                    .orElse(new TicketType());

            existingTicketType.setName(ticketType.getName());
            existingTicketType.setPrice(ticketType.getPrice());
            existingTicketType.setQuantity(ticketType.getQuantity());
            existingTicketType.setEvent(existEvent);

            return existingTicketType;
        }).collect(Collectors.toList());

        existEvent.setTicketTypes(updatedTicketTypes);

        Event updatedEvent = eventRepository.save(existEvent);


        return updatedEvent.toSummaryDto();

    }

    @Override
    public List<ReviewDto> getEventReviews(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        List<ReviewDto> reviewDtos = event.getReviews().stream().map(Review::toDto).collect(Collectors.toList());

        return reviewDtos;
    }

    @Override
    public List<TicketTypeDto> getTicketTypesByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));

        List<TicketTypeDto> ticketTypeDtos = event.getTicketTypes().stream().map(TicketType::toDto).collect(Collectors.toList());

        return ticketTypeDtos;
    }

    @Override
    public void deleteEvent(Long eventId, String email) throws AccessDeniedException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        if(!user.getOrganizer().getId().equals(event.getOrganizer().getId())) {
            throw new AccessDeniedException("You are not authorized to delete this event.");
        }

        eventRepository.delete(event);

    }

    private String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return publicId;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
