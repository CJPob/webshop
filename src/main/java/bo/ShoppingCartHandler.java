package bo;

import ui.ItemInfo;

import java.util.Collection;

/**
 * The shopppingcarthandler is responsible for redirecting requests from the servlets to the static methods in the shoppingcart model
 * Methods for creating, adding, showing and removing items.
 */
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

    public static boolean removeItemFromCart(int cartId, int itemId) {
        return ShoppingCart.removeItem(cartId, itemId);
    }

    public static Collection<ItemInfo> showMyCart(String username) {
        return ShoppingCart.displayMyCart(username);
    }

}