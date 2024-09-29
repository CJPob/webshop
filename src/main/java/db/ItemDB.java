package db;

import bo.ItemColour;
import bo.ItemType;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;

public class ItemDB extends bo.Item {

    public static Collection searchItems(String item_group) {
        Vector<ItemDB> v = new Vector<>();
        try {
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, type, colour, price, quantity, description FROM T_ITEM WHERE type = '" + item_group + "'");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                ItemType type = ItemType.valueOf(rs.getString("type"));  // Assuming ItemType is an enum
                ItemColour colour = ItemColour.valueOf(rs.getString("colour"));  // Assuming ItemColour is an enum
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");

                // Create a new ItemDB object with all the retrieved fields
                v.addElement(new ItemDB(id, name, type, colour, price, quantity, desc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }


    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        try {
            Connection con = DBManager.getConnection();  // Assuming DBManager handles the database connection
            String query = "INSERT INTO T_ITEM (name, type, colour, price, quantity, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, type.toString());
            ps.setString(3, colour.toString());
            ps.setInt(4, price);
            ps.setInt(5, quantity);
            ps.setString(6, description);

            int result = ps.executeUpdate();  // Execute the query
            ps.close();
            con.close();

            return result > 0;  // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ItemDB(int id, String name, ItemType type, ItemColour colour, int price, int quantity, String desc) {
        super(id, name, type, colour, price, quantity, desc);
    }
}
