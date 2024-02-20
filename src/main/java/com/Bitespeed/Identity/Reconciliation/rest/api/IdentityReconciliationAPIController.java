package com.Bitespeed.Identity.Reconciliation.rest.api;

import com.Bitespeed.Identity.Reconciliation.rest.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IdentityReconciliation")
public class IdentityReconciliationAPIController {

    @PostMapping("/identify")
    public Contact doIdentify() {
        return new Contact();
    }
}
