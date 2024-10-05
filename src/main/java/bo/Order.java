package bo;

import db.OrderDB;
import java.util.Collection;

/**
 * The Order class represents an order with details like userId, cartId, and status.
 * It provides methods to place, send, and retrieve orders, with order statuses defined in the OrderStatus enum.
 */
public class Order {
    private int id;
    private int userId;
    private int cartId;
    private OrderStatus status;

    public static boolean placeOrder(int userId, int cartId) {
        return OrderDB.createOrder(userId, cartId);
    }

    public static boolean sendOrder(int orderId) {
        return OrderDB.sendOrder(orderId);
    }

    public static Collection getAllOrders() {
        return OrderDB.getAllOrders();
    }

    protected Order(int userId, int cartId) {
        this.userId = userId;
        this.cartId = cartId;
        this.status = OrderStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCartId() {
        return cartId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setShipped() {
        this.status = OrderStatus.SHIPPED;
    }

    public enum OrderStatus {
        PENDING("Pending"),
        SHIPPED("Shipped"),
        DELIVERED("Delivered");

        private final String status;

        OrderStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }
    }
}