package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by Sergey on 01.02.2017.
 */
public class SqlFunctionExample extends SqlFunction<String> {
    private static final String SQL =
            "SELECT getFirstNameById(?)";

    public SqlFunctionExample(DataSource ds) {
        super(ds, SQL);
        declareParameter(new SqlParameter(Types.INTEGER));
        compile();
    }
}
