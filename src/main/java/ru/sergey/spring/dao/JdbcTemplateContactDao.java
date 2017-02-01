package ru.sergey.spring.dao;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.sergey.spring.model.Contact;
import ru.sergey.spring.model.ContactTelDetails;
import ru.sergey.spring.util.*;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by Sergey on 31.01.2017.
 */
public class JdbcTemplateContactDao implements ContactDao, InitializingBean {
    private JdbcTemplate jdbcTemplate;
    private MappingSqlQueryExample mappingSqlQueryExample;
    private SqlUpdateExample sqlUpdateExample;
    private InsertExample insertExample;
    private BatchSqlUpdateExample batchSqlUpdateExample;
    private DeleteExample deleteExample;
    private SqlFunctionExample sqlFunctionExample;
//    private NamedParameterJdbcTemplate jdbcTemplate1;

    @Override
    public String findFirstNameById(Long id) {
        // example with SQL function
        List<String> result = sqlFunctionExample.execute(id);
        return result.get(0);
       /* //example with JdbcTemplate
      return jdbcTemplate.queryForObject(
                "select FIRST_NAME from contact where id = ?",
                new Object[]{id}, String.class);*/
    }

    @Override
    public String findLastNameById(Long id) {
//        It`s for NamedParameterJdbcTemplate
        /*String sql =  "select LAST _NAME from contact where id = :contactId";
        Map<String, Object> namesParameter = new HashMap<>();
        namesParameter.put("contactId", id);
        return jdbcTemplate1.queryForObject(sql, namesParameter, String.class);*/
        return jdbcTemplate.queryForObject(
                "select LAST _NAME from contact where id = ?",
                new Object[]{id}, String.class);
    }
    // for complex object
    @Override
    public List<Contact> findAll() {
        String sql = "select ID, FIRST_NAME, LAST_NAME, BIRTH_DAY FROM contact";
        return jdbcTemplate.query(sql, (RowMapper<Contact>) (resultSet, i) -> {
            Contact contact = new Contact();
            contact.setId(resultSet.getLong("ID"));
            contact.setFirstName(resultSet.getString("FIRST_NAME"));
            contact.setLastName(resultSet.getString("LAST_NAME"));
            contact.setBirthDay(resultSet.getDate("BIRTH_DAY"));
            return contact;
        });
    }

    // for complex object with details (ResultSetExtractor)
    public List<Contact> findAllWithDetails(){
        String sql = "SELECT c.ID, c.FIRST_NAME, c.LAST_NAME, c.BIRTH_DAY" +
                ", t.ID as contact_tel_id, t.TEL_TYPE, t.TEL_NUMBER FROM contact as c " +
                "LEFT JOIN contact_tel_detail t  ON c.ID = t.CONTACT_ID";
       return jdbcTemplate.query(sql, (ResultSetExtractor<List<Contact>>) (ResultSet rs) -> {
            Map<Long, Contact> map = new HashMap<Long, Contact>();
            Contact contact = new Contact();
            while (rs.next()){
                Long id = rs.getLong("ID");
                contact = map.get(id);
                 if (contact == null){
                     contact = new Contact();
                     contact.setId(id);
                     contact.setFirstName(rs.getString("FIRST_NAME"));
                     contact.setLastName(rs.getString("LAST_NAME"));
                     contact.setBirthDay(rs.getDate("BIRTH_DAY"));
                     contact.setContactTelDetails(new ArrayList<ContactTelDetails>());
                     map.put(id, contact);
                 }

                 Long contactTelDetailsID = rs.getLong("contact_tel_id");

                if (contactTelDetailsID > 0){
                    ContactTelDetails contactTelDetails = new ContactTelDetails();
                    contactTelDetails.setId(contactTelDetailsID);
                    contactTelDetails.setContactId(id);
                    contactTelDetails.setTelNumber(rs.getString("TEL_NUMBER"));
                    contactTelDetails.setTelType(rs.getString("TEL_TYPE"));
                    contact.getContactTelDetails().add(contactTelDetails);
                }
            }
            return new ArrayList<Contact>(map.values());
        });
    }

    @Override
    public void insertWithDetail(Contact contact) {
        insert(contact);
        List<ContactTelDetails> contactTelDetailses =
                contact.getContactTelDetails();
        if (contactTelDetailses != null){
            for (ContactTelDetails contactTelDetails: contact.getContactTelDetails()){
                Map <String, Object> paramMap = new HashMap<>();
                paramMap.put("contact_id", contact.getId());
                paramMap.put("tel_type", contactTelDetails.getTelType());
                paramMap.put("tel_number", contactTelDetails.getTelNumber());
                batchSqlUpdateExample.updateByNamedParam(paramMap);

            }
        }
        batchSqlUpdateExample.flush();
    }

    @Override
    public List<Contact> findByFirstName(String firstName) {
        Map <String, Object> paramMap = new HashMap<>();
        paramMap.put("first_name", firstName);
       return mappingSqlQueryExample.executeByNamedParam(paramMap);
    }

    @Override
    public void update(Contact contact) {
        Map <String, Object> paramMap = new HashMap<>();
        paramMap.put("first_name", contact.getFirstName());
        paramMap.put("last_name", contact.getLastName());
        paramMap.put("birth_day", contact.getBirthDay());
        paramMap.put("id", contact.getId());
        sqlUpdateExample.updateByNamedParam(paramMap);
    }

    @Override
    public void insert(Contact contact) {
        Map <String, Object> paramMap = new HashMap<>();
        paramMap.put("first_name", contact.getFirstName());
        paramMap.put("last_name", contact.getLastName());
        paramMap.put("birth_day", contact.getBirthDay());
        // keyholder to store our key
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertExample.updateByNamedParam(paramMap, keyHolder);
        contact.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void delete(Long contactId) {
        Map <String, Object> paramMap = new HashMap<>();
        paramMap.put("id", contactId);
        deleteExample.updateByNamedParam(paramMap);
    }

    public void setSqlUpdateExample(SqlUpdateExample sqlUpdateExample) {
        this.sqlUpdateExample = sqlUpdateExample;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setInsertExample(InsertExample insertExample) {
        this.insertExample = insertExample;
    }

    public void setMappingSqlQueryExample(MappingSqlQueryExample mappingSqlQueryExample) {
        this.mappingSqlQueryExample = mappingSqlQueryExample;
    }

    public void setDeleteExample(DeleteExample deleteExample) {
        this.deleteExample = deleteExample;
    }

    public void setBatchSqlUpdateExample(BatchSqlUpdateExample batchSqlUpdateExample) {
        this.batchSqlUpdateExample = batchSqlUpdateExample;
    }

    public void setSqlFunctionExample(SqlFunctionExample sqlFunctionExample) {
        this.sqlFunctionExample = sqlFunctionExample;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (jdbcTemplate == null){
            throw new BeanCreationException("Jdbc templating is Null");
        }
    }
}
