package bo;

import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The ItemHandler class manages item-related business logic, providing methods to retrieve, create, and update items.
 * It converts Item objects to ItemInfo for use in the user interface.
 */

public class ItemHandler {

    public static Collection<ItemInfo> getItemsByType(String type) {
        Collection<Item> itemsByType = Item.byType(type);
        return convertToItemInfo(itemsByType);
    }

    public static Collection<ItemInfo> getItemsInStock() {
        Collection<Item> itemInStock = Item.inStock();
        return convertToItemInfo(itemInStock);
    }

    public static Collection<ItemInfo> getAllItems() {
        Collection<Item> allItems = Item.all();
        return convertToItemInfo(allItems);
    }

    public static boolean updateItemQuantity(int itemId, int changeAmount) {
        return Item.updateQuantity(itemId, changeAmount);
    }

    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        return Item.createNewItem(name, type, colour, price, quantity, description);
    }

    public static ItemInfo getItemById(int itemId) {
        Item item = Item.getItemById(itemId);
        return convertToItemInfo(item);
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

    private static ItemInfo convertToItemInfo(Item item) {
        if (item == null) {
            return null;
        }
        return new ItemInfo(
                item.getId(),
                item.getName(),
                item.getType(),
                item.getColour(),
                item.getPrice(),
                item.getQuantity(),
                item.getDescription()
        );
    }

}
