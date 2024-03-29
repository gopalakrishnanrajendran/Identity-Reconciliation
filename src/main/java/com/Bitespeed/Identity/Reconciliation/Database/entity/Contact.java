package com.Bitespeed.Identity.Reconciliation.Database.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="contact")
@Getter
@Setter
@ToString
public class Contact {

    @Id
    @Column(name="id", unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "phone_number")
    private String phoneNumber ;

    @Column(name = "email")
    private String email;

    @Column(name = "linked_id")
    private int linkedId;

    @Column(name = "link_precedence")
    private String linkPrecedence;

    @Column(name = "created_at")
    private long createdAt;

    @Column(name = "updated_at")
    private long updatedAt ;

    @Column(name = "deleted_at")
    private long deletedAt;


}
