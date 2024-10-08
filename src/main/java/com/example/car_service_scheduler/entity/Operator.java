package com.example.car_service_scheduler.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "operators")
@Getter
@Setter
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=100)
    private String name;

    @Column(nullable=false, unique=true, length=100)
    private String email;

    @Column(length=20)
    private String phone;

    @Column(name = "created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable=false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments;

}

