package com.Bitespeed.Identity.Reconciliation.rest.service;
import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import com.Bitespeed.Identity.Reconciliation.Database.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static com.Bitespeed.Identity.Reconciliation.rest.Constants.PRIMARY;
import static com.Bitespeed.Identity.Reconciliation.rest.Constants.SECONDARY;

@Service
class DisjointSet {



    @Autowired
    ContactRepository contactRepository;

        public DisjointSet(ContactRepository contactRepository) {
           this.contactRepository = contactRepository;
        }

        public Contact findUPar(Contact node) {
            if (PRIMARY.equals(node.getLinkPrecedence())) {
                return node;
            }
            Contact ulp = findUPar(contactRepository.findById(node.getLinkedId()));
            node.setLinkedId(ulp.getId());
            return ulp;
        }

        public Contact unionByCreatedDate(Contact u, Contact v) {
            Contact ulp_u = findUPar(u);
            Contact ulp_v = findUPar(v);
            if (ulp_u == ulp_v) return ulp_v;
            long currTime = (new Date()).getTime();

            if(ulp_u.getCreatedAt()<ulp_v.getCreatedAt()){
                List<Contact> vChild = contactRepository.findByLinkedId(ulp_v.getId());
                ulp_v.setLinkedId(ulp_u.getId());
                ulp_v.setUpdatedAt(currTime);
                ulp_v.setLinkPrecedence(SECONDARY);
                updateLinkedId(vChild,ulp_u.getId(),currTime);
                vChild.add(ulp_v);
                contactRepository.saveAll(vChild);

                return ulp_u;
            }
            else{
                List<Contact> uChild = contactRepository.findByLinkedId(ulp_u.getId());
                ulp_u.setLinkedId(ulp_v.getId());
                ulp_u.setUpdatedAt(currTime);
                updateLinkedId(uChild,ulp_v.getId(),currTime);
                uChild.add(ulp_u);
                contactRepository.saveAll(uChild);
                return ulp_v;
            }

        }

        public void updateLinkedId(List<Contact> contactList, int parentId, long currTime){
            for(Contact contact:contactList){
                contact.setLinkedId(parentId);
                contact.setUpdatedAt(currTime);
            }
        }

}




