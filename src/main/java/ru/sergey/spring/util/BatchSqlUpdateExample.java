package ru.sergey.spring.util;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by Sergey on 01.02.2017.
 * example demonstrates insert contact
 * tel through BatchSqlUpdate
 */
public class BatchSqlUpdateExample extends BatchSqlUpdate {
    private static final String SQL_INSERT_CONTACT_TEL =
            "INSERT INTO contact_tel_detail (CONTACT_ID, TEL_TYPE, TEL_NUMBER)" +
                    "VALUES (:contact_id,:tel_type,:tel_number)";
    private static final int BATCH_SIZE = 10;

    public BatchSqlUpdateExample(DataSource ds) {
        super(ds, SQL_INSERT_CONTACT_TEL);
        super.declareParameter(new SqlParameter("contact_id", Types.INTEGER));
        super.declareParameter(new SqlParameter("tel_type", Types.VARCHAR));
        super.declareParameter(new SqlParameter("tel_number", Types.VARCHAR));
        setBatchSize(BATCH_SIZE);
    }
}
