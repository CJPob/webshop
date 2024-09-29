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
}
