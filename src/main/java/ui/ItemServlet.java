package ui;

import bo.ItemHandler;
import bo.ItemType;
import bo.ItemColour;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the 'type', 'colour', and 'name' parameters from the request
        String itemType = request.getParameter("type");
        String itemColour = request.getParameter("colour");
        String itemName = request.getParameter("name");

        Collection<ItemInfo> items;

        // Check if the user wants to filter by name
        if (itemName != null && !itemName.isEmpty()) {
            // If name is specified, filter by name
            items = ItemHandler.searchByName(itemName);
        } else if (itemColour != null && !itemColour.isEmpty()) {
            // If colour is specified, filter by colour
            items = ItemHandler.searchByColour(itemColour);
        } else if (itemType == null || itemType.isEmpty()) {
            // If "All Items" (empty value) is selected, fetch all items
            items = ItemHandler.searchAll();
        } else if ("IN_STOCK".equals(itemType)) {
            // Fetch only in-stock items
            items = ItemHandler.searchByInStock();
        } else {
            // Fetch items by the selected type (e.g., Surfboard, Towel, etc.)
            items = ItemHandler.searchByType(itemType);
        }

        // Set the items in the request scope for the JSP
        request.setAttribute("items", items);

        // Forward to the main JSP
        request.getRequestDispatcher("/WEB-INF/test/main.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String type = req.getParameter("type");
        String colour = req.getParameter("colour");

        boolean success = ItemHandler.createItem(name, ItemType.valueOf(type.toUpperCase()), ItemColour.valueOf(colour.toUpperCase()), price, quantity, description);

        if (success) {
            req.setAttribute("successMessage", "success!");
            req.getRequestDispatcher("additem.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Item creation failed.");
            req.getRequestDispatcher("additem.jsp").forward(req, resp);
        }
    }
}