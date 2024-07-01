package com.ramarizdev.eventureBackend.user.entity;

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

    @NotNull
    @Column(name = "total_points", nullable = false)
    private Integer totalPoints = 0;

    @OneToMany(mappedBy = "attendee", cascade = CascadeType.ALL)
    private List<Point> points = new ArrayList<>();
}
