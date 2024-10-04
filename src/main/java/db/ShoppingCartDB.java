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
        // First, check if the item is already in the cart
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
                    int result = updateStmt.executeUpdate();
                    return result > 0;
                }
            } else {
                try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
                    insertStmt.setInt(1, cartId);
                    insertStmt.setInt(2, itemId);
                    insertStmt.setInt(3, quantity);
                    int result = insertStmt.executeUpdate();
                    return result > 0;
                }
            }
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
            con = DBManager.getConnection();

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
}