package bo;

import db.ShoppingCartDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

public class ShoppingCartHandler {
    public static boolean createCartForUser(int userId) {
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        return ShoppingCart.createNewCart(shoppingCart);
    }

    public static boolean addItemToCart(int cartId, int itemId, int quantity) {
        return ShoppingCart.addItem(cartId, itemId, quantity);
    }

    public static void emptyCartItems(int cartId) {
        ShoppingCart.clearCart(cartId);
    }

    /*public static boolean removeItemFromCart(int cartId, int itemId, int quantity) {

    }*/

    public static Collection<ItemInfo> showMyCart(String username) {
        return ShoppingCart.displayMyCart(username);
    }

}