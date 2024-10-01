package ui;

import bo.UserHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;  // Import HttpSession

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve username and password from form
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate user credentials using UserHandler
        boolean loginSuccess = UserHandler.loginUser(username, password);

        // If login is successful, store username in session
        if (loginSuccess) {
            HttpSession session = req.getSession();  // Create or retrieve session
            session.setAttribute("username", username);  // Store username in session
            System.out.println("DEBUG: Username stored in session: " + username);  // Debug output
            // Redirect to UserServlet instead of forwarding to JSP directly
            resp.sendRedirect(req.getContextPath() + "/user");
        } else {
            // If login fails, reload the login page (which is outside WEB-INF)
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
