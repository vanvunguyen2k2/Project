package com.example.project_job_seeking.modal.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "`User`")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "password", length = 100, unique = true, nullable = false)
    private String password;

    @Column(name = "information", length = 500, nullable = false)
    private String information;

    @Column(name = "full_name", length = 50, nullable = false)
    private String full_name;

    @Column(name = "email", length = 70, nullable = false, unique = true)
    private String email;

//    @Column(name = "forgot_password", length = 70)
//
//    private String forgot_password;

    @Column(name = "date_of_birth")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date_of_birth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<jobApplication> applicationList;






}
