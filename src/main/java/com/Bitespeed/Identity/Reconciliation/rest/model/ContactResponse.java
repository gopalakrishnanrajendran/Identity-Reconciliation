package com.Bitespeed.Identity.Reconciliation.rest.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString

public class ContactResponse {

    private int primaryContatctId;

    private List<String> emails;

    private List<String> phoneNumbers;

    private List<Integer> secondaryContactIds;

}
