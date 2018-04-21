package azkaban.webapp.servlet;

import azkaban.server.HttpRequestUtils;
import azkaban.server.session.Session;
import azkaban.user.User;
import azkaban.user.UserManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by meng on 2018/4/21.
 */
public class UserManagerServlet extends LoginAbstractAzkabanServlet {


    private UserManager usermanager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        usermanager = getApplication().getUserManager();
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
        Page page = newPage(req, resp, session, "azkaban/webapp/servlet/velocity/adduser.vm");
        if (!user.isInGroup("admin")) {
            page.add("errorMsg", "You have no privilege for user manager!");

        }
        page.render();
    }
}
