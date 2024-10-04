package ui;

import bo.ItemColour;
import bo.ItemHandler;
import bo.ItemType;
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
        String filter = request.getParameter("filter");
        String type = request.getParameter("type");
        Collection<ItemInfo> items;

        try {
            if (type != null) {
                if (!type.isEmpty()) {
                    // Fetch items by type
                    items = ItemHandler.getItemsByType(type);
                } else {
                    // Type is empty (All selected), fetch all items
                    items = ItemHandler.getAllItems();
                }
                request.setAttribute("items", items);
                request.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(request, response);
            } else if ("all".equals(filter)) {
                // Fetch all items when filter=all
                items = ItemHandler.getAllItems();
                request.setAttribute("items", items);
                request.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(request, response);
            } else {
                // Default: Fetch items in stock
                items = ItemHandler.getItemsInStock();
                request.setAttribute("items", items);
                request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp").forward(request, response);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing request: " + e.getMessage());
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addItem".equals(action)) {
            // Handle adding a new item
            try {
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int price = Integer.parseInt(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                String type = request.getParameter("type");
                String colour = request.getParameter("colour");

                boolean success = ItemHandler.createItem(
                        name,
                        ItemType.valueOf(type.toUpperCase()),
                        ItemColour.valueOf(colour.toUpperCase()),
                        price,
                        quantity,
                        description
                );

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/item?filter=all");
                } else {
                    request.setAttribute("error", "Failed to create the item.");
                    request.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid number format.");
                request.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(request, response);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid item type or colour.");
                request.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", "An error occurred: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(request, response);
            }
        } else if ("updateQuantity".equals(action)) {
            // Handle updating item quantity
            try {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                int changeAmount = Integer.parseInt(request.getParameter("changeAmount"));

                boolean updateSuccess = ItemHandler.updateItemQuantity(itemId, changeAmount);

                if (updateSuccess) {
                    response.sendRedirect(request.getContextPath() + "/item?filter=all");
                } else {
                    request.setAttribute("error", "Failed to update item quantity.");
                    request.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid input for item ID or change amount.");
                request.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(request, response);
            }
        } else {
            // smth else
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
        }
    }
}