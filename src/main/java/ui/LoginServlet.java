package ui;

import bo.ShoppingCartHandler;
import bo.UserHandler;
import db.ShoppingCartDB;
import db.UserDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * The LoginServlet handles user login requests, validates credentials,
 * and manages session attributes for user data such as userId, username, cartId, and userRole.
 * It redirects users based on login success or failure.
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean loginSuccess = UserHandler.loginUser(username, password);

        if (loginSuccess) {
            int userId = UserDB.getUserIdByUsername(username);

            if (userId != -1) {
                HttpSession session = req.getSession();
                int cartId = ShoppingCartDB.getCartIdByUserId(userId);
                String userRole = UserDB.getUserRoleByUserId(userId);
                System.out.println("DEBUG: Retrieved CartId for UserId: " + userId + " is: " + cartId);

                session.setAttribute("userId", userId);
                session.setAttribute("username", username);
                session.setAttribute("userRole", userRole);

                if (cartId == -1) {
                    ShoppingCartHandler.createCartForUser(userId);
                    cartId = ShoppingCartDB.getCartIdByUserId(userId);
                }
                session.setAttribute("cartId", cartId);
                System.out.println("TEST: UserId, Username, CartId, and UserRole stored in session: " + userId + ", " + username + ", " + cartId + ", " + userRole);

                Integer pendingItemId = (Integer) session.getAttribute("pendingItemId");
                Integer pendingQuantity = (Integer) session.getAttribute("pendingQuantity");

                if (pendingItemId != null && pendingQuantity != null) {
                    ShoppingCartHandler.addItemToCart(cartId, pendingItemId, pendingQuantity);
                    session.removeAttribute("pendingItemId");
                    session.removeAttribute("pendingQuantity");
                }

                String redirectAfterLogin = (String) session.getAttribute("redirectAfterLogin");
                if (redirectAfterLogin != null) {
                    session.removeAttribute("redirectAfterLogin");
                    if (redirectAfterLogin.equals("cart")) {
                        resp.sendRedirect(req.getContextPath() + "/cart");
                    } else {
                        resp.sendRedirect(req.getContextPath() + "/user");
                    }
                } else {
                    resp.sendRedirect(req.getContextPath() + "/user");
                }
            } else {
                req.getSession().setAttribute("loginError", "User not found");
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }

        } else {
            req.getSession().setAttribute("loginError", "Invalid username or password");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }
}
