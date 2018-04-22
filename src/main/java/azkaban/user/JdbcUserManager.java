package azkaban.user;

import azkaban.database.AzkabanDataSource;
import azkaban.database.DataSourceUtils;
import azkaban.utils.Props;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by meng on 2018/4/21.
 */
public class JdbcUserManager implements UserManager {

    private static final Logger logger = Logger.getLogger(JdbcUserManager.class);

    private final AzkabanDataSource datasource;

    public JdbcUserManager(Props props) {
        datasource = DataSourceUtils.getDataSource(props);
    }


    public User getUser(String username, String password) throws UserManagerException {
        User user = null;
        Connection conn = null;
        PreparedStatement statment = null;
        ResultSet result = null;

        try {
            conn = datasource.getConnection();
            statment = conn.prepareStatement("select * from users where name= ? and password= ?");
            statment.setString(1, username);
            statment.setString(2, password);
            result = statment.executeQuery();

            if (result.next()) {
                int user_id = result.getInt("id");
                user = new User(result.getString("name"));
                user.setEmail(result.getString("email"));
                resolveGroupRoles(user_id, user);
                user.setPermissions(new User.UserPermissions() {
                    @Override
                    public boolean hasPermission(final String permission) {
                        return true;
                    }

                    @Override
                    public void addPermission(final String permission) {
                    }
                });
            }


        } catch (SQLException e) {
            logger.error("Get User ERROR", e.fillInStackTrace());
        } finally {
            try {
                DataBaseUtils.closeConnection(conn, statment, result);
            } catch (SQLException e) {
                logger.error(e.fillInStackTrace());
            }
        }
        return user;
    }


    private void resolveGroupRoles(int user_id, User user) {
        Connection conn = null;
        PreparedStatement statment = null;
        ResultSet result = null;
        try {
            conn = datasource.getConnection();
            statment = conn.prepareStatement("select name from groups where id in(select group_id from user_groups where user_id = ?)");
            statment.setInt(1, user_id);
            result = statment.executeQuery();
            while (result.next()) {
                user.addGroup(result.getString("name"));
            }
        } catch (SQLException e) {
            logger.error("Get Group ERROR", e.fillInStackTrace());
        } finally {
            try {
                DataBaseUtils.closeConnection(conn, statment, result);
            } catch (SQLException e) {
                logger.error(e.fillInStackTrace());
            }
        }
    }

    public User addUser(String username, String password) throws UserManagerException {

        return null;
    }

    public boolean validateUser(String username) {
        String sql = "select * from users where username='%s' and isActive=true'";
        return false;
    }

    public boolean validateGroup(String group) {
        String sql = "select * from groups where group='%s'";
        return false;
    }

    public Role getRole(String roleName) {
        String sql = "select * from roles where role='%s'";
        return null;
    }

    public boolean validateProxyUser(String proxyUser, User user) {
        return false;
    }

    public void initTables() {

    }
}
