package com.example.project_job_seeking.modal.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "`Job_Management`")
public class jobApplication extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "apply_by")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "job_id")
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobStatus")
    private JobStatus jobStatus;
}
