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

/**
 * The OrderServlet handles HTTP requests related to order management.
 * It allows users to place orders and, for authorized roles, send orders.
 * Customers can place orders, while only staff/admin can send orders.
 * The servlet forwards order-related data to the appropriate JSP views.
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String userRole = (String) session.getAttribute("userRole");

        if ("Customer".equalsIgnoreCase(userRole)) {
            request.setAttribute("error", "You do not have permission to access this page.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        Collection<OrderInfo> allOrders = OrderHandler.getAllOrders();
        request.setAttribute("orders", allOrders);

        request.getRequestDispatcher("/WEB-INF/jsp/sendorder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String userRole = (String) session.getAttribute("userRole");

        String action = req.getParameter("action");

        if ("placeOrder".equals(action)) {
            int userId = (Integer) session.getAttribute("userId");
            int cartId = (Integer) session.getAttribute("cartId");

            boolean success = OrderHandler.placeOrder(userId, cartId);

            if (success) {
                req.setAttribute("message", "Order placed successfully!");
                ShoppingCartHandler.emptyCartItems(cartId);
                session.removeAttribute("cartItems");
            } else {
                req.setAttribute("error", "Failed to place order.");
            }

        } else if ("sendOrder".equals(action)) {
            if ("Customer".equalsIgnoreCase(userRole)) {
                req.setAttribute("error", "You do not have permission to send an order.");
                req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
                return;
            }

            String orderIdParam = req.getParameter("orderId");

            if (orderIdParam != null) {
                try {
                    int orderId = Integer.parseInt(orderIdParam);

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

        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }
}
