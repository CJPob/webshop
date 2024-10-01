package ui;

import bo.ShoppingCartHandler;
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
        String username = (String) req.getSession().getAttribute("username");

        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        Collection<ItemInfo> cartItems = ShoppingCartHandler.getItemsForUser(username);
        req.setAttribute("cartItems", cartItems);

        req.getRequestDispatcher("/WEB-INF/jsp/cart-test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");

        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if ("add".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                // Add item to the cart
                ShoppingCartHandler.addItem(username, itemID, quantity);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item or quantity. Please try again.");
            }
        } else if ("remove".equals(action)) {
            try {
                int itemID = Integer.parseInt(req.getParameter("itemID"));

                ShoppingCartHandler.removeItem(username, itemID);
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid item ID. Please try again.");
            }
        }
    }
}
