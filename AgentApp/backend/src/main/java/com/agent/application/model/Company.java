package com.agent.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@Table(name = "companies")
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name of company is mandatory!")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotBlank(message = "Description of company is mandatory!")
    private String description;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Phone number of company is mandatory!")
    private String phoneNumber;

    @OneToMany(mappedBy="company")
    private Set<JobOffer> jobOffers;

    @OneToMany(mappedBy="company")
    private Set<Comment> comments;

    @OneToMany(mappedBy="company")
    private Set<CommentSalary> commentSalaries;

    @OneToMany(mappedBy="company")
    private Set<CommentInterview> commentInterviews;

    private String ownerEmail;

    private boolean isActive;

    private boolean isRejected;
}
