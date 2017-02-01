package ru.sergey.spring.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.sergey.spring.config.WebConfig;
import ru.sergey.spring.dao.JdbcTemplateContactDao;
import ru.sergey.spring.model.Contact;
import ru.sergey.spring.model.ContactTelDetails;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by Sergey on 31.01.2017.
 */
public class JdbcTemplateContactDaoSample {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateContactDaoSample.class);
    private static ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);
    private static JdbcTemplateContactDao jdbc = context.getBean("jdbcTemplateDao", JdbcTemplateContactDao.class);

    public static void main(String[] args) {
        findNameByID(2L);
        findAll();
        findAllWithDetails();
        findContactByName();
        updateContact();
        addAndDeleteContact();
//      addContactWithDetails();
        findAllWithDetails();

    }

    private static void findContactByName() {
        System.out.println("search by name: " + jdbc.findByFirstName("Chris"));
        System.out.println("---------------------------------------------");
    }

    private static void addContactWithDetails() {
        Contact contactWithDetails = new Contact();
        contactWithDetails.setFirstName("Vladimir");
        contactWithDetails.setLastName("Kuzmin");
        contactWithDetails.setBirthDay(new Date(
                new GregorianCalendar(1955, 6, 31).getTime().getTime()));

        List<ContactTelDetails> allContacts = new ArrayList<>();

        ContactTelDetails contactTelDetails1 = new ContactTelDetails();
        contactTelDetails1.setTelType("Home");
        contactTelDetails1.setTelNumber("5-55-55-55");
        allContacts.add(contactTelDetails1);

        ContactTelDetails contactTelDetails2 = new ContactTelDetails();
        contactTelDetails2.setTelType("Mobile");
        contactTelDetails2.setTelNumber("8-(911)-5-55-55-55");
        allContacts.add(contactTelDetails2);

        ContactTelDetails contactTelDetails3 = new ContactTelDetails();
        contactTelDetails3.setTelType("Works");
        contactTelDetails3.setTelNumber("5-55-55-55-55");
        allContacts.add(contactTelDetails3);

        contactWithDetails.setContactTelDetails(allContacts);
        System.out.println("Insert contact with details: " + contactWithDetails);
        jdbc.insertWithDetail(contactWithDetails);
        System.out.println("---------------------------------------------");
    }

    private static void addAndDeleteContact() {
        Contact contactNew = new Contact();
        contactNew.setFirstName("Vasya");
        contactNew.setLastName("Pupkin");
        contactNew.setBirthDay(new Date(
                new GregorianCalendar(1980, 7, 11).getTime().getTime()));
        jdbc.insert(contactNew);
        System.out.println("Add new contact: " + contactNew);
        System.out.println("---------------------------------------------");
        jdbc.delete(contactNew.getId());
        System.out.println("Delete new contact: " + contactNew);
        System.out.println("---------------------------------------------");
    }

    private static void updateContact() {
        Contact contact = new Contact();
        contact.setId(2L);
        contact.setFirstName("Sergey");
        contact.setLastName("Petrov");
        contact.setBirthDay(new Date(
                new GregorianCalendar(1990, 5, 14).getTime().getTime()));
        jdbc.update(contact);
        System.out.println("Update contact with ID = " + contact.getId() +
                ". New data: " + contact);
        System.out.println("---------------------------------------------");
    }

    private static void findAllWithDetails() {
        System.out.println("search all with details:");
        listContacts(jdbc.findAllWithDetails());
        System.out.println("---------------------------------------------");

    }

    private static void findAll() {
        System.out.println("search all: "  + jdbc.findAll());
        System.out.println("---------------------------------------------");
    }

    private static void findNameByID(long id) {
        System.out.println("search name by ID with SQL function: " + jdbc.findFirstNameById(id));
        System.out.println("---------------------------------------------");
    }

    private static void listContacts(List<Contact> allWithDetails) {
        for (Contact contact : allWithDetails){
            System.out.println(contact);
            if (contact.getContactTelDetails() != null){
                for (ContactTelDetails listDetails : contact.getContactTelDetails()){
                    System.out.println(listDetails);
                }
            }
            System.out.println();
        }
    }
}
