package bo;

import db.CartDB;
import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CartHandler {

    // Retrieve items for a user by their userId
    public static Collection<ItemInfo> getItemsForUser(int userId) {
        Collection<Cart> carts = Cart.getItemsByUserId(userId);
        ArrayList<ItemInfo> items = new ArrayList<>();

        // Iterate through the carts and add the items to the ItemInfo list
        for (Cart cart : carts) {
            ArrayList<bo.Item> itemList = cart.getItems();
            for (bo.Item item : itemList) {
                items.add(new ItemInfo(
                        item.getId(),
                        item.getName(),
                        item.getType(),
                        item.getColour(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getDescription()
                ));
            }
        }
        return items;
    }

    // Add item to the user's cart using userId
    public static boolean addItem(int userId, int itemId, int quantity) {
        try {
            return CartDB.addItemToCart(userId, itemId, quantity);  // Using userId instead of username
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }

    // Remove item from the user's cart using userId
    public static boolean removeItem(int userId, int itemId) {
        try {
            return CartDB.removeItemFromCart(userId, itemId);  // Using userId instead of username
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there's an error
        }
    }


    // Ensure the user has a cart, create one if not
    public static void ensureCartForUser(int userId) {
        if (!CartDB.cartExistsForUser(userId)) {
            CartDB.createCartForUser(userId);
        }
    }
}
