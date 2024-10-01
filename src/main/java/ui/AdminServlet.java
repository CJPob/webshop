package ui;

import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {


    // ver 1, need to implement role check
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        Collection<UserInfo> users;

        if (username != null && !username.isEmpty()) {
            users = UserHandler.getUser(username);
        } else {
            users = UserHandler.getUser("");
        }

        req.setAttribute("users", users);

        req.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(req, resp);
    }
}