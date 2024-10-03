package ui;

import bo.UserHandler;
import bo.CartHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Check if user is already logged in
        if (session.getAttribute("userId") != null) {
            resp.sendRedirect(req.getContextPath() + "/user");  // Redirect to user's main page
            return;  // no need in further execution
        }

        // Retrieve username and password from form
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate user credentials using UserHandler
        UserInfo user = UserHandler.loginUser(username, password);

        // If login is successful, store user details in session
        if (user != null) {
            session.setAttribute("username", username);  // Store username in session
            session.setAttribute("userId", user.getId());  // Store user ID in session
            System.out.println("DEBUG: Username stored in session: " + username);  // Debug output

            // Check if the user has a cart, create one if not
            CartHandler.ensureCartForUser(user.getId());

            // Check if the user was redirected from a specific action
            String redirectAfterLogin = (String) session.getAttribute("redirectAfterLogin");

            if (redirectAfterLogin != null) {
                session.removeAttribute("redirectAfterLogin");  // Clear the attribute
                if (redirectAfterLogin.equals("cart")) {
                    resp.sendRedirect(req.getContextPath() + "/cart");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/user");  // Default "my page"
                }
            } else {
                resp.sendRedirect(req.getContextPath() + "/user");  // Default if no prior action
            }
        } else {
            // If login fails, reload the login page (which is outside WEB-INF)
            session.setAttribute("loginError", "Invalid username or password");  // Optional: add error message
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
