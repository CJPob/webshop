package ui;

import java.util.ArrayList;

/**
 * The OrderInfo class represents an order's information in the UI layer,
 * including order details such as orderId, userId, status, and a list of items in the order.
 * It provides methods to manage and add items to the order.
 * This model is used by staffs and admin to send and watch orders.
 */
public class OrderInfo {
    private int orderId;
    private int userId;
    private String status;
    private ArrayList<ItemInfo> items;

    // Constructor
    public OrderInfo(int orderId, int userId, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.items = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void addItem(int itemId, String itemName, bo.ItemType itemType, bo.ItemColour itemColour, int itemPrice, int itemQuantity, String itemDescription) {
        ItemInfo newItem = new ItemInfo(itemId, itemName, itemType, itemColour, itemPrice, itemQuantity, itemDescription);
        this.items.add(newItem);
    }
}
