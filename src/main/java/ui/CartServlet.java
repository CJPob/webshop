package ui;

import bo.ShoppingCart;
import bo.ShoppingCartHandler;
import db.ShoppingCartDB;
import jakarta.servlet.http.HttpSession;
import ui.ItemInfo;
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
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            Collection<ItemInfo> cartItems = ShoppingCartHandler.showMyCart(username);
            req.setAttribute("cartItems", cartItems);
            req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Integer cartId = (Integer) session.getAttribute("cartId");  // Retrieve cartId from session

        // If user is not logged in or cartId is missing, redirect to login page
        if (userId == null || cartId == null) {
            session.setAttribute("redirectAfterLogin", "cart");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        // Handling 'add' action
        if ("add".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                // Add item to the user's cart using cartId
                ShoppingCartHandler.addItemToCart(cartId, itemID, quantity);
                req.setAttribute("message", "Item successfully added to cart.");
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item or quantity. Please try again.");
            }
        }

        // Handling 'remove' action
        /*else if ("remove".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));

                // Remove item from the user's cart using cartId
                ShoppingCartHandler.removeItem(cartId, itemID);
                req.setAttribute("message", "Item successfully removed from cart.");
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item ID. Please try again.");
            }
        }*/

        // Redirect to cart page to show updated cart
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

}

