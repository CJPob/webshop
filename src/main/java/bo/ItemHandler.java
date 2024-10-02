package bo;

import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class ItemHandler {

    public static Collection<ItemInfo> searchByType(String type) {
        Collection<Item> itemsByType = Item.byType(type);
        return convertToItemInfo(itemsByType);
    }

    public static Collection<ItemInfo> searchByInStock() {
        Collection<Item> itemInStock = Item.inStock();
        return convertToItemInfo(itemInStock);
    }

    public static Collection<ItemInfo> searchByColour(String colour) {
        Collection<Item> itemsByColour = Item.byColour(colour);
        return convertToItemInfo(itemsByColour);
    }

    public static Collection<ItemInfo> searchByName(String name) {
        Collection<Item> itemsByName = Item.byName(name);
        return convertToItemInfo(itemsByName);
    }

    public static Collection<ItemInfo> searchAll() {
        Collection<Item> all = Item.all();
        return convertToItemInfo(all);
    }

    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        return Item.createNewItem(name, type, colour, price, quantity, description);
    }

    private static Collection<ItemInfo> convertToItemInfo(Collection<Item> items) {
        Collection<ItemInfo> itemInfos = new ArrayList<>();
        for (Item item : items) {
            itemInfos.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getType(),
                    item.getColour(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getDescription()
            ));
        }
        return itemInfos;
    }

}