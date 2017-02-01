package ru.sergey.spring.test;

import  ru.sergey.spring.dao.ContactDao;
import  ru.sergey.spring.dao.PlainContactDao;
import ru.sergey.spring.model.Contact;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Sergey on 31.01.2017.
 * text example for PlainContactDao
 */
public class PlainJdbcSample {
     private static ContactDao contactDao = new PlainContactDao();

    public static void main(String[] args) {
        System.out.println("Listing initial contact data:");
        listAllContacts();

        System.out.println();
        System.out.println("Insert new contact:");

        Contact contact = new Contact();
        contact.setFirstName("Sergey");
        contact.setLastName("Petrov");
        contact.setBirthDay(new Date(
                new GregorianCalendar(1990, 5, 14).getTime().getTime()));
        contactDao.insert(contact);

        System.out.println("Listing initial contact data:");
        listAllContacts();

        System.out.println();
        System.out.println("Deleting the previous creating contact:");
        System.out.println(contact.getId());
        contactDao.delete(contact.getId());
        listAllContacts();
    }

    private static void listAllContacts(){
        List<Contact> contacts = contactDao.findAll();
        for (Contact contact : contacts){
            System.out.println(contact);
        }
    }
}
