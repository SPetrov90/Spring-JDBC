package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import ru.sergey.spring.model.Contact;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by Sergey on 31.01.2017.
 * example for select same contacts using MappingSqlQuery
 * action - MappingSqlQueryExample.execute
 */
public class MappingSqlQueryExample extends MappingSqlQuery<Contact> {

    private static final String SQL_FIND_BY_FIRST_NAME =
            "select ID, FIRST_NAME, LAST_NAME, BIRTH_DAY FROM contact" +
                    " WHERE FIRST_NAME = :first_name";

    public MappingSqlQueryExample(DataSource ds) {
        super(ds, SQL_FIND_BY_FIRST_NAME);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
    }

    @Override
    protected Contact mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Contact contact = new Contact();
        contact.setFirstName(resultSet.getString("FIRST_NAME"));
        contact.setId(resultSet.getLong("ID"));
        contact.setLastName(resultSet.getString("LAST_NAME"));
        contact.setBirthDay(resultSet.getDate("BIRTH_DAY"));
        return contact;
    }
}
