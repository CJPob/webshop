package bo;

import db.ShoppingCartDB;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ShoppingCart {

    private int cartId;
    private int userID;
    private ArrayList<Item> items;
    private int sumTotal;

    protected ShoppingCart(int cartId, int userID) {
        this.cartId = cartId;
        this.userID = userID;
        this.items = new ArrayList<>();
        this.sumTotal = 0;
    }

    static public Collection getItems(String username){
        return ShoppingCartDB.viewShoppingCart(username);
    }

    public void addItem(Item item) {
        for (Item cartItem : items) {
            if (cartItem.getId() == item.getId()) {
                int newQuantity = cartItem.getQuantity() + item.getQuantity();
                sumTotal += item.getPrice() * item.getQuantity();
                cartItem.setQuantity(newQuantity);
                return;
            }
        }
        items.add(item);
        sumTotal += item.getPrice() * item.getQuantity();
    }

    public void addItem(int itemID, String name, String type, String colour, int price, int quantity, String description) {
        Item item = new Item(itemID, name, bo.ItemType.valueOf(type), bo.ItemColour.valueOf(colour), price, quantity, description);
        this.items.add(item);  // Add the newly created item to the list
    }

    public void removeItem(int itemId) {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.getId() == itemId) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    sumTotal -= item.getPrice(); // Adjust for quantity decrease
                } else {
                    sumTotal -= item.getPrice() * item.getQuantity(); // Adjust for complete removal
                    iterator.remove();
                }
                break;
            }
        }
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<>(items);
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
