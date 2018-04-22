package azkaban.user;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meng on 2018/4/22.
 */
public class DataBaseUtils {
    public static void closeConnection(Connection conn, PreparedStatement statment, ResultSet result) throws SQLException {

        DbUtils.close(result);
        DbUtils.close(statment);
        DbUtils.close(conn);

    }
}
