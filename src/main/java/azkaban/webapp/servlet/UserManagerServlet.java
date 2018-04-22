package azkaban.webapp.servlet;

import azkaban.database.AzkabanDataSource;
import azkaban.database.DataSourceUtils;
import azkaban.server.HttpRequestUtils;
import azkaban.server.session.Session;
import azkaban.user.DataBaseUtils;
import azkaban.user.User;
import azkaban.user.UserManager;
import azkaban.utils.Props;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meng on 2018/4/21.
 */
public class UserManagerServlet extends LoginAbstractAzkabanServlet {

    private static final Logger logger = Logger.getLogger(UserManagerServlet.class);


    private UserManager usermanager;

    private AzkabanDataSource datasource;

    public UserManagerServlet(Props props) {


    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usermanager = getApplication().getUserManager();
        datasource = DataSourceUtils.getDataSource(getApplication().getServerProps());
    }

    @Override
    protected void handleGet(HttpServletRequest request, HttpServletResponse response, Session session)
            throws ServletException, IOException {

        if (HttpRequestUtils.hasParam(request, "adduser")) {
            handleAddUserView(request, response, session);
        } else if (HttpRequestUtils.hasParam(request, "passwd")) {
            handleChangePasswdView(request, response, session);
        } else {
            handleUserListView(request, response, session);
        }


    }

    @Override
    protected void handlePost(HttpServletRequest request, HttpServletResponse response, Session session)
            throws ServletException, IOException {
        handleGet(request, response, session);

    }

    private void handleAddUserView(HttpServletRequest req,
                                   HttpServletResponse resp, Session session) {
        User user = session.getUser();
        Page page = newPage(req, resp, session, "azkaban/webapp/servlet/velocity/adduser.vm");
        if (!user.isInGroup("admin")) {
            page.add("errorMsg", "You have no privilege for user manager!");

        }
        page.render();
    }


    private void handleChangePasswdView(HttpServletRequest req,
                                        HttpServletResponse resp, Session session) {
        User user = session.getUser();
        Page page = newPage(req, resp, session, "azkaban/webapp/servlet/velocity/adduser.vm");
        if (!user.isInGroup("admin")) {
            page.add("errorMsg", "You have no privilege for user manager!");
        }
        page.render();
    }

    private void handleUserListView(HttpServletRequest req,
                                    HttpServletResponse resp, Session session) {
        User user = session.getUser();
        Page page = newPage(req, resp, session, "azkaban/webapp/servlet/velocity/userlist.vm");
        if (!user.isInGroup("admin")) {
            page.add("errorMsg", "You have no privilege for user manager!");
        } else {
            if (HttpRequestUtils.hasParam(req, "groups")) {
                page.add("groupList", fatchAllGroup());
            } else if (HttpRequestUtils.hasParam(req, "roles")) {

            } else {
                page.add("userList", fatchAllUsers());
            }

        }
        page.render();
    }


    private List<User> fatchAllUsers() {
        List<User> users = new ArrayList<User>();
        Connection conn = null;
        PreparedStatement statment = null;
        ResultSet result = null;

        try {
            conn = datasource.getConnection();
            statment = conn.prepareStatement("select * from users");

            result = statment.executeQuery();

            while (result.next()) {
                User user = new User(result.getString("name"));
                user.setEmail(result.getString("email"));
                users.add(user);
            }

        } catch (SQLException e) {
            logger.error("Fatch All User ERROR", e.fillInStackTrace());
        } finally {
            try {
                DataBaseUtils.closeConnection(conn, statment, result);
            } catch (Exception e) {
                logger.error(e.fillInStackTrace());
            }
        }
        return users;
    }

    private List<String> fatchAllGroup() {
        List<String> groupList = new ArrayList<String>();
        Connection conn = null;
        PreparedStatement statment = null;
        ResultSet result = null;

        try {
            conn = datasource.getConnection();
            statment = conn.prepareStatement("select * from groups");
            result = statment.executeQuery();
            while (result.next()) {
                groupList.add(result.getString("name"));
            }
        } catch (SQLException e) {
            logger.error("Fatch All Group ERROR", e.fillInStackTrace());
        } finally {
            try {
                DataBaseUtils.closeConnection(conn, statment, result);
            } catch (Exception e) {
                logger.error(e.fillInStackTrace());
            }
        }
        return groupList;
    }


}
