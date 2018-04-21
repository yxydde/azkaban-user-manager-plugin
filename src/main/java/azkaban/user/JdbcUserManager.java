package azkaban.user;

import azkaban.database.AzkabanDataSource;
import azkaban.database.DataSourceUtils;
import azkaban.utils.Props;

/**
 * Created by meng on 2018/4/21.
 */
public class JdbcUserManager implements UserManager {

    private static final String ADD_USER = "INSERT INTO TABLE USERS";

    private final AzkabanDataSource datasource;

    public JdbcUserManager(Props props) {
        datasource = DataSourceUtils.getDataSource(props);
    }


    public User getUser(String username, String password) throws UserManagerException {
        String sql = "select * from users where username='%s' and password='%s'";
        return null;
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
