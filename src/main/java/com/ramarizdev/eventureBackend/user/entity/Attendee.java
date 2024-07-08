package com.ramarizdev.eventureBackend.user.entity;

import com.ramarizdev.eventureBackend.event.entity.Review;
import com.ramarizdev.eventureBackend.order.entity.Order;
import com.ramarizdev.eventureBackend.user.dto.AttendeeDto;
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
@Table(name = "attendees")
public class Attendee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendee_id_gen")
    @SequenceGenerator(name = "attendee_id_gen", sequenceName = "attendee_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "address")
    private String address;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @OneToOne(mappedBy = "attendee", cascade = CascadeType.ALL)
    private ReferralCode referralCode;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Point> points = new ArrayList<>();

    @OneToOne(mappedBy = "referredAttendee", cascade = CascadeType.ALL)
    private ReferralUsage referralUsage;

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public AttendeeDto toDto() {
        AttendeeDto attendeeDto = new AttendeeDto();
        attendeeDto.setId(id);
        attendeeDto.setReferralCode(referralCode.getCode());
        attendeeDto.setName(name);
        attendeeDto.setPhone(phone);
        attendeeDto.setImage(image);

        int totalPoints = points.stream().mapToInt(Point::getAmount).sum();

        attendeeDto.setTotalPoints(totalPoints);


        return attendeeDto;
    }
}
