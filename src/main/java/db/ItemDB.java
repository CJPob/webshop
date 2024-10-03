package db;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class ItemDB extends bo.Item {


    private ItemDB(int id, String name, ItemType type, ItemColour colour, int price, int quantity, String desc) {
        super(id, name, type, colour, price, quantity, desc);
    }

    //  search items by type
    public static Collection<ItemDB> searchItemsByType(ItemType type) {
        return searchItemsBy("type = '" + type.name() + "'");
    }

    // search items with quantity > 0
    public static Collection<ItemDB> searchItemsInStock() {
        return searchItemsBy("quantity > 0");
    }

    // retrieves all items
    public static Collection<ItemDB> getAllItems() {
        return searchItemsBy("1=1");  // Always true, fetches all records
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
}
