package ui;

import bo.Item;

public class ItemInfo {
    private int id;
    private String name;
    private Item.ItemType type;
    private Item.ItemColour colour;
    private int price;
    private int quantity;
    private String description;

    public ItemInfo(int id, String name, Item.ItemType type, Item.ItemColour colour, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Item.ItemType getType() {
        return type;
    }

    public Item.ItemColour getColour() {
        return colour;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Item.ItemType type) {
        this.type = type;
    }

    public void setColour(Item.ItemColour colour) {
        this.colour = colour;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
