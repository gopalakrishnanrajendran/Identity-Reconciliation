package com.Bitespeed.Identity.Reconciliation.rest.service;

import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import com.Bitespeed.Identity.Reconciliation.Database.repository.ContactRepository;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactRequest;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.Bitespeed.Identity.Reconciliation.rest.Constants.PRIMARY;
import static com.Bitespeed.Identity.Reconciliation.rest.Constants.SECONDARY;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
      DisjointSet disjointSet;

    ContactService(ContactRepository contactRepository, DisjointSet disjointSet){
        this.contactRepository = contactRepository;
        this.disjointSet = disjointSet;
    }
    public Contact getContactByEmail(String email){

        List<Contact> contactList = contactRepository.findByEmail(email);

        if(contactList.isEmpty()){
            return null;
        }

        Contact ultimateParent = disjointSet.findUPar(contactList.get(0));

        return ultimateParent;
    }

    public Contact getContactByPhoneNumber(String phoneNumber){

        List<Contact> contactList = contactRepository.findByPhoneNumber(phoneNumber);

        if(contactList.isEmpty()){
            return null;
        }
        Contact ultimateParent = disjointSet.findUPar(contactList.get(0));

        return ultimateParent;
    }

    public Contact createContact(String email, String phoneNumber){
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setCreatedAt((new Date()).getTime());
        contact.setLinkPrecedence(PRIMARY);

        return contactRepository.save(contact);
    }

    public Contact createContact(String email, String phoneNumber, Contact parent){
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setCreatedAt((new Date()).getTime());
        contact.setLinkPrecedence(SECONDARY);
        contact.setLinkedId(parent.getId());
        return contactRepository.save(contact);
    }


    public Contact performUnion(Contact u, Contact v){
        return disjointSet.unionByCreatedDate(u,v);

    }







}
