package com.example.project_job_seeking.modal.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonIgnoreProperties({"appliedJob"})
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "job_id")
    @JsonIgnoreProperties({"isAppliedBy"})
    private Job job;

    @Enumerated(EnumType.STRING)
    @Column(name = "jobStatus")
    private JobStatus jobStatus;
}
