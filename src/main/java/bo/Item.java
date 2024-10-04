package bo;

import db.ItemDB;
import java.util.Collection;

public class Item {
    private final int id;
    private String name;
    private final ItemType type;
    private final ItemColour colour;
    private int price;
    private int quantity;
    private String description;

    static public Collection all() {
        return ItemDB.findAll();
    }

    static public Collection inStock() {
        return ItemDB.findByInStock();
    }

    static public Collection byType(String type) {
        return ItemDB.findByType(type);
    }

    static public Collection byColour(String colour) {
        return ItemDB.findByColour(colour);
    }

    static public Collection byName(String name) {
        return ItemDB.findByName(name);
    }

    static public boolean createNewItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        return ItemDB.createItem(name, type, colour, price, quantity, description);
    }

    static public boolean updateQuantity(int itemId, int changeAmount) {
        return ItemDB.updateItemQuantity(itemId, changeAmount);
    }

    protected Item(int id, String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.colour = colour;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public ItemType getType() {
        return this.type;
    }

    public ItemColour getColour() {
        return this.colour;
    }

    public int getPrice() {
        return this.price;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}