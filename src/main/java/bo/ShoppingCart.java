package bo;

import db.ShoppingCartDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The shoppingcart is the model representating the items in the cart. It includes the id of the cart and the associated user and the sum price.
 */
public class ShoppingCart {

    private int cartId;
    private int userID;
    private Collection<ItemInfo> items;
    private int sumTotal;

    public static boolean createNewCart(ShoppingCart shoppingCart) {
        return ShoppingCartDB.newCart(shoppingCart);
    }

    public static boolean addItem(int cartId, int itemId, int quantity) {
        return ShoppingCartDB.insertItemIntoCart(cartId, itemId, quantity);
    }

    public static void clearCart(int cartId) {
        ShoppingCartDB.emptyCart(cartId);
    }

    public static Collection<ItemInfo> displayMyCart(String username) {
        return ShoppingCartDB.viewShoppingCart(username);
    }

    public static boolean removeItem(int cartId, int itemId) {
        return ShoppingCartDB.removeItemFromCart(cartId, itemId);
    }

    protected ShoppingCart(int userID) {
        this.userID = userID;
        this.items = new ArrayList<>();
        this.sumTotal = 0;
    }

    public int getTotal() {
        return sumTotal;
    }

    public void clearCart() {
        items.clear();
        sumTotal = 0;
    }

    public int getCartId() {
        return cartId;
    }

    public int getUserID() {
        return userID;
    }

    public int getSumTotal() {
        return sumTotal;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}