package ui;

import bo.OrderHandler;
import bo.ShoppingCartHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
import ui.OrderInfo;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Retrieve the userRole from session and log it for debugging
        String userRole = (String) session.getAttribute("userRole");
        System.out.println("User role in doGet is: " + userRole);

        // Block access for users with the role "Customer"
        if ("Customer".equalsIgnoreCase(userRole)) {
            request.setAttribute("error", "You do not have permission to access this page.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return; // Stop further processing
        }

        // Retrieve all orders and set them as request attribute
        Collection<OrderInfo> allOrders = OrderHandler.getAllOrders();
        request.setAttribute("orders", allOrders);

        // Forward to sendorder.jsp to display all orders
        request.getRequestDispatcher("/WEB-INF/jsp/sendorder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Retrieve the userRole from session and log it for debugging
        String userRole = (String) session.getAttribute("userRole");
        System.out.println("User role in doPost is: " + userRole);

        // Block access for users with the role "Customer"
        if ("Customer".equalsIgnoreCase(userRole)) {
            req.setAttribute("error", "You do not have permission to perform this action.");
            req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
            return; // Stop further processing
        }

        // Proceed with order actions if the user is not a Customer
        int userId = (Integer) session.getAttribute("userId");
        int cartId = (Integer) session.getAttribute("cartId");

        // Determine which action to perform
        String action = req.getParameter("action");

        if ("placeOrder".equals(action)) {
            // Place the order
            boolean success = OrderHandler.placeOrder(userId, cartId);

            if (success) {
                req.setAttribute("message", "Order placed successfully!");
                ShoppingCartHandler.emptyCartItems(cartId); // Empty the cart after placing the order
                session.removeAttribute("cartItems"); // Clear cartItems from session
            } else {
                req.setAttribute("error", "Failed to place order.");
            }

        } else if ("sendOrder".equals(action)) {
            // Send the order (only requires the orderId now)
            String orderIdParam = req.getParameter("orderId");

            if (orderIdParam != null) {
                try {
                    int orderId = Integer.parseInt(orderIdParam);

                    // Now send the order with just the orderId
                    boolean success = OrderHandler.sendOrder(orderId);

                    if (success) {
                        req.setAttribute("message", "Order sent successfully!");
                    } else {
                        req.setAttribute("error", "Failed to send order. Not enough stock.");
                    }

                } catch (NumberFormatException e) {
                    req.setAttribute("error", "Invalid order ID. Please enter a valid number.");
                }

            } else {
                req.setAttribute("error", "Missing order ID. Cannot send order.");
            }
        }

        // Forward to the cart page after either action
        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }
}
