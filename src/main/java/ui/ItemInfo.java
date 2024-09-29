package ui;

import bo.ItemColour;
import bo.ItemType;

public class ItemInfo {
    private int Id;
    private String name;
    private ItemType type;
    private ItemColour colour;
    private int price;
    private int quantity;
    private String description;

    public ItemInfo(int Id, String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        this.Id = Id;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.price = price;
        this.quantity = quantity;
        this.description = description;

    }

    public ItemInfo(String itemName, int itemPrice, int itemId, int itemQuantity) {
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemColour getColour() {
        return colour;
    }

    public void setColour(ItemColour colour) {
        this.colour = colour;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
