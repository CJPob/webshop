package bo;


import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ItemHandler {

    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection<Item> c = Item.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<ItemInfo>();
        for (Iterator<Item> it = c.iterator(); it.hasNext();) {
            Item item = it.next();
            items.add(new ItemInfo(item.getName(), item.getType(), item.getColour(), item.getPrice(), item.getQuantity(), item.getDescription()));
        }
        return items;
    }

    public static boolean createItem(String name, ItemType type, ItemColour colour, int price, int quantity, String description) {
        return ItemDB.createItem(name, type, colour, price, quantity, description);
    }
}