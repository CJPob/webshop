package ui;


import bo.UserHandler;
import bo.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

/**
 * * The UserServlet handles user-related actions such as viewing user details, signing up,
 * * and updating user roles. It processes both GET and POST requests for user management.
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            Collection<UserInfo> userInfo = UserHandler.getUser(username);

            if (userInfo != null && !userInfo.isEmpty()) {
                UserInfo user = userInfo.iterator().next();
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/jsp/userdetails.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "No user information found.");
                req.getRequestDispatcher("/WEB-INF/jsp/errorpage.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("signup".equals(action)) {
            handleSignup(req, resp);
        } else if ("setRole".equals(action)) {
            handleSetRole(req, resp);
        }
    }

    private void handleSignup(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean signupSuccess = UserHandler.signupUser(name, username, password);

        if (signupSuccess) {
            req.setAttribute("message", "Signup successful! Please log in.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Signup failed. Please try again.");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }
    }

    private void handleSetRole(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String newRole = req.getParameter("newRole");

        boolean updateSuccess = UserHandler.setNewUserRole(username, UserRole.valueOf(newRole));

        if (updateSuccess) {
            req.setAttribute("message", "Role updated successfully for " + username);
        } else {
            req.setAttribute("error", "Failed to update role for " + username);
        }
        resp.sendRedirect(req.getContextPath() + "/admin?action=viewUsers");
    }
}
