package ui;

import bo.CartHandler;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);  // Retrieve existing session without creating a new one
        Integer userId = (Integer) session.getAttribute("userId");

        // Check if the user is logged in
        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // Fetch cart items for the user
        Collection<ItemInfo> cartItems = CartHandler.getItemsForUser(userId);
        req.setAttribute("cartItems", cartItems);

        // Forward to the cart.jsp page to display the cart items
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // If user is not logged in, redirect to login page
        if (userId == null) {
            session.setAttribute("redirectAfterLogin", "cart");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                // Add item to the user's cart
                CartHandler.addItem(userId, itemID, quantity);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item or quantity. Please try again.");
            }
        } else if ("remove".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));

                // Remove item from the user's cart
                CartHandler.removeItem(userId, itemID);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item ID. Please try again.");
            }
        }

        // Redirect to cart page to show updated cart
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
