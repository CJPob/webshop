package bo;

import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;


public class ItemHandler {

    // Utilizes ItemDB's methods to fetch items based on the type or conditions

    public static Collection<ItemInfo> getItemsByType(String type) {
        Collection<ItemDB> dbItems = ItemDB.searchItemsByType(ItemType.valueOf(type.toUpperCase()));
        return convertToItemInfo(dbItems);
    }

    public static Collection<ItemInfo> getItemsInStock() {
        Collection<ItemDB> dbItems = ItemDB.searchItemsInStock();
        return convertToItemInfo(dbItems);
    }

    public static Collection<ItemInfo> getAllItems() {
        Collection<ItemDB> dbItems = ItemDB.getAllItems();
        return convertToItemInfo(dbItems);
    }

    private static Collection<ItemInfo> convertToItemInfo(Collection<ItemDB> dbItems) {
        Collection<ItemInfo> items = new ArrayList<>();
        for (ItemDB item : dbItems) {
            items.add(new ItemInfo(item.getName(), item.getType(), item.getColour(), item.getPrice(), item.getQuantity(), item.getDescription()));
        }
        return items;
    }

    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        return ItemDB.createItem(name, type, colour, price, quantity, description);
    }
}