package ui;

import bo.UserHandler;

import db.ShoppingCartDB;
import db.UserDB;
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
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate user credentials using UserHandler
        boolean loginSuccess = UserHandler.loginUser(username, password);

        if (loginSuccess) {
            // After successful login, directly retrieve the userId from the database
            int userId = UserDB.getUserIdByUsername(username);  // Directly querying UserDB for userId

            if (userId != -1) {  // Ensure that a valid userId was found
                // Retrieve the cartId based on userId
                int cartId = ShoppingCartDB.getCartIdByUserId(userId);  // Fetch the cartId from the database

                // Debug print to check the retrieved cartId
                System.out.println("DEBUG: Retrieved CartId for UserId: " + userId + " is: " + cartId);

                // Create or retrieve session
                HttpSession session = req.getSession();
                // Store userId, username, and cartId in the session
                session.setAttribute("userId", userId);  // Store userId in session
                session.setAttribute("username", username);  // Store username in session
                session.setAttribute("cartId", cartId);  // Store cartId in session

                System.out.println("DEBUG: UserId, Username, and CartId stored in session: " + userId + ", " + username + ", " + cartId);

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
                // If userId could not be found, reload the login page
                req.getSession().setAttribute("loginError", "User not found");
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }

        } else {
            // If login fails, reload the login page
            req.getSession().setAttribute("loginError", "Invalid username or password");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}