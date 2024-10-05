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

public class OrderDB extends Order {

    public OrderDB(int userId, int cartId) {
        super(userId, cartId);
    }

    public static boolean createOrder(int userId, int cartId) {
        String insertOrderSQL = "INSERT INTO t_order (userID, status) VALUES (?, ?)";
        String insertOrderItemSQL = "INSERT INTO t_order_items (orderID, itemID, quantity) VALUES (?, ?, ?)";
        String getCartItemsSQL = "SELECT itemID, quantity FROM t_cart_items WHERE cartID = ?";

        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement orderItemStmt = null;
        PreparedStatement cartItemsStmt = null;
        ResultSet rs = null;
        ResultSet cartItemsRS = null;

        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);

            // Step 1: Insert the order into t_order with default status 'PENDING'
            orderStmt = conn.prepareStatement(insertOrderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.setString(2, OrderStatus.PENDING.toString());

            int rowsAffected = orderStmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // Retrieve the generated orderID
            rs = orderStmt.getGeneratedKeys();
            int orderId;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no order ID obtained.");
            }

            // Step 2: Retrieve items from the cart (t_cart_items)
            cartItemsStmt = conn.prepareStatement(getCartItemsSQL);
            cartItemsStmt.setInt(1, cartId);
            cartItemsRS = cartItemsStmt.executeQuery();

            // Step 3: Insert each item from the cart into t_order_items
            orderItemStmt = conn.prepareStatement(insertOrderItemSQL);
            while (cartItemsRS.next()) {
                int itemId = cartItemsRS.getInt("itemID");
                int quantity = cartItemsRS.getInt("quantity");

                orderItemStmt.setInt(1, orderId);       // Set orderID
                orderItemStmt.setInt(2, itemId);        // Set itemID
                orderItemStmt.setInt(3, quantity);      // Set quantity
                orderItemStmt.addBatch();               // Add to batch
            }

            // Execute batch insert for order items
            orderItemStmt.executeBatch();

            // Commit the transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            // Handle any SQL exceptions
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback in case of failure
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            // Close all resources
            try {
                if (cartItemsRS != null) cartItemsRS.close();
                if (rs != null) rs.close();
                if (orderStmt != null) orderStmt.close();
                if (orderItemStmt != null) orderItemStmt.close();
                if (cartItemsStmt != null) cartItemsStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
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

                // Add the order item to the collection
                orderItems.add(new ItemInfo(itemId, itemName, ItemType.valueOf(itemType),
                        ItemColour.valueOf(itemColour), price, quantity, ""));
            }

            rs.close();
        }

        return orderItems;
    }


    public static boolean sendOrder(int orderId) {
        Connection con = null;

        try {
            // Step 1: Get database connection and disable auto-commit for transaction
            con = DBManager.getConnection();
            con.setAutoCommit(false);

            // Step 2: Retrieve the order items from the t_order_items table
            Collection<ItemInfo> orderItems = getOrderItems(con, orderId);

            // Step 3: Check if there is enough stock for all items in the order
            if (!checkStock(con, orderItems)) {
                con.rollback(); // Rollback the transaction if stock is insufficient
                return false;
            }

            // Step 4: Update the stock levels (deduct the quantity)
            if (!updateStock(con, orderItems)) {
                con.rollback(); // Rollback the transaction if stock update fails
                return false;
            }

            // Step 5: Mark the order as shipped in the database
            if (!markOrderAsShipped(con, orderId)) {
                con.rollback(); // Rollback the transaction if marking as shipped fails
                return false;
            }

            // Step 6: Commit the transaction if all steps are successful
            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // Rollback the transaction in case of an error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            // Ensure that the connection's auto-commit is restored and resources are closed
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


    private static boolean checkStock(Connection con, Collection<ItemInfo> orderItems) throws SQLException {
        String checkStockQuery = "SELECT quantity FROM t_item WHERE id = ?";

        for (ItemInfo item : orderItems) {
            try (PreparedStatement checkStmt = con.prepareStatement(checkStockQuery)) {
                checkStmt.setInt(1, item.getId()); // Check the stock for each item
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    int stockQuantity = rs.getInt("quantity");

                    if (stockQuantity < item.getQuantity()) {
                        // If the item quantity in stock is less than required, return false
                        return false;
                    }
                } else {
                    // If the item is not found in the database, return false
                    return false;
                }
            }
        }
        // If all items have enough stock, return true
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
                    // If no rows were updated, return false
                    return false;
                }
            }
        }
        // If all stock levels were updated, return true
        return true;
    }

    private static boolean markOrderAsShipped(Connection con, int orderId) throws SQLException {
        String updateOrderStatusQuery = "UPDATE t_order SET status = ? WHERE id = ?";

        try (PreparedStatement updateOrderStmt = con.prepareStatement(updateOrderStatusQuery)) {
            // Set the order status to 'SHIPPED'
            updateOrderStmt.setString(1, OrderStatus.SHIPPED.toString());
            updateOrderStmt.setInt(2, orderId); // Set the order ID

            // Execute the update
            int rowsAffected = updateOrderStmt.executeUpdate();

            // Return true if the status was successfully updated, false otherwise
            return rowsAffected > 0;
        }
    }

    public static Collection<OrderInfo> getAllOrders() {
        Collection<OrderInfo> orders = new ArrayList<>();

        // Query to retrieve orders
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

                // Create an OrderInfo object
                OrderInfo order = new OrderInfo(orderId, userId, status);

                // Fetch the items for each order
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

                        // Add the item to the order
                        ItemInfo item = new ItemInfo(itemId, itemName, ItemType.valueOf(itemType),
                                ItemColour.valueOf(itemColour), price, quantity, "");
                        order.addItem(item);
                    }
                    itemRs.close();
                }

                // Add order to list of orders
                orders.add(order);
            }
            orderRs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }



}