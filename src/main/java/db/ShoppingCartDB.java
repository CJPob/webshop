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

public class ShoppingCartDB extends ShoppingCart {

    protected ShoppingCartDB(int userID) {
        super(userID); // Call the ShoppingCart constructor
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
        String query = "INSERT INTO T_CART_ITEMS (cartId, itemId, quantity) VALUES (?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            // this should be fetched dynamically not manually fix later
            ps.setInt(1, cartId);
            ps.setInt(2, itemId);
            ps.setInt(3, quantity);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getCartIdByUserId(int userId) {
        int cartId = -1;  // Default value if no cart is found
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
            // Establish a connection to the database
            con = DBManager.getConnection();

            // SQL query to fetch the cart and its items for the specific user
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT i.id, i.name, i.price, i.type, i.colour, i.description, ci.quantity " +
                            "FROM t_shoppingcart sc " +
                            "JOIN t_cart_items ci ON sc.cartID = ci.cartID " +
                            "JOIN t_item i ON ci.itemID = i.id " +
                            "JOIN t_user u ON sc.userID = u.id " +
                            "WHERE u.username = ?"
            );
            stmt.setString(1, username);  // Set the username in the query
            ResultSet rs = stmt.executeQuery();

            // Fetch each item and add it to the item list
            while (rs.next()) {
                int itemID = rs.getInt("id");
                String itemName = rs.getString("name");
                ItemType itemType = ItemType.valueOf(rs.getString("type"));  // Using ItemType enum
                ItemColour itemColour = ItemColour.valueOf(rs.getString("colour"));  // Using ItemColour enum
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                String description = rs.getString("description");

                // Create an ItemInfo and add it to the list
                itemList.add(new ItemInfo(itemID, itemName, itemType, itemColour, price, quantity, description));
            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemList;  // Return the collection containing items
    }
}