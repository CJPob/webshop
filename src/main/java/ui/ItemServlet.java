package ui;

import bo.Item;
import bo.ItemHandler;
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
            if (type != null && !type.isEmpty()) {
                // Fetch items by type if the type parameter is provided
                items = ItemHandler.getItemsByType(type);
                request.setAttribute("items", items);
                request.getRequestDispatcher("/WEB-INF/jsp/inventory.jsp").forward(request, response);
            } else if ("all".equals(filter)) {
                // Fetch all items when requested
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            int price = Integer.parseInt(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String type = req.getParameter("type");
            String colour = req.getParameter("colour");

            boolean success = ItemHandler.createItem(
                    name,
                    Item.ItemType.valueOf(type.toUpperCase()),
                    Item.ItemColour.valueOf(colour.toUpperCase()),
                    price,
                    quantity,
                    description
            );

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/item?filter=all");
            } else {
                req.setAttribute("error", "Failed to create the item.");
                req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number format.");
            req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Invalid item type or colour.");
            req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "An error occurred: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/additem.jsp").forward(req, resp);
        }
    }
}
