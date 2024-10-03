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

            // Call the handler method to display cart items
            Collection<ItemInfo> cartItems = ShoppingCartHandler.showMyCart(username);

            // Attach the cart items to the request so they can be displayed in the JSP
            req.setAttribute("cartItems", cartItems);

            // Forward to the JSP page to display the cart
            req.getRequestDispatcher("/WEB-INF/test/cart.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer cartId = (Integer) session.getAttribute("cartId");

        if (cartId == null) {
            request.setAttribute("error", "No cart available in session.");
            request.getRequestDispatcher("/WEB-INF/test/cart.jsp").forward(request, response);
            return;
        }

        try {
            // Retrieve itemId and quantity from request
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Add the item to the cart via ShoppingCartHandler
            boolean success = ShoppingCartHandler.addItemToCart(cartId, itemId, quantity);

            if (success) {
                request.setAttribute("message", "Item successfully added to the cart!");
            } else {
                request.setAttribute("error", "Failed to add the item to the cart.");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid item ID or quantity.");
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred while adding the item.");
            e.printStackTrace();
        }

        request.getRequestDispatcher("/WEB-INF/test/cart.jsp").forward(request, response);
    }
}

