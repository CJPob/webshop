package ui;

import bo.ShoppingCartHandler;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;


/**
 * The CartServlet handles actions related to the user's shopping cart, including viewing the cart,
 * adding items, and managing cart contents. It ensures users are logged in before modifying the cart.
 */
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
        Integer cartId = (Integer) session.getAttribute("cartId");

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemId"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                if (userId == null || cartId == null) {
                    // User not logged in, store item info in session
                    session.setAttribute("pendingItemId", itemID);
                    session.setAttribute("pendingQuantity", quantity);

                    // Set redirectAfterLogin to navigate back to the cart after login
                    session.setAttribute("redirectAfterLogin", "cart");

                    // Redirect to login page
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
                } else {
                    // User is logged in, add item to cart
                    ShoppingCartHandler.addItemToCart(cartId, itemID, quantity);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item or quantity. Please try again.");
                req.getRequestDispatcher("/WEB-INF/jsp/itemDetails.jsp").forward(req, resp);
            }
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
    //  resp.sendRedirect(req.getContextPath() + "/cart");

}



