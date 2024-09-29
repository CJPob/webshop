package db;

import bo.ShoppingCart;
import bo.Item;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class ShoppingCartDB extends ShoppingCart {

    // Protected constructor matching the superclass constructor
    protected ShoppingCartDB(int cartID, int userID) {
        super(cartID, userID); // Call the ShoppingCart constructor
    }

    /**
     * Fetches and returns the shopping cart and its items for a specific user.
     *
     * @param username The username of the user whose cart is to be viewed.
     * @return A collection containing the ShoppingCart object with items inside.
     */
    public static Collection<ShoppingCart> viewShoppingCart(String username) {
        Connection con;
        Vector<ShoppingCart> cartList = new Vector<>();
        ShoppingCartDB cart = null;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // SQL query to fetch the cart and its items for the specific user
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT sc.cartID, sc.userID, ci.itemID, i.name, i.price, i.type, i.colour, i.description, ci.quantity " +
                            "FROM t_shoppingcart sc " +
                            "JOIN t_cart_items ci ON sc.cartID = ci.cartID " +
                            "JOIN t_item i ON ci.itemID = i.id " +
                            "JOIN t_user u ON sc.userID = u.id " +
                            "WHERE u.username = ?"
            );
            stmt.setString(1, username);  // Set the username in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cartID = rs.getInt("cartID");
                int userID = rs.getInt("userID");
                String itemName = rs.getString("name");
                int itemID = rs.getInt("itemID");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String itemType = rs.getString("type");
                String itemColour = rs.getString("colour");
                String description = rs.getString("description");

                // Initialize the cart the first time an item is found
                if (cart == null) {
                    cart = new ShoppingCartDB(cartID, userID); // Create a new ShoppingCartDB object
                }

                cart.addItem(itemID, itemName, itemType, itemColour, price, quantity, description);
            }

            // Add the cart to the collection if items were found
            if (cart != null) {
                cartList.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartList;  // Return the collection containing the cart
    }

    public static boolean addItemToCart(String username, int itemID, int quantity) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // Get the user's cartID by username
            stmt = con.prepareStatement(
                    "SELECT sc.cartID FROM t_shoppingcart sc " +
                            "JOIN t_user u ON sc.userID = u.id " +
                            "WHERE u.username = ?"
            );
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            int cartID = -1;
            if (rs.next()) {
                cartID = rs.getInt("cartID");
            }

            if (cartID != -1) {
                // Check if the item already exists in the cart
                stmt = con.prepareStatement(
                        "SELECT quantity FROM t_cart_items WHERE cartID = ? AND itemID = ?"
                );
                stmt.setInt(1, cartID);
                stmt.setInt(2, itemID);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    // Item exists, update the quantity
                    int currentQuantity = rs.getInt("quantity");
                    stmt = con.prepareStatement(
                            "UPDATE t_cart_items SET quantity = ? WHERE cartID = ? AND itemID = ?"
                    );
                    stmt.setInt(1, currentQuantity + quantity);
                    stmt.setInt(2, cartID);
                    stmt.setInt(3, itemID);
                } else {
                    // Item doesn't exist, insert a new record
                    stmt = con.prepareStatement(
                            "INSERT INTO t_cart_items (cartID, itemID, quantity) VALUES (?, ?, ?)"
                    );
                    stmt.setInt(1, cartID);
                    stmt.setInt(2, itemID);
                    stmt.setInt(3, quantity);
                }

                stmt.executeUpdate();
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }

    public static boolean removeItemFromCart(String username, int itemID) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // Get the user's cartID by username
            stmt = con.prepareStatement(
                    "SELECT sc.cartID FROM t_shoppingcart sc " +
                            "JOIN t_user u ON sc.userID = u.id " +
                            "WHERE u.username = ?"
            );
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            int cartID = -1;
            if (rs.next()) {
                cartID = rs.getInt("cartID");
            }

            if (cartID != -1) {
                // Delete the item from the cart
                stmt = con.prepareStatement(
                        "DELETE FROM t_cart_items WHERE cartID = ? AND itemID = ?"
                );
                stmt.setInt(1, cartID);
                stmt.setInt(2, itemID);

                int rowsAffected = stmt.executeUpdate();
                success = (rowsAffected > 0);  // Item successfully removed
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }


}
