package com.Bitespeed.Identity.Reconciliation.rest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class Contact {

    private int primaryContatctId;

    private List<String> emails;

    private List<String> phoneNumbers;

    private List<Integer> secondaryContactIds;

}
