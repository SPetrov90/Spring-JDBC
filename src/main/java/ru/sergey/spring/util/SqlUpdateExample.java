package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by Sergey on 01.02.2017.
 */
public class SqlUpdateExample extends SqlUpdate {
    private static final String SQL_UPDATE_CONTACT =
            "UPDATE contact SET FIRST_NAME=:first_name, " +
                    "LAST_NAME=:last_name, BIRTH_DAY=:birth_day WHERE ID=:id";

    public SqlUpdateExample(DataSource ds) {
        super(ds, SQL_UPDATE_CONTACT);
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("last_name", Types.VARCHAR));
        super.declareParameter(new SqlParameter("birth_day", Types.DATE));
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }
}
