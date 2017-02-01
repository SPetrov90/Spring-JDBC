package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by Sergey on 01.02.2017.
 */
public class InsertExample extends SqlUpdate {
    private static final String SQL_INSERT_CONTACT =
            "INSERT INTO contact  (FIRST_NAME, " +
                    "LAST_NAME, BIRTH_DAY) VALUES " +
                    "(:first_name,:last_name,:birth_day)";

    public InsertExample(DataSource ds) {
        super(ds, SQL_INSERT_CONTACT);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("last_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("birth_day", Types.DATE));
        super.setGeneratedKeysColumnNames("ID");
        super.setReturnGeneratedKeys(true);
    }
}
