package com.example.spring_playground.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"users\"")
@Setter
@Getter
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    @Column(name = "name")
    private String name;

    @Getter
    @Column(name = "username")
    private String username;

    @Getter
    @Column(name = "email")
    private String email;

    @Getter
    @Column(name = "password")
    private String password;

    @Getter
    @Column(name = "access_token")
    private  String accessToken;

    @Getter
    @Column(name = "refresh_token")
    private  String refreshToken;

    @Getter
    @Column(name = "version")
    private String version;

    @Getter
    @Column(name = "created_at")
    private  String createdAt;

    @Getter
    @Column(name = "updated_at")
    private  String updatedAt;

}