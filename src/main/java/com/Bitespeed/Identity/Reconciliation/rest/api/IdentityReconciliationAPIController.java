package com.Bitespeed.Identity.Reconciliation.rest.api;

import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactResponse;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactRequest;
import com.Bitespeed.Identity.Reconciliation.rest.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IdentityReconciliation")
public class IdentityReconciliationAPIController {

    @Autowired
    ContactService contactService;

    IdentityReconciliationAPIController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping("/identify")
    public ContactResponse doIdentify(ContactRequest contactRequest) {

        Contact byEmail =  contactService.getContactByEmail(contactRequest.getEmail());
        Contact byPhone =  contactService.getContactByPhoneNumber(contactRequest.getPhoneNumber());

        if(byEmail == null && byPhone == null){
            contactService.createContact(contactRequest.getEmail(),contactRequest.getPhoneNumber());
        }
        else if(byEmail == null){
            contactService.createContact(contactRequest.getEmail(), contactRequest.getPhoneNumber(), byPhone);
        }
        else if(byPhone == null){
            contactService.createContact(contactRequest.getEmail(), contactRequest.getPhoneNumber(), byPhone);
        }
        else{
            contactService.performUnion(byEmail, byPhone);
        }


        return new ContactResponse();
    }
}
