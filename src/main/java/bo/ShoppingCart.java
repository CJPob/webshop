package bo;

import db.ShoppingCartDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

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

    public static Collection<ItemInfo> displayMyCart(String username) {
        // Delegate the call to ShoppingCartDB
        return ShoppingCartDB.viewShoppingCart(username);
    }

    public void addItem(ItemInfo item) {
        this.items.add(item); // Add the ItemInfo to the list
        this.sumTotal += item.getPrice() * item.getQuantity(); // Update the total price
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
