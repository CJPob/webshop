package bo;


import ui.OrderInfo;

import java.util.Collection;

public class OrderHandler {

    public static boolean placeOrder(int userId, int cartId) {
        return Order.placeOrder(userId, cartId);
    }

    public static boolean sendOrder(int orderId) {
        return Order.sendOrder(orderId);
    }

    public static Collection<OrderInfo> getAllOrders() {
        Collection<OrderInfo> orders = Order.getAllOrders();

        // Log the number of orders retrieved for debugging
        System.out.println("OrderHandler.getAllOrders() - Number of orders: " + (orders != null ? orders.size() : "null"));

        return orders;
    }
}