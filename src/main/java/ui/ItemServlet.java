package ui;

import bo.ItemHandler;
import bo.ItemType;
import bo.ItemColour;
import ui.ItemInfo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/item")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String group = req.getParameter("group");
        Collection<ItemInfo> items;

        if (group != null && !group.isEmpty()) {
            items = ItemHandler.getItemsWithGroup(group);
        } else {
            items = ItemHandler.getItemsWithGroup("default_group");
        }

        req.setAttribute("items", items);

        req.getRequestDispatcher("/items.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        ItemType type = ItemType.valueOf(req.getParameter("type").toUpperCase());
        ItemColour colour = ItemColour.valueOf(req.getParameter("colour").toUpperCase());

        boolean success = ItemHandler.createItem(name, type, colour, price, quantity, description);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/item");
        } else {
            // If item creation fails, send an error message back to the form
            req.setAttribute("error", "Item creation failed.");
            req.getRequestDispatcher("/create_item.jsp").forward(req, resp);
        }
    }
}
