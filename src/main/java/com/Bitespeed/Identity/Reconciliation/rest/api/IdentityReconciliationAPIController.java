package com.Bitespeed.Identity.Reconciliation.rest.api;

import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactRequest;
import com.Bitespeed.Identity.Reconciliation.rest.model.Response;
import com.Bitespeed.Identity.Reconciliation.rest.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Response doIdentify(@RequestBody ContactRequest contactRequest) {

        Contact byEmail =  contactService.getContactByEmail(contactRequest.getEmail());
        Contact byPhone =  contactService.getContactByPhoneNumber(contactRequest.getPhoneNumber());

        Contact parent = null;
        System.out.println(byEmail+" "+byPhone);

        if(contactRequest.getPhoneNumber()==null && contactRequest.getEmail()==null){
            return new Response();
        }
        else if(contactRequest.getPhoneNumber()==null){
            parent = byEmail;
        }
        else if(contactRequest.getEmail()==null){
            parent = byPhone;
        }
        else if(byEmail == null && byPhone == null){

            parent = contactService.createContact(contactRequest.getEmail(),contactRequest.getPhoneNumber());

        }
        else if(byEmail == null){
            parent = byPhone;
            contactService.createContact(contactRequest.getEmail(), contactRequest.getPhoneNumber(), byPhone);
        }
        else if(byPhone == null){
            parent = byEmail;
            contactService.createContact(contactRequest.getEmail(), contactRequest.getPhoneNumber(), byPhone);
        }
        else if(byEmail==byPhone){
            parent = byEmail;
        }
        else{
            parent = contactService.performUnion(byEmail, byPhone);
        }

        if(parent!=null){
            return contactService.getResponse(parent);
        }


        return new Response();

    }
}
