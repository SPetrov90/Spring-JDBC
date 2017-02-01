package ru.sergey.spring.dao;

import ru.sergey.spring.model.Contact;

import java.util.List;

/**
 * Created by Sergey on 31.01.2017.
 */
public interface ContactDao {
    List<Contact> findAll();
    List<Contact> findByFirstName(String firstName);
    String findLastNameById (Long id);
    String findFirstNameById (Long id);
    void insert(Contact contact);
    void update (Contact contact);
    void delete (Long contactId);
    List<Contact> findAllWithDetails();
    void insertWithDetail (Contact contact);

}
