package azkaban.webapp.servlet;

import azkaban.server.session.Session;
import azkaban.user.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by meng on 2018/4/21.
 */
public class UserManagerServlet extends LoginAbstractAzkabanServlet {
    @Override
    protected void handleGet(HttpServletRequest request, HttpServletResponse response, Session session)
            throws ServletException, IOException {
        handleAddUser(request, response, session);
    }

    @Override
    protected void handlePost(HttpServletRequest request, HttpServletResponse response, Session session)
            throws ServletException, IOException {
        handleGet(request, response, session);

    }

    private void handleAddUser(HttpServletRequest req,
                               HttpServletResponse resp, Session session) {
        User user = session.getUser();

        Page page = newPage(req, resp, session, "azkaban/webapp/servlet/velocity/usermanager.vm");


        page.render();
    }
}
