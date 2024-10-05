package ui;

import bo.ItemColour;
import bo.ItemType;

import java.util.ArrayList;

/**
 * The ShoppingCartInfo class represents a user's shopping cart, containing the cart ID, user ID, and a list of items.
 */
public class ShoppingCartInfo {

    private int shoppingCartId;
    private int userID;
    private ArrayList<ItemInfo> items;

    public ShoppingCartInfo(int shoppingCartId, int userID) {
        this.shoppingCartId = shoppingCartId;
        this.userID = userID;
        this.items = new ArrayList<>();
    }

    public int getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(int shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public ArrayList<ItemInfo> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemInfo> items) {
        this.items = items;
    }

    public void addItem(ItemInfo item) {
        this.items.add(item);
    }

    public void addItem(int itemId, String itemName, ItemType itemType, ItemColour itemColour, int itemPrice, int itemQuantity, String itemDescription) {
        ItemInfo newItem = new ItemInfo(itemId, itemName, itemType, itemColour, itemPrice, itemQuantity, itemDescription);
        this.items.add(newItem);
    }
}