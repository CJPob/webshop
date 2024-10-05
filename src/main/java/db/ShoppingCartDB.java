package db;

import bo.ItemColour;
import bo.ItemType;
import bo.ShoppingCart;
import bo.Item;
import ui.ItemInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * The ShoppingCartDB class manages database operations for shopping carts.
 * It handles creating carts, adding items, viewing cart contents, and emptying carts.
 *
 * Key methods:
 * - {@code newCart()}: Creates a new cart.
 * - {@code insertItemIntoCart()}: Adds or updates items in the cart.
 * - {@code viewShoppingCart()}: Retrieves cart contents.
 * - {@code removeItemFromCart()}: Retrieves cart contents.
 * - {@code emptyCart()}: Empties the cart.
 */
public class ShoppingCartDB extends ShoppingCart {

    protected ShoppingCartDB(int userID) {
        super(userID);
    }

    public static boolean newCart(ShoppingCart cart) {
        String query = "INSERT INTO T_SHOPPINGCART (userID, sumTotal) VALUES (?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, cart.getUserID());
            ps.setInt(2, cart.getSumTotal());

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertItemIntoCart(int cartId, int itemId, int quantity) {
        String checkQuery = "SELECT quantity FROM T_CART_ITEMS WHERE cartId = ? AND itemId = ?";
        String insertQuery = "INSERT INTO T_CART_ITEMS (cartId, itemId, quantity) VALUES (?, ?, ?)";
        String updateQuery = "UPDATE T_CART_ITEMS SET quantity = ? WHERE cartId = ? AND itemId = ?";

        try (Connection con = DBManager.getConnection();
             PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {

            checkStmt.setInt(1, cartId);
            checkStmt.setInt(2, itemId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, currentQuantity + quantity); // Increase the quantity
                    updateStmt.setInt(2, cartId);
                    updateStmt.setInt(3, itemId);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, cartId);
                    insertStmt.setInt(2, itemId);
                    insertStmt.setInt(3, quantity);
                    insertStmt.executeUpdate();
                }
            }
            updateCartTotal(cartId);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getCartIdByUserId(int userId) {
        int cartId = -1;
        try {
            Connection con = DBManager.getConnection();
            String query = "SELECT cartId FROM T_SHOPPINGCART WHERE userID = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartId = rs.getInt("cartId");
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartId;
    }

    public static Collection<ItemInfo> viewShoppingCart(String username) {
        Connection con;
        Collection<ItemInfo> itemList = new ArrayList<>();

        try {
            con = DBManager.getConnection();

            PreparedStatement stmt = con.prepareStatement(
                    "SELECT i.id, i.name, i.price, i.type, i.colour, i.description, ci.quantity " +
                            "FROM t_shoppingcart sc " +
                            "JOIN t_cart_items ci ON sc.cartID = ci.cartID " +
                            "JOIN t_item i ON ci.itemID = i.id " +
                            "JOIN t_user u ON sc.userID = u.id " +
                            "WHERE u.username = ?"
            );
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("id");
                String itemName = rs.getString("name");
                ItemType itemType = ItemType.valueOf(rs.getString("type"));
                ItemColour itemColour = ItemColour.valueOf(rs.getString("colour"));
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");
                itemList.add(new ItemInfo(itemID, itemName, itemType, itemColour, price, quantity, description));
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    public static boolean removeItemFromCart(int cartId, int itemId) {
        String query = "DELETE FROM t_cart_items WHERE cartID = ? AND itemID = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, cartId);
            ps.setInt(2, itemId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                updateCartTotal(cartId);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void emptyCart(int cartId) {
        String deleteCartItemsSQL = "DELETE FROM t_cart_items WHERE cartID = ?";
        String updateCartSumSQL = "UPDATE t_shoppingcart SET sumTotal = 0 WHERE cartID = ?";

        Connection conn = null;
        PreparedStatement deleteStmt = null;
        PreparedStatement updateStmt = null;

        try {
            conn = DBManager.getConnection();

            conn.setAutoCommit(false);

            deleteStmt = conn.prepareStatement(deleteCartItemsSQL);
            deleteStmt.setInt(1, cartId);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cart items removed successfully.");
            } else {
                System.out.println("Cart is already empty or cartID not found.");
            }

            updateStmt = conn.prepareStatement(updateCartSumSQL);
            updateStmt.setInt(1, cartId);  // Set the cartId parameter
            int updateCount = updateStmt.executeUpdate();

            if (updateCount > 0) {
                System.out.println("Cart sumTotal set to 0 successfully.");
            } else {
                System.out.println("Failed to update sumTotal or cartID not found.");
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (deleteStmt != null) deleteStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updateCartTotal(int cartId) {
        String calculateTotalSQL = "SELECT SUM(i.price * ci.quantity) AS total " +
                "FROM t_cart_items ci " +
                "JOIN t_item i ON ci.itemID = i.id " +
                "WHERE ci.cartID = ?";

        String updateSumTotalSQL = "UPDATE t_shoppingcart SET sumTotal = ? WHERE cartID = ?";

        Connection conn = null;
        PreparedStatement calculateStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            conn = DBManager.getConnection();

            calculateStmt = conn.prepareStatement(calculateTotalSQL);
            calculateStmt.setInt(1, cartId);
            rs = calculateStmt.executeQuery();

            if (rs.next()) {
                double total = rs.getDouble("total");

                updateStmt = conn.prepareStatement(updateSumTotalSQL);
                updateStmt.setDouble(1, total);
                updateStmt.setInt(2, cartId);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (calculateStmt != null) calculateStmt.close();
                if (updateStmt != null) updateStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}