package com.ramarizdev.eventureBackend.user.entity;

import com.ramarizdev.eventureBackend.event.entity.Event;
import com.ramarizdev.eventureBackend.user.dto.OrganizerDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "organizers")
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organizer_id_gen")
    @SequenceGenerator(name = "organizer_id_gen", sequenceName = "organizer_id_seq")
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "address")
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();

    public OrganizerDto toDto() {
        OrganizerDto organizerDto = new OrganizerDto();
        organizerDto.setId(id);
        organizerDto.setName(name);
        organizerDto.setAddress(address);
        organizerDto.setImage(image);
        return organizerDto;
    }
}
