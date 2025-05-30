package db;
import bo.ItemColour;
import bo.ItemType;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

/**
 * This is the class that interacts with the actual DB and manages all the static methods called from the servlet. It provides ths standard crud operations
 * The class is used to manage the item entities.
 */

public class ItemDB extends bo.Item {

    private ItemDB(int id, String name, ItemType type, ItemColour colour, int price, int quantity, String desc) {
        super(id, name, type, colour, price, quantity, desc);
    }

    public static Collection<ItemDB> findByType(String type) {
        return searchItemsBy("type = '" + type.toUpperCase() + "'");
    }

    public static Collection<ItemDB> findByInStock() {
        return searchItemsBy("quantity > 0");
    }


    public static Collection<ItemDB> findAll() {
        return searchItemsBy("1=1");
    }

    public static ItemDB findById(int itemId) {
        Collection<ItemDB> items = searchItemsBy("id = " + itemId);
        if (!items.isEmpty()) {
            return items.iterator().next();
        } else {
            return null;
        }
    }

    private static Collection<ItemDB> searchItemsBy(String condition) {
        Vector<ItemDB> items = new Vector<>();
        String query = "SELECT * FROM T_ITEM WHERE " + condition;

        try (Connection connection = DBManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                items.add(new ItemDB(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        ItemType.valueOf(resultSet.getString("type")),
                        ItemColour.valueOf(resultSet.getString("colour")),
                        resultSet.getInt("price"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        String query = "INSERT INTO T_ITEM (name, type, colour, price, quantity, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, type.toString());
            ps.setString(3, colour.toString());
            ps.setInt(4, price);
            ps.setInt(5, quantity);
            ps.setString(6, description);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateItemQuantity(int itemId, int changeAmount) {
        String query = "UPDATE T_ITEM SET quantity = quantity + ? WHERE id = ? AND quantity + ? >= 0";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, changeAmount);
            ps.setInt(2, itemId);
            ps.setInt(3, changeAmount);

            int result = ps.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
