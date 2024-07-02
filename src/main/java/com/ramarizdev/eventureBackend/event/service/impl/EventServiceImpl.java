package com.ramarizdev.eventureBackend.event.service.impl;

import com.cloudinary.Cloudinary;
import com.ramarizdev.eventureBackend.category.entity.Category;
import com.ramarizdev.eventureBackend.category.repository.CategoryRepository;
import com.ramarizdev.eventureBackend.event.dto.EventRequestDto;
import com.ramarizdev.eventureBackend.event.dto.EventResponseDto;
import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.event.entity.TicketType;
import com.ramarizdev.eventureBackend.event.repository.EventRepository;
import com.ramarizdev.eventureBackend.event.service.EventService;
import com.ramarizdev.eventureBackend.event.specification.EventSpecification;
import com.ramarizdev.eventureBackend.user.entity.Organizer;
import com.ramarizdev.eventureBackend.user.repository.OrganizerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerRepository organizerRepository;
    private final Cloudinary cloudinary;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, OrganizerRepository organizerRepository, Cloudinary cloudinary) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.organizerRepository = organizerRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public Page<EventResponseDto> getAllEvents(String categorySlug, String location, boolean isFree, String search, int page, int size) {
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

        List<EventResponseDto> eventResponseDtos = eventPage.getContent().stream().map(Event::toDto).collect(Collectors.toList());

        return new PageImpl<>(eventResponseDtos, pageable, eventPage.getTotalElements());
    }

    @Override
    public EventResponseDto createEvent(EventRequestDto requestDto, Long organizerId) {
        Event event = requestDto.toEntity();

        Category category = categoryRepository.findById(requestDto.getCategory()).orElseThrow(
                () -> new IllegalArgumentException("Invalid category ID")
        );

        event.setCategory(category);

        String imageUrl = uploadFile(requestDto.getImage(), "events");

        event.setImage(imageUrl);

        Organizer organizer = organizerRepository.findById(organizerId).orElseThrow(() -> new IllegalArgumentException("Organizer not found"));
        event.setOrganizer(organizer);

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

        Event newEvent = eventRepository.save(event);

        EventResponseDto responseDto = newEvent.toDto();

        return responseDto;
    }

    @Override
    public void deleteEvent(Long eventId, String currentUserEmail) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

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
