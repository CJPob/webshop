package bo;

import db.ShoppingCartDB;
import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ShoppingCartHandler {


    public static Collection<ItemInfo> getItemsForUser(String username) {
        Collection<ShoppingCart> c = ShoppingCart.getItems(username);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Iterator<ShoppingCart> it = c.iterator(); it.hasNext();) {
            ShoppingCart cart = it.next();
            ArrayList<bo.Item> itemList = cart.getItems();
            for (int i = 0; i < itemList.size(); i++) {
                bo.Item item = itemList.get(i);
                items.add(new ItemInfo(
                        item.getId(),
                        item.getName(),
                        item.getType(),
                        item.getColour(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getDescription()));
            }
        }
        return items;
    }

    public static boolean addItem(String username, int itemId, int quantity) {
        try {
            return ShoppingCartDB.addItemToCart(username, itemId, quantity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    public static boolean removeItem(String username, int itemId) {
        try {
            return ShoppingCartDB.removeItemFromCart(username, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }
}
