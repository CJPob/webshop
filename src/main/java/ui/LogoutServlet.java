package ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


// cleans the session and redirects to login page
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the current session, if it exists
        HttpSession session = req.getSession(false);  // false ensures it doesn't create a new session if none exists

        if (session != null) {
            // Invalidate the session, removing all attributes
            session.invalidate();
        }

        // Redirect to the home page (or login page)
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
