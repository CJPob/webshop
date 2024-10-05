package ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


// basically cleans the session and redirects to login page

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);  // false ensures it doesn't create a new session if none exists

        if (session != null) {
            // iinvalidate the session --  remove all attributes
            session.invalidate();
        }
        // redirect to login
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
