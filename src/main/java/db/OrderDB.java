package db;

import bo.ItemColour;
import bo.ItemType;
import bo.Order;
import bo.Order.OrderStatus;
import ui.ItemInfo;
import ui.OrderInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The OrderDB class handles database operations for orders, including creating orders, sending orders,
 * and retrieving order details. It ensures proper transaction management, using private helper methods to
 * split tasks and improve readabilty
 *
 * Main methods:
 * - {@code createOrder(int userId, int cartId)}: Creates an order, adds items from the cart, and manages transactions.
 * - {@code sendOrder(int orderId)}: Sends the order and updates its status.
 * - {@code getAllOrders()}: Retrieves all orders from the database.
 *
 * Private helper methods handle specific tasks like inserting orders and managing the connection lifecycle.
 */
public class OrderDB extends Order {

    public OrderDB(int userId, int cartId) {
        super(userId, cartId);
    }

    public static boolean createOrder(int userId, int cartId) {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);

            int orderId = insertOrder(conn, userId);
            insertOrderItems(conn, orderId, cartId);
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(conn);
        }
    }

    public static boolean sendOrder(int orderId) {
        Connection con = null;

        try {
            con = DBManager.getConnection();
            con.setAutoCommit(false);
            Collection<ItemInfo> orderItems = getOrderItems(con, orderId);

            if (!checkStock(con, orderItems)) {
                con.rollback();
                return false;
            }
            if (!updateStock(con, orderItems)) {
                con.rollback();
                return false;
            }
            if (!markOrderAsShipped(con, orderId)) {
                con.rollback();
                return false;
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public static Collection<OrderInfo> getAllOrders() {
        Collection<OrderInfo> orders = new ArrayList<>();

        String orderQuery = "SELECT o.id, o.userID, o.status FROM t_order o";
        String itemQuery = "SELECT i.id, i.name, i.type, i.colour, i.price, oi.quantity " +
                "FROM t_order_items oi " +
                "JOIN t_item i ON oi.itemID = i.id " +
                "WHERE oi.orderID = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement orderStmt = con.prepareStatement(orderQuery)) {

            ResultSet orderRs = orderStmt.executeQuery();
            while (orderRs.next()) {
                int orderId = orderRs.getInt("id");
                int userId = orderRs.getInt("userID");
                String status = orderRs.getString("status");

                OrderInfo order = new OrderInfo(orderId, userId, status);

                try (PreparedStatement itemStmt = con.prepareStatement(itemQuery)) {
                    itemStmt.setInt(1, orderId);
                    ResultSet itemRs = itemStmt.executeQuery();

                    while (itemRs.next()) {
                        int itemId = itemRs.getInt("id");
                        String itemName = itemRs.getString("name");
                        String itemType = itemRs.getString("type");
                        String itemColour = itemRs.getString("colour");
                        int price = itemRs.getInt("price");
                        int quantity = itemRs.getInt("quantity");

                        ItemInfo item = new ItemInfo(itemId, itemName, ItemType.valueOf(itemType),
                                ItemColour.valueOf(itemColour), price, quantity, "");
                        order.addItem(item);
                    }
                    itemRs.close();
                }

                orders.add(order);
            }
            orderRs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    //private helper methods for creating order & sending an order
    private static int insertOrder(Connection conn, int userId) throws SQLException {
        String insertOrderSQL = "INSERT INTO t_order (userID, status) VALUES (?, ?)";
        try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            orderStmt.setInt(1, userId);
            orderStmt.setString(2, OrderStatus.PENDING.toString());

            int rowsAffected = orderStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet rs = orderStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return generated order ID
                } else {
                    throw new SQLException("Creating order failed, no order ID obtained.");
                }
            }
        }
    }

    private static void insertOrderItems(Connection conn, int orderId, int cartId) throws SQLException {
        String getCartItemsSQL = "SELECT itemID, quantity FROM t_cart_items WHERE cartID = ?";
        String insertOrderItemSQL = "INSERT INTO t_order_items (orderID, itemID, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement cartItemsStmt = conn.prepareStatement(getCartItemsSQL);
             PreparedStatement orderItemStmt = conn.prepareStatement(insertOrderItemSQL)) {

            cartItemsStmt.setInt(1, cartId);
            try (ResultSet cartItemsRS = cartItemsStmt.executeQuery()) {
                while (cartItemsRS.next()) {
                    int itemId = cartItemsRS.getInt("itemID");
                    int quantity = cartItemsRS.getInt("quantity");

                    orderItemStmt.setInt(1, orderId);  // Set orderID
                    orderItemStmt.setInt(2, itemId);   // Set itemID
                    orderItemStmt.setInt(3, quantity); // Set quantity
                    orderItemStmt.addBatch();          // Add to batch
                }
                orderItemStmt.executeBatch();
            }
        }
    }

    private static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // Restore auto-commit mode
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static Collection<ItemInfo> getOrderItems(Connection con, int orderId) throws SQLException {
        String getOrderItemsSQL = "SELECT i.id, i.name, i.type, i.colour, i.price, oi.quantity " +
                "FROM t_order_items oi " +
                "JOIN t_item i ON oi.itemID = i.id " +
                "WHERE oi.orderID = ?";

        Collection<ItemInfo> orderItems = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(getOrderItemsSQL)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("id");
                String itemName = rs.getString("name");
                String itemType = rs.getString("type");
                String itemColour = rs.getString("colour");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");

                orderItems.add(new ItemInfo(itemId, itemName, ItemType.valueOf(itemType),
                        ItemColour.valueOf(itemColour), price, quantity, ""));
            }

            rs.close();
        }

        return orderItems;
    }

    private static boolean checkStock(Connection con, Collection<ItemInfo> orderItems) throws SQLException {
        String checkStockQuery = "SELECT quantity FROM t_item WHERE id = ?";

        for (ItemInfo item : orderItems) {
            try (PreparedStatement checkStmt = con.prepareStatement(checkStockQuery)) {
                checkStmt.setInt(1, item.getId()); // Check the stock for each item
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int stockQuantity = rs.getInt("quantity");

                    if (stockQuantity < item.getQuantity()) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean updateStock(Connection con, Collection<ItemInfo> orderItems) throws SQLException {
        String updateStockQuery = "UPDATE t_item SET quantity = quantity - ? WHERE id = ?";

        for (ItemInfo item : orderItems) {
            try (PreparedStatement updateStmt = con.prepareStatement(updateStockQuery)) {
                updateStmt.setInt(1, item.getQuantity()); // Deduct the quantity
                updateStmt.setInt(2, item.getId());       // Specify the item ID

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean markOrderAsShipped(Connection con, int orderId) throws SQLException {
        String updateOrderStatusQuery = "UPDATE t_order SET status = ? WHERE id = ?";

        try (PreparedStatement updateOrderStmt = con.prepareStatement(updateOrderStatusQuery)) {
            updateOrderStmt.setString(1, OrderStatus.SHIPPED.toString());
            updateOrderStmt.setInt(2, orderId);

            int rowsAffected = updateOrderStmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}