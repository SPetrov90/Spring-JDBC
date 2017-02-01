package ru.sergey.spring.dao;

import ru.sergey.spring.model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 31.01.2017.
 *  Simple implementation of ContactDao
 *  with JDBC
 */
public class PlainContactDao implements ContactDao {

    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/spring-example",
                "root", "root");
    }

    private void closeConnection(Connection connection){
        if (connection == null){
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public List<Contact> findAll() {
        List<Contact> result = new ArrayList<Contact>();
        Connection connection = null;

        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                Contact contact = new Contact();
                contact.setId(resultSet.getLong("id"));
                contact.setFirstName(resultSet.getString("FIRST_NAME"));
                contact.setLastName(resultSet.getString("LAST_NAME"));
                contact.setBirthDay(resultSet.getDate("BIRTH_DAY"));
                result.add(contact);
            }

        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }
        return  result;
    }

    public void insert(Contact contact) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contact (first_name, last_name, birth_day) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setDate(3, contact.getBirthDay());
            statement.execute();
            ResultSet generedKey = statement.getGeneratedKeys();
            if (generedKey.next()){
                contact.setId(generedKey.getLong(1));
            }
        }  catch (SQLException ex){
        ex.printStackTrace();
    } finally {
        closeConnection(connection);
    }
    }

    public void delete(Long contactId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM contact WHERE id=?");
            statement.setLong(1, contactId);
            statement.execute();
        }  catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public List<Contact> findAllWithDetails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertWithDetail(Contact contact) {
        throw new UnsupportedOperationException();
    }

    public List<Contact> findByFirstName(String firstName) {
        return null;
    }

    public String findLastNameById(Long id) {
        throw new UnsupportedOperationException();
    }

    public String findFirstNameById(Long id) {
        throw new UnsupportedOperationException();
    }

    public void update(Contact contact) {
        throw new UnsupportedOperationException();
    }
}
