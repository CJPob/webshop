package db;
import bo.Cart;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class CartDB extends bo.Cart {
    protected CartDB(int cartId, int userID) {
        super(cartId, userID);
    }

    // Fetches and returns the shopping cart and its items for a specific user by userId
    public static Collection<Cart> viewShoppingCartByUserId(int userId) {
        Connection con = null;
        Vector<Cart> cartList = new Vector<>();
        CartDB cart = null;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // SQL query to fetch the cart and its items for the specific user
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT sc.cartID, sc.userID, ci.itemID, i.name, i.price, i.type, i.colour, i.description, ci.quantity " +
                            "FROM t_shoppingcart sc " +
                            "JOIN t_cart_items ci ON sc.cartID = ci.cartID " +
                            "JOIN t_item i ON ci.itemID = i.id " +
                            "WHERE sc.userID = ?"
            );
            stmt.setInt(1, userId);  // Set the userId in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cartID = rs.getInt("cartID");
                int itemID = rs.getInt("itemID");
                String itemName = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");
                String itemType = rs.getString("type");
                String itemColour = rs.getString("colour");
                String description = rs.getString("description");

                // Initialize the cart the first time an item is found
                if (cart == null) {
                    cart = new CartDB(cartID, userId); // Create a new ShoppingCartDB object
                }

                cart.addItem(itemID, itemName, itemType, itemColour, price, quantity, description);
            }

            // Add the cart to the collection if items were found
            if (cart != null) {
                cartList.add(cart);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cartList;  // Return the collection containing the cart
    }

    public static boolean addItemToCart(int userId, int itemID, int quantity) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // Get the user's cartID from t_shoppingcart
            stmt = con.prepareStatement(
                    "SELECT cartID FROM t_shoppingcart WHERE userID = ?"
            );
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            int cartID = -1;
            if (rs.next()) {
                cartID = rs.getInt("cartID");  // Retrieve the cartID for the logged-in user
            } else {
                // No cart exists, create a new cart for this user
                stmt = con.prepareStatement(
                        "INSERT INTO t_shoppingcart (userID) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS  // Retrieve the newly created cartID
                );
                stmt.setInt(1, userId);
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    // Get the newly created cartID
                    rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        cartID = rs.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve cartID after cart creation.");
                    }
                } else {
                    throw new SQLException("Failed to create cart for userID: " + userId);
                }
            }

            // Now add the item to the cart in t_cart_items
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
                    // Item doesn't exist in the cart, insert a new record
                    stmt = con.prepareStatement(
                            "INSERT INTO t_cart_items (cartID, itemID, quantity) VALUES (?, ?, ?)"
                    );
                    stmt.setInt(1, cartID);
                    stmt.setInt(2, itemID);
                    stmt.setInt(3, quantity);
                }

                // Execute the insert or update query
                stmt.executeUpdate();
                success = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return success;
    }



    public static boolean removeItemFromCart(int userId, int itemID) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            // Establish a connection to the database
            con = DBManager.getConnection();

            // Get the user's cartID by userID
            stmt = con.prepareStatement(
                    "SELECT sc.cartID FROM t_shoppingcart sc WHERE sc.userID = ?"
            );
            stmt.setInt(1, userId);
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
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    // Check if a cart exists for a given userId
    public static boolean cartExistsForUser(int userId) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            con = DBManager.getConnection();

            // Query to check if the cart exists
            stmt = con.prepareStatement("SELECT cartID FROM t_shoppingcart WHERE userID = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            exists = rs.next();  // If there's a result, the cart exists
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return exists;
    }

    // Create a new cart for the user
    public static void createCartForUser(int userId) {
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = DBManager.getConnection();

            // Insert a new cart for the user
            stmt = con.prepareStatement("INSERT INTO t_shoppingcart (userID) VALUES (?)");
            stmt.setInt(1, userId);
            stmt.executeUpdate();  // Create the cart

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}