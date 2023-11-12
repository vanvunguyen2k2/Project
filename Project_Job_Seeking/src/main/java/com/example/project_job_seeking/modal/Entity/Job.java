package com.example.project_job_seeking.modal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "`Job`")
public class Job extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;



    @Column(name = "jobTitleName", length = 50, nullable = false)
    private String jobTitleName;

    @Column(name = "salary", nullable = false)
    private float salary;

    @Column(name = "image", nullable = false)
    private String image;

//    private Gender gender;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "JobDescription", length = 100)
    private String jobDescription;

    @Column(name = "companyName", length = 50)
    private String companyName;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @Column(name = "jobRequirements", length = 200)
    private String jobRequirements;

    @Column(name = "application_way")
    @Enumerated(EnumType.STRING)
    private applicationForm application_way;

    @Column(name = "career", length = 100, nullable = false)
    private String career;





}
