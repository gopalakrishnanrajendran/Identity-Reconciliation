package com.Bitespeed.Identity.Reconciliation.Database.repository;

import com.Bitespeed.Identity.Reconciliation.Database.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findAll();

    Contact findById(int id);

    Contact save(Contact contacts);

    void deleteById(int id);

    List<Contact> findByPhoneNumber(String phoneNumber);

    List<Contact> findByEmail(String email);

    List<Contact> findByLinkedId(int linkedId);



}
