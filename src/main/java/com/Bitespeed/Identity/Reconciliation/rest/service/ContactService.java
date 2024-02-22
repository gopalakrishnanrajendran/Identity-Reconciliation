package com.Bitespeed.Identity.Reconciliation.rest.service;

import com.Bitespeed.Identity.Reconciliation.Database.repository.ContactRepository;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactResponse;
import com.Bitespeed.Identity.Reconciliation.rest.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public com.Bitespeed.Identity.Reconciliation.Database.entity.Contact getContactByEmail(String email){

        List<com.Bitespeed.Identity.Reconciliation.Database.entity.Contact> contactList = contactRepository.findByEmail(email);

        System.out.println(email+" printing contact list from email "+contactList);
        if(contactList.isEmpty()){
            return null;
        }

        com.Bitespeed.Identity.Reconciliation.Database.entity.Contact ultimateParent = disjointSet.findUPar(contactList.get(0));

        return ultimateParent;
    }

    public com.Bitespeed.Identity.Reconciliation.Database.entity.Contact getContactByPhoneNumber(String phoneNumber){

        List<com.Bitespeed.Identity.Reconciliation.Database.entity.Contact> contactList = contactRepository.findByPhoneNumber(phoneNumber);

        System.out.println(phoneNumber+" printing contact list from phoneNumber "+contactList);
        if(contactList.isEmpty()){
            return null;
        }
        com.Bitespeed.Identity.Reconciliation.Database.entity.Contact ultimateParent = disjointSet.findUPar(contactList.get(0));

        return ultimateParent;
    }

    public com.Bitespeed.Identity.Reconciliation.Database.entity.Contact createContact(String email, String phoneNumber){
        com.Bitespeed.Identity.Reconciliation.Database.entity.Contact contact = new com.Bitespeed.Identity.Reconciliation.Database.entity.Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setCreatedAt((new Date()).getTime());
        contact.setLinkPrecedence(PRIMARY);

        return contactRepository.save(contact);
    }

    public com.Bitespeed.Identity.Reconciliation.Database.entity.Contact createContact(String email, String phoneNumber, com.Bitespeed.Identity.Reconciliation.Database.entity.Contact parent){
        com.Bitespeed.Identity.Reconciliation.Database.entity.Contact contact = new com.Bitespeed.Identity.Reconciliation.Database.entity.Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setCreatedAt((new Date()).getTime());
        contact.setLinkPrecedence(SECONDARY);
        contact.setLinkedId(parent.getId());
        return contactRepository.save(contact);
    }


    public com.Bitespeed.Identity.Reconciliation.Database.entity.Contact performUnion(com.Bitespeed.Identity.Reconciliation.Database.entity.Contact u, com.Bitespeed.Identity.Reconciliation.Database.entity.Contact v){
        return disjointSet.unionByCreatedDate(u,v);
    }


    public Response getResponse(com.Bitespeed.Identity.Reconciliation.Database.entity.Contact parent){
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setPrimaryContatctId(parent.getId());

        List<String> emails = new ArrayList<>();
        Set<String> emailSet = new HashSet<>();
        emails.add(parent.getEmail());

        Set<String> phoneSet = new HashSet<>();
        List<String>phoneNumbers = new ArrayList<>();
        phoneNumbers.add(parent.getPhoneNumber());

        List<Integer> secondaryIds = new ArrayList<>();

        addSecondaryData(parent, emails, phoneNumbers, secondaryIds,emailSet,phoneSet);

        contactResponse.setEmails(emails);
        contactResponse.setPhoneNumbers(phoneNumbers);
        contactResponse.setSecondaryContactIds(secondaryIds);
        Response response = new Response();
        response.setContact(contactResponse);
        return response;
    }

    public void addSecondaryData(com.Bitespeed.Identity.Reconciliation.Database.entity.Contact parent, List<String> emails, List<String> phoneNumbers, List<Integer> secondaryIds, Set<String> emailSet, Set<String> phoneSet){
        List<com.Bitespeed.Identity.Reconciliation.Database.entity.Contact> contactList = contactRepository.findByLinkedId(parent.getId());

        for(com.Bitespeed.Identity.Reconciliation.Database.entity.Contact contact:contactList){
            String email = contact.getEmail();
            String phoneNumber = contact.getPhoneNumber();
            if(!emailSet.contains(email)) {
                emails.add(email);
            }
            if(!phoneSet.contains(phoneNumber)) {
                phoneNumbers.add(phoneNumber);
            }
            secondaryIds.add(contact.getId());
        }
    }









}
