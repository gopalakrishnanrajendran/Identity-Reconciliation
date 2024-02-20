package com.Bitespeed.Identity.Reconciliation.rest.service;

import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import com.Bitespeed.Identity.Reconciliation.Database.repository.ContactRepository;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactRequest;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }
    public Contact getContactByEmail(String email){

        List<Contact> contactList = contactRepository.findByEmail(email);

        if(contactList.isEmpty()){
            return null;
        }

        return contactList.get(0);
    }

    public Contact getContactByPhoneNumber(String phoneNumber){

        List<Contact> contactList = contactRepository.findByPhoneNumber(phoneNumber);

        if(contactList.isEmpty()){
            return null;
        }

        return contactList.get(0);
    }


}
