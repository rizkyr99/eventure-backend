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
    private final Cloudinary cloudinary;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository, Cloudinary cloudinary) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinary = cloudinary;
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        return eventRepository.findAll().stream().map(Event::toDto).collect(Collectors.toList());
    }

    @Override
    public EventResponseDto createEvent(EventRequestDto requestDto) {
        Event event = requestDto.toEntity();

        Category category = categoryRepository.findById(requestDto.getCategory()).orElseThrow(
                () -> new IllegalArgumentException("Invalid category ID")
        );

        event.setCategory(category);

        String imageUrl = uploadFile(requestDto.getImage(), "events");

        event.setImage(imageUrl);

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

    private String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
