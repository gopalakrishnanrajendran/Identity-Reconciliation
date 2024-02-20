package com.Bitespeed.Identity.Reconciliation.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContactRequest {

    private String email;

    private String phoneNumber;
}
