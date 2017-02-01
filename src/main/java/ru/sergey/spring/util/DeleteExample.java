package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by Sergey on 01.02.2017.
 */
public class DeleteExample extends SqlUpdate {
    private static final String SQL_DELETE_CONTACT =
            "DELETE FROM contact WHERE ID=:id";

    public DeleteExample(DataSource ds) {
        super(ds, SQL_DELETE_CONTACT);
        super.declareParameter(new SqlParameter("id", Types.INTEGER));
    }
}
