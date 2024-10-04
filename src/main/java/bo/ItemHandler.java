package bo;

import ui.ItemInfo;
import java.util.ArrayList;
import java.util.Collection;

public class ItemHandler {

    // Renamed from searchByType to old getItemsByType
    public static Collection<ItemInfo> getItemsByType(String type) {
        Collection<Item> itemsByType = Item.byType(type);
        return convertToItemInfo(itemsByType);
    }

    // Renamed from searchByInStock to old  getItemsInStock
    public static Collection<ItemInfo> getItemsInStock() {
        Collection<Item> itemInStock = Item.inStock();
        return convertToItemInfo(itemInStock);
    }

    // Renamed from searchAll to old getAllItems
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
