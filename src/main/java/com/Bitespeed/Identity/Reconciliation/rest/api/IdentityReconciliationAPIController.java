package com.Bitespeed.Identity.Reconciliation.rest.api;

import com.Bitespeed.Identity.Reconciliation.rest.model.ContactResponse;
import com.Bitespeed.Identity.Reconciliation.rest.model.ContactRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IdentityReconciliation")
public class IdentityReconciliationAPIController {

    @PostMapping("/identify")
    public ContactResponse doIdentify(ContactRequest contactRequest) {
        return new ContactResponse();
    }
}
