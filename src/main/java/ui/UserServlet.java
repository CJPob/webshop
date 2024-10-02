package ui;

import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Get the existing session, don't create if it doesn't exist

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            Collection<UserInfo> userInfo = UserHandler.getUser(username);

            if (userInfo != null && !userInfo.isEmpty()) {
                // Directly set the first UserInfo object since the collection is not empty
                UserInfo user = userInfo.iterator().next();
                req.setAttribute("user", user);
                req.getRequestDispatcher("/WEB-INF/jsp/userdetails.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "No user information found.");
                req.getRequestDispatcher("/WEB-INF/jsp/errorpage.jsp").forward(req, resp);
            }
        } else {
            // No user is logged in or session is expired
            resp.sendRedirect("login.jsp"); // Redirect to login page
        }
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
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Signup failed. Please try again.");
            req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
        }
    }
}