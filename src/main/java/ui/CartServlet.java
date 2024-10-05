package ui;

import bo.ShoppingCartHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
        if (checkLoginAndRedirect(req, resp)) return;

        String username = (String) req.getSession().getAttribute("username");
        Collection<ItemInfo> cartItems = ShoppingCartHandler.showMyCart(username);
        req.setAttribute("cartItems", cartItems);
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (checkLoginAndRedirect(req, resp)) return;

        HttpSession session = req.getSession();
        Integer cartId = (Integer) session.getAttribute("cartId");
        String action = req.getParameter("action");

        if (cartId == null || action == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cart or action.");
            return;
        }

        try {
            switch (action) {
                case "add":
                    int itemId = Integer.parseInt(req.getParameter("itemId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    ShoppingCartHandler.addItemToCart(cartId, itemId, quantity);
                    break;
                case "remove":
                    itemId = Integer.parseInt(req.getParameter("itemId"));
                    ShoppingCartHandler.removeItemFromCart(cartId, itemId);
                    break;
                case "emptyCart":
                    ShoppingCartHandler.emptyCartItems(cartId);
                    session.removeAttribute("cartItems");
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                    return;
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid item or quantity.");
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private boolean checkLoginAndRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.jsp");
            return true;
        }
        return false;
    }
}