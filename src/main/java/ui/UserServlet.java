package ui;

import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

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

        req.getRequestDispatcher("/frontend/jsp/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Retrieve form data
        String name = req.getParameter("name");       // Add 'name' field in form
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean signupSuccess = UserHandler.signupUser(name, username, password);

        if (signupSuccess) {
            req.setAttribute("message", "Signup successful! Please log in.");
            req.getRequestDispatcher("/frontend/jsp/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Signup failed. Please try again.");
            req.getRequestDispatcher("/frontend/jsp/signup.jsp").forward(req, resp);
        }
    }
}
