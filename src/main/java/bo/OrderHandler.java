package bo;


import ui.OrderInfo;

import java.util.Collection;

/**
 * The OrderHandler class manages order-related business logic, providing methods to place, send, and retrieve orders.
 */
public class OrderHandler {

    public static boolean placeOrder(int userId, int cartId) {
        return Order.placeOrder(userId, cartId);
    }

    public static boolean sendOrder(int orderId) {
        return Order.sendOrder(orderId);
    }

    public static Collection<OrderInfo> getAllOrders() {
        Collection<OrderInfo> orders = Order.getAllOrders();
        return orders;
    }
}